import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrambleCipher extends Cipher {

    ArrayList<Character> key = new ArrayList<>();

    public ScrambleCipher() {
        alphabet = getAlphabet(new String[] {"lower"});
        key = getPermutation();
    }

    public ScrambleCipher(String[] names) {
        alphabet = getAlphabet(names);
        key = getPermutation();
    }

    @Override
    public String encrypt(String plaintext) {
        char[] chars = plaintext.toCharArray();
        StringBuilder ciphertext = new StringBuilder();
        for (char c : chars) {
            int idx = alphabet.indexOf(c);
            if (idx < 0) {
                ciphertext.append(c);
                continue;
            }
            char e = key.get(idx);
            ciphertext.append(e);
        }
        return ciphertext.toString();
    }

    @Override
    public String decrypt(String ciphertext) {
        char[] chars = ciphertext.toCharArray();
        StringBuilder plaintext = new StringBuilder();
        for (char c : chars) {
            int idx = key.indexOf(c);
            if (idx < 0) {
                plaintext.append(c);
                continue;
            }
            char e = alphabet.get(idx);
            plaintext.append(e);
        }
        return plaintext.toString();
    }

    Map<Character, Double> getFrequencies(String ciphertext) {
        char[] chars = ciphertext.toCharArray();
        HashMap<Character, Integer> letterCounts = new HashMap<>();
        double totalLength = 0.0;
        for (char c : chars) {
            int count = letterCounts.getOrDefault(c, 0);
            count += 1;
            letterCounts.put(c, count);
            totalLength += 1.0;
        }
        Map<Character, Double> letterFrequencies = new HashMap<>();
        for (char c : letterCounts.keySet()) {
            double freq = letterCounts.get(c) / totalLength;
            letterFrequencies.put(c, freq);
        }
        return letterFrequencies;
    }

    Map<Character, Character> getInitialMapping(Map<Character, Double> freqs, Map<Character, Character> guesses) {
        List<Map.Entry<Character, Double>> cipherList = new ArrayList<>(freqs.entrySet());
        List<Map.Entry<Character, Double>> englishList = new ArrayList<>(LetterFrequency.frequencies.entrySet());

        // Sort both lists by frequency (descending)
        cipherList.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        englishList.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        Map<Character, Character> mapping = new HashMap<>();
        for (int i = 0; i < Math.min(cipherList.size(), englishList.size()); i++) {
            mapping.put(cipherList.get(i).getKey(), englishList.get(i).getKey());
        }

        return mapping;
    }

    void swap(Map<Character, Character> mapping, char a, char b) {
        char c = mapping.get(a);
        mapping.put(a, mapping.get(b));
        mapping.put(b, c);
    }

    public void crack(String ciphertext, Map<Character, Character> guesses) {
        Map<Character, Double> freqs = getFrequencies(ciphertext);
        Map<Character, Character> mapping = getInitialMapping(freqs, guesses);
        for(char i : mapping.keySet()) {
            for (char j : mapping.keySet()) {
                StringBuilder decrypted = new StringBuilder();
                for (char c : ciphertext.toCharArray()) {
                    if (guesses.containsKey(c)) {
                        decrypted.append(guesses.get(c));
                        continue;
                    }
                    if (!mapping.containsKey(c)) {
                        decrypted.append(c);
                        continue;
                    }
                    decrypted.append(mapping.get(c));
                }
                int wordCount = Dictionary.wordCount(decrypted.toString());
                System.out.printf("%d %s%n", wordCount, decrypted);
                if (wordCount > 3) {
                    break;
                }
                swap(mapping, i, j);
            }
        }
    }

    public void printKey() {
        for (char c : key) {
            System.out.print(c);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ScrambleCipher cipher = new ScrambleCipher();
        cipher.printKey();
        //String plaintext = "The quick brown fox jumps over the lazy dog.";
        //System.out.println(plaintext);
        //String ciphertext = cipher.encrypt(plaintext);
        String ciphertext = "]31iO=3OP27\"T6vO]31iO=3OP27\"T6vO`WP8o7OV32TO\"X8WO8WP8OQ3$tO.O\"32ST6CO|39O]><iO=3OP27\"T6v";
        String secondCipher = "xtmvrtwpcprtbltxyprxtmvrtwpmbgrtbltxyprxtmvrtwpvspblmxrfbyxtmvrtwpvspbllbbjxrwaprrxtmvrtwppubqwblcpjxplxtmvrtwppubqwblxaqgpfzjxtn";
        //System.out.println(ciphertext);
        System.out.println(secondCipher);
        Map<Character, Double> freqs = cipher.getFrequencies(secondCipher);
        List<Map.Entry<Character, Double>> cipherList = new ArrayList<>(freqs.entrySet());
        cipherList.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        for (Map.Entry<Character, Double> e : cipherList) {
            System.out.printf("%c %.2f%n", e.getKey(), e.getValue());
        }

        //String decrypted = cipher.decrypt(ciphertext);
        //System.out.println(decrypted);
        Map<Character, Character> guesses = new HashMap<>();
        //guesses.put('v', '.');
        //cipher.crack(ciphertext, guesses);
    }
}
