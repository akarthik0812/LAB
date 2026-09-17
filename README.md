import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AES {

    // Convert bytes to hexadecimal
    public static String asHex(byte[] buf) {
        StringBuilder strbuf = new StringBuilder(buf.length * 2);

        for (byte b : buf) {
            if ((b & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString(b & 0xff, 16));
        }

        return strbuf.toString();
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // Get message from user
        System.out.print("Input your message: ");
        String message = sc.nextLine();

        // Generate AES key
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(128);

        SecretKey secretKey = keygen.generateKey();

        // Create key specification
        byte[] raw = secretKey.getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");

        // Create cipher for encryption
        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        // Encrypt the message
        byte[] encrypted = cipher.doFinal(
                message.getBytes(StandardCharsets.UTF_8)
        );

        System.out.println("Encrypted text: " + asHex(encrypted));

        // Create cipher for decryption
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        // Decrypt the encrypted data
        byte[] original = cipher.doFinal(encrypted);

        String originalString =
                new String(original, StandardCharsets.UTF_8);

        System.out.println("Decrypted text: " + originalString);

        sc.close();
    }
}
