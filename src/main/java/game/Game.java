package game;


import gui.Main;
import gui.Notification;
import javafx.application.Platform;
import networking.Packets;
import networking.ServerListener;
import gui.GameController;

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
        generateDrawings();

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
            finishedPlayers.get(x).resetDrawingNum();
        }
        listener.sendToAllClients(ender);
        listener.setGame(new Game(listener));
        for(int x = 0; x < listener.pairs.size(); x++){
            listener.game.playerJoin(listener.pairs.get(x).player);
        }

    }

    //Exit the lobby and deny incoming joiners.
    public void startGame(){
        this.gameStarted = true;
        listener.sendToAllClients(new Packets.GameStarter());
        for(int x = 0; x < listener.pairs.size(); x++){
            Packets.PlayerUpdate notif = new Packets.PlayerUpdate();
            String[] players = new String[listener.pairs.size()];
            for(int y = 0; y < listener.pairs.size(); y++){
                players[x] = listener.pairs.get(y).player.getUsername();
            }
            notif.players = players;
            Packets.Drawing drawing = new Packets.Drawing();
            drawing.id = drawings.get(listener.pairs.get(x).player.getDrawingNum());
            listener.pairs.get(x).connection.sendTCP(drawing);
            listener.pairs.get(x).connection.sendTCP(notif);
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


    //The player has left the game.
    public void playerLeave(Player player){
        players.remove(player);
        finishedPlayers.remove(player);
        System.out.println(player.username + " has left the game.");
        //Main.game.showMessage("Player left",player.username + " has left the game.");
    }

    public boolean gameStarted(){
        return gameStarted;
    }

    public void generateDrawings(){
        Random rand = new Random();
        //Generates the drawings for the players.
        drawings.clear();
        for(int x = 0; x < numDrawings; x++){
            drawings.add(rand.nextInt(250));
        }
    }


}
