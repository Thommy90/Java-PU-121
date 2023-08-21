package step.learning.ioc;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA implements IHashService  {
    @Override
    public void GetHash(String text) {
        {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                messageDigest.update(text.getBytes());
                byte[] digiest = messageDigest.digest();
                String hashedOutput = DatatypeConverter.printHexBinary(digiest);
                System.out.println( "SHA hash: " + hashedOutput);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

    }
}