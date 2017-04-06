package tests;

import networking.*;
import ai.*;
import utilities.*;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.net.InetAddress;

public class Tests {

    public static void main(String[] args) throws Exception{
        //testServer();
        //testClient();
        testNeuralNet();
    }

    private static void testServer(){
        try {
            Server server = new Server();
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
            INDArray tens = Nd4j.zeros(1, 1, 225, 225).addi(255);
            NeuralNet net = new NeuralNet();
            int[] results = net.getGraph().predict(DataManipulation.dataPreprocess(tens));

            System.out.println(results[0] + " " + DataManipulation.idToString(results[0]));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
