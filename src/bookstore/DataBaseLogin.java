package bookstore;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DataBaseLogin {

	static Statement stmt;

	public static boolean register(String userName, String password) throws SQLException {

		try {
			if ((userName != null) && (password != null)) {

				stmt = DataBaseConnection.connectMe();
				stmt.executeUpdate("insert into users (name, pass) values (\"" + userName + "\",\"" + password + "\");");

				return true; // name was found
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseConnection.closeMe();
		}

		return false; // name was not found
	}

	public static boolean checkLogin(String userName, String password) throws SQLException {
		try {
			if (userName != null && password != null) {

				stmt = DataBaseConnection.connectMe();
				ResultSet userN = stmt.executeQuery("select name,pass,admin from users");

				while (userN.next())

					if (userName.equals(userN.getString("name")) && password.equals(userN.getString("pass"))) {

						if (!userN.getBoolean("admin")) {
							Demo.isAdmin = false; // Reverse true statement//
													// empty = true so returns
													// false
						}
						return true;
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseConnection.closeMe();
		}

		return false; // the name was not found
	}

	static void loginDbCheck(String uss, String paw) throws SQLException, IOException {
		if (checkLogin(uss, paw)) {
			if (Demo.isAdmin) {
				Demo.mainContainer.removeAll();
				Demo.mainContainer.add(AdminPanel.adminPanel(uss, null));
				Demo.mainContainer.validate();
			} else {
				Demo.mainContainer.removeAll();
				Demo.mainContainer.add(CustomerPanel.customerPanel(uss, null));
				Demo.mainContainer.validate();
			}
		} else {
			JOptionPane.showMessageDialog(Demo.mainPanel, "Incorrect Details, Please Try Again");
		}
	}

}
