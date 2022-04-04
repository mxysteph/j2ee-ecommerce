<%String path = request.getContextPath();%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import= "classes.*"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>

<title>Blitz - Shopping Cart</title>
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
			<div class="w3-center w3-padding-16">Shopping Cart</div>
		</div>
	</div>
	
	<!--PAGE CONTENT -->
	<div class="w3-main w3-content w3-padding"
		style="max-width: 1200px; margin-top: 100px">
		<div class="w3-row-padding w3-padding-16 w3-center">
		<% 	
			try{
					double totalPrice = 0;
					double conversionRate = 1;
					
					ArrayList <OrderItem> drinkCart = (ArrayList <OrderItem>) session.getAttribute("drinkCart");
					String currency = (String) session.getAttribute("currency");
					if(currency == null){currency = "SGD";}
					try {
						conversionRate = new Double(session.getAttribute("conversionRate").toString());
					} 
					catch (NullPointerException e){ conversionRate = 1;}
				
					for(OrderItem drink : drinkCart) {
						//loop through array attribute
						int product_id = drink.getProduct_id();
						String name = drink.getName();
						int quantity = drink.getQuantity();
						String image = drink.getImage_location();
						double price = drink.getRetail_price() * conversionRate;
						
						//format prices
						double quantifiedPrice = price * quantity;
						String priceStr = String.format("%.2f", price);
						String quantifiedPriceStr = String.format("%.2f", quantifiedPrice);
						totalPrice += quantifiedPrice;

		%>	
			
			<div class="w3-container w3-padding-10 w3-center">
				<form action = "shoppingCartPageServlet?action=quantity" method="POST">
					<table style= "margin-left:auto; margin-right: auto; border-collapse: collapse; width: 50%">
						<tr>
							<td style="text-align: left; width: 10%;"><img src=<%=image%> style="width:20px"></td>
							<td style="text-align: left; width: 30%"><b><%=name%></b></td>
							<td style="text-align: left; width: 15%"><p>$<%=priceStr%></p></td>
							<td style="text-align: left; width: 10%"><p><%=quantity%></p></td>
							<td style="text-align: left; width: 20%"><p>$<%=quantifiedPriceStr%>  <%=currency%></p></td>
							<td style="text-align: left; width: 10%">
							 	<p><input type="submit" name="type" value="+"/></p>
							</td>
							<td style="text-align: left; width: 10%">
							 	<p><input type="submit" name="type" value="-"/></p>
							</td>
					  	</tr>
				  	<input type="hidden" name="product_id" value=<%=product_id%>/>
 				</form>
					<%} //end of loop
						String totalPriceStr = String.format("%.2f", totalPrice);
					
					if(drinkCart.size() > 0){
					%>
						<table style= "margin-left:auto; margin-right: auto; margin-top: 25px; border-collapse: collapse; width: 50%">
							<tr>
								<td style="text-align: left; width: 15%;"><a href="clearServlet">Clear</a></td>
								<td style="text-align: left; width: 20%;"><a href="listingPageServlet">Go Back </a></td> 
								<td style="text-align: left; width: 35%;">
									<form action = "shoppingCartPageServlet?action=currency" method="POST">
									 <select name="currency">
								      	<option value="SGD">SGD</option>
								      	<option value="USD">USD</option>
								      	<option value="MYR">MYR</option>
			 						    <option value="GBP">GBP</option>
			   						    <option value="EUR">EUR</option>
								      </select>
			   						<input type="submit" value="Change">
									</form>					
								</td>     
								<td style="text-align: left; width: 25%;"><b>Total: $<%=totalPriceStr%></b></td>   
								<td style="text-align: left; width: 5%;">
									<form action = "<%=path%>/paymentPageServlet?action=display" method="POST">
				   						<input type="hidden" name= "price" value="<%=totalPriceStr%>">
				   						<input type="submit" value="Pay">
				   					</form>
		   						</td>
			  				</tr>
						</table>
					<%}
				} //end of if statement
			catch (Exception e) {%>
				<div class="w3-container w3-padding-10 w3-center">
					<p> You have no items in your cart. Start <a href="<%=path%>/listingPageServlet">shopping </a> now!</p>
				</div>
			<%}%>
			</div>
		</div>
	</div>
</body>
</html>
	
	