import java.util.Arrays;
import java.util.Random;

public class KnapsackCrypto {
    private final int[] privateKey;
    private final int[] publicKey;
    private final int modulus;
    private final int multiplier;

    // Constructor to initialize the keys and modulus
    public KnapsackCrypto(int[] superIncreasingSequence, int modulus, int multiplier) {
        this.privateKey = superIncreasingSequence;
        this.modulus = modulus;
        this.multiplier = multiplier;

        // Generate public key using private key, modulus, and multiplier
        publicKey = new int[privateKey.length];
        for (int i = 0; i < privateKey.length; i++) {
            publicKey[i] = (privateKey[i] * multiplier) % modulus;
        }
    }

    // Generate superincreasing sequence for the private key
    public static int[] generateSuperIncreasingSequence(int size) {
        Random random = new Random();
        int[] sequence = new int[size];
        sequence[0] = random.nextInt(10) + 1; // Initial value

        for (int i = 1; i < size; i++) {
            sequence[i] = Arrays.stream(sequence).sum() + random.nextInt(10) + 1;
        }
        return sequence;
    }

    // Method to generate keys (public and private) and modulus parameters
    public static KnapsackCrypto generateKeys(int size) {
        int[] privateKey = generateSuperIncreasingSequence(size);
        int modulus = Arrays.stream(privateKey).sum() + new Random().nextInt(50) + 10;
        int multiplier = 2; // Multiplier chosen to be coprime with modulus
        return new KnapsackCrypto(privateKey, modulus, multiplier);
    }

    // Encrypt message using the public key
    public int encrypt(boolean[] message) {
        int cipher = 0;
        for (int i = 0; i < message.length; i++) {
            if (message[i]) {
                cipher += publicKey[i];
            }
        }
        return cipher;
    }

    // Decrypt message using the private key
    public boolean[] decrypt(int cipher) {
        int tInverse = modInverse(multiplier, modulus);
        int decryptedValue = (cipher * tInverse) % modulus;

        boolean[] decryptedMessage = new boolean[privateKey.length];
        for (int i = privateKey.length - 1; i >= 0; i--) {
            if (decryptedValue >= privateKey[i]) {
                decryptedMessage[i] = true;
                decryptedValue -= privateKey[i];
            } else {
                decryptedMessage[i] = false;
            }
        }
        return decryptedMessage;
    }

    // Modular multiplicative inverse using the extended Euclidean algorithm
    private int modInverse(int a, int m) {
        int m0 = m, y = 0, x = 1;
        while (a > 1) {
            int q = a / m;
            int t = m;
            m = a % m;
            a = t;
            t = y;
            y = x - q * y;
            x = t;
        }
        return x < 0 ? x + m0 : x;
    }

    // Getters
    public int[] getPrivateKey() {
        return privateKey;
    }

    public int[] getPublicKey() {
        return publicKey;
    }

    public int getModulus() {
        return modulus;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
