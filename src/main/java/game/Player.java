package game;

import java.net.*;
import java.io.*;
import java.util.UUID;

public class Player{

    protected int drawingNum = 0;
    protected String username;

    public Player(){

    }
    public Player(String username){
        this.username = username;
    }

    public int nextDrawing(){
        return ++this.drawingNum;
    }
    public int getDrawingNum(){
        return drawingNum;
    }
    public void resetDrawingNum() {drawingNum = 0;}
    public String getUsername(){
        return username;
    }



}

