package tests;

import networking.*;

import java.net.InetAddress;

public class Tests {

    public static void main(String[] args) throws Exception{
        testServer();
        testClient();
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
}
