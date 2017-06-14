package networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import game.*;

import java.util.ArrayList;


public class ServerListener extends Listener {

    public Game game;
    public ArrayList<ConPlayerPair> pairs = new ArrayList<ConPlayerPair>();

    public ServerListener(){

    }

    public void setGame(Game game){
        this.game = game;
    }
    public void connected(Connection c) {

    }

    // Disconnection handling
    public void disconnected(Connection c) {
        for(int x = 0; x < pairs.size(); x++){
            if(pairs.get(x).connection.equals(c)){
                game.playerLeave(pairs.get(x).player);
                Packets.PlayerUpdate notif = new Packets.PlayerUpdate();
                notif.players = new String[pairs.size() - 1];
                ArrayList<String> players = new ArrayList<String>();

                for(int y = 0; y < pairs.size(); y++){
                    if(!pairs.get(x).player.getUsername().equals(pairs.get(y).player.getUsername())){
                        players.add(pairs.get(y).player.getUsername());
                    }
                }
                notif.players = players.toArray(new String[players.size()]);

                pairs.remove(x);
                sendToAllClients(notif);
                break;
            }
        }
    }

    public void received(Connection c, Object o){

        if(o instanceof Packets.RequestPlayers){
            Packets.PlayerUpdate notif = new Packets.PlayerUpdate();
            String[] players = new String[pairs.size()];
            for(int x = 0; x < pairs.size(); x++){
                players[x] = pairs.get(x).player.getUsername();
            }
            notif.players = players;
            c.sendTCP(notif);
        }

        //Receives a request to join the game.
        if(o instanceof Packets.JoinRequest) {
            //Responds depending on whether or not the game has started.
            if (!game.gameStarted()) {
                Player player = new Player(((Packets.JoinRequest) o).username);
                pairs.add(new ConPlayerPair(c, player));
                game.playerJoin(player);
                Packets.JoinResponse response = new Packets.JoinResponse();
                response.accepted = true;
                c.sendTCP(response);

                Packets.PlayerUpdate notif = new Packets.PlayerUpdate();
                String[] players = new String[pairs.size()];
                for(int x = 0; x < pairs.size(); x++){
                    players[x] = pairs.get(x).player.getUsername();
                }
                notif.players = players;
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
        else if(o instanceof Packets.GameStarter){
            game.startGame();

        }

        //Receives a correct drawing acknowledgement from the client.
        else if(o instanceof Packets.CorrectDrawing){
            for(int x = 0; x < pairs.size(); x++){
                if(pairs.get(x).connection.equals(c)){
                    game.playerCorrectDrawing(pairs.get(x).player);
                    break;
                }
            }
        }

        //Receives a chat message from the server.
        else if(o instanceof Packets.ChatMessage){
            String username = null;
            for(int x = 0; x < pairs.size(); x++){
                if(pairs.get(x).connection.equals(c)){
                    username = pairs.get(x).player.getUsername();
                }
            }

            //Broadcasts that chat message to all players.
            String msg = ((Packets.ChatMessage) o).message;
            Packets.ChatMessage newMessage = new Packets.ChatMessage();
            newMessage.message = username + ": " + msg;
            sendToAllClients(newMessage);

        }

    }

    //Broadcast a message to all of the clients.
    public void sendToAllClients(Object o){

        for(int x = 0; x < pairs.size(); x++){
            pairs.get(x).connection.sendTCP(o);
        }
    }

}


