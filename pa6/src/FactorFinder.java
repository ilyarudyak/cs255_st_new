
import com.google.common.math.BigIntegerMath;

import java.io.IOException;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by ilyarudyak on 25/02/15.
 */
public class FactorFinder {

    private BigInteger N;
    private BigInteger p;
    private BigInteger q;

    public BigInteger getN() {
        return N;
    }
    public BigInteger getP() {
        return p;
    }
    public BigInteger getQ() {
        return q;
    }

    public FactorFinder(String fileName) {

        StringBuilder sb = new StringBuilder();
        try {
            List<String> list = Files.readAllLines(
                    Paths.get(fileName), Charset.defaultCharset());

            for(String s: list) { sb.append(s.replace('\\', ' ').trim()); }

            N = new BigInteger(sb.toString());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public FactorFinder(int n) {
        N = BigInteger.valueOf(n);
    }

    public  static BigInteger sqrt(BigInteger number) {
        return sqrt(number, BigInteger.ONE);
    }
    private static BigInteger sqrt(BigInteger number, BigInteger guess) {

        // redoing this to avoid StackOverFlow
        BigInteger result = BigInteger.ZERO;
        BigInteger flipA = result;
        BigInteger flipB = result;
        boolean first = true;
        while( result.compareTo(guess) != 0 )
        {
            if(!first)
                guess = result;
            else
                first=false;

            result = number.divide(guess).add(guess).divide(BigInteger.valueOf(2));
            // handle flip flops
            if(result.equals(flipB))
                return flipA;

            flipB = flipA;
            flipA = result;
        }
        return result;

    }

    public BigInteger solveFirst() {

        BigInteger A = sqrt(N).add(BigInteger.ONE);
        BigInteger x =  sqrt(A.pow(2).subtract(N));

        p = A.subtract(x);
        q = A.add(x);

        return A.subtract(x);
    }

    public BigInteger solveSecond() {

        BigInteger sqrtN = sqrt(N);
        BigInteger p;
        for(int i=1; i<(int)Math.pow(2,20); i++) {
            p = checkA(sqrtN.add(BigInteger.valueOf(i)));
            if ( !p.equals(BigInteger.ZERO) )
                { return p; }
        }

        return BigInteger.ZERO;
    }

    private BigInteger checkA(BigInteger A) {

        BigInteger x =  sqrt(A.pow(2).subtract(N));
        BigInteger p = A.subtract(x);
        BigInteger q = A.add(x);
        if (checkPQ(p, q)) return p;
        else return BigInteger.ZERO;
    }

    private boolean checkPQ(BigInteger p, BigInteger q) {
        return p.multiply(q).equals(N);
    }



    /**
     * We have to use a trick not to solve a quadratic equation.
     * N' = 24N and A'=3p+2q then p'=6p and q'=4q
     * */
    public  BigInteger solveThird() {

        BigInteger A1 = BigIntegerMath.sqrt(N.multiply(
                BigInteger.valueOf(24)), RoundingMode.CEILING);

//        System.out.println("A1="+A1);

        BigInteger x = BigIntegerMath.sqrt(
                        A1.pow(2).subtract(N.multiply(BigInteger.valueOf(24))),
                        RoundingMode.CEILING);

//        BigInteger p1 = A1.subtract(x);
//        BigInteger q1 = A1.add(x);
//        System.out.println("n="+p1.multiply(q1).divide(BigInteger.valueOf(24)));

        BigInteger p = A1.subtract(x).divide(BigInteger.valueOf(6));
//        BigInteger q = A1.add(x).divide(BigInteger.valueOf(4));

//        BigInteger n = p.multiply(q);
//        System.out.println("p="+p+" q="+q+" n="+n);

        return p;
    }





    public static void main(String[] args) {

        FactorFinder ff = new FactorFinder("data/challenge3");

        System.out.println(ff.solveThird());
    }
}









