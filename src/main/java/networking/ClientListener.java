package networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {

    public void connected(Connection c) {

    }

    // Disconnection handling
    public void disconnected(Connection c) {

    }

    public void received(Connection c, Object o){
        if(o instanceof Packets.PlayerJoinedNotification){
            System.out.println("Client: " + ((Packets.PlayerJoinedNotification) o).username + " has joined the game.");
        }

        if(o instanceof Packets.JoinResponse){
            if(((Packets.JoinResponse)o).accepted){
                System.out.println("Client successfully joined.");
            }
            else{
                System.out.println("Game has already started. Join failed.");
            }
        }

    }


}
