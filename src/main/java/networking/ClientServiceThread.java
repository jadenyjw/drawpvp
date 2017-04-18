package networking;


import java.io.*;
import java.net.Socket;

public class ClientServiceThread extends Thread{
    protected Socket socket;
    protected boolean gameStarted;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    public ClientServiceThread(Socket s) {
        socket = s;
    }
    public void run() {


        boolean m_bRunThread = true;
        System.out.println("Accepted Client Address - " + socket.getInetAddress().getHostAddress());
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

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
