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

import classes.Order;
import classes.OrderItem;
import model.OrderDB;
import model.OrderItemDB;

/**
 * Servlet implementation class userOrdersPageServlet
 */
@WebServlet("/userOrdersPageServlet")
public class userOrdersPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userOrdersPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		
		String role = (String) session.getAttribute("role");
		
		if(role == null){
			response.sendRedirect("view/loginPage.jsp?status=unauthorized");
			return;
		}
		
		String userIdStr = (String) session.getAttribute("id");
		int userId = Integer.parseInt(userIdStr);
		OrderDB odatabase = new OrderDB();
		OrderItemDB oidatabase = new OrderItemDB();
		
		ArrayList <Order> orderArr = odatabase.selectOrdersByUserId(userId);
		session.setAttribute("orderArr", orderArr);
		
		for(int i = 0; i < orderArr.size(); i++ ) {			
			int order_id = orderArr.get(i).getId();
			ArrayList <OrderItem> orderItemArr = oidatabase.selectOrderItemsByOrder(order_id);
			String orderItemArrName =  "OIA" + i;
			session.setAttribute(orderItemArrName, orderItemArr);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/view/userOrdersPage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
