package bookstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author ARTE & LEE This Class is designed for building Administration Panel
 *         with administrative options
 */
public class AdminPanel {

	static JPanel customerPanel, cardDisplay, adminPanel;
	static JButton quit, next;
	static ResultSet rs;
	static java.sql.Connection con;
	JFrame frame;

	/**
	 * Constructor passing:
	 * 
	 * @param s
	 *            : name of the user
	 * @param frame
	 *            : frame/panel
	 */
	public AdminPanel(String s, JFrame frame) {
		adminPanel = adminPanel(s, frame);
	}

	/**
	 * Method that builds actual panel
	 * 
	 * @param uss
	 * @param frame
	 * @return
	 */
	public static JPanel adminPanel(String uss, JFrame frame) {
		{
			adminPanel = new JPanel();
			adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
			adminPanel.add(new JLabel("Welcome " + uss + " to your Administration PANEL"));
			JButton button = new JButton("Quit");

			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Demo.mainContainer.removeAll();
					try {
						Demo.mainContainer.add(MainPanel.mainPanel(frame));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Demo.mainContainer.validate();

				}
			});

			adminPanel.add(button);
		}
		return adminPanel;
	}

}
