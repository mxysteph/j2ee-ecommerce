package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import classes.*;
import static java.lang.System.out;

public class OrderDB {
	
		//GET all orders
		public ArrayList <Order> getAllOrders () {
	    	ArrayList <Order> orders = new ArrayList<Order>();
	    	Order beanOrder = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
				Connection conn = DriverManager.getConnection(connURL);
				Statement stmt = conn.createStatement();
				String sqlStr = "SELECT orders.*, users.username FROM orders LEFT OUTER JOIN users ON orders.user_id = users.id;";
				ResultSet rs = stmt.executeQuery(sqlStr);
				
				while(rs.next()) {
					beanOrder = new Order();
					beanOrder.setId(rs.getInt("id"));
						if(rs.getString("username") == null){
							beanOrder.setUsername("Unregistered User");
						}
						else {
							beanOrder.setUsername(rs.getString("username"));
						}
					beanOrder.setUser_id(rs.getInt("user_id"));
					beanOrder.setOrder_date(rs.getString("order_date"));
					beanOrder.setTotal_price(rs.getDouble("total_price"));
					beanOrder.setAddress(rs.getString("address"));
					beanOrder.setContact_number(rs.getString("contact_number"));
					orders.add(beanOrder);
				} 
				conn.close();
				
			} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
			return orders;
		}
		
		//GET Orders By User Id
		public ArrayList <Order> selectOrdersByUserId (int user_id) {
			ArrayList <Order> orderArr = new ArrayList<>();
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
				Connection conn = DriverManager.getConnection(connURL);
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM orders WHERE user_id = ?");
				stmt.setInt(1, user_id);
				ResultSet rs = stmt.executeQuery();
				
	    		while(rs.next()) {
	    			Order orderAtt = new Order();
	    			orderAtt.setId(rs.getInt("id"));
	    			orderAtt.setTotal_price(rs.getDouble("total_price"));
	    			orderAtt.setOrder_date(rs.getString("order_date"));
	    			orderArr.add(orderAtt);
				}
	    		
				conn.close();
			} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
			
			return orderArr;
		}
		
		//GET Orders By Order ID
			public ArrayList <Order> selectOrdersByOrderID (int order_id) {
		    	ArrayList <Order> orderArr = new ArrayList<Order>();
		    	Order beanOrder = null;
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
					Connection conn = DriverManager.getConnection(connURL);
					PreparedStatement stmt = conn.prepareStatement("SELECT orders.*, users.username FROM orders LEFT OUTER JOIN users ON orders.user_id = users.id  WHERE orders.id = ?;");
					stmt.setInt(1, order_id);
					ResultSet rs = stmt.executeQuery();
					
					while(rs.next()) {
						beanOrder = new Order();
						beanOrder.setId(rs.getInt("id"));
							if(rs.getString("username") == null){
								beanOrder.setUsername("Unregistered User");
							}
							else {
								beanOrder.setUsername(rs.getString("username"));
							}
						beanOrder.setUser_id(rs.getInt("user_id"));
						beanOrder.setOrder_date(rs.getString("order_date"));
						beanOrder.setTotal_price(rs.getDouble("total_price"));
						beanOrder.setAddress(rs.getString("address"));
						beanOrder.setContact_number(rs.getString("contact_number"));
						orderArr.add(beanOrder);
					}
		    		
					conn.close();
				} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
				
				return orderArr;
			}
		
		
		
		//GET Orders By Price
		public ArrayList <Order> selectAllOrdersByPrice (String order) {
	    	ArrayList <Order> orders = new ArrayList<Order>();
	    	Order beanOrder = null;
	    	String sqlStr = "";
	    	
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
				Connection conn = DriverManager.getConnection(connURL);
				Statement stmt = conn.createStatement();
				
				if(order.equals("highest")) {
					sqlStr = "SELECT orders.*, users.username FROM orders LEFT OUTER JOIN users ON orders.user_id = users.id ORDER BY CAST(total_price AS DECIMAL(10,2)) DESC";
				}
				
				else {
					sqlStr = "SELECT orders.*, users.username FROM orders LEFT OUTER JOIN users ON orders.user_id = users.id ORDER BY CAST(total_price AS DECIMAL(10,2)) ASC";
				}
				
				ResultSet rs = stmt.executeQuery(sqlStr);
				
				while(rs.next()) {
					beanOrder = new Order();
					beanOrder.setId(rs.getInt("id"));
						if(rs.getString("username") == null){
							beanOrder.setUsername("Unregistered User");
						}
						else {
							beanOrder.setUsername(rs.getString("username"));
						}
					beanOrder.setUser_id(rs.getInt("user_id"));
					beanOrder.setOrder_date(rs.getString("order_date"));
					beanOrder.setTotal_price(rs.getDouble("total_price"));
					beanOrder.setAddress(rs.getString("address"));
					beanOrder.setContact_number(rs.getString("contact_number"));
					orders.add(beanOrder);
				} 
				conn.close();
				
			} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
			return orders;
		}		
		
		

		//POST Order From Registered User
		public int postUserOrder (int user_id, double total_price, String address, String contact_number) {
			int rs = 0;
			int order_id = 0;
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
				Connection conn = DriverManager.getConnection(connURL);
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (user_id, total_price, address, contact_number) VALUES (?,?,?,?);");
				stmt.setInt(1, user_id);
				stmt.setDouble(2, total_price);
				stmt.setString(3, address);
				stmt.setString(4, contact_number);
				rs = stmt.executeUpdate();
				
	    		if(rs == 1) {
	    			Statement stmt2 = conn.createStatement();
	    			String sqlStr = "SELECT id FROM orders ORDER BY id DESC LIMIT 1;";
	    			ResultSet rs2 = stmt2.executeQuery(sqlStr);
	    			
	    			if(rs2.next()) {
	    				order_id = rs2.getInt("id");
	    			}
				}
				conn.close();
			} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
			
			return order_id;
		}
		
		//POST Order From Unregistered User
		public int postOrder (double total_price, String address, String contact_number) {
			int rs = 0;
			int order_id = 0;
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
				Connection conn = DriverManager.getConnection(connURL);
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (total_price, address, contact_number) VALUES (?,?,?);");
				stmt.setDouble(1, total_price);
				stmt.setString(2, address);
				stmt.setString(3, contact_number);
				rs = stmt.executeUpdate();
				
	    		if(rs == 1) {
	    			System.out.println("Order received!");
	    			Statement stmt2 = conn.createStatement();
	    			String sqlStr = "SELECT id FROM orders ORDER BY id DESC LIMIT 1;";
	    			ResultSet rs2 = stmt2.executeQuery(sqlStr);
	    			
	    			if(rs2.next()) {
	    				order_id = rs2.getInt("id");
	    			}
				}
				conn.close();
			} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
			
			return order_id;
		}
		
		
		//GET valid credit card numbers
		public ArrayList <String> selectValidCardNumbers () {
			ArrayList <String> cardNumArr = new ArrayList<String>();
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
				Connection conn = DriverManager.getConnection(connURL);
				Statement stmt = conn.createStatement();
				String sqlStr = "SELECT * FROM cards";
				ResultSet rs = stmt.executeQuery(sqlStr);
				
	    		while(rs.next()) {
	    			cardNumArr.add(rs.getString("card_number"));
				}
	    		
				conn.close();
			} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
			
			return cardNumArr;
		}
}
