package networking;
//Look at this link: http://cs.lmu.edu/~ray/notes/javanetexamples/

import java.io.*;
import java.net.*;


public class Server{
    final private int port = 42069;

    public Server() throws Exception{
        try
        {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server successfully started.");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

   

}