package ir.ac.aut.ceit.tml;

import ir.ac.aut.ceit.tml.GrammarTools.CFSimplifier;
import ir.ac.aut.ceit.tml.GrammarTools.Parser;
import ir.ac.aut.ceit.tml.Graph.Graph;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser("test.txt");
        CFSimplifier cfSimplifier = new CFSimplifier();
        cfSimplifier.removeUselessProducts(parser.getGrammar());
         System.out.println(parser.getGrammar());
      //  cfSimplifier.removeUnableToGetFromStart(parser.getGrammar());

    }
}
