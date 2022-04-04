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

import classes.Drink;
import classes.User;
import model.DrinkDB;
import model.UserDB;

/**
 * Servlet implementation class userReportServlet
 */
@WebServlet("/userReportServlet")
public class userReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserDB udatabase = new UserDB();
		HttpSession session = request.getSession();
		ArrayList <User> ubean = udatabase.getAllUsers();
		ArrayList <String> userRoles = new ArrayList <String> ();
		
		String role = (String) session.getAttribute("role");
		if(role == null || role.equals("member")){
			response.sendRedirect("view/loginPage.jsp?status=unauthorized");
			return;
		}
		
        for(User user : ubean){
        	role = user.getRole();
            if(userRoles.indexOf(role) == -1){
                userRoles.add(role);
            }
        }
        
		session.setAttribute("sortedUsers", ubean);
		request.setAttribute("userRoles", userRoles);
		RequestDispatcher rd = request.getRequestDispatcher("view/userReport.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDB udatabase = new UserDB();
		HttpSession session = request.getSession();
		ArrayList <User> ubean = udatabase.getAllUsers();
		String path = "";
		
		String role = (String) session.getAttribute("role");
		if(role == null || role.equals("member")){
			response.sendRedirect("view/loginPage.jsp?status=unauthorized");
			return;
		}
		
		if(request.getParameter("action").equals("sort")) {
			String order = request.getParameter("option");
			ubean = udatabase.getUserByRole(order);
			session.setAttribute("sortedUsers", ubean);
			path = "view/userReport.jsp";
		}
		
		else if(request.getParameter("action").equals("filter")) {
			String order = request.getParameter("option");
			String value = request.getParameter("value");
			ubean = udatabase.getUserByDate(value, order);
			session.setAttribute("sortedUsers", ubean);
			path = "view/userReport.jsp";
		}

		else if(request.getParameter("action").equals("delete")) {
			String idStr = request.getParameter("id");
			int id = Integer.parseInt(idStr);
			int rs = udatabase.deleteUser(id);
			path = "view/adminSuccessPage.jsp?type=user&action=deleted";
		}
		
		else if(request.getParameter("action").equals("create")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			role = request.getParameter("role");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String contact_number = request.getParameter("contact_number");
			
			User rs = udatabase.postUser(username, password, role, email, address, contact_number);
			path = "view/adminSuccessPage.jsp?type=user&action=created";
		}
		
		else if(request.getParameter("action").equals("updateForm")) {
			ArrayList <String> userRoles = new ArrayList <String> ();
	        for(User user : ubean){
	        	role = user.getRole();
	            if(userRoles.indexOf(role) == -1){
	                userRoles.add(role);
	            }
	        }
	  
			String idStr = request.getParameter("id");
			int id = Integer.parseInt(idStr);
			User updatingUser = udatabase.getUserById(id);
			session.setAttribute("updatingUser", updatingUser);
			session.setAttribute("userRoles", userRoles);
			path = "view/updateFormPage.jsp?type=user&action=update";
		}
		
		else if(request.getParameter("action").equals("update")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			role = request.getParameter("role");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String contact_number = request.getParameter("contact_number");
			String idStr = request.getParameter("id");
			int id = Integer.parseInt(idStr);
			
			int rs = udatabase.putUser(username, password, role, email, address, contact_number, id);
			path = "view/adminSuccessPage.jsp?type=user&action=updated";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
