import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your goal this week is to write a program to compute
 * discrete log modulo a prime p. Let g be some element
 * in ℤ∗p and suppose you are given h in ℤ∗p such that
 * h=g^x where 1≤x≤240. Your goal is to find x.
 *
 * Let B=2^20. Since x is less than B^2 we can write
 * the unknown x base B as x=x0*B+x1 where x0,x1 are in
 * the range [0,B−1]. Then h/g^x1=(g^B)^x0.
 */
public class MeetInTheMiddleAttack {

    public static final String FILENAME = "data/numbers";
    public static final long B = 1048576; // =2^20; for test data B=1024

    private BigInteger p;
    private BigInteger g;
    private BigInteger h;
    private Map<BigInteger, Long> map;

    public MeetInTheMiddleAttack() {

        try {

            List<String> numbers = Files.readAllLines(
                    Paths.get(FILENAME), Charset.defaultCharset());
            p = new BigInteger(numbers.get(0).trim());
            g = new BigInteger(numbers.get(1).trim());
            h = new BigInteger(numbers.get(2).trim());

            buildMap();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    /** build a hash table of all possible values of
     * the left hand side h/g^x1 for x1=0,1,…,2^20 */
    private void buildMap() {

        map = new HashMap<>();
        for (long x1=1; x1<= B; x1++) {

            BigInteger key = g.modPow(BigInteger.valueOf(x1),p)
                    .modInverse(p).multiply(h).mod(p);

            map.put(key, x1);

//            // test data
//            if (x1 == 783)
//                System.out.println(key);
        }

    }

    /** for each value x0=0,1,2,…,2^20 check if the right
     * hand side (g^B)^x0 is in this hash table. If so, then
     * you have found a solution (x0,x1) from which you can
     * compute the required x as x=x0*B+x1 */
    public void attack() {

        for (long x0=1; x0<=B; x0++) {

            BigInteger key = g.modPow(BigInteger.valueOf(B), p)
                    .modPow(BigInteger.valueOf(x0), p);

//            // test data
//            if (x0 == 1002)
//                System.out.println(key);

            if (map.containsKey(key)) {
                System.out.println("x0=" + x0 + " x1=" + map.get(key) + " x=" + (x0*B+map.get(key)));
                break;
            }
        }

    }



    public static void main(String[] args) {

        MeetInTheMiddleAttack mima = new MeetInTheMiddleAttack();
        mima.attack();

//        // this is test data from numbers2 file
//        System.out.println(mima.map.containsKey(BigInteger.valueOf(658308031)));
    }
}
