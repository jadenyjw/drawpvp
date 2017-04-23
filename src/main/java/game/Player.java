package game;

import java.net.*;
import java.io.*;
import java.util.UUID;

public class Player{

    protected int score = 0;
    protected int drawingNum = 0;
    protected UUID uuid;
    protected String username;

    public Player(){

    }
    public Player(UUID uuid, String username){
        this.uuid = uuid;
        this.username = username;
    }

    public void addPoints(int n){
        this.score += n;
    }
    public int getPoints(){
        return score;
    }
    public int nextDrawing(){
        return ++this.drawingNum;
    }
    public int getDrawingNum(){
        return drawingNum;
    }
    public String getUsername(){
        return username;
    }



}

