package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import classes.Drink;
import classes.OrderItem;

public class OrderItemDB {

	//GET Orders By Product ID
	public ArrayList <Integer> selectOrderItemsByProductID (int product_id) {
    	ArrayList <Integer> orderNumber = new ArrayList<Integer>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM order_item WHERE product_id = ?");
			stmt.setInt(1, product_id);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				orderNumber.add(rs.getInt("order_id"));
			} 
			conn.close();			
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		return orderNumber;
	}
	
	//GET OrderItem FROM Order ID
			public ArrayList <OrderItem> selectOrderItemsByOrder (int order_id) {
				ArrayList <OrderItem> orderItemArr = new ArrayList<OrderItem>();
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
					Connection conn = DriverManager.getConnection(connURL);
					PreparedStatement stmt = conn.prepareStatement("SELECT * FROM order_item WHERE order_id =?");
					stmt.setInt(1, order_id);
					ResultSet rs = stmt.executeQuery();
					
		    		while(rs.next()) {
		    			OrderItem orderItem = new OrderItem();
		    			orderItem.setId(rs.getInt("order_item_id"));
		    			orderItem.setOrder_id(rs.getInt("order_id"));
		    			orderItem.setImage_location(rs.getString("image_location"));
		    			orderItem.setName(rs.getString("name"));
		    			orderItem.setQuantity(rs.getInt("quantity"));
		    			orderItem.setRetail_price(rs.getDouble("retail_price"));
		    			orderItem.setProduct_id(rs.getInt("product_id"));
		    			orderItemArr.add(orderItem);
					}
					conn.close();
				} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
				
				return orderItemArr;
			}
	
	//POST Order Item
	public int postOrderItem (int order_id, int product_id, String name, int order_quantity, double retail_price, String image_location) {
		int rs = 0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO order_item (order_id, product_id, name, quantity, retail_price, image_location) VALUES (?,?,?,?,?,?);");
			stmt.setInt(1, order_id);
			stmt.setInt(2, product_id);
			stmt.setString(3, name);
			stmt.setInt(4, order_quantity);
			stmt.setDouble(5, retail_price);
			stmt.setString(6, image_location);
			rs = stmt.executeUpdate();
			
    		if(rs == 1) {
    			conn.close();
			}
    		
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		
		return rs;
	}
	
	
}
