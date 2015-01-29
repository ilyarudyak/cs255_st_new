import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Created by ilyarudyak on 29/01/15.
 */
public class Oracle {

    private static final String BASE_URI = "http://crypto-class.appspot.com/po?er=";

    public static int getHttpCodeInt(int[] b) throws IOException {

        // create URI and URL for our get request
        String ctext = HexConverter.hexArrayToHexString(b);
        URL url = URI.create(BASE_URI + ctext).toURL();

        // create http request and get response code
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection.getResponseCode();
    }

    public static int getHttpCodeByte(byte[] b) throws IOException {

        // create URI and URL for our get request
        String ctext = Utility.byteArrayToHexString(b);
        URL url = URI.create(BASE_URI + ctext).toURL();

        // create http request and get response code
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection.getResponseCode();
    }


}
