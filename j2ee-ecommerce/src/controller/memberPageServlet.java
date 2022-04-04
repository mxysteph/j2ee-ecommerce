package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;
import classes.*;

/**
 * Servlet implementation class memberPageServlet
 */
@WebServlet("/memberPageServlet")
public class memberPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public memberPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		
		String role = (String) session.getAttribute("role");
		if(role == null){
			response.sendRedirect("view/loginPage.jsp?status=unauthorized");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserDB udatabase = new UserDB();
		

		if(request.getParameter("action").equals("login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			User ubean = udatabase.verifyLogin(username, password);
			
			if(ubean == null) {
				response.sendRedirect("/j2ee-ecommerce/view/loginPage.jsp?status=invalid");
				return;
			}
			
			else {
				String role = ubean.getRole();
				String id = Integer.toString(ubean.getId());
				String email = ubean.getEmail();
				String address = ubean.getAddress();
				String contact_number = ubean.getContact_number();
				String created_at = ubean.getCreated_at();

				
				session.setAttribute("role", role);
				session.setAttribute("id", id);
				session.setAttribute("username", username);
				session.setAttribute("email", email);
				session.setAttribute("address", address);
				session.setAttribute("contact_number", contact_number);	
				session.setAttribute("created_at", created_at);				
				response.sendRedirect("view/memberPage.jsp");
				return;
			}		
		}
		
		else if(request.getParameter("action").equals("post")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String role = "member";
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String contact_number = request.getParameter("contact_number");
			
			User ubean = udatabase.postUser(username, password, role, email, address, contact_number);

			role = ubean.getRole();
			String id = Integer.toString(ubean.getId());
			username = ubean.getUsername();
			email = ubean.getEmail();
			address = ubean.getAddress();
			contact_number = ubean.getContact_number();
			
			session.setAttribute("role", role);
			session.setAttribute("id", id);
			session.setAttribute("username", username);
			session.setAttribute("email", email);
			session.setAttribute("address", address);
			session.setAttribute("contact_number", contact_number);
		}
		
		else if (request.getParameter("action").equals("update")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String role = "member";
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String contact_number = request.getParameter("contact_number");
			int id = Integer.parseInt(request.getParameter("id"));

			int ubean = udatabase.putUser(username, password, role, email, address, contact_number, id);
			
			if(ubean == 1) {
				User queriedUser = udatabase.getUserById(id);
				role = queriedUser.getRole();
				username = queriedUser.getUsername();
				email = queriedUser.getEmail();
				address = queriedUser.getAddress();
				contact_number = queriedUser.getContact_number();
				id = queriedUser.getId();
				
				session.setAttribute("role", role);
				session.setAttribute("id", id);
				session.setAttribute("username", username);
				session.setAttribute("email", email);
				session.setAttribute("address", address);
				session.setAttribute("contact_number", contact_number);
			}
		}
		
		String role = (String) session.getAttribute("role");
		if(role == null){
			response.sendRedirect("view/loginPage.jsp?status=unauthorized");
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/view/memberPage.jsp");
		rd.forward(request, response);
	}
}


