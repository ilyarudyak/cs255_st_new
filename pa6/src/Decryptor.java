import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by ilyarudyak on 25/02/15.
 */
public class Decryptor {

    private BigInteger ciphertext;
    private BigInteger plaintext;
    private BigInteger N;
    private BigInteger p;
    private BigInteger q;
    private BigInteger fiN;
    private BigInteger e = BigInteger.valueOf(65537);
    private BigInteger d;

    public Decryptor() {

        try {
            ciphertext = new BigInteger(Files.readAllLines(
                    Paths.get("data/ciphertext"),
                    Charset.defaultCharset())
                    .get(0).trim());
        } catch (IOException e) {
            e.printStackTrace();
        }

        FactorFinder ff = new FactorFinder("data/challenge1");
        ff.solveFirst();
        N = ff.getN();
        p = ff.getP();
        q = ff.getQ();
        fiN =  p.subtract(BigInteger.ONE)
                .multiply(q.subtract(BigInteger.ONE));
        d = e.modInverse(fiN);
    }

    public void decrypt() {

        plaintext = ciphertext.modPow(d, N);
    }

    public static void main(String[] args) {
        Decryptor d = new Decryptor();
        d.decrypt();

        String hexString = String.format("%x", d.plaintext).split("00")[1];
        System.out.println(Utility.byteArrayToString(Utility.hexStringToByteArray(hexString)));
    }
}

















