package classes;

public class Order {
	//Class Attributes//
	int id;
	int user_id;
	String username;
	String order_date;
	double total_price;
	String address;
	String contact_number;
	
	//Default Constructor//
	public Order() {
	}

	//Overload Constructor//
	public Order(int id, int user_id, double total_price, String address, String contact_number) {
		this.id = id;
		this.user_id = user_id;
		this.total_price = total_price;
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
	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getOrder_date() {
		return order_date;
	}


	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	
	public double getTotal_price() {
		return total_price;
	}


	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
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
}
