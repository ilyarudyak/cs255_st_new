/**
 * Created by ilyarudyak on 14/01/15.
 */
public class Question7 {

    public static void main(String[] args) {

        String m0 = "attack at dawn";
        String m1 = "attack at dusk";
        String c0 = "6c73d5240a948c86981bc294814d";
        String c1;

        int[] key = HexConverter.xorHexArrays(
                HexConverter.asciiStringToHexArray(m0),
                HexConverter.hexStringToHexArray(c0));

        HexConverter.printHexStringFromHexArray(HexConverter.xorHexArrays(
                key, HexConverter.asciiStringToHexArray(m1)), HexConverter.ARRAY_LENGTH);
    }
}
