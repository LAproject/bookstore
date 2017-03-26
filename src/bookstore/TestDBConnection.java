package bookstore;

import java.sql.SQLException;
import java.sql.Connection;
import org.junit.Assert;
import org.junit.Test;
/**
 * Checks Connection
 * @author ARTE
 *
 */
public class TestDBConnection {

	/**
	 * Connects to DB and closes connection
	 * @throws SQLException
	 */
	@Test
	public void testGetConnection() throws SQLException {
		Connection con = DataBaseConnection.Connection();
		Assert.assertNotNull(con);
		Assert.assertTrue(con.isValid(0));
		con.close();
	}

}
