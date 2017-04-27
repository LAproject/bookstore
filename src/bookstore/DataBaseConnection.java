package bookstore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database Connector class | Provide connection to DB
 * 
 * @author ARTE & LEE
 *
 */
public class DataBaseConnection {

	private static Connection connection;
	static Statement stat;
	
	static Connection Connection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bookstore", "root", "Password1");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return connection; 
	}

	/**
	 * Statement  method | receives statement from foreign method and inserts it to database 
	 * 
	 * @return connection
	 */
	public static Statement connectMe() {

		try {

			
			stat = Connection().createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stat;

	}

	/**
	 * Termination of the connection method
	 * 
	 */
	public static void closeMe() {

		try {
			connection.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
