/*
 * The main aim of this class is to maintain a general functionality and enable to manage a client�s connections.
 */


import java.util.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;


/*
 * @author Daniils Sokolovs
 * @version 12/12/2021
 */

public class ClientHandler implements Runnable{
	
	//Creating an ArrayList of client's connection
	//When ever client sent a message we can loop through to the ArrayList and sent message to each client - broadcasting
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

	
	//Establish connection between client and server
	private Socket socket;
	//Read a messages sent by client
	private BufferedReader bufferedReader;
	//Sent messages to the client
	private BufferedWriter bufferedWriter;
	//Name of each client
	private String clientUsername;
	

	
	SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm:ss a dd/MM/yyyy");
	
	Date ClientHandlerLogDateTime = new Date();
	
	public ClientHandler(Socket socket) throws IOException {
		try {
			//object of the current class equal to the object of the server class
			this.socket = socket;
			//create stream of data sent as characters & sent message to the client
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			//Receiving messages from the client as characters stream
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//First from buffered reader is client name
			this.clientUsername = bufferedReader.readLine();
			//Add client to array list
			clientHandlers.add(this); // add object of the ClientHandler constructor
			
			//Creating a broad cast message once a new client joined to the chat
			broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");
			logFile(newFormat.format(ClientHandlerLogDateTime) + " SERVER: " + clientUsername + " has entered the chat!");
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			//close everything in case of error
			closeEverything(socket, bufferedReader, bufferedWriter);
			
		}
		
	}
	
	
	//Log file property, records general announcements from the server to the separate text file
	public void logFile(String fileInput) {
		try {
			File newFile = new File("ClientHandleLog.txt");
			FileWriter fileWriter = new FileWriter(newFile, true);
			BufferedWriter buffer = new BufferedWriter(fileWriter);
			PrintWriter printWriter = new PrintWriter(buffer);
			
			if(newFile.exists() == false) {
				newFile.createNewFile();
				System.out.println("A New file has been created");
			} else {
				System.out.println("File already exist");
			}
			
			printWriter.print(fileInput + "\n");
			printWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//Getter for client name
	public String getUser() {
		return this.clientUsername;
	}
	
	//Getter for output stream of data
	public BufferedWriter getOutputStream() {
		return this.bufferedWriter;
	}

	
	//Run method enable to start the thread.
	@Override
	public void run() {
		

		while (socket.isConnected()) {
			
			try {
				
				inputList();
				
				String messsageFromClient = bufferedReader.readLine();

				if(messsageFromClient.contains(">>") && !messsageFromClient.equals(">>")) {
				
					sendPrivateMessage(messsageFromClient);
				}
								
				 else {
					
					//program will hold until we receive a message from the client
					broadcastMessage(messsageFromClient);
				}
				

			} catch (IOException e) {
				
				try {
					closeEverything(socket, bufferedReader, bufferedWriter);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break; // out of the loop
			}
		}
	}

	
	//Method which enable to have actual messaging functionality.
	//It iterates through existing ArrayList of data and broadcasting the message among all users
	public void broadcastMessage (String messageToSend) throws IOException {
		
		//Loop through each object of the Array List
		for (ClientHandler clientHandler : clientHandlers) {
			try {
				
				//sent a broadcasting message to everyone except of sender
				if(!clientHandler.clientUsername.equals(clientUsername)) {
					clientHandler.bufferedWriter.write(messageToSend);
					//inform system there are no more data to be sent
					clientHandler.bufferedWriter.newLine();
					//Flush the buffer as normally it is stays until it is full
					clientHandler.bufferedWriter.flush();
				}
			} catch (IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
			}
			
		}
	}
	
	
	//Putting key words to the data stream in order to retrieve a list of users in Main.class
	public void inputList() throws IOException {
		
		StringBuilder listOfUsers = onlineUsers();
		
		for (ClientHandler clientHandler : clientHandlers) {
			PrintWriter sendNewUser = new PrintWriter(clientHandler.getOutputStream(), true);
			  sendNewUser.println("newUser");
			  sendNewUser.println(listOfUsers);
			  sendNewUser.println("finished");
		}
		
	}
	
	
	//Getting list of users within the ArrayList
	public StringBuilder onlineUsers() {

	    StringBuilder listOfUsers = new StringBuilder();
	    for(ClientHandler user : clientHandlers) {
	      listOfUsers.append(user.getUser() + "\n");
	    }
	    return listOfUsers;
	  }

	//Method enables have a private messaging functionality
	public void sendPrivateMessage(String messageIn) throws IOException {
		
		//Split array of string to the segments by using ">>" symbol
		String[] messageSegments = messageIn.split(">>");
		String username = messageSegments[0].split(" ")[1];
		String message = messageSegments[1];

		for(ClientHandler clientHandler : clientHandlers) {
			if(username.equalsIgnoreCase(clientHandler.getUser())) {
				String outMsg = "Private message from " + clientUsername + ": " + message;
				clientHandler.bufferedWriter.write(outMsg);
				clientHandler.bufferedWriter.newLine();
				//Flush the buffer as normally it is stays until it is full
				clientHandler.bufferedWriter.flush();

			}
		}
		
	}


	//Method enable to remove client from array list once connection is closed
	public void removeClientHandler() throws IOException {
		
		//remove client
		clientHandlers.remove(this);
		broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
		logFile(newFormat.format(ClientHandlerLogDateTime) + " SERVER: " + clientUsername + " has left the chat!");
		inputList();
	}
	
	
	//Close everything
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IOException {
		
		removeClientHandler();
		try {
			if(bufferedReader != null) {
				bufferedReader.close();
			}
			if(bufferedWriter != null) {
				bufferedWriter.close();
			}
			//closing each socket closing its output / input stream
			if(socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
