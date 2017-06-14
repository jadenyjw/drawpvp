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
    public static class PlayerUpdate{
        public String[] players;
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
    public static class ChatMessage{
        String message;
    }
    public static class RequestPlayers{

    }


    public static void registerPackets(Kryo kryo){
        kryo.register(JoinRequest.class);
        kryo.register(JoinResponse.class);
        kryo.register(Drawing.class);
        kryo.register(PlayerUpdate.class);
        kryo.register(GameStarter.class);
        kryo.register(CorrectDrawing.class);
        kryo.register(DrawingsCompleted.class);
        kryo.register(GameEnder.class);
        kryo.register(String[].class);
        kryo.register(ChatMessage.class);
        kryo.register(RequestPlayers.class);
    }
}
