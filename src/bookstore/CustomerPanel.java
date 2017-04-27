package bookstore;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

/**
 * 
 * @author ARTE & LEE This Class is designed for building Customer Panel with
 *         customer options
 */

public class CustomerPanel {

	static JPanel customerPanel;
	static JPanel cardDisplay;
	static JButton quit, next;
	static ResultSet rs;
	static java.sql.Connection con;
	JFrame frame;

	/**
	 * Constructor with frame and user name parameters
	 * 
	 * @param s
	 * @param frame
	 */
	public CustomerPanel(String s, JFrame frame) {
		// TODO Auto-generated constructor stub
		try {
			customerPanel = customerPanel(s, frame);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Actual method for customer panel | allows user to flip trough the cards |
	 * informations on the cards are taken from DB
	 * 
	 * @param uss
	 * @param frame
	 * @return full panel and internal cards
	 * @throws IOException
	 */
	public static JPanel customerPanel(String uss, JFrame frame) throws IOException {
		{

			quit = new JButton("Logout");
			next = new JButton("Next Book");
			customerPanel = new JPanel(); // main panel 
			customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));
			JPanel header = new JPanel();
			header.setLayout(new GridLayout(3,1));
			header.setBackground(Color.lightGray);
			JPanel buttons = new JPanel();
			buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
			buttons.setBackground(Color.YELLOW);
			buttons.add(quit);
			buttons.add(next);
			JLabel welcome = new JLabel("Welcome " + uss);
			JLabel welcome1 = new JLabel(" This is your customer panel");
			Font font = new Font("Verdana", Font.BOLD, 28);
			welcome.setFont(font);

			header.add(welcome);
			header.add(welcome1);
			header.add(buttons);
			
			customerPanel.add(header);
			
		

			cardDisplay = new JPanel();
			cardDisplay.setLayout(new CardLayout());

			try {

				Class.forName("com.mysql.jdbc.Driver");
				con = DataBaseConnection.Connection(); 
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
						JButton customerOrder = new JButton("Order this book!");
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
								} else {

									JOptionPane.showMessageDialog(CustomerPanel.customerPanel,
											"Quantitty is null, Item can not be ordered");

								}

							}

						});

						BufferedImage img = ImageIO.read(new File("../bookstore/src/" + image + ".png"));
												
						JPanel infoBox = new JPanel();
						infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
						
						infoBox.add(new JLabel(bookName));
						infoBox.add(new JLabel(author));
						infoBox.add(new JLabel(description));
						infoBox.add(new JLabel(price));
						infoBox.add(new JLabel(qty1));
						JPanel imageBox = new JPanel();
						
						imageBox.add(new JLabel(new ImageIcon(img)));
						
						
						card.add(infoBox);
						card.add(imageBox);
						card.add(customerOrder);
						card.setBackground(Color.lightGray);
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
					DataBaseConnection.closeMe(); 
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
