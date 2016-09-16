/**
 * Created by bensmith on 9/13/16.
 */

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Pilot {

    public static String mainClassName = "NoClassGiven";
    public static List<String> subClassNames = new ArrayList<String>();
    public static List<String> importNames = new ArrayList<String>();
    public static HashSet<String> methodWords = new HashSet();

    /*
        main in Pilot is being used as a testing area at the moment.
    */

    public static void main(String[] args) {
        IgnoreKeyWords IKW = new IgnoreKeyWords();
        ReadJavaFile RJF = new ReadJavaFile();

        IKW.loadUpKeywords();

        RJF.readInput();
        List<String> wordList = RJF.getWordList();

        clearComments(wordList);

        importNames = getImports(wordList);
        subClassNames = getClassName(wordList);
        mainClassName = subClassNames.get(0);
        methodWords = getMethodWords(wordList);

        for(String s : importNames) {
            System.out.println(s);
        }

        for(String s : subClassNames) {
            System.out.println(s);
        }

        //printFoundWords(wordList);

        for(String s : methodWords) {
            System.out.println(s);
        }
    }

    /*
        clearComments takes in the text of the java program and removes all of the comments before
        any searching for method and class names occurs. This prevents any kind of
        issues from someone commenting out class/method info.

        ex1: // print("Hello world")
        ex2: /*
                public class hello {

                }
             */
    /*
        This comment is just to fix the spacing from having a close comment in my example.

        Limitations: I am assuming that comments are separated from code by at least 1 space.
        The following example would not be picked up as a comment: int c = 2//comment
        The following example would be picked up as a comment: int c = 2 //comment

        Note: This method should be run before before any method that looks for any
        methods/classes/imports/etc.
    */
    public static void clearComments(List<String> wordList) {

        for(int i = 0; i < wordList.size(); i++) {
            String sentence = wordList.get(i);
            String[] words = sentence.split(" ");

            for(int j = 0; j < words.length; j++) {
                if (words[j].startsWith("//")) {
                    for(int k = j; k < words.length; k++) {
                        words[k] = "";
                    }
                    String temp = Arrays.toString(words);
                    wordList.set(i, temp);
                    break;
                }
                if (words[j].startsWith("/*")) {
                    for(int k = i; k < wordList.size(); k++) {
                        if(!(wordList.get(k).contains("*/"))) {
                            wordList.set(k, "");
                        } else {
                            wordList.set(k, "");
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    /*
        Searches the lines of the program for import statements.
        The method assumes that all imports are stated prior to the first instance of
        a class. If this is violated the results may not be accurate. Once the first
        instance of a class is found, the search for imports ends.
    */
    public static List<String> getImports(List<String> wordList) {
        List<String> importNamesTemp = new ArrayList<String>();

        boolean importsDone = false;
        for(int i = 0; i < wordList.size(); i++) {
            String sentence = wordList.get(i);
            String[] words = sentence.split(" ");

            for(int j = 0; j < words.length; j++) {
                if(words[j].equals("import")) {
                    importNamesTemp.add(words[j+1]);
                    break;
                } else if(words[j].equals("class")) {
                   importsDone = true;
                    break;
                }
            }

            if(importsDone == true) {
                break;
            }
        }

        return importNamesTemp;
    }

    /*
        Searches the lines of the java program for any classes.
        These will be used later in determining a classes relationship to another class.
    */
    public static List<String> getClassName(List<String> wordList) {
        List<String> subClassNamesTemp = new ArrayList<String>();

        for(int i = 0; i < wordList.size(); i++) {
            String sentence = wordList.get(i);
            String[] words = sentence.split(" ");

            for(int j = 0; j < words.length; j++) {
                if(words[j].equals("class")) {
                    subClassNamesTemp.add(words[j+1]);
                    break;
                }
            }
        }

        return subClassNamesTemp;
    }

    /*
        Looks for potential method calls adds them to a set along with the class they were
        called in. If a method calls a method from another class this is reflected as follows:
        c0 c1.m1
        where c0 is the current class and c1 is the class we are calling m1 from.
    */
    public static HashSet<String> getMethodWords(List<String> wordList) {
        HashSet<String> methodWordsTemp = new HashSet();

        IgnoreKeyWords IKW = new IgnoreKeyWords();

        IKW.loadUpKeywords();

        for(int i = 0; i < wordList.size(); i++) {
            String sentence = wordList.get(i);

            String word = "";
            for(int j = 0; j < sentence.length(); j++) {

                if(sentence.charAt(j) == '(') {
                    if(IKW.isWordReserved(word)) {
                        //System.out.println(word);
                    } else if (sentence.charAt(j) == ')') {
                        word = "";
                    } else {
                        //System.out.println(className + ": " + word);
                        methodWordsTemp.add(mainClassName + " " + word);
                        word = "";
                    }
                } else if(sentence.charAt(j) == ' ') {
                    word = "";
                } else {
                    word += sentence.charAt(j);
                }
            }

        }

        return methodWordsTemp;
    }



}
