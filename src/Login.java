/*
 * The main of this class it to maintain authentication function by connecting to database as a main storage of data.
 * 
 * Database in use: postgresql
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;


/*
 * @author Daniils Sokolovs
 * @version 12/12/2021
 */

public class Login {

	
	JFrame jFrame = new JFrame("User Login");
	JPanel contentPane = new JPanel();
	JLabel jLabelUserName = new JLabel("Username: ");
	JLabel jLabelPassword = new JLabel("Password: ");
	JLabel jLabelTitle = new JLabel("Sing In");
	JTextField jTextUserName = new JTextField();
	JTextField jTextPassword = new JTextField();
	JButton jButtonLogin = new JButton("Login");
	JButton jButtonRegister = new JButton("Register");
	JButton jButtonClear = new JButton("Clear");
	JButton jButtonExit = new JButton("Exit");
	
	
	//Creating connection to database using following parameters:
	//Url to the project folder
	//User name
	//Password
	private  String url = "jdbc:postgresql://localhost/newDatabase";
	private  String user = "newuser";
	private  String password = "password";
	
	//Connection objects
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	
	Main main;
	
	//Constructor of the class which invokes GUI and connection to database methods
	public Login() {
		
		getConnection();
		Frame();
	}
	
	//Creating actual connection to database
	public void getConnection() {
		try {
			
			
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			
			if(connection != null) {
				System.out.println("Connected to PostgreSQL successfully!");
			} else {
				System.out.println("Failed to connect to PostgreSQL server");
			}
			
			
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Checking user input by validating it through data base
	public void getUser() {
		main = new Main();
		try {
			
			
			String user = jTextUserName.getText().trim();
			String password = jTextPassword.getText().trim();
			
			
			
			if(jTextUserName.getText().isEmpty() || jTextPassword.getText().isEmpty()) {
				
				JOptionPane.showMessageDialog(contentPane, "No User name or password entered!",null, JOptionPane.ERROR_MESSAGE);

				 
			 } else {
				 
				 	//Execute query from database
					String sql = "select name,password from \"Login\" where name='"+user+"'and password='"+password+"'";
					resultSet = statement.executeQuery(sql);
					
					//Counting a number of users in database
					int countUsers = 0;
					while(resultSet.next()) {
						countUsers = countUsers + 1;
					}
												
					if(countUsers == 1) {
						JOptionPane.showMessageDialog(null, "User Found. Access Granted!");
						main.mainFrame();
						jFrame.setVisible(false);
						
					} 
					
					else {
						JOptionPane.showMessageDialog(contentPane, "User not found!",null, JOptionPane.ERROR_MESSAGE);

					}

			 }

			
		} catch(Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(contentPane,"Unexpectable Error!",null, JOptionPane.ERROR_MESSAGE);
			
		}
		
		
	}
	
	
	//Registering a new user to data base
	public void registerUser() {
		try {
			
			String user = jTextUserName.getText().trim();
			String password = jTextPassword.getText().trim();
			
			
			
			if(jTextUserName.getText().isEmpty() || jTextPassword.getText().isEmpty()) {
				
				JOptionPane.showMessageDialog(contentPane, "No User name or password entered!", null, JOptionPane.ERROR_MESSAGE);
				
			} else {
				
				//Execute query from DB
				String sql1 = "insert into \"Login\" (name, password) values('"+user+"','"+password+"')";
				statement.executeUpdate(sql1);
				JOptionPane.showMessageDialog(contentPane, "New User Registered!", null, JOptionPane.INFORMATION_MESSAGE);
				jTextUserName.setText(null);
				jTextPassword.setText(null);
			}
			

		
		} catch(Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(contentPane,"Hint: User could have already been registered in database", "Unexpectable Error!",JOptionPane.ERROR_MESSAGE);
		}
			
		
	}
	


	//Creating GUI frame
	public void Frame() {
				
		
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setBounds(100, 100, 534, 323);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		jFrame.setContentPane(contentPane);
		jFrame.setVisible(true);
		
		jLabelTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		jLabelUserName.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		jLabelPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		jTextUserName.setColumns(10);
		
		jTextPassword.setColumns(10);
		
		//Getting user by using getUser() method
		jButtonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				getUser();
			}
			
		});
		
		jButtonLogin.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		//Register using by using registerUser() method
		jButtonRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				registerUser();
				
			}
			
		});
		jButtonRegister.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		//Clear user inputs
		jButtonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				jTextUserName.setText(null);
				jTextPassword.setText(null);
			}
		});
		jButtonClear.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		//Exit from the system
		jButtonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		jButtonExit.setFont(new Font("Tahoma", Font.BOLD, 10));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(84)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(jButtonLogin, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(jButtonRegister, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
							.addComponent(jButtonClear, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(jButtonExit, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(jLabelPassword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jLabelUserName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(jTextPassword)
								.addComponent(jTextUserName, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(38, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(240, Short.MAX_VALUE)
					.addComponent(jLabelTitle, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addGap(213))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(21)
					.addComponent(jLabelTitle, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(56)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabelUserName)
						.addComponent(jTextUserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabelPassword)
						.addComponent(jTextPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(75)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(jButtonLogin)
						.addComponent(jButtonExit)
						.addComponent(jButtonClear)
						.addComponent(jButtonRegister))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	
		
	}
}
