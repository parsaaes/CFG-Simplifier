package ir.ac.aut.ceit.tml;

import ir.ac.aut.ceit.tml.fileUtils.FileOps;
import ir.ac.aut.ceit.tml.grammarTools.CFSimplifier;
import ir.ac.aut.ceit.tml.grammarTools.Parser;
import ir.ac.aut.ceit.tml.graph.Graph;
import ir.ac.aut.ceit.tml.gui.GUI;

public class Main {

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.runFrame();

        // If you don't want GUI, comment above lines and uncomment these lines

//        String path = System.getProperty("user.dir") + "/" + "test6.txt";
//        Parser parser = new Parser(path);
//        CFSimplifier cfSimplifier = new CFSimplifier();
//        cfSimplifier.simplify(parser.getGrammar());
//        System.out.println(parser.getGrammar());
//        FileOps.write(parser.getGrammar().toString(),path.substring(0,path.length()-4) + "-Simplified.txt");


    }
}
