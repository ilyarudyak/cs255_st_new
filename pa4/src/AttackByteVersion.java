import java.io.IOException;
import java.util.Arrays;

/**
 * This is the same algorithm as in the PaddingOracleAttack
 * but we use here byte arrays instead of int arrays.
 */
public class AttackByteVersion {


    public static final String FILENAME = "data/message";
    // length of sub message (= AES key length 16B)
    public static final int M_LENGTH = 16;
    // number of sub messages - 1
    public static final int M_NUMBER = 2;
    public static final int ERROR_403 = 403;
    public static final int ERROR_404 = 404;
    // padding for last (m2) sub message
    public static final int LAST_PAD = 0x09;

    private byte[] ctext;
    private byte[] mtext;

    public AttackByteVersion() {

        try {
            ctext = Utility.hexStringToByteArray(
                    Utility.readHexStringFromFile(FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
            ctext = new byte[0];
        }

        // ctext = IV || c0, so len(mtext) = len(ctext) - len(IV)
        mtext = new byte[ctext.length - M_LENGTH];
    }

    // b = 0, ... , 15 m = 0, 1, 2
    private byte solveByte(int m, int b) throws IOException {

        // we make a copy of ctext of proper length
        byte[] copy = Arrays.copyOf(ctext, ctext.length - M_LENGTH * (M_NUMBER - m));

        // real byte ## in message
        int rb = m * M_LENGTH + b;

        // if b = 15 padding = 0x01, b = 14 padding = 0x02 etc.
        int padding = (16 - b);

        // we change next to rb of cipher text to get proper padding in mtext
        for (int k = rb+1; k < (m+1)*M_LENGTH; k++) {
            copy[k] = (byte)((mtext[k] ^ padding) ^ copy[k]);
        }

        int code, i;
        for (i=0; i<256; i++) {
            copy[rb] = (byte)i;
            code = Oracle.getHttpCodeByte(copy);
            if (code == ERROR_404) break;
        }

        // calculate this byte of plain text
        return (byte)(padding ^ i ^ ctext[rb]);
    }

    public void solveBytes(int m) throws IOException {

        for (int b = 6; b >= 0; b--) {

            System.out.print("b=" + b + " ");

            // we have to fill proper bytes of plain text to move on!
            mtext[m*M_LENGTH+b] = solveByte(m, b);
            System.out.printf("0x%02x ", mtext[m * M_LENGTH + b]);

        }
    }

    public void solveBytes2(int m) throws IOException {

        setLastMessagePad();

        for (int b = 6; b >= 0; b--) {

            System.out.println("b=" + b);

            // we have to fill proper bytes of plain text to move on!
            mtext[m*M_LENGTH+b] = solveByte(m, b);

        }
    }

    private void setLastMessagePad() {

        for (int b=39; b<mtext.length; b++) {
            mtext[b] = LAST_PAD;
        }
    }

    private void findLastMessagePad() throws IOException {

        for (int b=32; b< mtext.length; b++) {

            byte[] copy = Arrays.copyOf(ctext, ctext.length);
            copy[b] += 1;
            if (Oracle.getHttpCodeByte(copy) == ERROR_403) {
                System.out.println(b);
                break;
            }

        }
    }

    public static void main(String[] args) throws IOException {

        AttackByteVersion a = new AttackByteVersion();

        a.setLastMessagePad();
        a.solveBytes(2);
        System.out.println(Utility.byteArrayToString(a.mtext));

//        // here is the solution of PA
//        a.solveBytes(0);
//        a.solveBytes(1);
//        a.solveBytes2(2);
//        System.out.println(Utility.byteArrayToString(a.mtext));

//        // 1) here we are looking for last message padding
//        a.findLastMessagePad();


//        // 2) an alternative approach to find last sub message padding
//        System.out.printf("0x%02x", a.ctext[47]);
//        for(int b=0; b<255; b++) {
//
//            a.ctext[47] = (byte)b; // 0xc0
//            if (Oracle.getHttpCodeByte(a.ctext) == ERROR_404) { // now m2 = 0x01
//                System.out.printf("0x%02x", b); // 0xc8
//                break;
//            }
//        }
//        System.out.printf("0x%02x", 0x01 ^ 0xc8 ^ 0xc0);

//        // 3) we may also find last message pad like this
//        a.solveBytes(2); // b =15 to 15 in solveBytes()



    }
}
