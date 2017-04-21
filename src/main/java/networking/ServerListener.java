package networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import game.Game;


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


        if(!game.gameStarted()){

        }

    }

}
