package bookstore;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

	public class Demo extends JFrame {
		
	    static JFrame frame;
	    static JButton quit, register, login, exit, next;
	    static JPanel panel, mainPanel, customerPanel, adminPanel, cardDisplay;
	    
	    static boolean isAdmin = true;
	    ResultSet rs;
	    static String user;
		static String pass;
	    static Connection con = null;
	    static Container mainContainer; 
	 
	    public Demo() throws IOException 
	    {
	    	mainContainer = getContentPane();
	    	panel = mainPanel();
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			mainContainer.add(panel);		
			setVisible(true);
			setSize(500,500);
			setLocation(250,150);
	    }
	   
	    public static JOptionPane registerPane () {
	    	   
  	      JTextField userField = new JTextField(15);
  	      JTextField passField = new JTextField(15);

  	      JPanel myPanel = new JPanel();
  	      myPanel.add(new JLabel("Username"));
  	      myPanel.add(userField);
  	      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
  	      myPanel.add(new JLabel("Password"));
  	      myPanel.add(passField);
	      
  	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
  	               "Registration Form", JOptionPane.OK_CANCEL_OPTION);
  	      
  	      if (result == JOptionPane.OK_OPTION){
  	    	  
  	    	  user = userField.getText();
  	          pass = passField.getText();
  	         
  	          try {
					if(register(user,pass)){
						JOptionPane.showMessageDialog(mainPanel, "Registered, Please login now. ");
						
					}
					else{
						JOptionPane.showMessageDialog(mainPanel, "Please try again. ");
					}
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
  	   }
			return null;
  	}
	    
	    public static boolean register(String userName, String password) throws SQLException {
    		try{
	    		if ((userName != null)&&(password !=null)) {
	    			
		    			Class.forName("com.mysql.jdbc.Driver");
		    		   con = DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/bookstore","root","Password1");
		    		   Statement stmt = con.createStatement();
		    		   stmt.executeUpdate("insert into users (name, pass) values (\"" + userName + "\",\"" + password + "\");" );
		    				    		   
		    		   return true;		    		   
		    		}	    			
    		}
    		catch (SQLException e) {
    			 e.printStackTrace();
     		}
     		catch (Exception e) {
     		    e.printStackTrace();
     		}
     		finally {
     		   con.close();
     		}

			return false; //the name was not found
		}
	    
	    public static JOptionPane loginPane () throws IOException {
    	   
    	      JTextField userField = new JTextField(15);
    	      JTextField passField = new JTextField(15);

    	      JPanel myPanel = new JPanel();
    	      myPanel.add(new JLabel("Username"));
    	      myPanel.add(userField);
    	      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
    	      myPanel.add(new JLabel("Password"));
    	      myPanel.add(passField);
 	      
    	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
    	               "Please Login", JOptionPane.OK_CANCEL_OPTION);
    	      
    	      if (result == JOptionPane.OK_OPTION){
    	    	  
    	    	  user = userField.getText();
    	          pass = passField.getText();
    	         
    	          try {
					loginDbCheck(user,pass);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
    	   }
			return null;
    	}
    	
    	public static boolean checkLogin(String userName, String password) throws SQLException {
    		try{
	    		if (userName != null) {
	    			
	    			Class.forName("com.mysql.jdbc.Driver");
	    		   con = DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/bookstore","root","Password1");
	    		   Statement stmt = con.createStatement();
	    		   ResultSet userN = stmt.executeQuery("select name,pass,admin from users");
	    		  
	    		   while (userN.next())
		    			   
	    		   if(userName.equals(userN.getString("name")) && password.equals(userN.getString("pass"))) {
	    			   
	    			   if(!userN.getBoolean("admin")){
	  	    			 isAdmin = false;
	  	    		   }
	    			   return true;
	    		   }
	    		}
    		}
    		catch (SQLException e) {
    			 e.printStackTrace();
     		}
     		catch (Exception e) {
     		    e.printStackTrace();
     		}
     		finally {
     		   con.close();
     		}

			return false; //the name was not found
		}
    	
    	private static void loginDbCheck(String uss, String paw) throws SQLException, IOException {
	    	if(checkLogin(uss,paw)) {
				if(isAdmin){
					mainContainer.removeAll();
					mainContainer.add(AdminPanel.adminPanel(uss,null));
					frame.validate();
				}
				else{
					mainContainer.removeAll();
					//mainContainer.add(customerPanel(uss));
					mainContainer.add(CustomerPanel.customerPanel(uss, null));
					frame.validate();
				}
			}
			else {
				JOptionPane.showMessageDialog(mainPanel, "Incorrect Details, Please Try Again");
			}
	    }
	    
    	public static JPanel mainPanel() throws IOException{
    		
    		isAdmin = true;
    		JPanel header = new JPanel();
    		header.setLayout(new BoxLayout(header,BoxLayout.Y_AXIS));
    		JPanel buttonsPanel = new JPanel();
    		buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.X_AXIS));
			mainPanel= new JPanel();
    		//mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));	
    		BufferedImage img = ImageIO.read(new File("/Users/arturwac/Desktop/advanced/bookstore/src/bgimage.png"));
	    
			JLabel welcome = new JLabel("WELCOME");
			Font font = new Font("Verdana", Font.BOLD, 30);
			welcome.setFont(font);
			login = new JButton("LOGIN");
			exit = new JButton("EXIT");
			register = new JButton("REGISTER");
			
			
			login.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	try {
						loginPane();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            	
	            }});	
			
			
			
			register.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	registerPane();
	            	
	            }});	
			exit.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	System.exit(0);
	            	
	            }});	
			
			
			buttonsPanel.add(login);
			buttonsPanel.add(register);
			buttonsPanel.add(exit);
			header.add(welcome);
			
			mainPanel.add(header, BorderLayout.NORTH );
			mainPanel.add(new JLabel(new ImageIcon(img)), BorderLayout.CENTER);
			mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
			
    		
			return mainPanel;
    		
    	}
	   
    	static boolean orderThis(String bookN, String uss, int qtty) throws SQLException {
    		
    		try{
	    		if ((bookN != null)&&(uss !=null)) {
	    			
		    			Class.forName("com.mysql.jdbc.Driver");
		    		   con = DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/bookstore","root","Password1");
		    		   Statement stmt = con.createStatement();
		    		   stmt.executeUpdate("insert into orders (bookName, cust) values (\"" + bookN + "\",\"" + uss + "\");" );
		    				    		   
		    		   return true;		    		   
		    		}	    			
    		}
    		catch (SQLException e) {
    			 e.printStackTrace();
     		}
     		catch (Exception e) {
     		    e.printStackTrace();
     		}
     		finally {
     		   con.close();
     		}

			return false; //the name was not found
		}
			
    	public static boolean reduceQtty(int qtty, String bookN) throws SQLException {
    		try{
	    		int newQtty = qtty-1;
	    		
		    			Class.forName("com.mysql.jdbc.Driver");
		    		   con = DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/bookstore","root","Password1");
		    		   Statement stmt = con.createStatement();
		    		   stmt.executeUpdate("update stock set qty =\""+newQtty+"\""+ " where bookname = \""+bookN+"\";");
		    		   return true;
		    }
    		catch (SQLException e) {
    			 e.printStackTrace();
     		}
     		catch (Exception e) {
     		    e.printStackTrace();
     		}
     		finally {
     		   con.close();
     		}
			return false;
    	}
			
	    public static void main (String[] args) throws IOException {
	     
	        frame = new Demo();
	        
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	
	}


