package bookstore;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Order Class | inserts new order into order table
 * 
 * @author ARTE & LEE
 *
 */
public class DataBaseOrder {
	static Statement stmt;

	/**
	 * Method for | inserts new order into order table
	 * 
	 * @param bookN
	 * @param uss
	 * @param qtty
	 * @return
	 * @throws SQLException
	 */
	static boolean orderThis(String bookN, String uss, int qtty) throws SQLException {

		try {

			if ((bookN != null) && (uss != null)) {

				stmt = DataBaseConnection.connectMe();
				stmt.executeUpdate("insert into orders (bookName, cust) values (\"" + bookN + "\",\"" + uss + "\");");

				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseConnection.closeMe();
		}

		return false; // not ordered
	}

}
