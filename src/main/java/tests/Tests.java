package tests;

import ai.*;
import networking.DClient;
import networking.DServer;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Tests {

    public static void main(String[] args) throws Exception{
        testServer();
        testClient();
        //testNeuralNet();
    }

    private static void testNeuralNet(){
        try {
            NeuralNet net = new NeuralNet();
            BufferedImage image = new BufferedImage(225, 225, BufferedImage.TYPE_BYTE_GRAY);
            System.out.println(net.checkDrawing(image, 248));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void testServer(){
        DServer server = new DServer();
    }
    private static void testClient() {
        try {
            DClient client = new DClient(InetAddress.getLocalHost().getHostAddress(), "Bob");
            client.joinGame();
            stay(500);
            DClient client2 = new DClient(InetAddress.getLocalHost().getHostAddress(), "John");
            client2.joinGame();
            stay(500);
            client.startGame();
            client2.sendChatMessage("Hello.");
            stay(500);
            client.sendChatMessage("Bye.");
            stay(500);
            for(int x = 0; x < 5; x++){
                client.sendCorrectDrawing();
                stay(500);
                client2.sendCorrectDrawing();
                stay(500);
            }

            client2.leaveGame();
            stay(500);
            client.startGame();
            for(int x = 0; x < 5; x++){
                client.sendCorrectDrawing();
                stay(300);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void stay(long n){
        try{
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
