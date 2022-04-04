package model;

//Imports
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import classes.Drink;

public class DrinkDB {

	//GET All Drinks
	public ArrayList <Drink> getAllDrinks () {
    	ArrayList <Drink> drinks = new ArrayList<Drink>();
    	Drink beanDrink = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			Statement stmt = conn.createStatement();
			String sqlStr = "SELECT * FROM menu;";
			ResultSet rs = stmt.executeQuery(sqlStr);
			
			while(rs.next()) {
				beanDrink = new Drink();
				beanDrink.setId(rs.getInt("id"));
				beanDrink.setName(rs.getString("name"));
				beanDrink.setDescription_short(rs.getString("description_short"));
				beanDrink.setDescription_long(rs.getString("description_long"));
				beanDrink.setCost_price(rs.getDouble("cost_price"));
				beanDrink.setRetail_price(rs.getDouble("retail_price"));
				beanDrink.setStock_quantity(rs.getInt("stock_quantity"));
				beanDrink.setProduct_category(rs.getString("product_category"));
				beanDrink.setSold_quantity(rs.getInt("sold_quantity"));
				beanDrink.setImage_location(rs.getString("image_location"));
				drinks.add(beanDrink);
			} 
			conn.close();
			
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		return drinks;
	}
	
	//GET Drinks By Category
	public ArrayList <Drink> getDrinksByCat (String category) {
    	ArrayList <Drink> drinks = new ArrayList<Drink>();
    	Drink beanDrink = null;
    
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("SELECT id, name, description_short, image_location FROM menu WHERE product_category=?");
			stmt.setString(1, category);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				beanDrink = new Drink();
				beanDrink.setId(rs.getInt("id"));
				beanDrink.setName(rs.getString("name"));
				beanDrink.setDescription_short(rs.getString("description_short"));
				beanDrink.setImage_location(rs.getString("image_location"));
				drinks.add(beanDrink);
			} 
			conn.close();
			
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		return drinks;
	}
	
	//GET Drink Details
	public Drink getDrink(int id) {
		Drink  beanDrink = new Drink();

    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
    		Connection conn = DriverManager.getConnection(connURL);
    		PreparedStatement stmt = conn.prepareStatement(
    		"SELECT * FROM menu WHERE id=?");
    		stmt.setInt(1, id);
    		ResultSet rs = stmt.executeQuery();

    		if (rs.next()) {
				beanDrink.setId(rs.getInt("id"));
				beanDrink.setName(rs.getString("name"));
				beanDrink.setDescription_short(rs.getString("description_short"));
				beanDrink.setDescription_long(rs.getString("description_long"));
				beanDrink.setCost_price(rs.getDouble("cost_price"));
				beanDrink.setRetail_price(rs.getDouble("retail_price"));
				beanDrink.setStock_quantity(rs.getInt("stock_quantity"));
				beanDrink.setProduct_category(rs.getString("product_category"));
				beanDrink.setSold_quantity(rs.getInt("sold_quantity"));
				beanDrink.setImage_location(rs.getString("image_location"));
    		}
    		
    	} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		return beanDrink;
	}
	
	//GET Drink By Query
	public ArrayList <Drink> getDrinkByQuery (String query) {
    	ArrayList <Drink> drinks = new ArrayList<Drink>();
    	Drink beanDrink = null;
    
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM menu WHERE name LIKE ?;");
			stmt.setString(1, "%" + query + "%");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				beanDrink = new Drink();
				beanDrink.setId(rs.getInt("id"));
				beanDrink.setName(rs.getString("name"));
				beanDrink.setDescription_short(rs.getString("description_short"));
				beanDrink.setDescription_long(rs.getString("description_long"));
				beanDrink.setCost_price(rs.getDouble("cost_price"));
				beanDrink.setRetail_price(rs.getDouble("retail_price"));
				beanDrink.setStock_quantity(rs.getInt("stock_quantity"));
				beanDrink.setProduct_category(rs.getString("product_category"));
				beanDrink.setSold_quantity(rs.getInt("sold_quantity"));
				beanDrink.setImage_location(rs.getString("image_location"));
				drinks.add(beanDrink);
			} 
			conn.close();
			
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		return drinks;
	}
	
	//POST New Drink
	public int postDrink(String name, String description_short, String description_long, String cost_price, String retail_price, String stock_quantity, String product_category, int sold_quantity, String image_location) {
		int rs = 0;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO menu (name, description_short, description_long, cost_price, retail_price, stock_quantity, product_category, sold_quantity, image_location) VALUES (?,?,?,?,?,?,?,?,?);");
			stmt.setString(1,name);
			stmt.setString(2, description_short);
			stmt.setString(3, description_long);
			stmt.setString(4, cost_price);
			stmt.setString(5, retail_price);
			stmt.setString(6, stock_quantity);
			stmt.setString(7, product_category);
			stmt.setInt(8, sold_quantity);
			stmt.setString(9, image_location);
			rs = stmt.executeUpdate();
			conn.close();		
			
		} catch (Exception e){JOptionPane.showMessageDialog(null, e);}
		return rs;
	}
	
	public int postFile(Blob photo) {
		int rs = 0;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO menu (image) VALUES (?);");
			stmt.setBlob(1,photo);
			rs = stmt.executeUpdate();
			conn.close();		
			
		} catch (Exception e){JOptionPane.showMessageDialog(null, e);}
		return rs;
	}
	
	//PUT Sold quantity
	public void putSoldQuantity(int quantity, int id) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("UPDATE menu SET sold_quantity = sold_quantity + ? WHERE id=?;");
			stmt.setInt(1,quantity);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			conn.close();		
		} catch (Exception e){JOptionPane.showMessageDialog(null, e);}
	}
		
	//PUT Existing Drink
	public int putDrink(String name, String description_short, String description_long, String cost_price, String retail_price, String stock_quantity, String product_category, String image_location, int id) {
		int rs = 0;
		if(image_location == null || image_location.equals("")){
			image_location = "https://i.imgur.com/tFSvK8Jg.png";
		}
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("UPDATE menu SET name=?, description_short=?, description_long=?, cost_price=?, retail_price=?, stock_quantity=?, product_category=?, image_location=? WHERE id=?;");
			stmt.setString(1,name);
			stmt.setString(2, description_short);
			stmt.setString(3, description_long);
			stmt.setString(4, cost_price);
			stmt.setString(5, retail_price);
			stmt.setString(6, stock_quantity);
			stmt.setString(7, product_category);
			stmt.setString(8, image_location);
			stmt.setInt(9, id);
			rs = stmt.executeUpdate();
			conn.close();		
		} catch (Exception e){JOptionPane.showMessageDialog(null, e);}
		return rs;
	}
	
	//DELETE Existing Drink
	public int deleteDrink(int id) {
		int rs = 0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM menu WHERE id=?;");
			stmt.setInt(1,id);
			rs = stmt.executeUpdate();
			conn.close();		
		} catch (Exception e){JOptionPane.showMessageDialog(null, e);}
		return rs;
	}
	
	// PUT Drink Quantity After Order
	public int putDrinkAfterOrder(int product_id, int order_quantity) {
		int stock_quantity = 0;
		int rs2 = 0;

    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
    		Connection conn = DriverManager.getConnection(connURL);
    		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM menu WHERE id=?");
    		stmt.setInt(1, product_id);
    		ResultSet rs = stmt.executeQuery();

    		if (rs.next()) {
				stock_quantity = rs.getInt("stock_quantity");
				int newStockQuantity = stock_quantity - order_quantity;
				PreparedStatement stmt2 = conn.prepareStatement("UPDATE menu SET stock_quantity=? WHERE id=?;");
				stmt2.setInt(1, newStockQuantity);
				stmt2.setInt(2, product_id);
	    		rs2 = stmt2.executeUpdate();
    		}	
    		
    		if(rs2 == 1) {
				conn.close();		
    		}

		} catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    	
		return rs2;
	}
	
}

