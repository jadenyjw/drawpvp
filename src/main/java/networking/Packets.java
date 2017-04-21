package networking;


import com.esotericsoftware.kryo.Kryo;

public class Packets {
    public static class JoinRequest{
        String username;
    }
    public static class JoinResponse{
        boolean accepted;
    }
    public static class PlayerJoinedNotification{
        String username;
    }
    public static class PlayerLeftNotification{
        String username;
    }
    public static class Drawing{
        int id;
    }

    public static class GameStarter{

    }

    public static void registerPackets(Kryo kryo){
        kryo.register(JoinRequest.class);
        kryo.register(JoinResponse.class);
        kryo.register(Drawing.class);
        kryo.register(PlayerJoinedNotification.class);
        kryo.register(PlayerLeftNotification.class);
        kryo.register(GameStarter.class);
    }
}
