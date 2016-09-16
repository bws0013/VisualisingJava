/**
 * Created by bensmith on 9/4/16.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;


/*
The purpose of this class is to read in files that will later be evaluated by a
different (evaluation) class.
*/
public class ReadJavaFile {

    public static List<String> wordList = new ArrayList<String>();

    public static void main(String[] args) {





    }

    /*
        Read in a file line by line to evaluate later.
        TODO: allow for whole directory access
    */
    public static void readInput() {
        // Filename needs to be the complete class path to the files to read

        String fileName = "/Users/bensmith/Documents/Visual_Test_3/src/Stuff_To_Read/hw.java";

        // Read in a file line by line
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();

            while(line != null) {
                wordList.add(line);
                line = br.readLine();
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println("No File");
        } catch (IOException ioe) {
            System.out.println("IOE");
        }

        //System.out.println(fileInfo);
    }

    /*
        List files in a given directory. This will be used by readInput to read
        files in every directory.
    */

    public static void listFiles(String dirName) {
        File folder = new File(dirName);
        File[] list = folder.listFiles();

        for(int i = 0; i < list.length; i++) {
            System.out.println(list[i].getName());
        }
    }

    /*
        Getter for calling from other classes.
    */
    public static List<String> getWordList() {
        return wordList;
    }



}
