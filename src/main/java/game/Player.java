package game;

import java.net.*;
import java.io.*;

public class Player implements Runnable{

    protected String username;
    protected int score = 0;
    protected int drawingNum = 0;
    protected Socket socket;
    protected BufferedReader input;
    protected PrintWriter output;

    public void run() {

    }







    public void addPoints(int n){
        this.score += n;
    }

    public void nextDrawing(){
        this.drawingNum++;
    }
    public int getDrawingNum(){
        return drawingNum;
    }



}

