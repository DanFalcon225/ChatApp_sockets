/*
 * The main of this class is to initialise a server and make it run for further client to connect to it
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;


import java.io.*;
import java.net.*;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * @author Daniils Sokolovs
 * @version 12/12/2021
 */

public class Server {

	private JPanel contentPane;
	private JTextField textFieldPort;
	private JFrame jFrame;
	private JTextArea textAreaServer;
	
	

	//Listening incoming connection
		private ServerSocket serverSocket;
		
		//Socket object constructor 
		public Server(ServerSocket serverSocket) {
			this.serverSocket = serverSocket;
			mainFrame();
		}
		
		//Method to keep server running
		public void startServer(){
			
			textFieldPort.setText("1234");
			
			try {

				
				//while server socket is not closed
				while (true) {
					
					Socket socket = serverSocket.accept();
					
					textAreaServer.append("A new client has connected!" + "\n");
					
					ClientHandler clientHandler = new ClientHandler(socket);
					
					//create a new thread of the object of client handler class
					Thread thread = new Thread(clientHandler);
					//method to start thread
					thread.start();
					
				} 
					
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
		}
		
		
		//Close server socket
		public void closeServerSocket() {
				try {
					if (serverSocket != null){
						serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		//Main method to keep server running
		public static void main(String[] args) throws IOException {
		
			ServerSocket serverSocket = new ServerSocket (1234);
			Server server = new Server(serverSocket);
			server.startServer();
			server.mainFrame();
			
		} 
	


	//Creating GUI frame
	public void mainFrame() {
		
		jFrame = new JFrame("Server");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setBounds(100, 100, 400, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		jFrame.setContentPane(contentPane);
		
		JLabel jLableTitle = new JLabel("Server");
		jLableTitle.setFont(new Font("Tahoma", Font.BOLD, 23));
		
		//Exit from system
		JButton jButtonStop = new JButton("Stop");
		jButtonStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		jButtonStop.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		textFieldPort = new JTextField();
		textFieldPort.setColumns(10);
		
		JLabel jLabelPort = new JLabel("Port");
		jLabelPort.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JScrollPane scrollPane = new JScrollPane();
		
		//Clear a text field
		JButton jButtonRest = new JButton("Reset");
		jButtonRest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaServer.setText(null);
			}
		});
		jButtonRest.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(jLabelPort, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textFieldPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(140)
							.addComponent(jLableTitle, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(jButtonStop, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(jButtonRest, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabelPort))
					.addGap(20)
					.addComponent(jLableTitle, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jButtonRest, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
						.addComponent(jButtonStop, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		textAreaServer = new JTextArea();
		scrollPane.setViewportView(textAreaServer);
		contentPane.setLayout(gl_contentPane);
		
		jFrame.setVisible(true);
	}
}
