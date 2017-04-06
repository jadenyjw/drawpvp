package game;
import ai.*;
import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;

import java.io.IOException;
import java.util.*;

public class Game {

    protected Player currentPlayer;
    protected ArrayList<Player> players = new ArrayList<Player>();
    protected boolean gameStarted = false; //False means still in lobby.

    public Game() throws UnsupportedKerasConfigurationException, IOException, InvalidKerasConfigurationException {
        NeuralNet net = new NeuralNet();
    }

    
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

    public boolean playerJoin(Player player){
        if(!gameStarted){
            players.add(player);
            return true;
        }
        else{
            return false;
        }
    }
    public void playerLeave(Player player){
        players.remove(player);
    }

}
