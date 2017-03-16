package bookstore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseReduce {

	static Connection con;

	public static boolean reduceQtty(int qtty, String bookN) throws SQLException {

		try {
			int newQtty = qtty - 1;

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bookstore", "root", "Password1");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update stock set qty =\"" + newQtty + "\"" + " where bookname = \"" + bookN + "\";");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return false;
	}

}
