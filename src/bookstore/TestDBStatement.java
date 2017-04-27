package bookstore;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
/**
 * Checks if Statement parameter is "poolable" | can be used for executing SQL queries
 * @author ARTE
 *
 */
public class TestDBStatement {

	@Test
    public void closeStatementShouldCloseStatement() throws SQLException {
        Statement statement = DataBaseConnection.connectMe();
        
        assertTrue(statement.isPoolable());
    }

}
