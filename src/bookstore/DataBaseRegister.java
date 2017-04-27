package bookstore;

import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Register Class provides register new user functionality
 * 
 * @author ARTE
 *
 */
public class DataBaseRegister {

	static JFrame frame;
	static Statement stmt;

	/**
	 * Method provides register new user functionality
	 * 
	 * @param userName
	 * @param password
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean register(String userName, String password) throws SQLException {

		try {
			if (password.length() >= 5 && userName.length() != 0) {

				stmt = DataBaseConnection.connectMe();
				stmt.executeUpdate(
						"insert into users (name, pass) values (\"" + userName + "\",\"" + password + "\");");
				DataBaseConnection.closeMe();
				return true; // registered
			} else {
				JOptionPane.showMessageDialog(MainPanel.mainPanel,
						"Password to be minimum five characters long, username can not be blank");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false; // name was not found
	}

}
