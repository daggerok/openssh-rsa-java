package daggerok.rsa;

import lombok.val;
import org.junit.Test;

import static daggerok.rsa.Main.decrypt;
import static daggerok.rsa.Main.encrypt;
import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void test() throws Exception {

        val plainText = "Hello World!!1!";
        val encryptedBytesRepresentation = encrypt(plainText);
        val decrypted = decrypt(encryptedBytesRepresentation);

        System.out.println(format("decrypted text '%s' is equal to source plain text: %b",
                decrypted, plainText.equals(decrypted)));

        assertThat(decrypted, is(plainText));
    }
}
