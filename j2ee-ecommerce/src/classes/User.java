package classes;

public class User {
	//Class Attributes//
	 int id;
	 String username;
	 String password;
	 String email;
	 String role;
	 String address;
	 String contact_number;
	 
	 
	 //Default Constructor//
	 public User() {
	 }

	 
	//Overload Constructor//
	 public User(int id, String username, String password, String role, String email, String address, String contact_number) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.email = email;
		this.address = address;
		this.contact_number = contact_number;
	 }
	 
	//Getter and Setter Methods//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact_number() {
		return contact_number;
	}
	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	String created_at;
}
