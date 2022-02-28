package project.noticeboard.config;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA256 {

    public String encrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
