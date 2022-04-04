<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import= "classes.*"%>
<%@ page import="java.util.ArrayList" %>

<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<title>Blitz - Order Management</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Karma">

<style>
	body, h1, h2, h3, h4, h5, h6 {
		font-family: "Karma", sans-serif
	}
	
	.w3-bar-block .w3-bar-item {
		padding: 20px
	}
	
	table {
	  border-collapse: collapse;
	  width: 100%;
	}
	
	td, th {
	  border: 1px solid #dddddd;
	  text-align: left;
	  padding: 8px;
	}
</style>

<script>
	// Script to open and close sidebar
	function w3_open() {
		document.getElementById("mySidebar").style.display = "block";
	}

	function w3_close() {
		document.getElementById("mySidebar").style.display = "none";
	}
</script>

<%
	try {
		String role = (String) session.getAttribute("role");
		if(role.equals("member") || role == null){
			response.sendRedirect("view/loginPage.jsp?status=unauthorized");
		}
		
	} catch (Exception e){response.sendRedirect("view/loginPage.jsp?status=unauthorized");}
%>

<body>
	<!-- SIDEBAR (hidden by default) -->
	<nav
		class="w3-sidebar w3-bar-block w3-card w3-top w3-xlarge w3-animate-left"
		style="display: none; z-index: 2; width: 40%; min-width: 300px"
		id="mySidebar">
		
		<a 	href="javascript:void(0)" onclick="w3_close()"
			class="w3-bar-item w3-button">Close</a> 
		<a 	href="<%=path%>/listingPageServlet"
			onclick="w3_close()" class="w3-bar-item w3-button">Our Drinks</a> 
		<a 	href="<%=path%>/categoryPageServlet?category=Boba" onclick="w3_close()"
			class="w3-bar-item w3-button">Boba</a> 
		<a	href="<%=path%>/categoryPageServlet?category=Kombuchas" onclick="w3_close()"
			class="w3-bar-item w3-button">Kombuchas</a> 
		<a	href="<%=path%>/categoryPageServlet?category=Milkshakes" onclick="w3_close()"
			class="w3-bar-item w3-button">Milkshakes</a> 
		<a	href="<%=path%>/categoryPageServlet?category=Lattes" onclick="w3_close()"
			class="w3-bar-item w3-button">Lattes</a> 
		<a	href="<%=path%>/view/shoppingCartPage.jsp" onclick="w3_close()"
			class="w3-bar-item w3-button">Shopping Cart</a> 
		<a	href="<%=path%>/view/registrationPage.jsp" onclick="w3_close()"
			class="w3-bar-item w3-button">Register</a> 
		<a	href="<%=path%>/view/memberPage.jsp" onclick="w3_close()"
			class="w3-bar-item w3-button">Member Page</a>
		<a	href="<%=path%>/clearServlet?action=all" onclick="w3_close()"
			class="w3-bar-item w3-button">Log Out</a>
	</nav>

	<!-- TOP MENU -->
	<div class="w3-top">
		<div class="w3-white w3-xlarge"
			style="max-width: 1200px; margin: auto">
			<a href="<%=path%>/view/index.jsp" style="text-decoration: none"><div class="w3-center w3-padding-16">Blitz</div></a>
			<div class="w3-button w3-padding-16 w3-left" onclick="w3_open()">Open</div>
			<div class="w3-center w3-padding-16">Order Management</div>
		</div>
	</div>

		<!--PAGE CONTENT -->
	<div class="w3-main w3-content w3-padding"
		style="max-width: 1200px; margin-top: 100px">
		<div class="w3-row-padding w3-padding-16 w3-center">
			<h4>Filter by:</h4>
		    <br>
		    <form action="<%=path%>/orderReportServlet?action=filter" method="post">
		    	Product:
				<select name="option">
		      		<%
	      			ArrayList <Drink> allDrinks = (ArrayList <Drink>) request.getAttribute("drinks");
	      		
		      		if(allDrinks != null){
		    			for(Drink drink : allDrinks) {
		    				int id = drink.getId();
		    				String name = drink.getName();
		      		%>
		      				<option value=<%=id%>><%=name%></option>
      				<% 
		    			}
		      		}
      				%>
	      		</select>
			    <input type="submit" value="Filter">
		    </form><br>
		    <form action="<%=path%>/orderReportServlet?action=top10" method="post">
			    <input type="submit" value="Top 10 customers who made most purchase by value">
		    </form><br>
		    <form action="<%=path%>/orderReportServlet?action=sort" method="post">
				<select name="option">
			      	<option value="highest">Highest Price</option>
			      	<option value="lowest">Lowest Price</option>
		      	</select>
		      	<input type="submit" value="Sort"><br>
	      	</form><br>
 	    	<a href="<%=path%>/view/memberPage.jsp"> <p> Go Back </p> </a>
      	<div>
      	
 <%	
	if (request.getParameter("action") == null || request.getParameter("action").equals("sort") || request.getParameter("action").equals("product")) { 
%>
    	<table>
    		<tr>
    			<th>ID</th>
    			<th>Username</th>
    			<th>Order Date</th>
    			<th>Total Price</th>
    			<th>Address</th>
    			<th>Contact Number</th>
    		</tr>
<%	
	if (request.getParameter("action") == null || request.getParameter("action").equals("sort")) {
		ArrayList <Order> orders = (ArrayList <Order>) request.getAttribute("sortedOrders");
			if(orders != null){
			for(Order order : orders) {
				int id = order.getId();
				String username = order.getUsername();
				String order_date = order.getOrder_date();
				Double total_price = order.getTotal_price();
				String address = order.getAddress();
				String contact_number = order.getContact_number();
	%>

					<tr>
						<td><%=id %></td>
						<td><%=username %></td>
						<td><%=order_date %></td>
						<td><%=total_price %></td>
						<td><%=address %></td>
						<td><%=contact_number %></td>
					</tr>
				<%}
			}
		} 


	else {
		String orderNumArrSizeStr = (String)session.getAttribute("orderNumSize");		
		Double orderNumArrSize = new Double (orderNumArrSizeStr);
		
		for(int i = 0; i < orderNumArrSize; i++ ) {
			String orderArrName = "OA" + i;
			ArrayList <Order> orderArr = (ArrayList <Order>) session.getAttribute(orderArrName);
			for(Order order: orderArr){
				int id = order.getId();
				String username = order.getUsername();
				String order_date = order.getOrder_date();
				Double total_price = order.getTotal_price();
				String address = order.getAddress();
				String contact_number = order.getContact_number();
				
			%>

				<tr>
					<td><%=id %></td>
					<td><b><%=username %></b></td>
					<td><%=order_date %></td>
					<td><%=total_price %></td>
					<td><%=address %></td>
					<td><%=contact_number %></td>
				</tr>
			<%
				}
			}
		}
	}

	else { %>
		<table>
    		<tr>
    			<th>ID</th>
    			<th>Username</th>
    			<th>Total Spent</th>
    		</tr>
	 <% 
	 	ArrayList <Order> orders = (ArrayList <Order>) request.getAttribute("sortedOrders");
		if(orders != null){
		for(Order order : orders) {
			int id = order.getId();
			String username = order.getUsername();
			Double total_price_str = order.getTotal_price();
			String total_price = String.format("%.2f", total_price_str);
	
	%>
			<tr>
				<td><%=id %></td>
				<td><%=username %></td>
				<td><%=total_price %></td>
			</tr>
		<%
			}
		}
	}%>
		</table>
	</div>
</div>
</div>
</body>
</html>
