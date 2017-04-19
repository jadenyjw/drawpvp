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
			while(true){
				String clientCommand = in.readUTF();
				processServerResponse(clientCommand);
			}
		}
		catch(IOException e){
			System.out.println("Could not join game.");
		}
	}

	//Gets its own IP Address.
	public static String getIpAddr() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

	public void processServerResponse(String s){
		
	}


	public int requestDrawing(){
		sendMessage("REQUEST_DRAWING");
		return Integer.parseInt(waitResponse());
	}

	public void sendCorrectDrawing(){
		sendMessage("CORRECT_DRAWING");
		requestDrawing();
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
	public String waitResponse(){
		String response = null;
		try{
			response = in.readUTF();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return response;

	}



}
