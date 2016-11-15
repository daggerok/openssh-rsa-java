package daggerok.rsa;

import com.github.fommil.ssh.SshRsaCrypto;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {

    public static void main(String[] args) {
        System.out.println("usage: run tests from src/test/java");
    }

    /**
     * encrypt give plain text string to byte array representation (client side)
     */
    @SneakyThrows
    static byte[] encrypt(final String text) {

        val cipher = cipher();

        cipher.init(Cipher.ENCRYPT_MODE, publicKey());
        return cipher.doFinal(text.getBytes());
    }

    /**
     * encrypt give plain text string to byte array representation using public key (client side)
     */
    @SneakyThrows
    static byte[] encrypt(final String plainText, final PublicKey publicKey) {

        val cipher = cipher();

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText.getBytes());
    }

    /**
     * decrypt encrypted byte array to plain text (server side)
     */
    @SneakyThrows
    public static String decrypt(final byte[] encryptedRepresentation) {

        val cipher = cipher();

        cipher.init(Cipher.DECRYPT_MODE, privateKey());
        return new String(cipher.doFinal(encryptedRepresentation));
    }

    /**
     * decrypt encrypted byte array to plain text using private key (server side)
     */
    @SneakyThrows
    public static String decrypt(final byte[] encryptedRepresentation, final PrivateKey privateKey) {

        val cipher = cipher();

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(encryptedRepresentation));
    }

    /**
     * reading classpath public key rom default path: ".ssh/id_rsa.pub"
     */
    @SneakyThrows
    public static PublicKey publicKey() {
        return publicKey(".ssh/id_rsa.pub");
    }

    /**
     * reading classpath public key
     *
     * @param pathToFile - path to file within content of public key, for example: ".ssh/id_rsa.pub"
     */
    @SneakyThrows
    public static PublicKey publicKey(final String pathToFile) {

        val sshRsaCrypto = new SshRsaCrypto();
        val publicKeyBody = body(pathToFile);
        val bytes = sshRsaCrypto.slurpPublicKey(publicKeyBody);

        return sshRsaCrypto.readPublicKey(bytes);
    }

    /**
     * reading classpath private key from default path: ".ssh/id_rsa"
     */
    @SneakyThrows
    public static PrivateKey privateKey() {
        return privateKey(".ssh/id_rsa");
    }

    /**
     * reading classpath private key
     *
     * @param pathToFile - path to file within content of private key, for example: ".ssh/id_rsa"
     */
    @SneakyThrows
    public static PrivateKey privateKey(final String pathToFile) {

        val sshRsaCrypto = new SshRsaCrypto();
        val privateKeyBody = body(pathToFile);
        val bytes = sshRsaCrypto.slurpPrivateKey(privateKeyBody);

        return sshRsaCrypto.readPrivateKey(bytes);
    }

    /**
     * DRY: getting RSA cipher
     */
    @SneakyThrows
    public static Cipher cipher() {
        return Cipher.getInstance(SshRsaCrypto.RSA);
    }

    /**
     * DRY: read file content. using for getting public/private key content
     */
    @SneakyThrows
    public static String body(final String pathToFile) {

        val classLoader = Main.class.getClassLoader();
        @Cleanup val stream = classLoader.getResourceAsStream(pathToFile);

        return IOUtils.toString(stream, UTF_8);
    }
}
