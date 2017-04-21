package networking;


import com.esotericsoftware.kryo.Kryo;

public class Packets {
    class JoinRequest{
        String username;
    }
    class Drawing{
        int id;
    }

    public static void registerPackets(Kryo kryo){

    }
}
