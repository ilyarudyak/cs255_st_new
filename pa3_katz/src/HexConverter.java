import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by ilyarudyak on 12/01/15.
 */
public class HexConverter {

    // change for main assignment to 11 and 200
    // change for pa2 katz to 7 and 50
    // change for pa1 katz to n/a and 470
    public static final int NUMBER_CIPHERTEXTS = 7;
    public static final int ARRAY_LENGTH = 14;

    // xor two hex arrays
    public static int[] xorHexArrays(int[] hexArray1, int[] hexArray2) {
        int len = hexArray1.length;
        int[] hexArray = new int[len];
        for(int i=0; i<len; i++) {
            hexArray[i] = hexArray1[i] ^ hexArray2[i];
        }
        return hexArray;
    }

    // --------------- convert hex and ascii strings to hex array -------

    // convert "315c" to [31, 5c]
    public static int[] hexStringToHexArray(String hexString) {

        int[] hexArray = new int[ARRAY_LENGTH];
        for(int i=0; i<hexString.length(); i+=2) {
            hexArray[i/2] = Integer.parseInt(hexString.substring(i, i + 2), 16);
        }
        return hexArray;
    }

    public static int[] asciiStringToHexArray(String asciiString) {

        int[] hexArray = new int[ARRAY_LENGTH];
        for(int i=0; i<asciiString.length(); i++)
            hexArray[i] = (int) asciiString.charAt(i);
        return hexArray;
    }

    // ----------------------- print functions --------------------------

    // print array of hex numbers as hex (not as decimals)
    public static void printHexArray(int[] hexArray, int start, int end) {
        System.out.print("[");
        for(int i=start; i<end; i++) {
            if (i < end - 1)
                System.out.printf("%02x, ", hexArray[i]);
            else if (end == hexArray.length && i == end-1)
                System.out.printf("%02x", hexArray[i]);
            else
                System.out.printf("%02x, ... ", hexArray[i]);
        }
        System.out.println("]");
    }

    public static void printHexStringFromHexArray(int[] hexArray, int n) {
        for(int i=0; i<n; i++) {
            if (i < n - 1)
                System.out.printf("%02x", hexArray[i]);
        }
        System.out.println();
    }

    public static void printAsciiStringFromHexArray(int[] hexArray, int n) {

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

    // ----------------------- reading from file ------------------------

    public static int[][] readCiphertext(String filename)
            throws FileNotFoundException {

        int[][] ciphertext = new int[NUMBER_CIPHERTEXTS+1][ARRAY_LENGTH];

        int i = 1;
        Scanner in = new Scanner(new File(filename));

        while (in.hasNext()) {
            String line = in.nextLine();
            if (!line.isEmpty() && !line.contains("#")) {
//                System.out.println(line.length()/2);
                ciphertext[i++] = hexStringToHexArray(line);
            }
        }

        return ciphertext;

    }

    public static void main(String[] args) {

        String s = "abc";
        printHexArray(asciiStringToHexArray(s), 0, 5);


    }
}
