package game;

import java.net.*;
import java.io.*;

public class Player{

    protected String username;
    protected int score = 0;
    protected int drawingNum = 0;
    protected Socket socket;
    protected boolean connected;
    protected Inport inport;
    public void run() {

    }
    public Player(Socket newSocket){

        // Set properties
        socket = newSocket;
        connected = true;
       // Get input
        inport = new Inport();
        inport.start();
    }

    private class Inport extends Thread{
        private ObjectInputStream in;
        public void run(){
            try{
                in = new ObjectInputStream(socket.getInputStream());
            }
            catch(IOException e){
                System.out.println(e);
                return;
            }
            while(true){

            }
        }
    }



    public boolean isConnected(){
        return connected;
    }

    public void purge(){
        try{
            connected = false;
            socket.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
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

