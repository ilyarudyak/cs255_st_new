import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by ilyarudyak on 29/01/15.
 */
public class Test {

    public static void main(String[] args)
            throws URISyntaxException, IOException {

//        // question 4 from assignment 2
//        List<String> list = Files.readAllLines(Paths.get("data/q4"),
//                Charset.defaultCharset());
//
//        System.out.println(list);
//
//        int[] a0, a1;
//        for (int i=0; i<list.size(); i+=2) {
//            a0 = HexConverter.hexStringToHexArray(
//                    list.get(i).replaceAll("\\s+", ""));
//            System.out.print(HexConverter.hexArrayToHexString(a0) + " ");
//
//            a1 = HexConverter.hexStringToHexArray(
//                    list.get(i+1).replaceAll("\\s+", ""));
//            System.out.print(HexConverter.hexArrayToHexString(a1) + " ");
//
//            System.out.println(HexConverter.hexArrayToHexString(
//                    HexConverter.xorHexArrays(a0, a1)));
//        }

        // question 8 from assignment 2
        List<String> list = Files.readAllLines(Paths.get("data/q8"),
                Charset.defaultCharset());
        for (String s: list)
            System.out.println(s.length());


    }
}
