import java.io.IOException;
import java.util.Arrays;

/**
 * We have 64B cipher text message c = IV || c0 || c1 || c2,
 * length of each sub message = 16B. We have m = m0 || m1 || m2
 * with the same length.
 *
 * In AES CBS F(c0 ^ m1) = c1, so m1 = F-1(c1) ^ c0.
 * 1) We look for some last byte of c0 to get 0x01 padding in m1.
 * 2) Then we compute m1 and look for next byte of c0 to get 0x02
 * padding in m1.
 * 3) We also change last byte of c0 etc.
 */
public class PaddingOracleAttack {

    public static final String FILENAME = "data/message";
    // length of sub message (= AES key length 16B)
    public static final int M_LENGTH = 16;
    // number of sub messages - 1
    public static final int M_NUMBER = 2;
    public static final int ERROR_404 = 404;

    private int[] ctext;
    private int[] mtext;

    public PaddingOracleAttack() {

        try {
            ctext = HexConverter.hexStringToHexArray(
                    HexConverter.readHexStringFromFile(FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
            ctext = new int[0];
        }

        // ctext = IV || c0, so len(mtext) = len(ctext) - len(IV)
        mtext = new int[ctext.length - M_LENGTH];
    }

    // b = 0, ... , 15 m = 0, 1, 2
    private int solveByte(int m, int b) throws IOException {

        // we make a copy of ctext of proper length
        int[] copy = Arrays.copyOf(ctext, ctext.length -  M_LENGTH * (M_NUMBER - m));

        // real byte ## in message
        int rb = m * M_LENGTH + b;

        // if b = 15 padding = 0x01, b = 14 padding = 0x02 etc.
        int padding = (16 - b);

        // we change next to rb of cipher text to get proper padding in mtext
        for (int k = rb+1; k < (m+1)*M_LENGTH; k++) {
            copy[k] = ((mtext[k] ^ padding) ^ copy[k]);
        }

        int code, i;
        for (i=0; i<256; i++) {
            copy[rb] = i;
            code = Oracle.getHttpCodeInt(copy);
            if (code == ERROR_404) break;
        }

        // calculate this byte of plain text
        return (padding ^ i ^ ctext[rb]);
    }

    public void solveBytes(int m) throws IOException {

        for (int b = 15; b >= 5; b--) {

            // we have to fill proper bytes of plain text to move on!
            mtext[m*M_LENGTH+b] = solveByte(m, b);
            System.out.println("b=" + b);
        }
    }

    public static void main(String[] args) throws IOException {

        PaddingOracleAttack poa = new PaddingOracleAttack();
//        poa.solveBytes(0);
//        poa.solveBytes(1);
        poa.solveBytes(2);
        HexConverter.printAsciiStringFromHexArray(poa.mtext, poa.mtext.length);

    }
}
