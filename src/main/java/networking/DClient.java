package networking;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;

public class DClient {
    protected static final int port = 42069;
    protected Client client;
    protected String username;

    public DClient(String ip, String username){
        try {
            this.username = username;
            client = new Client();
            client.start();
            registerPackets();
            client.connect(5000, ip, port);
            client.addListener(new ClientListener());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void joinGame(){
        Packets.JoinRequest joinPacket = new Packets.JoinRequest();
        joinPacket.username = username;
        client.sendTCP(joinPacket);
    }

    public void leaveGame(){
        try {
            client.dispose();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void registerPackets() {
        Kryo kryo = client.getKryo();
        Packets.registerPackets(kryo);
    }
}
