package bookstore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author ARTE & LEE This Class is designed for building Administration Panel
 *         with administrative options
 */
public class AdminPanel {

	static JPanel customerPanel, cardDisplay, adminPanel, display;
	static JButton logout;
	static ResultSet rs, res;
	static java.sql.Connection con;
	JFrame frame;
	static String orderNumber;
	static boolean delete;
	static JComboBox<String> comboBox;

	/**
	 * Constructor passing:
	 * 
	 * @param s
	 *            : name of the user
	 * @param frame
	 *            : frame/panel
	 */
	public AdminPanel(String s, JFrame frame) {
		try {
			adminPanel = adminPanel(s, frame);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param uss
	 * @param frame
	 * @return
	 * @throws ClassNotFoundException
	 */

	public static JPanel adminPanel(String uss, JFrame frame) throws ClassNotFoundException, SQLException {
		{
			adminPanel = new JPanel(); // main panel
			adminPanel.setBackground(Color.LIGHT_GRAY);
			adminPanel.setLayout(new BorderLayout());
			comboBox = new JComboBox<String>();
			JPanel header = new JPanel();
			display = new JPanel();
			JPanel footer = new JPanel();
			header.setBackground(Color.red);
			footer.setBackground(Color.red);
			header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
			JLabel headerText = new JLabel(uss + "! Welcome to Admin Panel!");
			Font fontOne = new Font("Verdana", Font.BOLD, 20);
			headerText.setFont(fontOne);
			header.add(headerText);
			logout = new JButton("Logout");
			header.add(logout);

			display = TablePanel.tablePanel(frame);

			try {

				addToCombo();

			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JLabel footerText = new JLabel("Select Completed Order's: ");
			Font font = new Font("Verdana", Font.BOLD, 15);
			footerText.setFont(font);

			footer.add(footerText);
			footer.add(comboBox);

			comboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					orderNumber = (String) comboBox.getSelectedItem();
					try {
						completeOrderPane(orderNumber);

						if (delete) {
							adminPanel.remove(display);
							adminPanel.add(display = TablePanel.tablePanel(frame));
							footer.revalidate();
							adminPanel.revalidate();
							delete = false;

						}

					} catch (IOException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});

			logout.addActionListener(new ActionListener() {
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
					DataBaseConnection.closeMe();
				}
			});

			adminPanel.add(header, BorderLayout.NORTH);
			adminPanel.add(display, BorderLayout.CENTER);
			adminPanel.add(footer, BorderLayout.SOUTH);
		}
		return adminPanel;
	}
	
	private static void addToCombo() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		
		con = DataBaseConnection.Connection();
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery("select orderID, bookName, cust from orders;");
		while (rs.next()) {

			String orderID = rs.getString("orderID");
			comboBox.addItem(orderID);
		}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param number
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static JOptionPane completeOrderPane(String number) throws IOException, ClassNotFoundException {

		JPanel myPanel = new JPanel();
		JLabel text = new JLabel("Please Confirm if this order is completed");
		Font fontOne = new Font("Verdana", Font.BOLD, 20);
		text.setFont(fontOne);
		myPanel.add(text);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Complete Order", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {

			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DataBaseConnection.Connection();
				Statement stmt = con.createStatement();
				stmt.executeUpdate("delete from orders where orderID  = " + number + ";");

			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			delete = true;

		}
		return null;
	}

}
