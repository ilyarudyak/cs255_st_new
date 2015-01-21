import java.net.Socket;
import java.io.*;

public class Oracle {
    String hostname = "54.165.60.84";

    int macPort = 6668;
    Socket macSocket;
    DataOutputStream macOut;
    DataInputStream macIn;

    int vrfyPort = 6669;
    Socket vrfySocket;
    DataOutputStream vrfyOut;
    DataInputStream vrfyIn;

    public int connect() {
        try {
            macSocket = new Socket(hostname, macPort);
            macOut = new DataOutputStream(new BufferedOutputStream(macSocket.getOutputStream()));
            macIn = new DataInputStream(new BufferedInputStream(macSocket.getInputStream()));
            System.out.println("Connected to Mac server successfully.");
            vrfySocket = new Socket(hostname, vrfyPort);
            vrfyOut = new DataOutputStream(new BufferedOutputStream(vrfySocket.getOutputStream()));
            vrfyIn = new DataInputStream(new BufferedInputStream(vrfySocket.getInputStream()));
            System.out.println("Connected to Vrfy server successfully.");
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int disconnect() {
        try {
            macSocket.close();
            vrfySocket.close();
            System.out.println("Connections closed successfully.");
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Packet Structure: < mlength(1) || message(mlength) || null-terminator(1) >
    public byte[] mac(byte[] message, int mlength){
        byte[] packet = new byte[mlength+2];
        byte[] t = new byte[16];

        packet[0] = (byte)mlength;
        for (int i=0; i<mlength; i++){
            packet[i+1]=(byte)message[i];
        }
        packet[mlength+1] = (byte)0;

        try{
            macOut.write(packet);
            macOut.flush();
            macIn.read(t,0,16);
        } catch (IOException e){
            e.printStackTrace();
        }

        return t;
    }

    //Packet Structure: < mlength(1) || message(mlength) || tag(16) || null-terminator(1) >
    public boolean verify(byte[] message, int mlength, byte[] tag){
        byte[] packet = new byte[mlength+18];
        byte[] result = new byte[2];

        packet[0] = (byte)mlength;
        for (int i=0; i<mlength; i++){
            packet[i+1]=(byte)message[i];
        }
        for (int i=0; i<16; i++){
            packet[mlength+i+1]=(byte)tag[i];
        }
        packet[mlength+17]=(byte)0;

        try{
            vrfyOut.write(packet);
            vrfyOut.flush();
            vrfyIn.read(result,0,2);
        } catch (IOException e){
            e.printStackTrace();
        }
        return Integer.valueOf(new String(result).replaceAll("\0", ""))==1;
    }

}
