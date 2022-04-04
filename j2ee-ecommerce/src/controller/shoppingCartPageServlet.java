package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import classes.*;
import model.*;

@WebServlet("/shoppingCartPageServlet")
public class shoppingCartPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public shoppingCartPageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//arguments
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));

		//obtain drink details
		DrinkDB udatabase = new DrinkDB();
		Drink ubean = udatabase.getDrink(id);
		double retail_price = ubean.getRetail_price();
		String image_location = ubean.getImage_location();
		String name = ubean.getName();
		
		//increment quantity if matching id is found
		try {
			ArrayList <OrderItem> drinkCart = (ArrayList <OrderItem>) session.getAttribute("drinkCart");			
				for(OrderItem order : drinkCart) {
					if(order.getProduct_id() == id) {
						int newQuantity = order.getQuantity() + 1;
						order.setQuantity(newQuantity);
						session.setAttribute("drinkCart", drinkCart);
						return;
					}
				}
				
			//create new instance of orderLine
			OrderItem orderLine = new OrderItem();
			orderLine.setProduct_id(id);
			orderLine.setName(name);
			orderLine.setQuantity(1);
			orderLine.setRetail_price(retail_price);
			orderLine.setImage_location(image_location);
			
			//push to drinkCart
			drinkCart.add(orderLine);
			
			//set attribute
			session.setAttribute("drinkCart", drinkCart);
		} 
		
		//if drinkCart is empty
		catch (NullPointerException e) 
		
		{
			//create new array
			ArrayList <OrderItem> drinkCart = new ArrayList<OrderItem>();
			OrderItem orderLine = new OrderItem();
			orderLine.setProduct_id(id);
			orderLine.setName(name);
			orderLine.setQuantity(1);
			orderLine.setRetail_price(retail_price);
			orderLine.setImage_location(image_location);
			drinkCart.add(orderLine);
			session.setAttribute("drinkCart", drinkCart);
		}
		
		//forward request
		finally {
			RequestDispatcher rd = request.getRequestDispatcher("/view/shoppingCartPage.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

	
		if(request.getParameter("action").equals("currency")) {
			String currency = request.getParameter("currency");
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target("http://localhost:8080/j2ee-ecommerce-rest-apis/api/").path("convert_currency").queryParam("currency",currency);
			Invocation.Builder invoBuilder = target.request(MediaType.APPLICATION_JSON);
			Response resp = invoBuilder.get(); //GET 
					
			if(resp.getStatus() == Response.Status.OK.getStatusCode()){
				String result = resp.readEntity(new GenericType<String>() {});
				JSONObject jsonObj = new JSONObject(result);
				double value = jsonObj.getDouble("conversionRate");
				session.setAttribute("conversionRate", value);
				session.setAttribute("currency", currency);
			}
		}
		
		else if(request.getParameter("action").equals("quantity")) {
			String type = request.getParameter("type");
			String product_idStr = request.getParameter("product_id");
			String product_id = product_idStr.replace("/", "");
			int id = Integer.parseInt(product_id);

			ArrayList <OrderItem> drinkCart = (ArrayList <OrderItem>) session.getAttribute("drinkCart");

			//increments quantity
			if(type.equals("+")) {
				for(OrderItem order : drinkCart) {
					if(order.getProduct_id() == id) {
						int newQuantity = order.getQuantity() + 1;
						order.setQuantity(newQuantity);
						session.setAttribute("drinkCart", drinkCart);
					}
				}
			}
			
			//decrements quantity
			else if (type.equals("-")) {
				for(int i = 0; i < drinkCart.size(); i++ ) {
					if(drinkCart.get(i).getProduct_id() == id) {
						int newQuantity = drinkCart.get(i).getQuantity() - 1;
						
						if(newQuantity <= 0) {
							drinkCart.remove(i);
						}
						else {
							drinkCart.get(i).setQuantity(newQuantity);
						}
						session.setAttribute("drinkCart", drinkCart);
					}
				}
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/view/shoppingCartPage.jsp");
		rd.forward(request, response);
	}

}

