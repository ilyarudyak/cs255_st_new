import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by ilyarudyak on 12/01/15.
 */
public class VigenereSolver {

    private int[] cipherText;

    public VigenereSolver() {
        try {
            Scanner in = new Scanner(new File("data/vigenere"));
            String line = in.nextLine();
//            System.out.println(line.length()/2);
            cipherText = HexConverter.hexStringToArray(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // calculate sum of square freq for given key length
    // see slide 10 in lectures
    private double sumOfSquareFreq(int keyLenght) {

        int[] q = new int[256];

        int n = 0;
        for (int i=0; i<cipherText.length; i+=keyLenght) {
            q[cipherText[i]]++;
            n++;
        }



        double count = 0;
        int m = 0;
        for (int i=0; i<q.length; i++) {
            if (q[i] != 0) {
                m++;
                count += ((double) q[i] * q[i]) / (n * n);
            }
        }

        return count * m;
    }

    public void printSquares() {
        double max = 0;
        int k = 0;
        for (int key=2; key<470; key++) {
            if (sumOfSquareFreq(key) > max) {
                max = sumOfSquareFreq(key);
                k = key;
//                System.out.println(max + " " + k);
            }
        }
        System.out.println(max + " " + k);
    }

    public static void main(String[] args) {

        VigenereSolver vs = new VigenereSolver();
        vs.printSquares();
    }
}
