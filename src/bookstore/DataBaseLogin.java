package bookstore;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Login check class | confirms if user is registered and can login | also
 * checks if user is administrator
 * 
 * @author ARTE & LEE
 *
 */
public class DataBaseLogin {

	static Statement stmt;

	/**
	 * Method for Login check
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws SQLException
	 */

	public static boolean checkLogin(String userName, String password) throws SQLException {
		try {
			if (userName != null && password != null) {

				stmt = DataBaseConnection.connectMe();
				ResultSet userN = stmt.executeQuery("select name,pass,admin from users");

				while (userN.next())

					if (userName.equals(userN.getString("name")) && password.equals(userN.getString("pass"))) {

						// System.out.println(!userN.getBoolean("admin"));

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

	/**
	 * Method for Administrator Check
	 * 
	 * @param uss
	 * @param paw
	 * @throws SQLException
	 * @throws IOException
	 */
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
