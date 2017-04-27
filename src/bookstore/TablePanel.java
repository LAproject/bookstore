package bookstore;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author ARTE & LEE
 *
 */
public class TablePanel {

	static JTable table;
	static JPanel customerPanel, cardDisplay, adminPanel, tablePanel;
	static JButton logout;
	static ResultSet rs, res;
	static java.sql.Connection con;
	static JFrame frame;

	/**
	 * 
	 * @param frame
	 * @throws ClassNotFoundException
	 */
	public TablePanel(JFrame frame) throws ClassNotFoundException {
		tablePanel = tablePanel(frame);
	}

	/**
	 * 
	 * @param frame
	 * @return
	 */
	public static JPanel tablePanel(JFrame frame) {

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		DefaultTableModel dtm = new DefaultTableModel();
		String id = "", b = "", c = "";
		table = new JTable(dtm);
		dtm.addColumn("Product ID");
		dtm.addColumn("Book Name");
		dtm.addColumn("Customer");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DataBaseConnection.Connection();
			Statement stmt = con.createStatement();
			res = stmt.executeQuery("select orderID, bookName, cust from orders;");

			while (res.next()) {
				id = res.getString("orderID");
				b = res.getString("bookName");
				c = res.getString("cust");

				dtm.addRow(new Object[] { id, b, c });

			}

			dtm.fireTableDataChanged();
			JLabel x = new JLabel("List of Current Orders: " + "\n");
			Font font = new Font("Verdana", Font.BOLD, 15);
			x.setFont(font);
			tablePanel.add(x, BorderLayout.NORTH);
			tablePanel.add(table, BorderLayout.CENTER);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}

		return tablePanel;
	}

}
