package bookstore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Main panel | Application front/first navigation window | 
 * @author ARTE
 *
 */
public class MainPanel {

	
	static JPanel customerPanel, mainPanel;
	static JPanel cardDisplay;
	static JButton quit, next, login, exit, register;
	static ResultSet rs;
	static java.sql.Connection con;
	JFrame frame;

	/**
	 * Constructor | passes frame to main method in Demo
	 * @param frame
	 */
	public MainPanel(JFrame frame) {
		// TODO Auto-generated constructor stub
		try {
			mainPanel = mainPanel(frame);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method that constructs actual main panel 
	 * @param frame
	 * @return
	 * @throws IOException
	 */
	public static JPanel mainPanel(JFrame frame) throws IOException {

		
		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.LIGHT_GRAY);
		// mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
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
					Demo.loginPane();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Demo.registerPane();

			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		buttonsPanel.add(login);
		buttonsPanel.add(register);
		buttonsPanel.add(exit);
		header.add(welcome);

		mainPanel.add(header, BorderLayout.NORTH);
		mainPanel.add(new JLabel(new ImageIcon(img)), BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

		return mainPanel;

	}

}
