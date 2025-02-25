import java.util.ArrayList;

public class PolyCipher extends Cipher {

    private String key;
    private char[][] square;

    public PolyCipher(String k) {
        key = k;
        alphabet = getAlphabet(new String[] {"lower"});
        square = new char[alphabet.size()][alphabet.size()];
    }

    public PolyCipher(String k, String[] names) {
        key = k;
        alphabet = getAlphabet(names);
        square = new char[alphabet.size()][alphabet.size()];
    }

    @Override
    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < plaintext.length(); i++) {
            char p = plaintext.charAt(i);
            int pIndex = alphabet.indexOf(p);
            if (pIndex == -1) {
                ciphertext.append(p); 
            } else {
                char k = key.charAt(i % keyLength);
                int kIndex = alphabet.indexOf(k);
                int cIndex = (pIndex + kIndex) % alphabet.size();
                ciphertext.append(alphabet.get(cIndex));
            }
        }
        return ciphertext.toString();
    }

    @Override
    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            int cIndex = alphabet.indexOf(c);
            if (cIndex == -1) {
                plaintext.append(c); 
            } else {
                char k = key.charAt(i % keyLength);
                int kIndex = alphabet.indexOf(k);
                int pIndex = (cIndex - kIndex + alphabet.size()) % alphabet.size();
                plaintext.append(alphabet.get(pIndex));
            }
        }
        return plaintext.toString();
    }

    public void generateSquare() {
        CaesarCipher generator = new CaesarCipher(0, alphabet);
        StringBuilder plaintext = new StringBuilder();
        for (char c : alphabet) {
            plaintext.append(c);
        }
        for (int row = 0; row < square.length; row++) {
            square[row] = generator.encrypt(plaintext.toString()).toCharArray();
            generator.setKey(row + 1);
        }
    }

    public void scrambleSquare() {
        for (int row = 0; row < square.length * 10; row++) {
            int a = Rand.randInt(square.length);
            int b = Rand.randInt(square.length);
            char[] swap = square[a];
            square[a] = square[b];
            square[b] = swap;
        }
        for (int col = 0; col < square.length * 10; col++) {
            int a = Rand.randInt(square.length);
            int b = Rand.randInt(square.length);
            for (int row = 0; row < square.length; row++) {
                char c = square[row][a];
                square[row][a] = square[row][b];
                square[row][b] = c;
            }
        }
    }

    public static String generateKey(String plaintext, ArrayList<Character> alpha) {
        StringBuilder k = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            int idx = Rand.randInt(alpha.size());
            k.append(alpha.get(idx));
        }
        return k.toString();
    }

    public void printSquare() {
        for (int row = 0; row < square.length; row++) {
            for (int col = 0; col < square.length; col++) {
                System.out.print(square[row][col]);
            }
            System.out.println();
        }
    }

    
    public char[][] getBeta() {
        return square;
    }

    public static void main(String[] args) {
        String plaintext = "Old friend, I hope this missive finds you well. Though, as you are now essentially immortal, I would guess that wellness on your part is something of a given. I realize that you are probably still angry. That is pleasant to know. Much as your perpetual health, I have come to rely upon your dissatisfaction with me. It is one of the Cosmere's great constants, I should think. Let me first assure you that the element is quite safe. I have found a good home for it. I protect its safety like I protect my own skin, you might say. You do not agree with my quest. I understand that, so much as it is possible to understand someone with whom I disagree so completely. Might I be quite frank? Before, you asked why I was so concerned. It is for the following reason: Ati was once a kind and generous man, and you saw what became of him. Rayse, on the other hand, was among the most loathsome, crafty, and dangerous individuals I had ever met. He holds the most frightening and terrible of all the Shards. Ponder on that for a time, you old reptile, and tell me if your insistence on nonintervention holds firm. Because I assure you, Rayse will not be similarly inhibited. One need only look at the aftermath of his brief visit to Sel to see proof of what I say. In case you have turned a blind eye to that disaster, know that Aona and Skai are both dead, and that which they held has been Splintered. Presumably to prevent anyone from rising up to challenge Rayse. You have accused me of arrogance in my quest. You have accused me of perpetuating my grudge against Rayse and Bavadin. Both accusations are true. Neither point makes the things I have written to you untrue. I am being chased. Your friends of the Seventeenth Shard, I suspect. I believe they're still lost, following a false trail I left for them. They'll be happier that way. I doubt they have any inkling what to do with me should they actually catch me. If anything I have said makes a glimmer of sense to you, I trust that you'll call them off. Or maybe you could astound me and ask them to do something productive for once. For I have never been dedicated to a more important purpose, and the very pillars of the sky will shake with the results of our war here. I ask again. Support me. Do not stand aside and let disaster consume more lives. I've never begged you for something before, old friend. I do so now.";
        String key =  "COSCPROJECT1";
        String[] alphas = new String[]{"lower", "upper", "numbers", "punctuation"};

        //String k = PolyCipher.generateKey(plaintext, Cipher.getAlphabet(alphas));
        PolyCipher cipher = new PolyCipher(key, alphas);
        cipher.generateSquare();
        cipher.printSquare();
        //System.out.println(key);

        // Encrypt the plaintext
        String encryptedText = cipher.encrypt(plaintext);
        System.out.println();
        System.out.println("Encrypted Text:");
        System.out.println(encryptedText);

        // Decrypt the ciphertext
        String decryptedText = cipher.decrypt(encryptedText);
        System.out.println();
        System.out.println("Decrypted Text:");
        System.out.println(decryptedText);
        
        // Accessing and printing the beta (square) matrix
        char[][] beta = cipher.getBeta();
        System.out.println();
        System.out.println("Beta Matrix:");
        for (int i = 0; i < beta.length; i++) {
            for (int j = 0; j < beta[i].length; j++) {
                System.out.print(beta[i][j] + " ");
            }
            System.out.println();
        }
    }
}
