package networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import game.*;

import java.util.ArrayList;
import java.util.UUID;


public class ServerListener extends Listener {

    protected Game game;
    protected DServer server;
    protected ArrayList<ConPlayerPair> pairs = new ArrayList<ConPlayerPair>();

    public ServerListener(Game game){
        this.game = game;
    }
    public void connected(Connection c) {

    }

    // Disconnection handling
    public void disconnected(Connection c) {
        for(int x = 0; x < pairs.size(); x++){
            if(pairs.get(x).connection.equals(c)){
                game.playerLeave(pairs.get(x).player);
                Packets.PlayerLeftNotification notif = new Packets.PlayerLeftNotification();
                notif.username = pairs.get(x).player.getUsername();
                pairs.remove(c);
                sendToAllClients(notif);
                break;
            }
        }
    }

    public void received(Connection c, Object o){

        //Receives a request to join the game.
        if(o instanceof Packets.JoinRequest) {
            if (!game.gameStarted()) {
                Player player = new Player(UUID.randomUUID(), ((Packets.JoinRequest) o).username);
                pairs.add(new ConPlayerPair(c, player));
                game.playerJoin(player);
                Packets.JoinResponse response = new Packets.JoinResponse();
                response.accepted = true;
                c.sendTCP(response);
                Packets.PlayerJoinedNotification notif = new Packets.PlayerJoinedNotification();
                notif.username = ((Packets.JoinRequest) o).username;
                sendToAllClients(notif);
            }
            else{
                Packets.JoinResponse response = new Packets.JoinResponse();
                response.accepted = false;
                c.sendTCP(response);
                c.close();
            }
        }

        //Received a call to initiate the game.
        if(o instanceof Packets.GameStarter){
            game.startGame();
            sendToAllClients(new Packets.GameStarter());
        }

    }

    //Broadcast a message to all of the clients.
    public void sendToAllClients(Object o){
        for(int x = 0; x < pairs.size(); x++){
            pairs.get(x).connection.sendTCP(o);
        }
    }

}

//Used for keeping track of players and their respective connections.
class ConPlayerPair{
    Connection connection;
    Player player;

    public ConPlayerPair(Connection c, Player p){
        this.connection = c;
        this.player = p;
    }
}
