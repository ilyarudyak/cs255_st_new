import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by ilyarudyak on 22/01/15.
 */
public class FileHash {

    private static final int BLOCK_SIZE = 1024;
    private static final int HASH_SIZE = 32;
    private static final String FILENAME = "data/6-1";

    private byte[] bytesFromFile;
    private byte[] lastBlock;
    private int numberOfFullBlocks;

    public FileHash() throws IOException {

        bytesFromFile = Files.readAllBytes(Paths.get(FILENAME));
        lastBlock = getLastBlock();

    }

    public byte[] calculateH0() throws NoSuchAlgorithmException {

        byte[] currentBlock;
        byte[] previousHash = sha256Hash(lastBlock);

        for(int i=numberOfFullBlocks; i>0; i--) {

            currentBlock = Arrays.copyOfRange(bytesFromFile, (i-1)*BLOCK_SIZE, i*BLOCK_SIZE);
            previousHash = sha256Hash(appendHash(currentBlock, previousHash));

        }
        return previousHash;

    }

    // ------------------------------ helper methods ------------------------------

    private byte[] getLastBlock() {

        numberOfFullBlocks = bytesFromFile.length / BLOCK_SIZE;
        int from = numberOfFullBlocks * BLOCK_SIZE;
        return Arrays.copyOfRange(bytesFromFile, from, bytesFromFile.length);
    }

    private byte[] sha256Hash(byte[] block) throws NoSuchAlgorithmException {

        // get message digest
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // calculate digest
        return md.digest(block);
    }

    // create new array of 1024+32 bytes and copy block and previous hash into it
    private byte[] appendHash(byte[] block, byte[] hash) {

        byte[] blockWithHash = new byte[BLOCK_SIZE+HASH_SIZE];
        System.arraycopy(block, 0, blockWithHash, 0, block.length);
        System.arraycopy(hash, 0, blockWithHash, block.length, hash.length);
        return blockWithHash;
    }

    // ------------------------------ helper methods ------------------------------

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        FileHash fh = new FileHash();

        System.out.println(Hex.encodeHex(fh.calculateH0()));

    } // main
}
