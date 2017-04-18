package game;

import java.net.*;
import java.io.*;

public class Player{

    protected String username;
    protected int score = 0;
    protected int drawingNum = 0;

    public Player(){


    }


    public void addPoints(int n){
        this.score += n;
    }
    public int nextDrawing(){
        return ++this.drawingNum;
    }
    public int getDrawingNum(){
        return drawingNum;
    }



}

