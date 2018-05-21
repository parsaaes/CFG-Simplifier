package ir.ac.aut.ceit.tml.GrammarTools;

import ir.ac.aut.ceit.tml.Grammar.Grammar;
import ir.ac.aut.ceit.tml.Grammar.Production;
import ir.ac.aut.ceit.tml.Graph.Graph;

import java.util.ArrayList;

public class CFSimplifier {
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
        setNewProducts(grammar,varGraph.getVisitedNodes());
        // System.out.println(varGraph.getVisitedNodes());
    }

    private void removeUnableToGiveTerminal(Grammar grammar){
        String v1 = "";
        // v1 += grammar.getStartVar();
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
                    else if(
                    (v == grammar.getStartVar()  && production.getLeftSide() == grammar.getStartVar() && production.getRightSide().equals("^"))
                            && !v1.contains(String.valueOf(v))){
                        v1 += v;
                        added = true;
                    }
                }
            }
        }
        grammar.setVars(v1);
        setNewProducts(grammar, v1);
    }

    private void setNewProducts(Grammar grammar, String newVars) {
        ArrayList<Production> newProducts = new ArrayList<>();
        for (Production production : grammar.getProductions()) {
            boolean productionIsSafe = true;
            if(newVars.contains(String.valueOf(production.getLeftSide()))){
                for (char c : production.getRightSide().replaceAll("|", "").toCharArray()) {
                    // taking care of start -> lambda(^)
                    if(!(newVars.contains(String.valueOf(c)) || grammar.getTerminals().contains(String.valueOf(c)) || c == '^')){
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
