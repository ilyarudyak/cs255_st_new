import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

/**
 * Created by ilyarudyak on 21/01/15.
 */
public class Attack {

    public static final String m1 = "I, the server, h";
    public static final String m2 = "ereby agree that";
    public static final String m3 = " I will pay $100";
    public static final String m4 = " to this student";

    public static final String message_full = m1 + m2 + m3 + m4;

    private Oracle oracle = new Oracle();

    public static void main(String[] args) {

        Attack a = new Attack();
        a.oracle.connect();

        // compute tag2 for m1 || m2
        byte[] tag2 = a.oracle.mac(Utility.stringToByteArray(m1+m2), (m1+m2).length());


        // compute tag for (m3 ^ tag2) || m4
        byte[] b = a.computeByteArray(m3, m4, tag2);
        byte[] tag = a.oracle.mac(b, b.length);
        System.out.println(a.oracle.verify(b, b.length, tag));

        // verify this tag
        System.out.println(a.oracle.verify(Utility.stringToByteArray(message_full),
                message_full.length(), tag));

        // print tag as hex string
        System.out.println(Utility.byteArrayToHexString(tag));

        a.oracle.disconnect();
    }

    private byte[] computeByteArray(String m3, String m4, byte[] tag2) {

        byte[] b = new byte[32];
        System.arraycopy(Utility.xorTwoByteArrays(m3.getBytes(), tag2), 0, b, 0, 16);
        System.arraycopy(Utility.stringToByteArray(m4), 0, b, 16, 16);
        return b;
    }
}











