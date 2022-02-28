package project.noticeboard.other;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import project.noticeboard.config.SHA256;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

public class SHATest {

    @Test
    public void passwordEncryptTest() throws NoSuchAlgorithmException {
        SHA256 sha256 = new SHA256();

        String password = "123456";
        String cryptogram = sha256.encrypt(password);

        System.out.println("cryptogram = " + cryptogram);

        boolean equals = cryptogram.equals(sha256.encrypt(password));

        assertThat(equals).isTrue();
    }



}
