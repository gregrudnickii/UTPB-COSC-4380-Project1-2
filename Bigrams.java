import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bigrams {

    public static Map<String, Double> frequencies = new HashMap<>();

    static {
        try {
            Scanner scan = new Scanner(new File("bigrams.txt"));
            while (scan.hasNext()) {
                String line = scan.nextLine();
                String bigram = line.substring(0, 2);
                String freq = line.substring(2).strip();
                Double frequency = Double.parseDouble(freq);
                frequencies.put(bigram, frequency);
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException(ioEx);
        }
    }
}
