import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Dictionary {

    public static Set<String> dict = new HashSet<>();

    static {
        try {
            Scanner scan = new Scanner(new File("dict.txt"));
            while(scan.hasNext()) {
                dict.add(scan.nextLine());
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException(ioEx);
        }
    }

    public static boolean isWord(String word) {
        return dict.contains(word.toLowerCase());
    }

    public static int wordCount(String sentence) {
        String[] words = sentence.split(Pattern.quote(" "));
        int validWordCount = 0;
        for (String word: words) {
            if (isWord(word)) {
                validWordCount += 1;
            }
        }
        return validWordCount;
    }
}
