package networking;

import com.esotericsoftware.kryonet.Connection;
import game.Player;

//Used for keeping track of players and their respective connections.
public class ConPlayerPair{
    public Connection connection;
    public Player player;

    public ConPlayerPair(Connection c, Player p){
        this.connection = c;
        this.player = p;
    }
}