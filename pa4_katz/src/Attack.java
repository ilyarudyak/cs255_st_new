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
        byte[] b12 = (m1+m2).getBytes();
        byte[] tag2 = a.oracle.mac(b12, b12.length);
        System.out.println(a.oracle.verify(b12, b12.length, tag2));


        // compute tag for (m3 ^ tag2) || m4
        byte[] b = new byte[32];
        byte[] m3xortag2 = Utility.xorTwoByteArrays(m3.getBytes(), tag2);
        byte[] m4b = m4.getBytes();
        for (int i=0; i<b.length; i++) {
            if (i <= 15)
                b[i] = m3xortag2[i];
            else
                b[i] = m4b[i-16];
        }
        byte[] tag = a.oracle.mac(b, b.length);
        System.out.println(a.oracle.verify(b, b.length, tag));


        System.out.println(DatatypeConverter.printHexBinary(tag));
        System.out.println(a.oracle.verify(message_full.getBytes(), message_full.length(), tag));



        a.oracle.disconnect();
    }

    public byte[] forgeTag(String text){
        System.out.println("Forging tag for string: "+text);

        // implementation of forgeTag only takes 6 lines of code, and one of them is a }  :)
        return null;
    }
}
