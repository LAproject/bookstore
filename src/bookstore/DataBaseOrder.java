package bookstore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseOrder {
	static Connection con;

	static boolean orderThis(String bookN, String uss, int qtty) throws SQLException {

		try {
			if ((bookN != null) && (uss != null)) {

				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bookstore", "root", "Password1");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("insert into orders (bookName, cust) values (\"" + bookN + "\",\"" + uss + "\");");

				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

		return false; // not ordered
	}

}
