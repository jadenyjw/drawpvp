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
            System.out.println(e);
        }
    }
    private static void testServer(){
        DServer server = new DServer();
    }
    private static void testClient() {
        try {
            DClient client = new DClient(InetAddress.getLocalHost().getHostAddress(), "Bob");
            client.joinGame();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
