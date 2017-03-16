package bookstore;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CustomerPanel {

	static JPanel customerPanel;
	static JPanel cardDisplay;
	static JButton quit, next;
	static ResultSet rs;
	static java.sql.Connection con;
	JFrame frame;

	public CustomerPanel(String s, JFrame frame) {
		// TODO Auto-generated constructor stub
		try {
			customerPanel = customerPanel(s, frame);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static JPanel customerPanel(String uss, JFrame frame) throws IOException {
		{

			quit = new JButton("Quit");
			next = new JButton("Next");
			customerPanel = new JPanel();
			JPanel buttons = new JPanel();
			buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
			customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));
			buttons.add(quit);
			buttons.add(next);
			JLabel welcome = new JLabel("Welcome " + uss);
			JLabel welcome1 = new JLabel("This is your customer panel");
			Font font = new Font("Verdana", Font.BOLD, 28);
			welcome.setFont(font);

			customerPanel.add(welcome);
			customerPanel.add(welcome1);
			customerPanel.add(buttons, BorderLayout.SOUTH);

			cardDisplay = new JPanel();
			cardDisplay.setLayout(new CardLayout());

			try {

				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bookstore", "root", "Password1");
				Statement stmt = con.createStatement();
				rs = stmt.executeQuery("select idstock, bookname, author, description, price, image, qty from stock");
				String[] arr = null;
				while (rs.next()) {
					String em = rs.getString("bookname");
					arr = em.split("\n");
					for (int i = 0; i < arr.length; i++) {
						JPanel card = new JPanel();
						card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
						card.setName(String.valueOf(i));

						String bookName = "BOOK NAME IS: " + rs.getString("bookname");
						String author = "AUTHOR OF THIS BOOK: " + rs.getString("author");
						String price = "PRICE FOR THIS BOOK: " + rs.getString("price");
						String description = "SHORT DESCRIPTION: " + rs.getString("description");
						String qty1 = "AVAILABLE QUANTITY " + rs.getString("qty");
						String image = rs.getString("image");
						int qtty = rs.getInt("qty");
						String bookN = rs.getString("bookname");
						JButton customerOrder = new JButton("Order");
						customerOrder.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if (qtty > 0) {
									try {
										if (DataBaseOrder.orderThis(bookN, uss, qtty)
												&& DataBaseReduce.reduceQtty(qtty, bookN)) {

											Demo.mainContainer.removeAll();
											try {
												Demo.mainContainer.add(customerPanel(uss, frame));
											} catch (IOException e1) {
												// TODO Auto-generated catch
												// block
												e1.printStackTrace();
											}
											Demo.mainContainer.validate();
											JOptionPane.showMessageDialog(card, "Congratulations " + uss
													+ " your order for " + bookN + " was placed succesfuly");

										} else {
											JOptionPane.showMessageDialog(card,
													"There was a problem with order, Please contact shop");
										}
									} catch (HeadlessException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}

						});

						BufferedImage img = ImageIO
								.read(new File("/Users/arturwac/Desktop/advanced/bookstore/src/" + image + ".png"));

						card.add(new JLabel(bookName));
						card.add(new JLabel(author));
						card.add(new JLabel(description));
						card.add(new JLabel(price));
						card.add(new JLabel(qty1));
						card.add(new JLabel(new ImageIcon(img)));
						card.add(customerOrder);

						cardDisplay.add(card);
					}
				}
			} catch (ClassNotFoundException | SQLException e) {

				e.printStackTrace();
			}
			quit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Demo.mainContainer.removeAll();
					try {
						Demo.mainContainer.add(MainPanel.mainPanel(frame));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Demo.mainContainer.validate();

				}
			});

			next.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CardLayout card = (CardLayout) cardDisplay.getLayout();
					card.next(cardDisplay);

				}
			});

			customerPanel.add(cardDisplay);

		}
		return customerPanel;
	}

}
