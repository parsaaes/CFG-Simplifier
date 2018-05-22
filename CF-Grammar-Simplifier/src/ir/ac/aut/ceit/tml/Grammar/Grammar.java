package ir.ac.aut.ceit.tml.grammar;


import java.util.ArrayList;

public class Grammar {

    private String vars;
    private String terminals;
    private char startVar;
    private ArrayList<Production> productions;
    public static final char lambda = '^';

    public Grammar(String vars, String terminals, char startVar) {
        this.vars = vars;
        this.terminals = terminals;
        this.startVar = startVar;
        productions = new ArrayList<>();
    }

    public void addProduction(char leftSide, String rightSide){
        if(rightSide.contains("|")){
           int lastIndex = -1;
           int index = rightSide.indexOf("|");
           while (index >= 0){
               productions.add(new Production(leftSide,rightSide.substring(lastIndex + 1, index)));
               lastIndex = index;
               index = rightSide.indexOf("|", index + 1);
           }
            productions.add(new Production(leftSide,rightSide.substring(lastIndex + 1)));
        }
        else {
            productions.add(new Production(leftSide, rightSide));
        }
    }

    @Override
    public String toString() {
        String result =
                "V : [" + vars + "]\n" +
                "T : [" + terminals + "]\n" +
                "S : " + startVar +
                "\nP : \n";
        for (Production production : productions) {
            result += production.toString();
            result += "\n";
        }
        return result;
    }

    public String getVars() {
        return vars;
    }
    public char[] getVarsAsArr() {
        return vars.toCharArray();
    }

    public String getTerminals() {
        return terminals;
    }

    public char[] getTerminalsAsArr() {
        return terminals.toCharArray();
    }

    public char getStartVar() {
        return startVar;
    }

    public ArrayList<Production> getProductions() {
        return productions;
    }

    public void setVars(String vars) {
        this.vars = vars;
    }

    public void setProductions(ArrayList<Production> productions) {
        this.productions = productions;
    }
}
