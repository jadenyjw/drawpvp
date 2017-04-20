package tests;

import ai.*;
import networking.DServer;

import java.awt.image.BufferedImage;

public class Tests {

    public static void main(String[] args) throws Exception{
        testServer();
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
}
