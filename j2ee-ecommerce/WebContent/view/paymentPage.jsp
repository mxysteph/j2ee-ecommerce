<%String path = request.getContextPath();%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import= "classes.*"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>

<title>Blitz - Payment</title>
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
	String priceStr = (String) session.getAttribute("price");
	double price = Double.parseDouble(priceStr);
	double tax = 0.1 * price;
	double finalPrice = (price + tax);
	String taxStr = String.format("%.2f", tax);
	String finalPriceStr = String.format("%.2f", finalPrice);
	
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
		<a	href="<%=path%>/view/loginPage.jsp" onclick="w3_close()"
			class="w3-bar-item w3-button">Login</a>
		<a	href="<%=path%>/view/memberPage.jsp" onclick="w3_close()"
			class="w3-bar-item w3-button">Member Page</a>
	</nav>

	<!-- TOP MENU -->
	<div class="w3-top">
		<div class="w3-white w3-xlarge"
			style="max-width: 1200px; margin: auto">
			<a href="<%=path%>/view/index.jsp" style="text-decoration: none"><div class="w3-center w3-padding-16">Blitz</div></a>
			<div class="w3-button w3-padding-16 w3-left" onclick="w3_open()">Open</div>
			<div class="w3-center w3-padding-16">Make Payment</div>
		</div>
	</div>
	
	<!--PAGE CONTENT -->
	<div class="w3-main w3-content w3-padding"
		style="max-width: 1200px; margin-top: 100px">
		<div class="w3-row-padding w3-padding-16 w3-center">
			<div class="w3-container w3-padding-10 w3-center">
				<table border="1" style= "margin-left:auto; margin-right: auto; margin-top: 50px; width: 50%; border-collapse: collapse;" >
						<tr height="50px" style="padding: 15px;">
							<td><p>Total</p></td>
							<td><p>$<%=priceStr%></p></td>
						</tr>
						<tr height="50px" style="padding: 15px;">
							<td><p>GST (10%)</p></td>
							<td><p>$<%=taxStr%></p></td>
						</tr>
						<tr height="50px" style="padding: 15px;">
							<td><p>Grand Total</p></td>
							<td><p><b>$<%=finalPriceStr%></b></p></td>
						</tr>
				</table>
				
				<%
				try {
					String statusCode = (String) request.getParameter("status");
					if(statusCode.equals("invalidCard")){
					%>
						<p> You have entered an invalid card! Please try again! </p>
				<%	}
				
				} catch (Exception e) {}
			
				%>
				
				<form action = "<%=path%>/paymentPageServlet?action=pay" method="POST">
					<table style= "margin-left:auto; margin-right: auto; border-collapse: collapse; width: 50%">
							<tr height="50px" style="padding: 15px;">
								<td>
				    				<p><b>Order Details: </b></p>
								</td>
							</tr>
					<% 
					String role = (String) session.getAttribute("role");

						if(role != null){
							String userId = (String) session.getAttribute("id");
							String address = (String) session.getAttribute("address");
							String contact_number = (String) session.getAttribute("contact_number");
					%>
							<tr height="50px" style="padding: 15px;">
								<td>
									<input readonly style="width: 50%" type="text" id="address" name="address" value="<%=address%>">
								</td>
						  	</tr>
							<tr height="50px" style="padding: 15px;">
								<td>
									<input readonly style="width: 50%" type="text" id="contact_number" name="contact_number" value="<%=contact_number%>">
								</td>
						  	</tr>
						  	<tr height="50px" style="padding: 15px;">
								<td>
									<input type="hidden" name="user_id" value="<%=userId%>">
								</td>
						  	</tr>
					  	<%} 
					  	
					  	else {%>
					  		<tr height="50px" style="padding: 15px;">
								<td>
									<input style="width: 50%" type="text" id="address" placeholder="Address" name="address">
								</td>
						  	</tr>
							<tr height="50px" style="padding: 15px;">
								<td>
									<input style="width: 50%" type="text" id="contact_number" placeholder="Contact Number" name="contact_number">
								</td>
						  	</tr>
					  <%}%> 
					</table>
				
					<table style= "margin-left:auto; margin-right: auto; border-collapse: collapse; width: 50%">
						<tr height="50px" style="padding: 15px;">
							<td>
			    				<p><b>Payment Details: </b></p>
							</td>
						</tr>
						<tr height="50px" style="padding: 15px;">
							<td>
								<input style="width: 50%" type="text" placeholder="Full Name">
							</td>
					  	</tr>
						<tr height="50px" style="padding: 15px;">
						  	<td>
								<input style="width: 50%" type="text" id="card_number" placeholder="Card Number" name="card_number" minlength="12">
							</td>
						</tr>
						<tr height="50px" style="padding: 15px;">
						  	<td>
								<input style="width: 50%" type="text" id="card_expiry" placeholder="MM/YY" name="card_expiry">
							</td>
						</tr>
						<tr height="50px" style="padding: 15px;">
						  	<td>
								<input style="width: 50%" type="text" placeholder="CVV">
							</td>
						</tr>
						<tr height="50px" style="padding: 15px;">
						  	<td>
								<input style="width: 50%" type="submit" value="submit">
							</td>
						</tr>
						<tr height="50px" style="padding: 15px;">
						  	<td>
								<input style="width: 50%" type="hidden"  name="final_price" value="<%=finalPriceStr%>">
							</td>
						</tr>
				  	</table>
				</form>	
				</div>
			</div>
		</div>
</body>
</html>
	
	