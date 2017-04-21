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

        //Received a notification that a new player has joined.
        if(o instanceof Packets.PlayerJoinedNotification){
            System.out.println("Client: " + ((Packets.PlayerJoinedNotification) o).username + " has joined the game.");
        }

        //Receives a response of the server whether or not joining was successful.
        if(o instanceof Packets.JoinResponse){
            if(((Packets.JoinResponse)o).accepted){
                System.out.println("Client successfully joined.");
            }
            else{
                System.out.println("Game has already started. Join failed.");
            }
        }

        //Received a notification that the game has started.
        if(o instanceof Packets.GameStarter){
            System.out.println("The host has started the game.");
        }

    }


}
