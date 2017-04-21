package networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import game.*;

import java.util.UUID;


public class ServerListener extends Listener {

    protected Game game;

    public ServerListener(Game game){
        this.game = game;
    }
    public void connected(Connection c) {

    }

    // Disconnection handling
    public void disconnected(Connection c) {

    }

    public void received(Connection c, Object o){

        if(o instanceof Packets.JoinRequest) {
            if (!game.gameStarted()) {
                game.playerJoin(new Player(UUID.randomUUID(), ((Packets.JoinRequest) o).username));
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
