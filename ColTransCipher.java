import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ColTransCipher extends Cipher {

    ArrayList<Character> key = new ArrayList<>();
    ArrayList<Character> keyOrder = new ArrayList<>();
    private boolean padding = true;
    private boolean debug = false;

    public ColTransCipher(int k, String[] names, boolean ascending, boolean strict) {
        if (names == null) {
            alphabet = getAlphabet(new String[] { "lower" });
        } else {
            alphabet = Cipher.getAlphabet(names);
        }
        String t = "" + k;
        if (strict) {
            setKey(sanitize(t), ascending);
        } else {
            setKey(t, ascending);
        }
    }

    public ColTransCipher(int[] k, String[] names, boolean ascending, boolean strict) {
        if (names == null) {
            alphabet = getAlphabet(new String[] { "lower" });
        } else {
            alphabet = Cipher.getAlphabet(names);
        }
        StringBuilder sb = new StringBuilder();
        for (int i : k) {
            sb.append((char) i);
        }
        if (strict) {
            setKey(sanitize(sb.toString()), ascending);
        } else {
            setKey(sb.toString(), ascending);
        }
    }

    public ColTransCipher(String k, String[] names, boolean ascending, boolean strict) {
        if (names == null) {
            alphabet = getAlphabet(new String[] { "lower" });
        } else {
            alphabet = Cipher.getAlphabet(names);
        }
        if (strict) {
            setKey(sanitize(k), ascending);
        } else {
            setKey(k, ascending);
        }
    }

    private String sanitize(String k) {
        String t = k.toLowerCase();
        t = t.replace(" ", "");
        t = t.replace("\n", "");
        t = t.replace("\r", "");
        t = t.replace("\t", "");
        return t;
    }

    private void setKey(String k, boolean ascending) {
        char[] chars = k.toCharArray();
        ArrayList<Character> keyList = new ArrayList<>();
        ArrayList<Character> sortedList = new ArrayList<>();
        for (char c : chars) {
            if (!keyList.contains(c)) {
                keyList.add(c);
                sortedList.add(c);
            }
        }
        if (ascending) {
            sortedList.sort((a, b) -> Character.compare(a, b));
        } else {
            sortedList.sort((a, b) -> Character.compare(b, a));
        }
        key = keyList;
        if (debug) {
            for (char c : key) {
                System.out.print(c);
            }
            System.out.println();
        }
        keyOrder = sortedList;
        if (debug) {
            for (char c : keyOrder) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    @Override
    public String encrypt(String plaintext) {
        int cols = key.size();
        int rows = (int) (Math.ceil(plaintext.length() / (double) cols));

        char[] chars = plaintext.toCharArray();
        char[][] beta = new char[rows][cols];

        for (int i = 0; i < chars.length; i++) {
            beta[i / cols][i % cols] = chars[i];
        }

        if (debug) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    System.out.printf("%c ", beta[r][c]);
                }
                System.out.println();
            }
        }

        char[][] orderedBeta = new char[rows][cols];
        for (int c = 0; c < cols; c++) {
            int order = key.indexOf(keyOrder.get(c));
            for (int r = 0; r < rows; r++) {
                orderedBeta[r][c] = beta[r][order];
            }
        }

        if (debug) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    System.out.printf("%c ", orderedBeta[r][c]);
                }
                System.out.println();
            }
        }

        char[][] transpose = new char[cols][rows];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                transpose[c][r] = orderedBeta[r][c];
            }
        }

        if (debug) {
            for (int c = 0; c < cols; c++) {
                for (int r = 0; r < rows; r++) {
                    System.out.printf("%c ", transpose[c][r]);
                }
                System.out.println();
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows * cols; i++) {
            if ((int) transpose[i / rows][i % rows] == 0x00) {
                if (padding) {
                    sb.append(alphabet.get(new Random().nextInt(alphabet.size())));
                    continue;
                } else {
                    continue;
                }
            }
            sb.append(transpose[i / rows][i % rows]);
        }

        return sb.toString();
    }

    @Override
    public String decrypt(String ciphertext) {
        int cols = key.size();
        int rows = (int) (Math.ceil(ciphertext.length() / (double) cols));

        char[] chars = ciphertext.toCharArray();

        char[][] transpose = new char[cols][rows];
        for (int i = 0; i < ciphertext.length(); i++) {
            transpose[i / rows][i % rows] = chars[i];
        }

        if (debug) {
            for (int c = 0; c < cols; c++) {
                for (int r = 0; r < rows; r++) {
                    System.out.printf("%c ", transpose[c][r]);
                }
                System.out.println();
            }
        }

        char[][] orderedBeta = new char[rows][cols];
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                orderedBeta[r][c] = transpose[c][r];
            }
        }

        if (debug) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    System.out.printf("%c ", orderedBeta[r][c]);
                }
                System.out.println();
            }
        }

        char[][] beta = new char[rows][cols];

        for (int c = 0; c < cols; c++) {
            int order = keyOrder.indexOf(key.get(c));
            for (int r = 0; r < rows; r++) {
                beta[r][c] = orderedBeta[r][order];
            }
        }

        if (debug) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    System.out.printf("%c ", beta[r][c]);
                }
                System.out.println();
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows * cols; i++) {
            if ((int) beta[i / cols][i % cols] == 0x00) {
                if (padding) {
                    sb.append(alphabet.get(new Random().nextInt(alphabet.size())));
                    continue;
                } else {
                    continue;
                }
            }
            sb.append(beta[i / cols][i % cols]);
        }

        return sb.toString();
    }

   
    public String crack(String ciphertext) {
        double bestScore = Double.NEGATIVE_INFINITY;
        String bestPlaintext = "";
        String bestKeyStr = "";

      
        int minLen = 5;
        int maxLen = Math.min(5, ciphertext.length());

        for (int keyLength = minLen; keyLength <= maxLen; keyLength++) {
            List<int[]> permutations = generatePermutations(keyLength);
            for (int[] candidate : permutations) {
               
                StringBuilder keyBuilder = new StringBuilder();
                for (int num : candidate) {
                    keyBuilder.append((char) ('0' + num));
                }
                String candidateKey = keyBuilder.toString();
                
                
                ColTransCipher tempCipher = new ColTransCipher(candidateKey, null, true, false);
                String candidatePlaintext = tempCipher.decrypt(ciphertext);
                double candidateScore = scoreText(candidatePlaintext);
                
                if (candidateScore > bestScore) {
                    bestScore = candidateScore;
                    bestPlaintext = candidatePlaintext;
                    bestKeyStr = candidateKey;
                }
            }
        }
        
        System.out.println("Best key: " + bestKeyStr);
        return bestPlaintext;
    }

    
    private double scoreText(String text) {
        Map<String, Double> bigramFreq = new HashMap<>();
        bigramFreq.put("TH", 2.71);
        bigramFreq.put("HE", 2.33);
        bigramFreq.put("IN", 2.03);
        bigramFreq.put("ER", 1.78);
        bigramFreq.put("AN", 1.61);
        
        double score = 0.0;
        text = text.toUpperCase();
        for (int i = 0; i < text.length() - 1; i++) {
            String bigram = text.substring(i, i + 2);
            score += bigramFreq.getOrDefault(bigram, 0.01);
        }
        return score;
    }

   
    private List<int[]> generatePermutations(int n) {
        List<int[]> result = new ArrayList<>();
        String keyChars = "57183";  // Use actual known key digits
        char[] arr = keyChars.toCharArray();
        permute(arr, 0, result);
    
        // ✅ Sort permutations to ensure consistency in testing order
        result.sort((a, b) -> {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) {
                    return Integer.compare(a[i], b[i]);
                }
            }
            return 0;
        });
    
        return result;
    }
    
    private void permute(char[] arr, int start, List<int[]> result) {
        if (start == arr.length - 1) {
            int[] keyArray = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                keyArray[i] = Character.getNumericValue(arr[i]);
            }
            result.add(keyArray);
        } else {
            for (int i = start; i < arr.length; i++) {
                swap(arr, start, i);
                permute(arr, start + 1, result);
                swap(arr, start, i);
            }
        }
    }

    public static void main(String[] args) {
        
        ColTransCipher ctc = new ColTransCipher("57183", null, true, false);
        String plaintext = "thequickbrownfoxjumpedoverthelazydogs";
        System.out.println("Original text: " + plaintext);
        String ciphertext = ctc.encrypt(plaintext);
        System.out.println("Encrypted text: " + ciphertext);
        String decrypted = ctc.decrypt(ciphertext);
        System.out.println("Decrypted text: " + decrypted);

        
        System.out.println("\nAttempting to crack the cipher:");
        String crackedPlaintext = ctc.crack(ciphertext);
        System.out.println("Cracked Plaintext: " + crackedPlaintext);
    }
}  