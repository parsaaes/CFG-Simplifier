package ir.ac.aut.ceit.tml.fileUtils;

import java.io.*;
import java.util.ArrayList;

public class FileOps {

    public static ArrayList<String> readLineByLine(String path){
        ArrayList<String> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String s = "";
            while ((s = br.readLine()) != null){
                result.add(s);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
