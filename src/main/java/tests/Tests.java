package tests;

import networking.*;
import ai.*;

import java.awt.image.BufferedImage;
import java.net.InetAddress;


public class Tests {

    public static void main(String[] args) throws Exception{
        testServer();
        testClient();
        //testNeuralNet();
    }

    private static void testServer(){
        try {
            Server server = new Server();
            server.start();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private static void testClient(){
        try {
            Client client = new Client(InetAddress.getLocalHost().getHostAddress());
            client.start();
            Client client2 = new Client(InetAddress.getLocalHost().getHostAddress());
            client2.start();
            try {
                Thread.sleep(1000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            for(long x = 0; x < 100; x++){

                client.sendMessage("Hello");
                client2.sendMessage("h2");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
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
}
