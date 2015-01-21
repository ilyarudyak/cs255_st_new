import javax.xml.bind.DatatypeConverter;

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



}
