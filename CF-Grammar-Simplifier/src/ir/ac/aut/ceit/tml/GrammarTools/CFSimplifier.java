package ir.ac.aut.ceit.tml.GrammarTools;

import ir.ac.aut.ceit.tml.Grammar.Grammar;
import ir.ac.aut.ceit.tml.Grammar.Production;

import java.util.ArrayList;

public class CFSimplifier {
    public void removeUselessProducts(Grammar grammar){
        String v1 = "";
        // v1 += grammar.getStartVar();
        boolean added = true;
        while(added){
            added = false;
            for (char v : grammar.getVarsAsArr()) {
                for (Production production : grammar.getProductions()) {
                    if(production.getLeftSide() == v && rightSideIsInV1T(production.getRightSide(), v1, grammar.getTerminals()) && !v1.contains(String.valueOf(v))){
                        v1 += v;
                        added = true;
                    }
                }
            }
        }
        System.out.println("out");

        ArrayList<Production> newProducts = new ArrayList<>();
        for (Production production : grammar.getProductions()) {
            boolean productionIsSafe = true;
            if(v1.contains(String.valueOf(production.getLeftSide()))){
                for (char c : production.getRightSide().replaceAll("|", "").toCharArray()) {
                    if(!(v1.contains(String.valueOf(c)) || grammar.getTerminals().contains(String.valueOf(c)))){
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
