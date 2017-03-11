package bookstore;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

	public class Demo extends JFrame implements ActionListener {
		
	    static JFrame frame;
	    static JButton buttonOne;
	    static JButton buttonTwo;
	    static JButton nextButton = new JButton ("Next Screen");
	    static JPanel panel, mainPanel, panelOne, adminPanel;
	    static boolean isAdmin = true;
	    
	    String user, pass;
	    Connection con = null;
	    static Container mainContainer; 
	 
	    public Demo()
	    {
	    	
			panel = mainPanel();
			mainContainer = getContentPane();
						
			mainContainer.add(panel);		
			setVisible(true);
			setSize(500,500);
	    }
	    
	    @Override
		public void actionPerformed(ActionEvent e) {
	    	if (e.getActionCommand().equals("LOGIN")){
	    		loginPane();
	    	}
	    	if (e.getActionCommand().equals("EXIT")){
    			System.exit(0);
    		}
	    	if (e.getActionCommand().equals("Quit")){
	    		
	    		mainContainer.removeAll();
				mainContainer.add(mainPanel());
				frame.validate();
    		}
		}
	    
	    public JOptionPane loginPane () {
    	   
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
    	
    	public boolean checkLogin(String userName, String password) throws SQLException {
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
    	
    	private void loginDbCheck(String uss, String paw) throws SQLException {
	    	if(checkLogin(uss,paw)) {
				if(isAdmin){
					mainContainer.removeAll();
					mainContainer.add(adminPanel());
					frame.validate();
				}
				else{
					mainContainer.removeAll();
					mainContainer.add(panelOne());
					frame.validate();
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "Incorrect Details, Please Try Again");
			}
	    }
	    
    	public JPanel mainPanel(){
    		
    		isAdmin = true;
    		mainPanel = new JPanel();
			
    		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));	
			
			JLabel welcome = new JLabel("WELCOME");
			
			buttonOne = new JButton("LOGIN");
			buttonTwo = new JButton("EXIT");
			
			buttonOne.addActionListener(this);	
			buttonTwo.addActionListener(this);
			
			mainPanel.add(welcome);
			mainPanel.add(buttonOne);
			mainPanel.add(buttonTwo);
    		
			return mainPanel;
    		
    	}
	    
    	public JPanel panelOne() { {
    		panelOne = new JPanel();
	    	panelOne.setLayout(new BoxLayout(panelOne, BoxLayout.Y_AXIS));
	        panelOne.add(new JLabel("Welcome to Customer PANEL "));
	        JButton button = new JButton("Quit");
	        button.addActionListener(this);
	        panelOne.add(button);
	      }
	   	return panelOne; 
	   }
    	
    	public JPanel adminPanel() { {
    		adminPanel = new JPanel();
    		adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
    		adminPanel.add(new JLabel("Welcome to Admin PANEL"));
	        JButton button = new JButton("Quit");
	        button.addActionListener(this);
	        adminPanel.add(button);
	      }
	   	return adminPanel; 
	   }

	    public static void main (String[] args) {
	     
	        frame = new Demo();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	
	}


