/**
 * Created by bensmith on 9/13/16.
 */
public class ConvertToUML {

    public static void main(String[] args) {

        String s = getUML("Hello there world");
        System.out.println(s);

    }

    /*
        Creates text that can be used with yuml. At the moment it needs to be
        copied and pasted in but that is being changed.
    */
    public static String getUML(String sentence) {
        String[] words = sentence.split(" ");
        if(words.length < 1) {
            return "";
        }
        String retWord = "";
        String tempWord = "[" + words[0] + "]";
        for(int i = 1; i < words.length; i++) {
            retWord += tempWord + "<-[" + words[i] + "]\n";
            tempWord = "[" + words[i] + "]";
        }

        return retWord;
    }


}

