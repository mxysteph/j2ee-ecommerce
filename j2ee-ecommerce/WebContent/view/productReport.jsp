<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import= "classes.*"%>
<%@ page import="java.util.ArrayList" %>

<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<title>Blitz - Product Metrics</title>
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
	try {
		String role = (String) session.getAttribute("role");
		if(role.equals("member")){
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
			<div class="w3-center w3-padding-16">Product Management</div>
		</div>
	</div>
		
		<!--PAGE CONTENT -->
	<div class="w3-main w3-content w3-padding"
		style="max-width: 1200px; margin-top: 100px">
		<div class="w3-row-padding w3-padding-16 w3-center">
		<form  action="<%=path%>/productReportServlet?action=search" method="post" style="margin-top:50px">
			<input type="text" id="query" name="query" placeholder="search drinks" style="width:90%; padding: 3px; margin: 10px; border:1px dashed black; border-radius: 10px;">
	    	<input type="submit" value="submit">
		</form>
      	<form action="<%=path%>/productReportServlet?action=stocklevel" method="post">
	      	Stock quantity lower than:
	      	<input name="value" placeholder="Enter value">
		    <input type="submit" value=Filter><br>
	    </form><br>
	    <form action="<%=path%>/productReportServlet?action=blselling" method="post">
			<select name="option">
		      	<option value="best">Best selling</option>
		      	<option value="least">Least selling</option>
	      	</select>
	      	<input type="submit" value="Sort"><br>
      	</form> <br>
      	<form action="<%=path%>/productReportServlet?action=createForm" method="post">
 		     <input type="submit" value="Create Drink"><br>
      	</form>
      	<br/>
	    <a href="<%=path%>/view/memberPage.jsp"> <p> Go Back </p> </a>
	<%
		ArrayList <Drink> drinks = (ArrayList <Drink>) request.getAttribute("sortedDrinks");
		if(drinks != null){
			for(Drink drink : drinks) {
				int id = drink.getId();
				String name = drink.getName();
				String image = drink.getImage_location();
				String description_short = drink.getDescription_short();
				String description_long = drink.getDescription_long();
				String cost_price = String.format("%.2f",drink.getCost_price());
				String retail_price = String.format("%.2f",drink.getRetail_price());
				String stock_quantity = Integer.toString(drink.getStock_quantity());
				String product_category = drink.getProduct_category();
				int sold_quantity = drink.getSold_quantity();
				String image_location = drink.getImage_location();
	%>
	
				<!--GRID-->
				<div class="w3-quarter">
					<table>
						<tr><td><img src="<%=image_location%>"alt="drink" style="width: 100%"></td></tr>
				
						<tr><td><b>ID</b>: <%=id%></td></tr>
						
						<tr><td><b>Name</b>: <%=name%></td></tr>
						
						<tr><td><b>Short Description</b>:</td></tr>
						<tr><td><%=description_short%></td></tr>
						
						<tr><td><b>Cost price</b>: <%=cost_price%></td></tr>
						 
						<tr><td><b>Retail price</b>: <%=retail_price%></td></tr>
						
						<tr><td><b>Stock quantity</b>: <%=stock_quantity%></td></tr>
						
						<tr><td><b>Product category</b>: <%=product_category%></td></tr>
						
						<tr><td><b>Sold quantity</b>: <%=sold_quantity%></td></tr>
						<tr>
							</br>
							<td>
							<form action="productReportServlet?action=delete" method="POST">
				      			<input type ="hidden" name="id" value="<%=id%>">
				      			<input type="submit" value="Delete">
			      			</form>
			      						
			      			</br>     
		      	 			<form action="productReportServlet?action=updateForm" method="POST">
				      			<input type ="hidden" name="id" value="<%=id%>">
				      			<input type="submit" value="Edit">
			      			</form>
			      			</td>
	     			 	</tr>
	     		</table>
	     	</div>
			<%}%>
		<%}%>
	</div>
</body>
</html>
