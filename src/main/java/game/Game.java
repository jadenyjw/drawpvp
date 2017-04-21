package game;

import java.util.*;

public class Game {

    protected Player currentPlayer;
    protected ArrayList<Player> players = new ArrayList<Player>();
    protected ArrayList<Player> finishedPlayers = new ArrayList<Player>();
    protected ArrayList<Integer> drawings = new ArrayList<Integer>();
    protected boolean gameStarted = false; //False means still in lobby.
    protected boolean gameEnded = false;
    protected static final int numDrawings = 5;

    public Game(){
        Random rand = new Random();
        for(int x = 0; x < numDrawings; x++){
            drawings.add(rand.nextInt(250));
        }
    }

    public ArrayList<Integer> getDrawings(){
        return drawings;
    }

    //Checks if all players are finished their drawings.
    public boolean gameEnded()
    {
        return(players.size() == finishedPlayers.size());
    }

    //Exit the lobby and deny incoming joiners.
    public void startGame(){
        this.gameStarted = true;
        //Do other stuff.
    }

    //Get a list of current players.
    public ArrayList<Player> getPlayers(){
        return this.players;
    }
    //Checks if drawing is correct.
    public boolean isCorrectDrawing(Player player, int n){
        return (n == drawings.get(player.getDrawingNum()));
    }

    //Joins the player to the game if it hasn't started yet.
    public boolean playerJoin(Player player){
        if(!gameStarted){
            players.add(player);
            return true;
        }
        else{
            return false;
        }
    }

    //The player has finished all their drawings.
    public void playerFinished(Player player){
        finishedPlayers.add(player);
    }

    //The player has left the game.
    public void playerLeave(Player player){
        players.remove(player);
        finishedPlayers.remove(player);
    }

    public boolean gameStarted(){
        return gameStarted;
    }
    //Get the maximum number of drawings.
    public static int getNumDrawings(){
        return numDrawings;
    }



}
