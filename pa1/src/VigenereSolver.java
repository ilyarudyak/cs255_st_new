import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by ilyarudyak on 12/01/15.
 */
public class VigenereSolver {

    private int[] cipherText;
    private int keyLength = 7;
    private int[] key = new int[]{186, 31, 145, 178, 83, 205, 62};

    public VigenereSolver() {
        try {
            Scanner in = new Scanner(new File("data/vigenere"));
            String line = in.nextLine();
//            System.out.println(line.length()/2);
            cipherText = HexConverter.hexStringToHexArray(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void decrypt() {

    }

    private int[] enlargeKey() {
        int[] largeKey = new int[cipherText.length];
        for(int i=0; i<cipherText.length; i++) {
            largeKey[i] = key[i % key.length];
        }
        return largeKey;
    }

    // stream - every k's element of cipherText
    // 0th stream 0, 0+k, 0+2k etc.
    // 1st stream 1, 1+k, 1+2k etc.
    // brute force 0 byte of keyLength from stream 0th etc.
    public void guessKeyByte(int byteNumber) {

        String s;
        boolean flag;
        int countLetters;
        for (int b=0; b<256; b++) {
            s = "";
            flag = true;
            countLetters = 0;
            for (int i=byteNumber; i<cipherText.length; i+= keyLength) {
                int xor = b ^ cipherText[i];
                s += String.valueOf((char) xor);
                if ((97 <= xor && xor <= 122) || xor == 32)
                    countLetters++;
                if (xor < 32 || xor > 127) {
                    flag = false;
                    break; // wrong b - xor must be ascii between 32 and 127
                }
            }
            if (flag && countLetters > 50)
                System.out.println(b + " " + countLetters + " " +s);
        }
    }

    // calculate sum of square freq for given keyLength length
    // see slide 10 in lectures
    private double sumOfSquareFreq(int keyLen) {

        int[] q = new int[256];

        int n = 0;
        for (int i=0; i<cipherText.length; i+=keyLen) {
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
//        vs.printSquares();
//        vs.guessKeyByte(6);

        HexConverter.printAsciiStringFromHexArray(
                HexConverter.xorHexArrays(vs.enlargeKey(), vs.cipherText), 470);
    }
}
