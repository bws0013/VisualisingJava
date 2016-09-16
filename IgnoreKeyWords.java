import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by bensmith on 9/4/16.
 *
 * This class adds the reserved words in java to a list to ensure they are not used as
 * a method later on.
 */
public class IgnoreKeyWords {

    public static HashSet<String> set = new HashSet();

    private static String words = "abstract assert boolean break byte" +
            " case catch char class const continue default do" +
            " double else enum extends false final finally float" +
            " for goto if implements import instanceof int interface" +
            " long native new null package private protected public" +
            " return short static strictfp super switch synchronized" +
            " this throw throws transient true try void volatile while";

    public static void main(String[] args) {

        loadUpKeywords();

        HashSet<String> ret = getSet();

        System.out.println(ret.contains("long"));
        System.out.println(ret.size());

    }


    public static void loadUpKeywords() {
        String[] toAdd = words.split(" ");
        for(int i = 0; i < toAdd.length; i++) {
            set.add(toAdd[i]);
        }

    }

    public static HashSet<String> getSet() {
        return set;
    }

    public static boolean isWordReserved(String word) {
        return set.contains(word);
    }



}
