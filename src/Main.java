/*
 * The main aim of this class is to display main chat windows and enable chat event among the users
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

/*
 * @author Daniils Sokolovs
 * @version 12/12/2021
 */

public class Main extends Thread{
	
	static Login login;

	private JPanel contentPane;
	private JLabel jLabelNewUser;
	private JTextArea textAreaOnlineBox;
	private JTextArea textAreaChatBox;
	private JTextArea textMessageBox;
	
	JFrame jFrame = new JFrame("Chat Application");
	

	Client client;

	//Main method to launch the application
	public static void main(String[] args) throws IOException {
		
		Main main = new Main();
		login = new Login();
		main.initMain();
		
	}
	
	//Initialising the connection between the client and server
	public void initConnection() throws IOException {
		
		      client = new Client();
		      client.startConnection("localhost", 1234, jLabelNewUser.getText());

		
	}
	
	//Method is not accepting empty inputs to the data base
	public void initMain() {
		
		login.jButtonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
					if(login.jTextUserName.getText().isEmpty() || login.jTextPassword.getText().isEmpty()) {
						
						return;
					
					} 
					
				}

		});

	}

	
	//Run method enable to get access to the data stream and display it on relevant GUI areas
	public void run () {
	    while (true) {
	      try {

	        String incoming = client.getIn();
	        if (incoming.equals("newUser")) {
	        	textAreaOnlineBox.setText("");
	          while(!(incoming = client.getIn()).equals("finished")) {
	        	  textAreaOnlineBox.append(incoming + "\n");
	          }

	        } else {
	        	textAreaChatBox.append(incoming + "\n");
	        }
	      } catch (Exception e) {
	        System.out.println(e.toString());
	      }
	    }
	  }
	
	
	//Empty constructor of the class
	public Main() {
		
	}

	
	//Creating GUI frame
	public void mainFrame() {
		
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setBounds(100, 100, 814, 703);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		jFrame.setContentPane(contentPane);
		
		jLabelNewUser = new JLabel("New User");
		jLabelNewUser.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		//Once user has been obtained from the login start the connection with server
		jLabelNewUser.setText(login.jTextUserName.getText());
		try {
			initConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		start();
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		
		//Send message event
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					
					client.sendMessage( jLabelNewUser.getText(),textMessageBox.getText());
					textAreaChatBox.append("My message: " + textMessageBox.getText() + "\n");
					textMessageBox.setText("");
	
			}
		});
		
		//Clear user inputs
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textMessageBox.setText("");
			}
		});
		
		//Exit from the system
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		
		JLabel JLabelTitle = new JLabel("Chat Application");
		JLabelTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel jLabelChatBox = new JLabel("Group Chat");
		jLabelChatBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel jLabelOnlineBox = new JLabel("Online Users");
		jLabelOnlineBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
							.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addGap(198)
							.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(jLabelNewUser, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(220)
							.addComponent(JLabelTitle, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
									.addGap(34))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(jLabelChatBox, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
									.addGap(481)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(jLabelOnlineBox, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(JLabelTitle)
						.addComponent(jLabelNewUser))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabelChatBox)
						.addComponent(jLabelOnlineBox, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE))
					.addGap(27)
					.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))))
		);
		
		textMessageBox = new JTextArea();
		scrollPane_2.setViewportView(textMessageBox);

		textAreaOnlineBox = new JTextArea();
		scrollPane_1.setViewportView(textAreaOnlineBox);
		
		textAreaChatBox = new JTextArea();
		scrollPane.setViewportView(textAreaChatBox);
		
		contentPane.setLayout(gl_contentPane);
		jFrame.setVisible(true);
		
	}
}