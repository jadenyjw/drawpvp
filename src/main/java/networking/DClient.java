package networking;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

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

    //Joins the game created.
    public void joinGame(){
        Packets.JoinRequest joinPacket = new Packets.JoinRequest();
        joinPacket.username = username;
        client.sendTCP(joinPacket);
    }

    //Initiates a call to start the game.
    public void startGame(){
        Packets.GameStarter starter = new Packets.GameStarter();
        client.sendTCP(starter);
    }

    public void sendCorrectDrawing(){
        Packets.CorrectDrawing drawing = new Packets.CorrectDrawing();
        client.sendTCP(drawing);
    }

    //Leaves the game.
    public void leaveGame(){
        try {
            client.dispose();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //Registers the packets for Kryonet.
    private void registerPackets() {
        Kryo kryo = client.getKryo();
        Packets.registerPackets(kryo);
    }
}
