package networking;


import com.esotericsoftware.kryo.Kryo;
import game.Player;

import java.util.ArrayList;

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
        public int id;
    }
    public static class CorrectDrawing{

    }
    public static class DrawingsCompleted{

    }
    public static class GameStarter{

    }

    public static class GameEnder{
        public String[] players;
    }

    public static void registerPackets(Kryo kryo){
        kryo.register(JoinRequest.class);
        kryo.register(JoinResponse.class);
        kryo.register(Drawing.class);
        kryo.register(PlayerJoinedNotification.class);
        kryo.register(PlayerLeftNotification.class);
        kryo.register(GameStarter.class);
        kryo.register(CorrectDrawing.class);
        kryo.register(DrawingsCompleted.class);
        kryo.register(GameEnder.class);
        kryo.register(String[].class);

    }
}
