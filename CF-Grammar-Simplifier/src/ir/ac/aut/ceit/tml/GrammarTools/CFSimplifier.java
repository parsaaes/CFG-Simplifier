package ir.ac.aut.ceit.tml.grammarTools;

import ir.ac.aut.ceit.tml.grammar.Grammar;
import ir.ac.aut.ceit.tml.grammar.Production;
import ir.ac.aut.ceit.tml.graph.Graph;

import java.util.ArrayList;
import java.util.HashSet;

public class CFSimplifier {
// TODO [check if cfg is lambda free or not]
private boolean lambdaFree = true;

    public void simplify(Grammar grammar){
        removeNullProducts(grammar);
        removeUnitProducts(grammar);
        removeUselessProducts(grammar);
        if(!lambdaFree){
            grammar.getProductions().add(new Production(grammar.getStartVar(),"^"));
        }
    }
    public void removeUnitProducts(Grammar grammar){
        ArrayList<Production> newProducts = new ArrayList<>();
        Graph dependencyGraph = new Graph(grammar.getVars());

        for (Production production : grammar.getProductions()) {
            if(!(production.getRightSide().length() == 1 && grammar.getVars().indexOf(production.getRightSide().charAt(0)) != -1)){
                    newProducts.add(production);
            }
            else if(production.getLeftSide() != production.getRightSide().charAt(0)){
                dependencyGraph.insertEdge(production.getLeftSide(),production.getRightSide().charAt(0));
            }
        }


        ArrayList<Production> newProductsPart2 = new ArrayList<>();
        for (char x : grammar.getVarsAsArr()) {
            for (char y : grammar.getVarsAsArr()) {
                if(dependencyGraph.isVisitedFrom(x,y)){
                    for (Production newProduct : newProducts) {
                        if(newProduct.getLeftSide() == y){
                            newProductsPart2.add(new Production(x, newProduct.getRightSide()));
                        }
                    }
                }
            }
        }
        for (Production production : newProductsPart2) {
            if(!newProducts.contains(production)){
                newProducts.add(production);
            }
        }
//        dependencyGraph.printAdjList();
//        System.out.println(newProducts);
        grammar.setProductions(newProducts);
    }

    public void removeNullProducts(Grammar grammar){
        // create set of nullable vars
        String vN = "";
        for (Production production : grammar.getProductions()) {
            if(production.getRightSide().equals(String.valueOf(Grammar.lambda))) {
//                if (production.getLeftSide() != grammar.getStartVar()) {
//                    vN += production.getLeftSide();
//                }
                vN += production.getLeftSide();
            }
        }

        // add A if A->A1A2..AN for All Ai in vN
        boolean added = true;
        while (added) {
            added = false;
            for (Production production : grammar.getProductions()) {
                boolean rightSideIsInVn = true;
                for (char c : production.getRightSide().toCharArray()) {
                    if (vN.indexOf(c) == -1) {
                        rightSideIsInVn = false;
                        break;
                    }
                }
                if (rightSideIsInVn && (vN.indexOf(production.getLeftSide()) == -1)) {
                    vN += production.getLeftSide();
                    added = true;
                }
            }
        }

        // find if it's lambda free
        if(vN.indexOf(grammar.getStartVar()) != -1){
            lambdaFree = false;
        }

        // System.out.println("--->  " + vN);


        // add products and replicates
        ArrayList<Production> newProducts = new ArrayList<>();
        for (Production production : grammar.getProductions()) {
            if(!production.getRightSide().equals(String.valueOf(Grammar.lambda))){
                newProducts.add(production);
                HashSet<String> combSet = createProductionComb(production.getRightSide(), vN);
                for (String s : combSet) {
                    if(!(s == null || s.length() <= 0 || s.equals(""))) {
                        // System.out.println("[" + s + "]");
                        newProducts.add(new Production(production.getLeftSide(), s));
                    }
                }
            }
        }
//        System.out.println(newProducts);
//        System.out.println(newProducts.size());
        grammar.setProductions(newProducts);
    }


    public HashSet<String> createProductionComb(String product, String vars){
        HashSet<String> resultSet = new HashSet<>();
        int count = 0;
        for (char c : product.toCharArray()) {
            if(vars.indexOf(c) != -1){
                count++;
            }
        }
        // not adding 111...111
        for(int i = 0; i < Math.pow(2,count) - 1; i++){
            String combBin = Integer.toBinaryString(i);
            if(combBin.length() < count){
                String zeroPad = "";
                for(int j = 0; j < count - combBin.length(); j++){
                    zeroPad += "0";
                }
                combBin = zeroPad + combBin;
            }

            int occurrence = -1;
            char[] productAsArr = product.toCharArray();
            String newProduct = "";
            for (int k = 0; k < productAsArr.length; k++) {
                if(vars.indexOf(productAsArr[k]) != -1){
                    occurrence++;
                    if(combBin.charAt(occurrence) == '1'){
                        newProduct += productAsArr[k];
                    }
                }
                else {
                    newProduct += productAsArr[k];
                }
            }
            resultSet.add(newProduct);
        }

        return resultSet;

    }

    public void removeUselessProducts(Grammar grammar) {
        removeUnableToGiveTerminal(grammar);
        removeUnableToGetFromStart(grammar);
    }

    public void removeUnableToGetFromStart(Grammar grammar) {
        Graph varGraph = new Graph(grammar.getVars());
        for (Production production : grammar.getProductions()) {
            char left = production.getLeftSide();
            for (char right : production.getRightSide().toCharArray()) {
                if(grammar.getVars().contains(String.valueOf(right))){
                    varGraph.insertEdge(left, right);
                }
            }
        }
        varGraph.runDFS(grammar.getStartVar());
        grammar.setVars(varGraph.getVisitedNodes());
        setNewProductsByNewVars(grammar,varGraph.getVisitedNodes());
        // System.out.println(varGraph.getVisitedNodes());
    }

    private void removeUnableToGiveTerminal(Grammar grammar){
        String v1 = "";
        boolean added = true;
        while(added){
            added = false;
            for (char v : grammar.getVarsAsArr()) {
                for (Production production : grammar.getProductions()) {
                    if(production.getLeftSide() == v &&
                            rightSideIsInV1T(production.getRightSide(), v1, grammar.getTerminals()) && !v1.contains(String.valueOf(v))){
                        v1 += v;
                        added = true;
                    }
                    // for taking care of start -> lambda(^)
//                    else if(
//                    (v == grammar.getStartVar()  && production.getLeftSide() == grammar.getStartVar() && production.getRightSide().equals("^"))
//                            && !v1.contains(String.valueOf(v))){
//                        v1 += v;
//                        added = true;
//                    }
                }
            }
        }
        grammar.setVars(v1);
        setNewProductsByNewVars(grammar, v1);
    }

    private void setNewProductsByNewVars(Grammar grammar, String newVars) {
        ArrayList<Production> newProducts = new ArrayList<>();
        for (Production production : grammar.getProductions()) {
            boolean productionIsSafe = true;
            if(newVars.contains(String.valueOf(production.getLeftSide()))){
                for (char c : production.getRightSide().replaceAll("|", "").toCharArray()) {
                    // taking care of start -> lambda(^)
                    // || c == '^'
                    if(!(newVars.contains(String.valueOf(c)) || grammar.getTerminals().contains(String.valueOf(c)) )){
                        productionIsSafe = false;
                    }
                }
            }
            else {
                productionIsSafe = false;
            }
            if(productionIsSafe){
                newProducts.add(production);
            }
        }

        grammar.setProductions(newProducts);
    }

    private boolean rightSideIsInV1T(String rightSide, String v1, String terminals) {
        for (char c : rightSide.replaceAll("|", "").toCharArray()) {
            if(!(v1.contains(String.valueOf(c)) || terminals.contains(String.valueOf(c)))){
                return false;
            }
        }
        return true;
    }

}
