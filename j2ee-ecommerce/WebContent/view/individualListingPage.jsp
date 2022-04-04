<%
	String path = request.getContextPath();
	String name = (String) request.getAttribute("name");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
	pageEncoding="ISO-8859-1"%>
<%@page import= "classes.*"%>
<!DOCTYPE html>
<html>
<title>Blitz - <%=name%></title>
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
			<div class="w3-center w3-padding-16">Our Drinks</div>
		</div>
	</div>

	<!-- PAGE CONTENT -->
	<%
		Drink drink = (Drink) request.getAttribute("drink");
		if(drink != null){
			int id = drink.getId();
			name = drink.getName();
			String description = drink.getDescription_long();
			String category = drink.getProduct_category();
			String imageURL = drink.getImage_location();	
			String price = String.format("%.2f", drink.getRetail_price());
	%>
	
	<div class="w3-container w3-padding-32 w3-center">
		<img src="<%=imageURL%>" alt="Drink" class="w3-image" style="display: block; margin: auto" width="400" height="533">
		<div class="w3-padding-32">
			<h4><b>$<%=price%></b></h4>
			<p><small><%=category%></small></p>
			<p><%=description%></p>
		</div>
		<a href="shoppingCartPageServlet?id=<%=id%>"><button> Add To Cart</button></a>
	<%}%>
	</div>
</body>
</html>
