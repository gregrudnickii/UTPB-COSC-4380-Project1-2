import java.util.HashMap;
import java.util.Map;

public class LetterFrequency {
    public static Map<Character, Double> frequencies = new HashMap<>();

    static {
        frequencies.put(' ', 0.2);
        frequencies.put('a', 0.082);
        frequencies.put('b', 0.015);
        frequencies.put('c', 0.028);
        frequencies.put('d', 0.043);
        frequencies.put('e', 0.127);
        frequencies.put('f', 0.022);
        frequencies.put('g', 0.020);
        frequencies.put('h', 0.061);
        frequencies.put('i', 0.070);
        frequencies.put('j', 0.0015);
        frequencies.put('k', 0.0077);
        frequencies.put('l', 0.040);
        frequencies.put('m', 0.024);
        frequencies.put('n', 0.067);
        frequencies.put('o', 0.075);
        frequencies.put('p', 0.019);
        frequencies.put('q', 0.0095);
        frequencies.put('r', 0.060);
        frequencies.put('s', 0.063);
        frequencies.put('t', 0.091);
        frequencies.put('u', 0.028);
        frequencies.put('v', 0.0098);
        frequencies.put('w', 0.024);
        frequencies.put('x', 0.0015);
        frequencies.put('y', 0.020);
        frequencies.put('z', 0.00074);
        frequencies.put('.', 0.0);
        frequencies.put('\'', 0.0);
    }
}
