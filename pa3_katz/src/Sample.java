import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Sample {

  private int[] ctext = new int[48];
  private Oracle oracle;
  private int[] mtext = new int[32];

  public Sample() {

    BufferedReader in;
    int i = 0;
    String filename = "data/ciphertext";
    fillM();

    oracle = new Oracle();
    oracle.connect();

    try {
      // read file with ciphertext to ctext array
      in = new BufferedReader(new FileReader(filename));
      char[] buf = new char[2];
      while (in.read(buf, 0, 2) != -1) {
        ctext[i++] = (Integer.parseInt(new String(buf), 16));
      }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public void close() { oracle.disconnect(); }

  private void modifyCtext(int n) {
    int[] copy = Arrays.copyOf(ctext, ctext.length);
    copy[n] += 1;
    System.out.println(oracle.send(copy, 3));
  }

  private void fillM() {

    for (int i=21; i<32; i++)
      mtext[i] = 0x0B;
  }

  private int solveByte2(int b) {

    int[] copy = Arrays.copyOf(ctext, ctext.length);

    int padding = 32 - b;
    System.out.printf("0x%02x\n", padding);

    for (int k = b+1; k < 32; k++)
      copy[k] = (mtext[k] ^ padding) ^ copy[k];

    int rc, i;
    for (i=0; i<256; i++) {
      copy[b] = i;
      rc = oracle.send(copy, 3);
      if (rc == 1)
        break;
    }

    return padding ^ i ^ ctext[b];
  }

  public void solveBytes2() {

      for (int b = 20; b >= 16; b--)
        mtext[b] = solveByte2(b);
  }

  private int solveByte1(int b) {

    int[] copy = Arrays.copyOf(ctext, ctext.length - 16);
//    HexConverter.printHexArray(copy,0,32);

    int padding = 16 - b;
    System.out.println(padding);

    for (int k = b+1; k < 16; k++)
      copy[k] = (mtext[k] ^ padding) ^ copy[k];

    int rc, i;
    for (i=0; i<256; i++) {
      copy[b] = i;
      rc = oracle.send(copy, 2);
      if (rc == 1)
        break;
    }

//    System.out.println(i);
//    System.out.println(padding ^ i ^ ctext[b]);

    return padding ^ i ^ ctext[b];

  }

  public void solveBytes1() {

    for (int b = 15; b >= 0; b--)
      mtext[b] = solveByte1(b);
  }




  public static void main(String[] args) {

    Sample s = new Sample();
    s.solveBytes2();

    HexConverter.printHexArray(s.mtext, 0, s.mtext.length);
    HexConverter.printAsciiStringFromHexArray(s.mtext, s.mtext.length);

    s.close();

  }
}
