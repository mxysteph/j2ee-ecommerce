<%String path = request.getContextPath();%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import= "classes.*"%>
<!DOCTYPE html>
<html>
<title>Blitz - Member Portal</title>
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
	boolean isAdmin = false;

	try {
		String role = (String) session.getAttribute("role");
		if(role == null){
			response.sendRedirect("loginPage.jsp?status=unauthorized");
		}
		
		else if(role.equals("admin")){
			isAdmin = true;
		}
		
	} catch (Exception e){response.sendRedirect("loginPage.jsp?status=unauthorized");}
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

	<%
			String username = (String) session.getAttribute("username");
			String id = (String) session.getAttribute("id");
			String email = (String) session.getAttribute("email");
			String address = (String) session.getAttribute("address");
			String contact_number = (String) session.getAttribute("contact_number");
	%>

	<!-- TOP MENU -->
	<div class="w3-top">
		<div class="w3-white w3-xlarge"
			style="max-width: 1200px; margin: auto">
			<a href="<%=path%>/view/index.jsp" style="text-decoration: none"><div class="w3-center w3-padding-16">Blitz</div></a>
			<div class="w3-button w3-padding-16 w3-left" onclick="w3_open()">Open</div>
			<div class="w3-center w3-padding-16"><b>Welcome back, <%=username%>!</b></div>
		</div>
	</div>
	
	<!--PAGE CONTENT -->
	<div class="w3-main w3-content w3-padding" style="max-width: 1200px; margin-top: 100px">
		<div class="w3-container w3-padding-32 w3-center">
				<table border='1' style='margin-left: auto; margin-right: auto; width: 40%; border-collapse: collapse;'>
					<thead>
						<h4>Your Profile:</h4>
						<a href="<%=path%>/clearServlet?action=all"><p>Logout</p></a>
					</thead>
					<tr>
						<td style="text-align: left"><b>ID</b></td>
						<td style="text-align: center"><%=id%></td>
					</tr>
					<tr>
						<td style="text-align: left"><b>Username</b></td>
						<td style="text-align: center"><%=username%></td>
					</tr>
					<tr>
						<td style="text-align: left"><b>Email</b></td>
						<td style="text-align: center"><%=email%></td>
					</tr>
					<tr>
						<td style="text-align: left"><b>Contact Number</b></td>
						<td style="text-align: center"><%=contact_number%></td>
					</tr>
					<tr>
						<td style="text-align: left"><b>Address</b></td>
						<td style="text-align: center"><%=address%></td>
					</tr>
				</table>
			<br/>
			
			<%if(isAdmin == true){%>
				<a href="<%=path%>/productReportServlet"><button>Product Management</button></a>
				<a href="<%=path%>/orderReportServlet"><button>Order Management</button></a>
				<a href="<%=path%>/userReportServlet"><button>User Management</button></a>
				
			<%}%>
			<a href="<%=path%>/userOrdersPageServlet"><button>View Order History</button></a>
		</div>

		<div class="w3-container w3-padding-32 w3-center">
			<form action="<%=path%>/memberPageServlet?action=update" method="POST">
				<table style="margin-left: auto; margin-right: auto; width: 40%;">
					<thead>
						<h4>Edit Your Details:</h4>
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
								<label for="email">Email:</label>
							</td>
							<td>
								<input type="email" id="email" name="email" value="<%=email%>">
							</td>
						</tr>
						<tr>
							<td height="50px" style="padding: 15px;">
								<label for="password">Password:</label>
							</td>
							<td>
								<input type="password" id="password" name="password" placeholder="password">
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
		</div>
	</div>
</body>
</html>