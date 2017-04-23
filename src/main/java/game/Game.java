package game;


import networking.Packets;
import networking.ServerListener;

import java.util.*;

public class Game {

    protected ArrayList<Player> players = new ArrayList<Player>();
    protected ArrayList<Player> finishedPlayers = new ArrayList<Player>();
    protected ArrayList<Integer> drawings = new ArrayList<Integer>();
    protected boolean gameStarted = false; //False means still in lobby.
    protected static final int numDrawings = 5;
    protected ServerListener listener;

    public Game(ServerListener listener){
        this.listener = listener;
        Random rand = new Random();
        for(int x = 0; x < numDrawings; x++){
            drawings.add(rand.nextInt(250));
        }
    }

    public ArrayList<Integer> getDrawings(){
        return drawings;
    }

    //Checks if all players are finished their drawings.
    public void gameEnded()
    {
        Packets.GameEnder ender = new Packets.GameEnder();
        ender.players = new String[finishedPlayers.size()];
        for(int x = 0; x < finishedPlayers.size(); x++){
            ender.players[x] = finishedPlayers.get(x).getUsername();
        }
        listener.sendToAllClients(ender);
        gameStarted = false;
    }

    //Exit the lobby and deny incoming joiners.
    public void startGame(){
        this.gameStarted = true;
        System.out.println("Game has started.");

        listener.sendToAllClients(new Packets.GameStarter());
        for(int x = 0; x < listener.pairs.size(); x++){
            Packets.Drawing drawing = new Packets.Drawing();
            drawing.id = drawings.get(listener.pairs.get(x).player.getDrawingNum());
            listener.pairs.get(x).connection.sendTCP(drawing);
        }

    }

    //Get a list of current players.
    public ArrayList<Player> getPlayers(){
        return this.players;
    }
    
    //Handles correct player drawings.
    public void playerCorrectDrawing(Player player){
        if(player.getDrawingNum() < numDrawings - 1){
            player.nextDrawing();
            for(int x = 0; x < listener.pairs.size(); x++){
                if(listener.pairs.get(x).player.equals(player)){
                    Packets.Drawing drawing = new Packets.Drawing();
                    drawing.id = drawings.get(listener.pairs.get(x).player.getDrawingNum());
                    listener.pairs.get(x).connection.sendTCP(drawing);
                }
            }
        }
        else{
            Packets.DrawingsCompleted completed = new Packets.DrawingsCompleted();
            for(int x = 0; x < listener.pairs.size(); x++){
                if(listener.pairs.get(x).player.equals(player)){
                    finishedPlayers.add(listener.pairs.get(x).player);
                    listener.pairs.get(x).connection.sendTCP(completed);
                }
            }

            //Check for game finished.

            if(finishedPlayers.size() == players.size()){
                gameEnded();
            }

        }
    }

    //Joins the player to the game if it hasn't started yet.
    public void playerJoin(Player player){
        players.add(player);
        System.out.println("Game: " + player.username + " has joined the game.");
    }

    //The player has finished all their drawings.
    public void playerFinished(Player player){
        finishedPlayers.add(player);
    }

    //The player has left the game.
    public void playerLeave(Player player){
        players.remove(player);
        finishedPlayers.remove(player);
        System.out.println(player.username + " has left the game.");
    }

    public boolean gameStarted(){
        return gameStarted;
    }
    //Get the maximum number of drawings.
    public static int getNumDrawings(){
        return numDrawings;
    }

}
