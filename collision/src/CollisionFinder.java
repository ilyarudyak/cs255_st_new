import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ilyarudyak on 06/02/15.
 */
public class CollisionFinder {

    private byte[] x1 = Utility.hexStringToByteArray("00000000000000000000000000000001"); // plain
    private byte[] y1 = Utility.hexStringToByteArray("00000000000000000000000000000000"); // key
    private byte[] x2;
    private byte[] y2 = Utility.hexStringToByteArray("00000000000000000000000000000001");

    public static void main(String[] args)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException,
            BadPaddingException, IllegalBlockSizeException {

        CollisionFinder cf = new CollisionFinder();
        System.out.println(Utility.encryptWithAES(cf.x1,
                Utility.byteArrayToSecretKey(cf.y1), new IvParameterSpec(cf.y1), "AES/CTR/NoPadding"));

        System.out.println("58e2fccefa7e3061367f1d57a4e7455a".length());
    }
}
