import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.util.Scanner;

/**
 * Created by ilyarudyak on 22/01/15.
 */
public class Md5Collision {

    private static final String FILENAME = "data/md5_data";

    public static void main(String[] args)throws Exception {

        for (int i=1; i<=2; i++) {

            // get message digest
            MessageDigest md = MessageDigest.getInstance("MD5");

            // read file
            byte[] message = readFile(i);

            // calculate digest
            byte[] md5 = md.digest(message);
            System.out.println(Hex.encodeHexString(md5));
        }


    } // main


    private static byte[] readFile(int n)
            throws FileNotFoundException, DecoderException {

        Scanner in = new Scanner(new File(FILENAME));

        String line = "";
        while(in.hasNext()) {
            if (in.nextLine().contains("#" + n)){
                for(int i=0; i<4; i++) {
                    line += in.nextLine().replaceAll("[ ]", "");
//                    System.out.println(line);
                }
            }
        }
        return Hex.decodeHex(line.toCharArray());
    }


}







