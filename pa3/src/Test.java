import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;

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

    // from FileHash
//    private int countBlocks() throws IOException {
//
//        // read from file
//        FileInputStream in = new FileInputStream("data/6-2");
//        byte[] buffer = new byte[BLOCK_SIZE];
//
//        int count = 0;
//        while (in.read(buffer) != -1) {
//            if (count == 16529) clean(buffer);
//            count++;
//        }
//
//        in.close();
//
//        // write buffer to file
//        Files.write(Paths.get("data/dump2end"), buffer);
//
//
//        System.out.println(Hex.encodeHex(buffer));
//        return count;
//    }
    private void clean(byte[] buffer) {
        Arrays.fill(buffer, (byte) 0);
    }
}
