package game;

import java.util.*;

public class Game {

    protected Player currentPlayer;
    protected ArrayList<Player> players = new ArrayList<Player>();
    protected boolean gameStarted = false; //False means still in lobby.
    
    public boolean hasWinner()
    {
        return true;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }
    public void gameStart(){
        this.gameStarted = true;
        //Do other stuff.
    }

    private boolean playerJoin(Player player){
        if(!gameStarted){
            players.add(player);
            return true;
        }
        else{
            return false;
        }
    }
    private void playerLeave(Player player){
        players.remove(player);
    }

}
