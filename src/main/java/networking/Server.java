package networking;
//Look at this link: http://cs.lmu.edu/~ray/notes/javanetexamples/

import game.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

import ai.*;
import org.datavec.image.loader.*;


public class Server{

    protected static final int port = 42069;
    protected Game game;
    protected NeuralNet network;
    protected ImageLoader loader;
    public Server() throws Exception{
        network = new NeuralNet();
        game = new Game();
        loader = new ImageLoader();
    }

    public void processPlayerDrawing(BufferedImage image, Player player){

        int[] prediction = network.getGraph().predict(loader.asMatrix(image));
        if(game.isCorrectDrawing(player, prediction[0])){

        }
    }

    public int requestNextDrawing(Player player) {

        if (player.getDrawingNum() < Game.getNumDrawings() - 1){
            return game.getDrawings().get(player.nextDrawing());
        }
        else{

            return -1; //This means that they are done.
        }
    }

    public ArrayList<Player> requestPlayers(){
        return game.getPlayers();
    }

    public void addPlayerToGame(Player player){
        if (game.playerJoin(player)){
            System.out.println("Game joined successfully!");
        }
        else{
            System.out.println("The game you are trying to join has already started.");
        }
    }
    public void removePlayerFromGame(Player player){
        game.playerLeave(player);
    }

   

}