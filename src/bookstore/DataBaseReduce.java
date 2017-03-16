package bookstore;

import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseReduce {

	static Statement stmt;

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
