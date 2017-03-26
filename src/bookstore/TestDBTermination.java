package bookstore;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * Test class for connection Termination testing | it sets up connection first and than it terminates it 
 * @author ARTE
 *
 */
public class TestDBTermination {

	static Connection con;

	/**
	 * Sets up connection for testing
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		con = DataBaseConnection.Connection();

	}

	/**
	 * takes connection and terminates it
	 * @throws SQLException
	 */
	@Test
	public void connectionTermination() throws SQLException {

		con.close();
		Assert.assertTrue(con.isClosed());
	}

}
