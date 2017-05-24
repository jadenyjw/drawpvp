package networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import game.Game;

import java.io.IOException;

public class DServer {
    protected static final int port = 42069;
    protected Server server;
    public Server listener;
    public DServer(){
        try{
            server = new Server();
            server.start();
            server.bind(port);
            registerPackets();
            ServerListener listener = new ServerListener();
            listener.setGame(new Game(listener));
            server.addListener(listener);

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    //Registers packets for Kryonet.
    private void registerPackets() {
        Kryo kryo = server.getKryo();
        Packets.registerPackets(kryo);
    }

    public void close(){
        try{
            server.dispose();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
