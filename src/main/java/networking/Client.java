package networking;
import java.io.*;
import java.net.*;

public class Client {
	
	final private int port = 42069;
	public Client(InetAddress ip) throws Exception{

	}
	public static String getIpAddr() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

}
