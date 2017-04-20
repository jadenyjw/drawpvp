package networking;


import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class DClient {
    protected static final int port = 42069;
    public DClient(String ip){
        try {
            Client client = new Client();
            client.start();
            client.connect(5000, ip, port);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
