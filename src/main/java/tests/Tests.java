package tests;

import ai.*;
import gui.Notification;
import networking.DClient;
import networking.DServer;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Tests {

    public static void main(String[] args) throws Exception{
        //testServer();
        //testClient();
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
            client.startGame();
            stay(50);

            for(int x = 0; x < 1000000; x++){
                client.startGame();
                for(int y = 0; y < 5; y++){
                    client.sendCorrectDrawing();
                    stay(1);

                }
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void stay(int ms){
        try{
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
