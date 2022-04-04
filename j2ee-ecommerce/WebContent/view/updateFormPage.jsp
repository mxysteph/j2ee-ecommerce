<%
String path = request.getContextPath();
String type = request.getParameter("type");
String action = request.getParameter("action");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import= "classes.*"%>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<title>Blitz - <%=action%> <%=type%></title>
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
			<div class="w3-center w3-padding-16"><%=action%> <%=type%></b></div>
		</div>
	</div>
	
	<!--PAGE CONTENT -->
	<div class="w3-main w3-content w3-padding" style="max-width: 1200px; margin-top: 100px">
		<div class="w3-container w3-padding-32 w3-center">
		<% 
			if(type.equals("user")){
				
			User updatingUser = (User) session.getAttribute("updatingUser");
			int id = updatingUser.getId();
			String username = updatingUser.getUsername();
			String email = updatingUser.getEmail();
			String password = updatingUser.getPassword();
			String address = updatingUser.getAddress();
			String contact_number = updatingUser.getContact_number();
		%>
			<form action="userReportServlet?action=update" method="POST">
				<table style="margin-left: auto; margin-right: auto; width: 40%;">
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="id">ID:</label>
							</td>
							<td>
								<input readonly type="text" id="id" name="id" value="<%=id%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="username">Username:</label>
							</td>
							<td>
								<input type="text" id="username" name="username" value="<%=username%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="password">Password:</label>
							</td>
							<td>
								<input type="text" id="password" name="password" value="<%=password%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="email">Email:</label>
							</td>
							<td>
								<input type="email" id="email" name="email" value="<%=email%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="address">Address:</label>
							</td>
							<td>
								<input type="text" id="address" name="address" value="<%=address%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="role">Role:</label>
							</td>
							<td>
								<select name="role">
					      			<%
						      			ArrayList <String> userRoles = (ArrayList <String>) session.getAttribute("userRoles");
						      		
							      		if(userRoles != null){
							    			for(String role : userRoles) {
							      		%>
							      				<option value=<%=role%>><%=role%></option>
					      				<% 
							    			}
							      		}
				      				%>
			      				</select>
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="contact_number">Contact Number:</label>
							</td>
							<td>
								<input type="text" id="contact_number" name="contact_number" value="<%=contact_number%>">
							</td>
						</tr>
						<tr>
							<td>
								<input type="hidden" name="id" value=<%=id%> /> 
								<input type="submit" value="Update">
							</td>
						</tr>
				</table>
			</form>
		<%}
		
			else if(type.equals("drink")){				
				if(action.equals("update")){
				
				Drink updatingDrink = (Drink) request.getAttribute("updatingDrink");
				int id = updatingDrink.getId();
				String name = updatingDrink.getName();
				String description_long = updatingDrink.getDescription_long();
				String description_short = updatingDrink.getDescription_short();
				double cost_price = updatingDrink.getCost_price();
				double retail_price = updatingDrink.getRetail_price();
				int stock_quantity = updatingDrink.getStock_quantity();
				int sold_quantity = updatingDrink.getSold_quantity();
		%>
		
				<form action="productReportServlet?action=update" method="POST" enctype="multipart/form-data">
					<table style="margin-left: auto; margin-right: auto; width: 40%;">
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="id">ID:</label>
							</td>
							<td>
								<input readonly type="text" id="id" name="id" value="<%=id%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="name">Name:</label>
							</td>
							<td>
								<input type="text" id="name" name="name" value="<%=name%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="description_long">Long Description:</label>
							</td>
							<td>
								<input type="text" id="description_long" name="description_long" value="<%=description_long%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="description_short">Short Description:</label>
							</td>
							<td>
								<input type="text" id="description_short" name="description_short" value="<%=description_short%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="cost_price">Cost Price:</label>
							</td>
							<td>
								<input type="text" id="cost_price" name="cost_price" value="<%=cost_price%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="retail_price">Retail Price:</label>
							</td>
							<td>
								<input type="text" id="retail_price" name="retail_price" value="<%=retail_price%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="stock_quantity">Stock Quantity:</label>
							</td>
							<td>
								<input type="text" id="stock_quantity" name="stock_quantity" value="<%=stock_quantity%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="product_category">Product Category:</label>
							</td>
							<td>
								<select name="product_category">
					      			<%
						      			ArrayList <String> productCategory = (ArrayList <String>) request.getAttribute("productCategory");
						      		
							      		if(productCategory != null){
							    			for(String category : productCategory) {
							      		%>
							      				<option value=<%=category%>><%=category%></option>
					      				<% 
							    			}
							      		}
				      				%>
			      				</select>
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="quantity_sold">Quantity Sold:</label>
							</td>
							<td>
								<input readonly type="text" id="quantity_sold" name="quantity_sold" value="<%=sold_quantity%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 35px;">
								<label for="file">Product Image:</label>
							</td>
							<td>
					      		<input type="file" name="file" accept="image/png, image/jpg, image/jpeg"><br>
							</td>
						</tr>
						<tr>
							<td>
				      			<input type="submit" value="Submit"><br>
							</td>
						</tr>
				</table>
			</form>
		<%}
				
				else {
		%>
		<form action="productReportServlet?action=create" method="POST" enctype="multipart/form-data">
					<table style="margin-left: auto; margin-right: auto; width: 40%;">
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="name">Name:</label>
							</td>
							<td>
								<input type="text" id="name" name="name">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="description_long">Long Description:</label>
							</td>
							<td>
								<input type="text" id="description_long" name="description_long">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="description_short">Short Description:</label>
							</td>
							<td>
								<input type="text" id="description_short" name="description_short">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="cost_price">Cost Price:</label>
							</td>
							<td>
								<input type="text" id="cost_price" name="cost_price">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="retail_price">Retail Price:</label>
							</td>
							<td>
								<input type="text" id="retail_price" name="retail_price">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="stock_quantity">Stock Quantity:</label>
							</td>
							<td>
								<input type="text" id="stock_quantity" name="stock_quantity">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="product_category">Product Category:</label>
							</td>
							<td>
								<select name="product_category">
					      			<%
						      			ArrayList <String> productCategory = (ArrayList <String>) request.getAttribute("productCategory");
						      		
							      		if(productCategory != null){
							    			for(String category : productCategory) {
							      		%>
							      				<option value=<%=category%>><%=category%></option>
					      				<% 
							    			}
							      		}
				      				%>
			      				</select>
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 35px;">
								<label for="file">Product Image:</label>
							</td>
							<td>
					      		<input type="file" name="file" accept="image/png, image/jpg, image/jpeg"><br>
							</td>
						</tr>
						<tr>
							<td>
				      			<input type="submit" value="Submit"><br>
							</td>
						</tr>
				</table>
			</form>
			<%}
		}%>
			</div>
		</div>
	</div>
</body>
</html>
		
		
		