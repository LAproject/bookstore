package bookstore;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
	   //KEEP IN DEMO
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
					if(DataBaseLogin.register(user,pass)){
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
	    //KEEP IN DEMO
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
					DataBaseLogin.loginDbCheck(user,pass);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
    	   }
			return null;
    	}
    	//KEEP IN DEMO
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
	    //KEEP IN DEMO
    	public static void main (String[] args) throws IOException {
	     
	        frame = new Demo();
	        
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	
	}


