package networking;
//Look at this link: http://cs.lmu.edu/~ray/notes/javanetexamples/

import game.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
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

   

}