package bookstore;

import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DataBaseRegister {

	static JFrame frame;
	static Statement stmt;

	public static boolean register(String userName, String password) throws SQLException {

		try {
			if (password.length() >= 5 && userName.length() != 0) {

				stmt = DataBaseConnection.connectMe();
				stmt.executeUpdate(
						"insert into users (name, pass) values (\"" + userName + "\",\"" + password + "\");");
				DataBaseConnection.closeMe();
				return true; // name was found
			} else {
				JOptionPane.showMessageDialog(MainPanel.mainPanel, "Password to be minimum five characters long");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false; // name was not found
	}

}
