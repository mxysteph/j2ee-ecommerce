package classes;

public class Drink {
	//Class Attributes//
	int id;
	String name;
	String description_short;
	String description_long;
	double cost_price;
	double retail_price;
	int stock_quantity;
	String product_category;
	int sold_quantity;
	String image_location;
	
	 //Default Constructor//
	 public Drink() {
	 }
	
	//Overload Constructor//
	 public Drink (int id, String name, String description_short, 
	String description_long, double cost_price, double retail_price,
	 int stock_quantity,String product_category,	String image_location) 
	 {
		this.id = id;
		this.name = name;
		this.description_short = description_short;
		this.description_long = description_long;
		this.cost_price = cost_price;
		this.retail_price = retail_price;
		this.stock_quantity = stock_quantity;
		this.product_category = product_category;
		this.sold_quantity = 0;
		this.image_location = image_location;
	  }
	
	//Getter and Setter Methods//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription_short() {
		return description_short;
	}
	public void setDescription_short(String description_short) {
		this.description_short = description_short;
	}
	public String getDescription_long() {
		return description_long;
	}
	public void setDescription_long(String description_long) {
		this.description_long = description_long;
	}
	public double getCost_price() {
		return cost_price;
	}
	public void setCost_price(double cost_price) {
		this.cost_price = cost_price;
	}
	public double getRetail_price() {
		return retail_price;
	}
	public void setRetail_price(double retail_price) {
		this.retail_price = retail_price;
	}
	public int getStock_quantity() {
		return stock_quantity;
	}
	public void setStock_quantity(int stock_quantity) {
		this.stock_quantity = stock_quantity;
	}
	public String getProduct_category() {
		return product_category;
	}
	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}
	public int getSold_quantity() {
		return sold_quantity;
	}
	public void setSold_quantity(int Sold_quantity) {
		this.sold_quantity = Sold_quantity;
	}
	public String getImage_location() {
		return image_location;
	}
	public void setImage_location(String image_location) {
		this.image_location = image_location;
	}
}
