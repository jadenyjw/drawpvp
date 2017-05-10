package networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import gui.HostJoinController;
import utilities.DataManipulation;

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
        else if(o instanceof Packets.JoinResponse){
            if(((Packets.JoinResponse)o).accepted){
                System.out.println("Client successfully joined.");
                HostJoinController.displayGameJoinSucceeded();
            }
            else{
                System.out.println("Game has already started. Join failed.");
                HostJoinController.displayGameJoinFailed();
            }
        }

        //Received a notification that the game has started.
        else if(o instanceof Packets.GameStarter){
            System.out.println("The host has started the game.");
        }

        //Received a notification that the game has ended.
        else if(o instanceof Packets.GameEnder){
            System.out.println("The game has ended. These are the standings:");
            String[] players = ((Packets.GameEnder) o).players;
            for(int x = 0; x < players.length; x++){
                System.out.println((x+1) + ". " + players[x]);
            }
        }

        //Receives a new drawing assignment from the server.
        else if(o instanceof Packets.Drawing){
            System.out.println("You have to draw a " + DataManipulation.idToString(((Packets.Drawing) o).id));
        }

        //Acknowledges that all drawings have been completed.
        else if(o instanceof Packets.DrawingsCompleted){
            System.out.println("You are done all drawings.");
        }

        //Receives a chat message.
        else if (o instanceof Packets.ChatMessage){
            System.out.println(((Packets.ChatMessage) o).message);
        }

    }


}
