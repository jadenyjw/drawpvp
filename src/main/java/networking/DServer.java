package networking;

import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class DServer {
    protected static final int port = 42069;
    public DServer(){
        try{
            Server server = new Server();
            server.start();
            server.bind(port);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
