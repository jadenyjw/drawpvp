package networking;


import java.io.*;
import java.net.Socket;

public class ClientServiceThread extends Thread{
    protected Socket socket;
    protected boolean gameStarted;

    public ClientServiceThread(){
        super();
    }
    ClientServiceThread(Socket s) {
        socket = s;
    }
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        boolean m_bRunThread = true;
        System.out.println("Accepted Client Address - " + socket.getInetAddress().getHostName());
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            while(m_bRunThread) {
                String clientCommand = in.readUTF();
                System.out.println("Client Says :" + clientCommand);

                if(!gameStarted) {
                    out.writeUTF("GAME_ALREADY_STARTED");
                    out.flush();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
