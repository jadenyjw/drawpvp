package networking;
import java.io.*;
import java.net.*;

public class Client {
	
	final private int port = 42069;
	private Socket socket;
	public Client(String ip){
		try{
			socket = new Socket(ip, port);
		}
		catch(IOException e){
			System.out.println("Could not join game.");
		}
	}

	//Gets its own IP Address.
	public static String getIpAddr() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

	//ToDo Asks the server for the drawing of the player.
	public int requestDrawing(){
		return 1;
	}

	//ToDo Sends the server to acknowledge the correct drawing has been drawn.
	public void sendCorrectDrawing(){

	}

}
