package game;

import java.util.*;

public class Game {

    protected Player currentPlayer;
    protected ArrayList<Player> players = new ArrayList<Player>();
    protected ArrayList<Player> finishedPlayers = new ArrayList<Player>();
    protected ArrayList<Integer> drawings = new ArrayList<Integer>();
    protected boolean gameStarted = false; //False means still in lobby.
    protected boolean hasWinner = false;
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
    public boolean hasWinner()
    {
        return hasWinner;
    }

    public void gameStart(){
        this.gameStarted = true;
        //Do other stuff.
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    public boolean isCorrectDrawing(Player player, int n){
        return (n == drawings.get(player.getDrawingNum()));
    }

    public boolean playerJoin(Player player){
        if(!gameStarted){
            players.add(player);
            return true;
        }
        else{
            return false;
        }
    }

    public void playerFinished(Player player){
        finishedPlayers.add(player);
    }

    public void playerLeave(Player player){
        players.remove(player);
        finishedPlayers.remove(player);
    }

    public static int getNumDrawings(){
        return numDrawings;
    }



}
