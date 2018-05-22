package ir.ac.aut.ceit.tml;

import ir.ac.aut.ceit.tml.grammarTools.CFSimplifier;
import ir.ac.aut.ceit.tml.grammarTools.Parser;
import ir.ac.aut.ceit.tml.graph.Graph;
import ir.ac.aut.ceit.tml.gui.GUI;

public class Main {

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.runFrame();

        // If you don't want GUI comment above lines and uncomment these lines
        // (just showing the result in terminal and not file):

//        Parser parser = new Parser("test2.txt");
//        CFSimplifier cfSimplifier = new CFSimplifier();
//        cfSimplifier.simplify(parser.getGrammar());
//        System.out.println(parser.getGrammar());

    }
}
