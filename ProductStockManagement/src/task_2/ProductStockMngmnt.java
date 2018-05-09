package task_2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ProductStockMngmnt implements Serializable {

	private static final long serialVersionUID = 1L;

	
	
	
	
	
	
	
	public static void main(String args[]) {
		ProductStockMngmnt psm = new ProductStockMngmnt();
		String x, con = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {

			do {

				System.out.println("\n\n-----------------Welcome to the store---------------");
				System.out.println("\n\n\nAdmin login? y/n");

				x = reader.readLine();

				if (x.equals("y") || x.equals("Y")) {
					psm.admin();
				} else if (x.equals("N") || x.equals("n")) {
					psm.customer();
				} else {
					System.out.println("Invalid input");
				}

				System.out.println("\n\nExit application?y/n");

				con = reader.readLine();

			} while (con.equals("n") || con.equals("N"));
			reader.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	
	
	
	
	
	
	
	public void customer() {

		String feed = null;
		String pname = null, quantity = null;

		String fileName = "Database.txt";
		BufferedReader reader7 = new BufferedReader(new InputStreamReader(System.in));

		ArrayList<String> templist = new ArrayList<String>();

		System.out.println("Hey there customer!\nEnter the name of the product you looking for:");

		try {
			pname = reader7.readLine();
			System.out.println("Enter the quantity:");

			quantity = reader7.readLine();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		int qn = Integer.parseInt(quantity);
		int lqn = 0, newstock = 0;
		float total = 0, distotal = 0;

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			boolean flag1 = false, flag2 = true, flag3 = false, flag4 = false;
			List<String> l = null;

			stream.forEach(s -> templist.add(s));
			for (String s : templist) {
				l = split(s);
				for (int i = 0; i < l.size(); i++) {
					if (l.get(i).equalsIgnoreCase(pname) && Integer.parseInt(l.get(i + 1)) > 0) {
						flag1 = true;

						if (l.get(i + 1).length() < 2 && lqn >= qn) {
                            System.out.println("<respond to the alert on the screen>");
							JFrame f1 = new JFrame();
							JOptionPane.showMessageDialog(f1,
									"Hurry up...This product is running out of stock(less than 10 items left!!)",
									"Alert", JOptionPane.WARNING_MESSAGE);
						}
						lqn = Integer.parseInt(l.get(i + 1));
						if (lqn < qn) {
							System.out.println("<respond to the alert on the screen>");
							flag2 = false;
							JFrame f2 = new JFrame();
							JOptionPane.showMessageDialog(f2, "Not enough quantity in stock..Try lesser quantity ",
									"Alert", JOptionPane.WARNING_MESSAGE);
						} else if (lqn > qn) {
							flag3 = true;
							newstock = lqn - qn;
							String b = Integer.toString(newstock);
							s.replace(l.get(i + 1), b);
							total = Integer.parseInt(l.get(i + 2)) * qn;

						} else if (lqn == qn) {
							flag4 = true;
							newstock = 0;
							String b = Integer.toString(newstock);
							s.replace(l.get(i + 1), b);
							float dis = findDiscount(pname);

							distotal = (dis / 100.0f) * Integer.parseInt(l.get(i + 2)) * qn;

						}
					}
				}
			}
			if (flag1 == false) {
				System.out.println("item not in stock!");
			}
			if (flag2 == true) {
				if (flag3 == true) {
					System.out.println("Item added successfully to the cart\nTotal cost: " + total);

				} else if (flag4 == true) {
					System.out.println(
							"Item added successfully to the cart.\nApplying discounts for purchasing the entire stock....\nTotal cost after discount: "
									+ distotal);
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Do you want to give feedback about the products?y/n");
		try {
			feed = reader7.readLine();
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (feed.equals("y") || feed.equals("Y")) {
			feedback();
		}

	}

	
	
	
	
	
	
	
	public void feedback() {
		BufferedReader reader6 = new BufferedReader(new InputStreamReader(System.in));
		String feedbak;

		String file = "feedback.txt";

		try (Writer fileWriter = new FileWriter(file, true)) {
			System.out.println("\nType your feedback here: \n");
			feedbak = reader6.readLine();
			System.out.println("\nfeedback recorded..Thanks for shopping with us :)");
			String nl = System.getProperty("line.separator");
			fileWriter.write(nl + "Feedback:  " + feedbak + nl);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	
	
	
	
	
	
	public float findDiscount(String str) {
		String fileName5 = "discount.txt";
		float discount = 0.0f;
		ArrayList<String> templist1 = new ArrayList<String>();
		try (Stream<String> stream5 = Files.lines(Paths.get(fileName5))) {

			List<String> l2 = null;

			stream5.forEach(s1 -> templist1.add(s1));
			for (String s1 : templist1) {
				l2 = split(s1);
				for (int i = 0; i < l2.size(); i++) {

					if (l2.get(i).equalsIgnoreCase(str)) {

						discount = Integer.parseInt(l2.get(i + 1));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return discount;
	}

	
	
	
	
	
	
	
	public static List<String> split(String str) {
		return Stream.of(str.split(",")).map(elem -> new String(elem)).collect(Collectors.toList());
	}

	
	
	
	
	
	
	
	public void admin() {

		int option;

		BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the admin password to continue: ");
		String pass = null;
		try {
			pass = reader1.readLine();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String fileName4 = "adminpass.txt";
		ArrayList<String> templist3 = new ArrayList<String>();

		try (Stream<String> stream4 = Files.lines(Paths.get(fileName4))) {

			stream4.forEach(s4 -> templist3.add(s4));
			for (String s : templist3) {
				if (s.equals(pass)) {

					System.out.println("---------------Admin Menu----------------\n");
					System.out.println(
							"Press 1 to show current stock status\nPress 2 to show the customer feedbacks\nPress 3 to show the discount for items\n");
					option = Integer.parseInt(reader1.readLine());
					switch (option) {
					case 1:
						System.out.println("Current Stock Status: \n\n\nName,Quantity,cost/item\n");
						String fileName1 = "Database.txt";

						try (Stream<String> stream1 = Files.lines(Paths.get(fileName1))) {

							stream1.forEach(System.out::println);

						} catch (IOException e) {
							e.printStackTrace();
						}

						break;
					case 2:
						System.out.println("Customer feedbacks:\n");
						String fileName2 = "feedback.txt";

						try (Stream<String> stream2 = Files.lines(Paths.get(fileName2))) {

							stream2.forEach(System.out::println);

						} catch (IOException e) {
							e.printStackTrace();
						}

						break;
					case 3:
						System.out.println("Discount Details:\n");
						String fileName3 = "discount.txt";

						try (Stream<String> stream3 = Files.lines(Paths.get(fileName3))) {

							stream3.forEach(System.out::println);

						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					default:
						System.out.println("Invalid Input\n");
						break;
					}

				} else {
					System.out.println("wrong credentials");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
