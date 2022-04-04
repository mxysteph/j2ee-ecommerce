<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import= "classes.*"%>
<%@ page import="java.util.ArrayList" %>

<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<title>Blitz - User Management</title>
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

<%
	try {
		String role = (String) session.getAttribute("role");
		if(role.equals("member") || role == null){
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
			<div class="w3-center w3-padding-16">User Management</div>
		</div>
	</div>

		<!--PAGE CONTENT -->
	<div class="w3-main w3-content w3-padding"
		style="max-width: 1200px; margin-top: 100px">
		<div class="w3-row-padding w3-padding-16 w3-center">
			<h4>Filter by:</h4>
			<form action="<%=path%>/userReportServlet?action=filter" method="post">
	      		Date of Creation:
		      	<input name="value" placeholder="Enter numeric value">
		      	<select name="option">
			      	<option value="day">Day</option>
				    <option value="month">Month</option>
  					<option value="year">Year</option>
		      	</select>
			    <input type="submit" value=Filter><br>
	   		 </form><br>
		    <br>
		    <form action="<%=path%>/userReportServlet?action=sort" method="post">
				<select name="option">
			      	<option value="admin">Admin</option>
			      	<option value="member">Member</option>
		      	</select>
		      	<input type="submit" value="Sort"><br>
	      	</form><br>
 	    	<a href="<%=path%>/view/memberPage.jsp"> <p> Go Back </p> </a>
	     <div>
    	<table>
    		<tr>
    			<th>ID</th>
    			<th>Username</th>
    			<th>Email</th>
    			<th>Address</th>
    			<th>Role </th>
    			<th>Contact Number</th>
    			<th>Registration Date</th>
    			<th>Delete</th>
   				<th>Update</th>
    		</tr>
		<%
			ArrayList <User> users = (ArrayList <User>) session.getAttribute("sortedUsers");
			if(users != null){
				for(User individualUser : users) {
					int id = individualUser.getId();
					String username = individualUser.getUsername();
					String password = individualUser.getPassword();
					String email = individualUser.getEmail();
					String address = individualUser.getAddress();
					String role = individualUser.getRole();
					String contact_number = individualUser.getContact_number();
					String created_at = individualUser.getCreated_at();
	
		%>
					<tr>
						<td><%=id %></td>
						<td style = "width: 10%"><%=username %></td>
						<td><%=email %></td>
						<td><%=address %></td>
						<td><%=role%></td>
						<td><%=contact_number%></td>
						<td><%=created_at%></td>
						<td> 
							<form action="userReportServlet?action=delete" method="POST">
				      			<input type ="hidden" name="id" value="<%=id%>">
				      			<input type="submit" value="Delete">
			      			</form>
		     			 </td>
		     			 <td> 
							<form action="userReportServlet?action=updateForm" method="POST">
				      			<input type ="hidden" name="id" value="<%=id%>">
				      			<input type="submit" value="Update">
			      			</form>
		     			 </td>
					</tr>
			<%
				}
			}
			%>
				<tr>
					<form action="userReportServlet?action=create" method="POST">
						<td><b>New User</b></td>
						<td> <input type="text" id="username" name="username" placeholder="username" size = "15"> </td>					
						<td> <input type="email" id="email" name="email"placeholder="email" size = "15"> </td>	
						<td> <input type="text" id="address" name="address" placeholder="address" size = "15"></td>
						<td>
							<select name="role">
				      			<%
					      			ArrayList <String> userRoles = (ArrayList <String>) request.getAttribute("userRoles");
					      		
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
						<td> <input type="text" id="contact_number" name="contact_number" placeholder="contact_number" size = "15"></td>
						<td> <input type="password" id="password" name="password" placeholder="password" size = "15"> </td>						
						<td><input type="reset" value="Reset"></td>
						<td><input type="submit" value="Create"></td>
					</form>
				</tr>
		</table>
		</div>
	</div>
</div>
</body>
</html>
