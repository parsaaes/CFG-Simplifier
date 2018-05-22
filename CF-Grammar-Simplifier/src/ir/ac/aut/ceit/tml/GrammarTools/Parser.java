package ir.ac.aut.ceit.tml.grammarTools;


import ir.ac.aut.ceit.tml.grammar.Grammar;
import ir.ac.aut.ceit.tml.fileUtils.FileOps;

import java.util.ArrayList;

public class Parser {

    private Grammar grammar;

    public Parser(String path){

        // get grammar from file
        ArrayList<String> grammarString = FileOps.readLineByLine(path);

        // remove spaces
        for (int i = 0; i < grammarString.size(); i++) {
            grammarString.set(i, grammarString.get(i).replaceAll(" ",""));
        }

        // instantiate grammar object
        grammar = new Grammar(grammarString.get(0), grammarString.get(1), grammarString.get(2).charAt(0));

        // add productions
        addProductions(grammarString);
    }

    private void addProductions(ArrayList<String> grammarString) {
        String p;
        for (int i = 3; i < grammarString.size(); i++) {
            p = grammarString.get(i);
            grammar.addProduction(p.charAt(0), p.substring(p.indexOf(">") + 1));
        }
    }

    public Grammar getGrammar() {
        return grammar;
    }
}
