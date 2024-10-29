import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int keySize = 8; // Choose size for key

        // Generate key pair
        KnapsackCrypto knapsack = KnapsackCrypto.generateKeys(keySize);
        System.out.println("Private Key: " + Arrays.toString(knapsack.getPrivateKey()));
        System.out.println("Public Key: " + Arrays.toString(knapsack.getPublicKey()));
        System.out.println("Modulus: " + knapsack.getModulus());
        System.out.println("Multiplier: " + knapsack.getMultiplier());

        // Message to encrypt (boolean array representing binary form)
        boolean[] message = {true, false, true, false, true, false, true, false};
        System.out.println("Original Message: " + Arrays.toString(message));

        // Encrypt the message
        int encryptedMessage = knapsack.encrypt(message);
        System.out.println("Encrypted Message: " + encryptedMessage);

        // Decrypt the message
        boolean[] decryptedMessage = knapsack.decrypt(encryptedMessage);
        System.out.println("Decrypted Message: " + Arrays.toString(decryptedMessage));

        // Check if decryption is successful
        System.out.println("Decryption successful: " + Arrays.equals(message, decryptedMessage));
    }
}
