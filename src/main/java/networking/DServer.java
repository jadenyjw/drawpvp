package networking;

import com.esotericsoftware.kryonet.Server;
import game.Game;

import java.io.IOException;

public class DServer {
    protected static final int port = 42069;
    public DServer(){
        try{
            Server server = new Server();
            server.start();
            server.bind(port);
            server.addListener(new ServerListener(new Game()));
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
