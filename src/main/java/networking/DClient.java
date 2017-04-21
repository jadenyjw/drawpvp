package networking;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class DClient {
    protected static final int port = 42069;
    protected Client client;
    public DClient(String ip){
        try {
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
    private void registerPackets() {
        Kryo kryo = client.getKryo();
        Packets.registerPackets(kryo);
    }
}
