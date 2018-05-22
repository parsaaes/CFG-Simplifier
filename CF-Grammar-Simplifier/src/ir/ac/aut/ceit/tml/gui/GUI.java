package ir.ac.aut.ceit.tml.gui;


import ir.ac.aut.ceit.tml.fileUtils.FileOps;
import ir.ac.aut.ceit.tml.grammar.Grammar;
import ir.ac.aut.ceit.tml.grammarTools.CFSimplifier;
import ir.ac.aut.ceit.tml.grammarTools.Parser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GUI {
    private boolean fileOpened;
    private File grammarFile;
    private Parser parser;
    private JFrame jFrame;

    public GUI(){
    }

    public void runFrame(){
        jFrame = new JFrame();
        jFrame.setSize(650, 720);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridLayout(2,2,10,10));
        jFrame.setTitle("CFG Simplifier");

        JPanel fileChoosePanel = new JPanel();
        fileChoosePanel.setLayout(new GridLayout(10,1));
        fileChoosePanel.add(new JLabel("Choose a CFG file and press Simplify:"));
        fileChoosePanel.add(new JLabel("<html>Attention! The file must be in the fallowing form: (some example files are available)</html>"));
        fileChoosePanel.add(new JLabel("<S> <V1> <V2> ..."));
        fileChoosePanel.add(new JLabel("<T1> <T2> ..."));
        fileChoosePanel.add(new JLabel("<S>"));
        fileChoosePanel.add(new JLabel("<V1 -> T1>"));
        fileChoosePanel.add(new JLabel("..."));
        fileChoosePanel.add(new JLabel("<html><b>Lambda notation is ^</b></html>"));
        JButton openBtn = new JButton("Open");
        JButton simplifyBtn = new JButton("Simplify");
        fileChoosePanel.add(openBtn);
        fileChoosePanel.add(simplifyBtn);


        fileChoosePanel.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right
        jFrame.add(fileChoosePanel);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.CYAN);
        infoPanel.setLayout(new BorderLayout());
        JLabel infoLabel = new JLabel("Please load a CFG");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        infoPanel.add(infoLabel,BorderLayout.CENTER);
        jFrame.add(infoPanel);

        JTextArea originalText = new JTextArea();
        originalText.setEditable(false);
        JTextArea simplifiedText = new JTextArea();
        simplifiedText.setEditable(false);
        JPanel originalPanel = new JPanel();
        JPanel simplifiedPanel = new JPanel();
        JLabel originalLabel = new JLabel("  Original CFG:");
        JLabel simplifiedLabel = new JLabel("  Simplified CFG:");
        originalPanel.setLayout(new BorderLayout());
        simplifiedPanel.setLayout(new BorderLayout());
        originalPanel.add(originalLabel,BorderLayout.NORTH);
        originalPanel.add(originalText);
        simplifiedPanel.add(simplifiedLabel,BorderLayout.NORTH);
        simplifiedPanel.add(simplifiedText);
        jFrame.add(originalPanel);
        jFrame.add(simplifiedPanel);
        //jFrame.pack();
        jFrame.setVisible(true);

        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simplifiedText.setText("");
                fileOpened = false;
                infoPanel.removeAll();
                infoPanel.add(infoLabel,BorderLayout.CENTER);
                if(e.getSource() == openBtn){
                    JFileChooser fc = new JFileChooser();
                    int i = fc.showOpenDialog(jFrame);
                    if(i==JFileChooser.APPROVE_OPTION){
                        grammarFile = fc.getSelectedFile();
                        String filepath = grammarFile.getPath();
                        parser = new Parser(filepath);
                        originalText.setText(parser.getGrammar().toString());
                        infoLabel.setText("Loaded.");
                        fileOpened = true;
                    }
                }
            }
        });
        simplifyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileOpened == false){
                    JOptionPane.showMessageDialog(null,"Select a valid CFG file first.");
                }
                else {
                    String savedInfo = "Simplified.<br>1- removed null products.<br> 2- removed unit products.<br> 3- removed useless products.";
                    CFSimplifier cfSimplifier = new CFSimplifier();
                    cfSimplifier.simplify(parser.getGrammar());
                    simplifiedText.setText(parser.getGrammar().toString());
                    infoLabel.setText("<html>"+ savedInfo +"</html>");
                    String saveFileName = grammarFile.getName().substring(0,  grammarFile.getName().length()-4) + "-Simplified.txt";
                    File save = new File(grammarFile.getParent() + "/" + saveFileName);
                    Grammar simplifiedGrammar = parser.getGrammar();
                    FileOps.write(simplifiedGrammar.getAsString(),saveFileName);
                    JPanel savedAddressPanel = new JPanel();
                    savedAddressPanel.setLayout(new BorderLayout());
                    savedAddressPanel.setBackground(Color.GREEN);
                    JLabel savedLabel = new JLabel("<html><b>saved at</b> " + saveFileName + "</html>");
                    savedLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                    savedAddressPanel.add(savedLabel);
                    infoPanel.add(savedAddressPanel,BorderLayout.SOUTH);
                }
            }
        });
    }
}
