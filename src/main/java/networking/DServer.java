package networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import game.Game;

import java.io.IOException;

public class DServer {
    protected static final int port = 42069;
    protected Server server;

    public DServer(){
        try{
            server = new Server();
            server.start();
            server.bind(port);
            server.addListener(new ServerListener(new Game()));
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    private void registerPackets() {
        // register packets
        Kryo kryo = server.getKryo();
        Packets.registerPackets(kryo);
    }
}
