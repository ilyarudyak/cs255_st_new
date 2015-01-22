import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;


/**
 * Created by ilyarudyak on 20/01/15.
 */
public class Decryptor {

    public String decryptWithAes(
            byte[] ciphertext, SecretKey key, IvParameterSpec iv, String mode)
            throws Exception {

        ByteArrayInputStream in = new ByteArrayInputStream(ciphertext);
        Cipher aes = Cipher.getInstance(mode);
        aes.init(Cipher.DECRYPT_MODE, key, iv);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new CipherInputStream(in, aes)));


        try {
            return br.readLine();
        } finally {
            br.close();
        }
    }

    public static void main(String[] args) throws Exception {

        Decryptor d = new Decryptor();
        HexConverter hc = new HexConverter(4);
//        String plaintext = d.decryptWithAes(hc.getCiphertext(), hc.getKey(),
//                hc.getIv(), "AES/CBC/PKCS5Padding");
        String plaintext = d.decryptWithAes(hc.getCiphertext(), hc.getKey(),
                hc.getIv(), "AES/CTR/NoPadding");
        System.out.println(plaintext);

    }
}











