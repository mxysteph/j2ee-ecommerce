package model;

import classes.*;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class UserDB {

	//GET Login Credentials
	public User verifyLogin(String username, String password) {
    	User verifiedUser = null;
		try {
    		Class.forName("com.mysql.jdbc.Driver");
    		String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
    		Connection conn = DriverManager.getConnection(connURL);
    		PreparedStatement stmt = conn.prepareStatement(
    		"SELECT id, role, email, address, contact_number FROM users WHERE username=? AND password=?");
    		stmt.setString(1, username);
    		stmt.setString(2, password);
    		ResultSet rs = stmt.executeQuery();
    		
    		if(rs.next()){
    			verifiedUser = new User();
    			verifiedUser.setUsername(username);
    			verifiedUser.setId(rs.getInt("id"));
    			verifiedUser.setRole(rs.getString("role"));
    			verifiedUser.setEmail(rs.getString("email"));
    			verifiedUser.setAddress(rs.getString("address"));
    			verifiedUser.setContact_number(rs.getString("contact_number"));
    		}
			conn.close();
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		
		return verifiedUser;
	}
	
	//GET All Users
	public ArrayList <User> getAllUsers() {
    	ArrayList <User> userArr = new ArrayList<User>();
    	User verifiedUser = null;
    	
		try {
    		Class.forName("com.mysql.jdbc.Driver");
    		String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
    		Connection conn = DriverManager.getConnection(connURL);
    		Statement stmt = conn.createStatement();
			String sqlStr = "SELECT * FROM users;";
			ResultSet rs = stmt.executeQuery(sqlStr);
    		
    		while(rs.next()){
    			verifiedUser = new User();
    			verifiedUser.setId(rs.getInt("id"));
    			verifiedUser.setUsername(rs.getString("username"));
    			verifiedUser.setPassword(rs.getString("password"));
    			verifiedUser.setEmail(rs.getString("email"));
    			verifiedUser.setRole(rs.getString("role"));
    			verifiedUser.setAddress(rs.getString("address"));
    			verifiedUser.setContact_number(rs.getString("contact_number"));
    			verifiedUser.setCreated_at(rs.getString("created_at"));
				userArr.add(verifiedUser);
    		}
			conn.close();
			
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		
		return userArr;
	}
	
	//GET User By Id
	public User getUserById(int id) {
    	User verifiedUser = null;
		try {
    		Class.forName("com.mysql.jdbc.Driver");
    		String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
    		Connection conn = DriverManager.getConnection(connURL);
    		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id=?");
    		stmt.setInt(1, id);
    		ResultSet rs = stmt.executeQuery();
    		
    		if(rs.next()){
    			verifiedUser = new User();
    			verifiedUser.setId(rs.getInt("id"));
    			verifiedUser.setUsername(rs.getString("username"));
    			verifiedUser.setPassword(rs.getString("password"));
    			verifiedUser.setEmail(rs.getString("email"));
    			verifiedUser.setRole(rs.getString("role"));
    			verifiedUser.setAddress(rs.getString("address"));
    			verifiedUser.setContact_number(rs.getString("contact_number"));
    			verifiedUser.setCreated_at(rs.getString("created_at"));
    		}
			conn.close();
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		
		return verifiedUser;
	}
	
	//GET Users By Role
	public ArrayList <User> getUserByRole (String order) {
    	ArrayList <User> userArr = new ArrayList<User>();
    	User verifiedUser = null;
    	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users ORDER BY INSTR(role, ?) DESC;");
			stmt.setString(1, order);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				verifiedUser = new User();
    			verifiedUser.setId(rs.getInt("id"));
    			verifiedUser.setUsername(rs.getString("username"));
    			verifiedUser.setPassword(rs.getString("password"));
    			verifiedUser.setEmail(rs.getString("email"));
    			verifiedUser.setRole(rs.getString("role"));
    			verifiedUser.setAddress(rs.getString("address"));
    			verifiedUser.setContact_number(rs.getString("contact_number"));
    			verifiedUser.setCreated_at(rs.getString("created_at"));
				userArr.add(verifiedUser);
			} 
			conn.close();
			
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		return userArr;
	}
	
	//GET Users By Date
		public ArrayList <User> getUserByDate (String value, String order) {
	    	ArrayList <User> userArr = new ArrayList<User>();
	    	User verifiedUser = null;
	    	
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
				Connection conn = DriverManager.getConnection(connURL);
				String sqlStatement = "";
				
				if(order.equals("day")) {
					sqlStatement = "SELECT * FROM users WHERE (DAY(`created_at`) = ?)";
				}
				
				else if(order.equals("month")) {
					sqlStatement = "SELECT * FROM users WHERE (MONTH(`created_at`) = ?)";
				}
				
				else if(order.equals("year")) {
					sqlStatement = "SELECT * FROM users WHERE (YEAR(`created_at`) = ?)";
				}
				
		    	PreparedStatement stmt = conn.prepareStatement(sqlStatement);
				stmt.setString(1, value);
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					verifiedUser = new User();
	    			verifiedUser.setId(rs.getInt("id"));
	    			verifiedUser.setUsername(rs.getString("username"));
	    			verifiedUser.setPassword(rs.getString("password"));
	    			verifiedUser.setEmail(rs.getString("email"));
	    			verifiedUser.setRole(rs.getString("role"));
	    			verifiedUser.setAddress(rs.getString("address"));
	    			verifiedUser.setContact_number(rs.getString("contact_number"));
	    			verifiedUser.setCreated_at(rs.getString("created_at"));
					userArr.add(verifiedUser);
				} 
				conn.close();
				
			} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
			return userArr;
		}
		
	
	//POST New User
	public User postUser(String username,String password, String role, String email, String address, String contact_number) {
    	User verifiedUser = null;
    	
		try {
    		Class.forName("com.mysql.jdbc.Driver");
    		String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
    		Connection conn = DriverManager.getConnection(connURL);
    		PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, role, email, address, contact_number) VALUES(?,?,?,?,?,?);");
    		stmt.setString(1, username);
    		stmt.setString(2, password);
    		stmt.setString(3, role);
    		stmt.setString(4, email);
    		stmt.setString(5, address);
    		stmt.setString(6, contact_number);
    		int rs = stmt.executeUpdate();
    		
    		if(rs == 1) {
    			Statement newStmt = conn.createStatement();
    			String sqlStr = "SELECT id, username, role, email, address, contact_number FROM users ORDER BY id DESC LIMIT 1";
    			ResultSet newRs = newStmt.executeQuery(sqlStr);
        		
        		if(newRs.next()){
        			verifiedUser = new User();
        			verifiedUser.setId(newRs.getInt("id"));
        			verifiedUser.setUsername(newRs.getString("username"));
        			verifiedUser.setRole(newRs.getString("role"));
        			verifiedUser.setEmail(newRs.getString("email"));
        			verifiedUser.setAddress(newRs.getString("address"));
        			verifiedUser.setContact_number(newRs.getString("contact_number"));
        		}
    		}
    		
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		
		return verifiedUser;
	}
	
	//PUT Existing User
	public int putUser(String username,String password, String role, String email, String address, String contact_number, int id) {
    	int rs = 0;
    	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("UPDATE users SET username=?, password=?, role=?, email=?, address=?, contact_number=? WHERE id=?;");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, role);
			stmt.setString(4, email);
			stmt.setString(5, address);
			stmt.setString(6, contact_number);
			stmt.setInt(7, id);
    		rs = stmt.executeUpdate();
 
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		
		return rs;
	}
	
	//DELETE Existing User
	
	public int deleteUser(int id) {
    	int rs = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connURL = "jdbc:mysql://localhost/drinkshop?user=root&password=root&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connURL);
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id=?;");
			stmt.setInt(1, id);
    		rs = stmt.executeUpdate();
 
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e);}	
		
		return rs;
	}
	
}
