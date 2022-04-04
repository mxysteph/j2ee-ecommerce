package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.*;
import model.*;

import static java.lang.System.out;
/**
 * Servlet implementation class orderReportServlet
 */
@WebServlet("/orderReportServlet")
public class orderReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public orderReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DrinkDB udatabase = new DrinkDB();
		ArrayList <Drink> ubean = udatabase.getAllDrinks();
		HttpSession session = request.getSession();
		request.setAttribute("drinks", ubean);
		
		String role = (String) session.getAttribute("role");
		if(role == null || role.equals("member")){
			response.sendRedirect("view/loginPage.jsp?status=unauthorized");
			return;
		}
		
		OrderDB odatabase = new OrderDB();
		ArrayList <Order> obean = odatabase.getAllOrders();
		request.setAttribute("sortedOrders", obean);
		
		RequestDispatcher rd = request.getRequestDispatcher("view/orderReport.jsp");
		rd.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "null", "null" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DrinkDB udatabase = new DrinkDB();
		OrderDB odatabase = new OrderDB();
		OrderItemDB oidatabase = new OrderItemDB();
		HttpSession session = request.getSession();
		ArrayList <Drink> ubean = udatabase.getAllDrinks();
		ArrayList <Order> obean = odatabase.getAllOrders();
		String path = "view/orderReport.jsp";
		
		String role = (String) session.getAttribute("role");
		if(role == null || role.equals("member")){
			response.sendRedirect("view/loginPage.jsp?status=unauthorized");
			return;
		}
		
		request.setAttribute("drinks", ubean);
		request.setAttribute("orders", obean);
		
		if(request.getParameter("action").equals("sort")) {	
			String order = request.getParameter("option");
			obean = odatabase.selectAllOrdersByPrice(order);
			request.setAttribute("sortedOrders", obean);
			path = "view/orderReport.jsp?action=sort";
		} 

		else if(request.getParameter("action").equals("filter")) {
			String product_id_string = (String) request.getParameter("option");
			
			int product_id = Integer.parseInt(product_id_string);
			ArrayList <Integer> orderNumber = oidatabase.selectOrderItemsByProductID(product_id);
			
			for(int i = 0; i < orderNumber.size(); i++) {
				int order_id = orderNumber.get(i);
				ArrayList <Order> orderArr = odatabase.selectOrdersByOrderID(order_id);
				String orderArrName =  "OA" + i;
				session.setAttribute(orderArrName, orderArr);
			}
			
			session.setAttribute("orderNumSize", orderNumber.size());
			path = "view/orderReport.jsp?action=product";
		}
		
		else if(request.getParameter("action").equals("top10")) {
			HashMap<String, Double> totalSpent = new HashMap<String, Double>();
			
			for(int i=0; i<obean.size(); i++) {
				if(!(obean.get(i).getUser_id() == 0)) {
					String key = String.valueOf(obean.get(i).getUsername());
					double value = obean.get(i).getTotal_price();
					
					if(totalSpent.get(key) != null) totalSpent.put(key, totalSpent.get(key)+value); //add the value to the key again if the key is not null
					else totalSpent.put(key, value);
				}
			}
			ArrayList <Order> sortedOrders = new ArrayList <Order>();
			Order sortedOrder;
			
			for(Map.Entry mapElement : totalSpent.entrySet()) {
				String key = String.valueOf(mapElement.getKey()), value = String.valueOf(mapElement.getValue());
				
				sortedOrder = new Order();
				
				sortedOrder.setId(0);
				sortedOrder.setUsername(key); //set username for display
				sortedOrder.setTotal_price(Double.parseDouble(value)); //set total price for display
				sortedOrder.setAddress("null");
				sortedOrder.setContact_number("null");
				sortedOrders.add(sortedOrder);
			}
			
			for(int i=1; i < 11; i++) {
				for(int j=sortedOrders.size(); j > 0+i; j--) {
					if(sortedOrders.get(j-1).getTotal_price() > sortedOrders.get(j-2).getTotal_price()) Collections.swap(sortedOrders, j-1, j-2); //swap index if they are smaller than each other
				}
				sortedOrders.get(i-1).setId(i);
			}
			
			sortedOrders.subList(10, sortedOrders.size()).clear(); //clear anything below index 10
			request.setAttribute("sortedOrders", sortedOrders);
			path = "view/orderReport.jsp?action=top10";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);	
	}

}




