package bookstore;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class designed to | reduce quantity of ordered stock
 * 
 * @author ARTE
 *
 */
public class DataBaseReduce {

	static Statement stmt;

	/**
	 * Method that | reduce quantity of ordered stock
	 * 
	 * @param qtty
	 * @param bookN
	 * @return
	 * @throws SQLException
	 */

	public static boolean reduceQtty(int qtty, String bookN) throws SQLException {

		try {
			int newQtty = qtty - 1;
			stmt = DataBaseConnection.connectMe();
			stmt.executeUpdate("update stock set qty =\"" + newQtty + "\"" + " where bookname = \"" + bookN + "\";");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseConnection.closeMe();
		}
		return false;
	}

}
