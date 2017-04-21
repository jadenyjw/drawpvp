package networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import game.*;

import java.util.ArrayList;
import java.util.UUID;


public class ServerListener extends Listener {

    protected Game game;
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
                break;
            }
        }
    }

    public void received(Connection c, Object o){

        if(o instanceof Packets.JoinRequest) {
            if (!game.gameStarted()) {
                Player player = new Player(UUID.randomUUID(), ((Packets.JoinRequest) o).username);
                pairs.add(new ConPlayerPair(c, player));
                game.playerJoin(player);
                Packets.JoinResponse response = new Packets.JoinResponse();
                response.accepted = true;
                c.sendTCP(response);
            }
        }
        else{
            Packets.JoinResponse response = new Packets.JoinResponse();
            response.accepted = false;
            c.sendTCP(response);
        }
    }

}

class ConPlayerPair{
    Connection connection;
    Player player;

    public ConPlayerPair(Connection c, Player p){
        this.connection = c;
        this.player = p;
    }
}
