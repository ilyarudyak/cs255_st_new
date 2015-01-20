import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by ilyarudyak on 20/01/15.
 */
public class HexConverter {

    public static final String FILENAME = "data/ciphertext";

    private byte[] ciphertext;
    private SecretKey key;
    private IvParameterSpec iv;

    public byte[] getCiphertext() {
        return ciphertext;
    }
    public SecretKey getKey() {
        return key;
    }
    public IvParameterSpec getIv() {
        return iv;
    }

    public HexConverter(int questionNumber) throws FileNotFoundException {

        Scanner in = new Scanner(new File(FILENAME));
        String question, ciphertextString, keyString, ivString;
        while(in.hasNext()) {
            question = in.nextLine();
            if (question.contains("#" + questionNumber)) {

                // read key
                keyString = in.nextLine().split(":")[1].trim();
                key = byteArrayToSecretKey(hexStringToByteArray(keyString));

                // read ciphertext
                ciphertextString = in.nextLine().split(":")[1].trim();
                ciphertext = hexStringToByteArray(ciphertextString.substring(32));

                // read iv
                ivString = ciphertextString.substring(0, 32);
                iv = new IvParameterSpec(hexStringToByteArray(ivString));

                break;
            }
        }

    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private SecretKey byteArrayToSecretKey(byte[] byteArrayKey) {

        return new SecretKeySpec(byteArrayKey, "AES");
    }

    public static void main(String[] args) throws FileNotFoundException {

        HexConverter hc = new HexConverter(1);
        System.out.println(hc.key);
    }
}











