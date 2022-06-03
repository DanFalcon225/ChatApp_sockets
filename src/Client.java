/*
 * The main aim of this class is to give access to the client�s properties through the stream of data.
 */

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * @author Daniils Sokolovs
 * @version 12/12/2021
 */

public class Client {
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private PrintWriter outPutStream;
	
	//Establish a connection with Server
	  public void startConnection(String hostname, int port, String username) throws IOException {
		    socket = new Socket(hostname, port);
		    outPutStream = new PrintWriter(socket.getOutputStream(), true);
		    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    outPutStream.println(username);
		  }
	  
	  //Creating a user message object and getting access to it through output stream
	  public void sendMessage(String username, String message) {
		  outPutStream.println(username + ": " + message);
		  }
	
	  //Creating object of input stream to enable to read data from the stream
	  public String getIn() throws IOException {
	    return this.bufferedReader.readLine();
	  }
	  
	  

}
