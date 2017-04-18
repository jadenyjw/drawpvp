package tests;

import networking.*;
import ai.*;

import java.awt.image.BufferedImage;
import java.net.InetAddress;


public class Tests {

    public static void main(String[] args) throws Exception{
        testServer();
        //testClient();
        //testNeuralNet();
    }

    private static void testServer(){
        try {
            Server server = new Server();
            System.out.println("Test");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private static void testClient(){
        try {
            Client client = new Client(InetAddress.getLocalHost());
        }
        catch (Exception e){
            System.out.println(e);
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
