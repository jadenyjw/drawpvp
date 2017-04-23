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

    //Tell the server that the correct drawing has been drawn.
    public void sendCorrectDrawing(){
        Packets.CorrectDrawing drawing = new Packets.CorrectDrawing();
        client.sendTCP(drawing);
    }

    //Sends a chat message to the server.
    public void sendChatMessage(String msg){
        Packets.ChatMessage message = new Packets.ChatMessage();
        message.message = msg;
        client.sendTCP(message);
    }

    //Leaves the game.
    public void leaveGame(){

            client.close();


    }

    //Registers the packets for Kryonet.
    private void registerPackets() {
        Kryo kryo = client.getKryo();
        Packets.registerPackets(kryo);
    }
}
