package ir.ac.aut.ceit.tml;

import ir.ac.aut.ceit.tml.grammarTools.CFSimplifier;
import ir.ac.aut.ceit.tml.grammarTools.Parser;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser("test3.txt");
        CFSimplifier cfSimplifier = new CFSimplifier();
        //cfSimplifier.removeUselessProducts(parser.getGrammar());
      //  System.out.println(parser.getGrammar());
        //  cfSimplifier.removeUnableToGetFromStart(parser.getGrammar());
        cfSimplifier.removeNullProducts(parser.getGrammar());
        System.out.println(parser.getGrammar());
    }
}
