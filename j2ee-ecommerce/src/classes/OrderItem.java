package classes;

public class OrderItem {
	//Class Attributes//
	int order_id;
	int id;
	int product_id;
	String username;
	String name;
	int quantity;
	double retail_price;
	String image_location;
	
	 //Default Constructor//
	 public OrderItem() {
	 }
	
	//Overload Constructor//
	public OrderItem (int order_id, int id, int product_id, String name, int quantity, double retail_price, String image_location) {
		this.order_id = order_id;
		this.id = id;;
		this.product_id = product_id;
		this.name = name;
		this.quantity = quantity;
		this.retail_price = retail_price;
		this.image_location = image_location;
	}
	
	//Getter and Setter Methods//
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getRetail_price() {
		return retail_price;
	}
	public void setRetail_price(double retail_price) {
		this.retail_price = retail_price;
	}
	public String getImage_location() {
		return image_location;
	}
	public void setImage_location(String image_location) {
		this.image_location = image_location;
	}
}
