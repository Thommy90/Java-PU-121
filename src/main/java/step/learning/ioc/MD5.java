package step.learning.ioc;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 implements IHashService  {
    @Override
    public void GetHash(String text) {
        {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(text.getBytes());
                byte[] digiest = messageDigest.digest();
                String hashedOutput = DatatypeConverter.printHexBinary(digiest);
                System.out.println( "MD5 hash: " + hashedOutput);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
