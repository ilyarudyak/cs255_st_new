import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by ilyarudyak on 21/01/15.
 */
public class Utility {

    public static byte[] xorTwoByteArrays(byte[] b1, byte[] b2) {

        byte[] b = new byte[b1.length];
        for (int i=0; i<b.length; i++) {
            b[i] = (byte) (b1[i] ^ b2[i]);
        }
        return b;
    }

    // ------------------------ conversion ------------------------

    public static byte[] stringToByteArray(String s) {

        return s.getBytes();
    }

    public static String byteArrayToString(byte[] b) {

        return new String(b);
    }

    public static byte[] hexStringToByteArray(String hexString) {

        return DatatypeConverter.parseHexBinary(hexString);
    }

    public static String byteArrayToHexString(byte[] b) {

        return DatatypeConverter.printHexBinary(b);
    }

    // ------------------------ print -----------------------------

    // print byte array (or its part) as hex array (not as decimals)
    public static void printHexArray(byte[] b, int start, int end) {
        System.out.print("[");
        for(int i=start; i<end; i++) {
            if (i < end - 1)
                System.out.printf("%02x, ", b[i]);
            else if (end == b.length && i == end-1)
                System.out.printf("%02x", b[i]);
            else
                System.out.printf("%02x, ... ", b[i]);
        }
        System.out.println("]");
    }

    public static void printHexArray(byte[] b) {
        printHexArray(b, 0, b.length);
    }

    public static void printAsciiStringFromHexArray(byte[] hexArray, int n) {

        for (int i=0; i<n; i++) {
            if (97 <= hexArray[i] && hexArray[i] <= 122)
                System.out.printf("%2c", hexArray[i]);
            else if (65 <= hexArray[i] && hexArray[i] <= 90)
                System.out.printf("%2c", hexArray[i]);
            else if (hexArray[i] == 32)
                System.out.printf("  ");
            else if (hexArray[i] == 46)
                System.out.printf(" .");
            else if (hexArray[i] == 63)
                System.out.printf(" ?");
            else
                System.out.printf(" *");
        }
        System.out.println();

    }

    // ------------------------ read file ---------------------------

    // read hex string from file
    public static String readHexStringFromFile(String fileName) throws IOException {

        return Files.readAllLines(Paths.get(fileName),
                Charset.defaultCharset()).get(0);

    }



}
