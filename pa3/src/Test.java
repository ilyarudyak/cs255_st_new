import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * Created by ilyarudyak on 22/01/15.
 */
public class Test {

    public static void main(String[] args)throws Exception {

        // get message digest
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // read file
        FileInputStream in = new FileInputStream("data/6-1");
        byte[] buffer = new byte[1024];

        int n;
        while ((n = in.read(buffer)) != -1) {
            md.update(buffer, 0, n);
        }

        // calculate digest
        byte[] sha256 = md.digest();
        System.out.println(Hex.encodeHexString(sha256));


    } // main
}
