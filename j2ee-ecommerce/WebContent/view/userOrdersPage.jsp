<%String path = request.getContextPath();%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import= "classes.*"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>

<title>Blitz - Your Orders</title>
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
</style>

<%
	try {
		String role = (String) session.getAttribute("role");
		if(role == null){
			response.sendRedirect("view/loginPage.jsp?status=unauthorized");
		}
		
	} catch (Exception e){response.sendRedirect("view/loginPage.jsp?status=unauthorized");}
%>

<script>
	// Script to open and close sidebar
	function w3_open() {
		document.getElementById("mySidebar").style.display = "block";
	}

	function w3_close() {
		document.getElementById("mySidebar").style.display = "none";
	}
</script>

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
		<a	href="<%=path%>/view/loginPage.jsp" onclick="w3_close()"
			class="w3-bar-item w3-button">Login</a>
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
			<div class="w3-center w3-padding-16">My Orders</div>
		</div>
	</div>
	
	<!--PAGE CONTENT -->
	<div class="w3-main w3-content w3-padding"
		style="max-width: 1200px; margin-top: 100px">
		<div class="w3-row-padding w3-padding-16 w3-center">		
		<% 		
		ArrayList <Order> orderArr = (ArrayList <Order>) session.getAttribute("orderArr");
		if (orderArr == null || orderArr.size() == 0) {
		%>
			<p> You have no orders. Start <a href="<%=path%>/listingPageServlet">shopping </a> now!</p>
		<%}
		
		else {
		%><a href="<%=path%>/view/memberPage.jsp"> <p> Go Back </p> </a><%
	
			for(int i = 0; i < orderArr.size(); i++ ) {
				double total_price = orderArr.get(i).getTotal_price();
				int order_id = orderArr.get(i).getId();
				String order_date = orderArr.get(i).getOrder_date();
				String orderItemArrayName = "OIA" + i;
				ArrayList <OrderItem> orderItemArr = (ArrayList <OrderItem>) session.getAttribute(orderItemArrayName);
		%> 
			<table style = "margin-left: auto; margin-right: auto; margin-top: 30px; width: 40%; border-collapse: collapse; ">
				<tr style= "background-color:#ebebeb;">
					<th style="text-align: left; padding: 10px">Order ID</th>
					<th style="text-align: left; padding: 10px">Total Price</th>
					<th style="text-align: left; padding: 10px">Order Date</th>
			  	</tr>  			  	
					<tr>
					<td style="text-align: left; padding: 10px" ><p><%=order_id%></p></td>
					<td style="text-align: left; padding: 10px"><p><%=total_price%></p></td>
					<td style="text-align: left; padding: 10px"><p><%=order_date%></p></td>
				</tr>
			</table>
		
			<table style = "margin-left: auto; margin-right: auto; width: 40%; border-collapse: collapse">
				<tr>
					<th style="text-align: left; padding: 10px"><b>Order Items</b></th>
					<th style="text-align: left; padding: 10px"><b>Quantity</b></th>
					<th style="text-align: left; padding: 10px"><b>Price</b></th>
			  	</tr> 
		<%
				for(OrderItem orderItem: orderItemArr){
					String name = orderItem.getName();
					int quantity = orderItem.getQuantity();
					double retail_price = orderItem.getRetail_price();
		%>
				<tr>
					<td style="text-align: left; padding: 10px" ><p><%=name%></p></td>
					<td style="text-align: left; padding: 10px" ><p><%=quantity%></p></td>
					<td style="text-align: left; padding: 10px" ><p><%=retail_price%></p></td>
			  	</tr>
			<%}%>
			</table>
		<%}
		}
		%>
				
			</div>
		</div>
	</div>
</body>
</html>
	

