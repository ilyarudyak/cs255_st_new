/**
 * Created by ilyarudyak on 21/01/15.
 */
public class Utility {

    public static byte[] xorTwoByteArrays(byte[] a1, byte[] a2) {

        byte[] a = new byte[a1.length];
        for (int i=0; i<a.length; i++) {
            a[i] = (byte) (a1[i] ^ a2[i]);
        }
        return a;
    }

    public static byte[] charArrayToByteArray(char[] a) {
        byte[] b = new byte[a.length];
        for (int i=0; i<a.length; i++) {
            b[i] = (byte) a[i];
        }
        return b;
    }
}
