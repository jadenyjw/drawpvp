package networking;
import java.io.*;
import java.net.*;

public class Client extends Thread{
	
	final protected int port = 42069;
	protected Socket socket;
	protected ObjectInputStream in = null;
	protected ObjectOutputStream out = null;
	protected String ip = null;

	public Client(String ip){
		this.ip = ip;
	}

	public void run(){
		try{
			socket = new Socket(ip, port);
			System.out.println("Connected to " + ip + " at port " + port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			System.out.println("created");
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

	public void sendMessage(String msg){
		try {

			out.writeUTF(msg);
			out.flush();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}



}
