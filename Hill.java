import java.util.Scanner;

public class HillCipher {

    // Encrypt the plaintext
    public static String encrypt(String text, int[][] key) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");

        // Add X if length is odd
        if (text.length() % 2 != 0) {
            text += "X";
        }

        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            int x1 = text.charAt(i) - 'A';
            int x2 = text.charAt(i + 1) - 'A';

            int y1 = (key[0][0] * x1 + key[0][1] * x2) % 26;
            int y2 = (key[1][0] * x1 + key[1][1] * x2) % 26;

            cipher.append((char) (y1 + 'A'));
            cipher.append((char) (y2 + 'A'));
        }

        return cipher.toString();
    }

    // Find modular inverse
    static int modInverse(int a, int m) {
        a = a % m;

        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }

        return -1;
    }

    // Find inverse of 2x2 key matrix
    static int[][] inverseKey(int[][] key) {
        int det = key[0][0] * key[1][1]
                - key[0][1] * key[1][0];

        det = ((det % 26) + 26) % 26;

        int detInverse = modInverse(det, 26);

        if (detInverse == -1) {
            throw new IllegalArgumentException(
                "Key matrix has no inverse modulo 26."
            );
        }

        int[][] inv = new int[2][2];

        inv[0][0] = key[1][1] * detInverse;
        inv[0][1] = -key[0][1] * detInverse;
        inv[1][0] = -key[1][0] * detInverse;
        inv[1][1] = key[0][0] * detInverse;

        // Convert values to 0-25
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inv[i][j] = (inv[i][j] % 26 + 26) % 26;
            }
        }

        return inv;
    }

    // Decrypt the ciphertext
    public static String decrypt(String cipher, int[][] key) {
        int[][] inverse = inverseKey(key);
        return encrypt(cipher, inverse);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[][] key = {
            {3, 3},
            {2, 5}
        };

        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine();

        String ciphertext = encrypt(plaintext, key);
        System.out.println("Encrypted text: " + ciphertext);

        String decrypted = decrypt(ciphertext, key);
        System.out.println("Decrypted text: " + decrypted);

        sc.close();
    }
}
