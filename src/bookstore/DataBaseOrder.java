package bookstore;

import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseOrder {
	static Statement stmt;

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
