package networking;


import com.esotericsoftware.kryo.Kryo;

public class Packets {
    public static class JoinRequest{
        String username;
    }
    public static class JoinResponse{
        boolean accepted;
    }
    public static class Drawing{
        int id;
    }

    public static void registerPackets(Kryo kryo){
        kryo.register(Packets.JoinRequest.class);
        kryo.register(Packets.JoinResponse.class);
        kryo.register(Packets.Drawing.class);
    }
}
