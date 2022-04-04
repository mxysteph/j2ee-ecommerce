package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import classes.OrderItem;
import classes.User;
import model.DrinkDB;
import model.OrderDB;
import model.OrderItemDB;
import model.UserDB;

@WebServlet("/paymentPageServlet")
public class paymentPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public paymentPageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "";
		
		if(request.getParameter("action").equals("display")) {
			HttpSession session = request.getSession();
			String role = (String) session.getAttribute("role");
			
			if(role != null) {
				String userIdStr = (String) session.getAttribute("id");
				int userId = Integer.parseInt(userIdStr);

				UserDB udatabase = new UserDB();
				User ubean = udatabase.getUserById(userId);
				session.setAttribute("contact_number", ubean.getContact_number());
				session.setAttribute("address", ubean.getAddress());
			}
			session.setAttribute("price", request.getParameter("price"));
			
			path = "/view/paymentPage.jsp";
		}
		
		
		else if(request.getParameter("action").equals("pay")) {
			HttpSession session = request.getSession();
			Client client = ClientBuilder.newClient();

			OrderDB odatabase = new OrderDB();
			DrinkDB ddatabase = new DrinkDB();
			OrderItemDB oidatabase = new OrderItemDB();
			
			int order_id = 0;
			int rs2 = 0;
			boolean isValidCard = false;
			boolean isValidExpiry = false;
			ArrayList <String> validCardNumbers = odatabase.selectValidCardNumbers();
			
			//arguments
			String address = request.getParameter("address");
			String contactNumber = request.getParameter("contact_number");
			String finalPriceStr = request.getParameter("final_price");
			String userIdStr = request.getParameter("user_id");
			String cardNumber = request.getParameter("card_number");
			String cardExpiry = request.getParameter("card_expiry");
			ArrayList <OrderItem> drinkCart = (ArrayList <OrderItem>) session.getAttribute("drinkCart");
			double totalPrice = Double.parseDouble(finalPriceStr);
			
			//check if CardNumber is valid
			for(String numbers: validCardNumbers) {
				if(numbers.equals(cardNumber)) {
					isValidCard = true;
					break;
				}
			}
			
			//check if card is expired
			try {
				String date = cardExpiry.toString();
				
				String input = "11/12"; // for example
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
				simpleDateFormat.setLenient(true);
				Date expiry = simpleDateFormat.parse(date);
				isValidExpiry = expiry.after(new Date());
				
			} catch (ParseException e) {e.printStackTrace();}
				
			
			
			if(isValidCard == false || isValidExpiry == false) {
				response.sendRedirect("view/paymentPage.jsp?status=invalidCard");
				return;
			}
			
			//check if order is made by registered user
			if(userIdStr != null) {
				int userId = Integer.parseInt(userIdStr);
				order_id = odatabase.postUserOrder(userId, totalPrice, address, contactNumber);
			}
			
			else {
				order_id = odatabase.postOrder(totalPrice, address, contactNumber);
			}
			
			
			//post to orderItem database
			for(OrderItem order : drinkCart) {
				int product_id = order.getProduct_id();
				String name = order.getName();
				int order_quantity = order.getQuantity();
				double retail_price = order.getRetail_price();
				String image_location = order.getImage_location();
				int rs = oidatabase.postOrderItem(order_id, product_id, name, order_quantity, retail_price, image_location);
				
				if(rs == 1) {
					//decrement stock quantity
					rs2 = ddatabase.putDrinkAfterOrder(product_id, order_quantity);
					ddatabase.putSoldQuantity(order_quantity, product_id);
				}
				
				if(rs2 == 1) {
					//invalidate session
					session.setAttribute("drinkCart", null);

					if(userIdStr != null) {
						String email = (String) session.getAttribute("email");
						String username = (String) session.getAttribute("username");
						WebTarget target = 	client.target("http://localhost:8080/j2ee-ecommerce-rest-apis/api/").path("send_email")
											.queryParam("to",email).queryParam("order_id",order_id).queryParam("username", username);
						Invocation.Builder invoBuilder = target.request(MediaType.APPLICATION_JSON);
						Response resp = invoBuilder.post(null); //POST 
								
						if(resp.getStatus() == Response.Status.OK.getStatusCode()){
						}
					}
				}
				
				path = "/view/successfulPurchasePage.jsp";
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}
