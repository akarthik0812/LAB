import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Scanner;

public class DES {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // Generate DES key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey key = keyGenerator.generateKey();

        // Input message
        System.out.print("Enter message: ");
        String message = sc.nextLine();

        // Encryption
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        String encryptedText = Base64.getEncoder()
                .encodeToString(encryptedBytes);

        System.out.println("Encrypted Text: " + encryptedText);

        // Decryption
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decryptedBytes = cipher.doFinal(
                Base64.getDecoder().decode(encryptedText));

        String decryptedText = new String(decryptedBytes);

        System.out.println("Decrypted Text: " + decryptedText);

        sc.close();
    }
}
