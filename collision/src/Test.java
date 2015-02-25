/**
 * Created by ilyarudyak on 06/02/15.
 */
public class Test {

    public static void main(String[] args) {

        String a00 = "66e94bd4ef8a2c3b884cfa59ca342b2e";
        String a11 = "a17e9f69e4f25a8b8620b4af78eefd6f";

        System.out.println(Utility.byteArrayToHexString(
                Utility.xorTwoByteArrays(
                    Utility.hexStringToByteArray(a00),
                    Utility.hexStringToByteArray(a11)
                )));
    }
}
