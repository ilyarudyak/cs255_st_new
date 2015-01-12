import java.io.FileNotFoundException;

/**
 * Created by ilyarudyak on 12/01/15.
 */
public class ManyTimePad {

    // 11 ciphertexts from assignment
    int[][] ciphertext;
    int[] key;


    public ManyTimePad(String filename) {

        try {
            ciphertext = HexConverter.readCiphertext(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        key = new int[HexConverter.ARRAY_LENGTH];

    }
    public static void main(String[] args) {

         // main assignment
//        ManyTimePad tp = new ManyTimePad("Data/ciphertext");
//        tp.key = HexConverter.xorHexArrays(
//                HexConverter.asciiStringToHexArray(
//                        "There are two types of cyptography: one that allows the Government + " +
//                                "to use brute force to break the code, and one that requires the +" +
//                                "Government to use brute force to break you."),
//                tp.ciphertext[7]);
//
//        for (int i=1; i<=HexConverter.NUMBER_CIPHERTEXTS; i++) {
//            System.out.print(i + ": ");
//            HexConverter.printAsciiFromHexArray(HexConverter.xorHexArrays(
//                    tp.ciphertext[i], tp.key), 150);
//        }
        // the assignment from Katz course
        ManyTimePad tp = new ManyTimePad("Data/ciphertext2");

        tp.key = HexConverter.xorHexArrays(
                HexConverter.asciiStringToHexArray(
                        "The current plan is top secret."),
                tp.ciphertext[3]);

        System.out.print("   ");
        for(int i=0; i<31; i++)
            System.out.printf("%2d", i);
        System.out.println();

        for (int i=1; i<=HexConverter.NUMBER_CIPHERTEXTS; i++) {
            System.out.print(i + ": ");
            HexConverter.printAsciiFromHexArray(HexConverter.xorHexArrays(
                    tp.ciphertext[i], tp.key), 31);
        }

    }


}
















