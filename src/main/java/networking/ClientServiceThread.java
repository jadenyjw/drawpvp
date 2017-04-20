package networking;


import game.Game;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientServiceThread extends Thread{
    protected Socket socket;
    protected boolean gameStarted;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected Server server;

    public ClientServiceThread(Socket s, Game game, ArrayList<ClientServiceThread> threads) {
        this.socket = s;
        this.server = server;
    }
    public void run() {


        boolean m_bRunThread = true;
        System.out.println("Accepted Client Address - " + socket.getInetAddress().getHostAddress());
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while(m_bRunThread) {
                String clientCommand = in.readUTF();
                processClientResponse(clientCommand);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void processClientResponse(String s){

    }

    public void sendMessage(String msg){
        try {
            out.writeUTF(msg);
            out.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public String waitResponse(){
        String response = null;
        try{
            response = in.readUTF();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return response;

    }
}
