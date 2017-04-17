package networking;
//Look at this link: http://cs.lmu.edu/~ray/notes/javanetexamples/

import game.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;



public class Server extends Thread{

    protected static final int port = 42069;
    protected Game game;
    protected ServerSocket serverSocket;
    protected Socket socket;

    public Server() throws Exception{
        game = new Game();
        try{
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e){
            System.out.println(e);
            return;
        }


    }
    public void run(){

        while(!game.gameStarted()){

            try{
                socket = serverSocket.accept();
            }
            catch(IOException e){
                System.out.println(e);
            }

            game.playerJoin(new Player(socket));


        }


    }


    public int requestNextDrawing(Player player) {

        if (player.getDrawingNum() < Game.getNumDrawings() - 1){
            return game.getDrawings().get(player.nextDrawing());
        }
        else{
            game.playerFinished(player);
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