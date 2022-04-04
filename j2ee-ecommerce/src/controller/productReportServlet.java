package controller;
import classes.*;
import model.DrinkDB;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import static java.lang.System.out;

/**
 * Servlet implementation class reportSortServlet
 */
@WebServlet("/productReportServlet")
@MultipartConfig 
public class productReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public productReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DrinkDB udatabase = new DrinkDB();
		HttpSession session = request.getSession();
		ArrayList <Drink> ubean = udatabase.getAllDrinks();
		ArrayList <String> productCategory = new ArrayList <String> ();
		
		
		String role = (String) session.getAttribute("role");
		if(role == null || role.equals("member")){
			response.sendRedirect("view/loginPage.jsp?status=unauthorized");
			return;
		}
		
		for(Drink drink : ubean){
        	String category = drink.getProduct_category();
            if(productCategory.indexOf(category) == -1){
                productCategory.add(category);
            }
        }
		
        
		request.setAttribute("sortedDrinks", ubean);
        request.setAttribute("productCategory", productCategory);
		RequestDispatcher rd = request.getRequestDispatcher("view/productReport.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DrinkDB udatabase = new DrinkDB();
		HttpSession session = request.getSession();
		ArrayList <Drink> ubean = udatabase.getAllDrinks();
		ArrayList <String> productCategory = new ArrayList <String> ();
		String urlPath = "";		
		
		String role = (String) session.getAttribute("role");
		if(role == null || role.equals("member")){
			response.sendRedirect("view/loginPage.jsp?status=unauthorized");
			return;
		}
		
		for(Drink drink : ubean){
        	String category = drink.getProduct_category();
            if(productCategory.indexOf(category) == -1){
                productCategory.add(category);
            }
        }
		
		request.setAttribute("drinks", ubean);
        request.setAttribute("productCategory", productCategory);
		
		if(request.getParameter("action").equals("blselling")) {
			
			if(request.getParameter("option").equals("best")) {
				ArrayList <Drink> drinks = (ArrayList <Drink>) request.getAttribute("drinks"); 
				for(int i=1; i < drinks.size(); i++) {
					for(int j=0; j < drinks.size()-i; j++) {
						if(drinks.get(j).getSold_quantity() < drinks.get(j+1).getSold_quantity()) Collections.swap(drinks, j, j+1);
					}
				}
				request.setAttribute("sortedDrinks", drinks);
			}
			
			else if(request.getParameter("option").equals("least")) {
				ArrayList <Drink> drinks = (ArrayList <Drink>) request.getAttribute("drinks"); 
				for(int i=1; i < drinks.size(); i++) {
					for(int k=0; k < drinks.size()-i; k++) {
						if(drinks.get(k).getSold_quantity() > drinks.get(k+1).getSold_quantity()) Collections.swap(drinks, k, k+1);
					}
				}
				request.setAttribute("sortedDrinks", drinks);
			}

			urlPath = "view/productReport.jsp";
		}
			
		
		else if(request.getParameter("action").equals("stocklevel")) {
			int value = Integer.parseInt(request.getParameter("value"));
			ArrayList <Drink> drinks = (ArrayList <Drink>) request.getAttribute("drinks"); 
			int count = 0, loop = drinks.size();
			for(int i=0; i < loop; i++) {
				if(drinks.get(count).getStock_quantity() >= value) {
					drinks.remove(count);
					count--;
				}
				count++;
			}
			request.setAttribute("sortedDrinks", drinks);
			urlPath = "view/productReport.jsp";
		} 
		
		else if(request.getParameter("action").equals("search")) {
			String query = request.getParameter("query");
			ArrayList <Drink> drinks = udatabase.getDrinkByQuery(query);
			request.setAttribute("sortedDrinks", drinks);
			urlPath = "view/productReport.jsp";
		} 
		
		else if(request.getParameter("action").equals("delete")) {
			String idStr = request.getParameter("id");
			int id = Integer.parseInt(idStr);
			int rs = udatabase.deleteDrink(id);
			urlPath = "view/adminSuccessPage.jsp?type=drink&action=deleted";
		}
		
		else if(request.getParameter("action").equals("updateForm")) {
			String idStr = request.getParameter("id");
			int id = Integer.parseInt(idStr);
			Drink updatingDrink = udatabase.getDrink(id);
			request.setAttribute("updatingDrink", updatingDrink);
			urlPath = "view/updateFormPage.jsp?type=drink&action=update";
        }
		
		else if(request.getParameter("action").equals("createForm")) {
			urlPath = "view/updateFormPage.jsp?type=drink&action=create";
        }
		
		else if(request.getParameter("action").equals("create")) {
			String image_location = null;
			Part filePart = request.getPart("file");
			
			String type = filePart.getContentType(); //image/<filetype>
			type = "." + type.substring(type.lastIndexOf("/") + 1); //gets the substring after the slash
			
			if(filePart.getSize() <= 0) {
				image_location = "images/default.png";
			}
			
			else {
				String fileName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());	
				String servletPath = getServletContext().getRealPath("/");
				File f = new File(servletPath);
				String dir  = "";
				for(int i = 0;  i < 6; i++) {
					dir = f.getParent();
					f = new File(dir);
					System.out.println(dir);
				}
				dir += "\\j2ee-ecommerce\\WebContent\\images";
				System.out.println(dir);
				
				File uploads = new File(dir);
				File file = new File(uploads, fileName + type);
				
				try (InputStream fileContent = filePart.getInputStream();) {
				    Files.copy(fileContent, file.toPath());
				}
				image_location = "images/" + fileName + type; 
			}  
			
			String name = request.getParameter("name");
			String description_short = request.getParameter("description_short");
			String description_long = request.getParameter("description_long");
			String cost_price = request.getParameter("cost_price");		
			String retail_price = request.getParameter("retail_price");
			String stock_quantity = request.getParameter("stock_quantity");
			String product_category = request.getParameter("product_category");
			
			udatabase.postDrink(name, description_short, description_long, cost_price, retail_price, stock_quantity, product_category, 0, image_location);
			urlPath = "view/adminSuccessPage.jsp?type=drink&action=created";
		}
		
		else if(request.getParameter("action").equals("update")) {
			String image_location = null;
			Part filePart = request.getPart("file");
			String type = filePart.getContentType(); //image/<filetype>
			type = "." + type.substring(type.lastIndexOf("/") + 1); //gets the substring after the slash
			
			if(filePart.getSize() <= 0) {
				DrinkDB drinkdb = new DrinkDB();
				ArrayList <Drink> drinks = drinkdb.getAllDrinks();
				
				if(drinks != null){
					for(Drink drink : drinks) {
						if(drink.getId() == (Integer.parseInt(request.getParameter("id")))) {
							image_location = drink.getImage_location();
						}
					}
				}
			}
			
			else {
				String fileName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());	
				String servletPath = getServletContext().getRealPath("/");
				File f = new File(servletPath);
				String dir  = "";
				for(int i = 0;  i < 6; i++) {
					dir = f.getParent();
					f = new File(dir);
					System.out.println(dir);
				}
				dir += "\\CA2\\WebContent\\images";
				System.out.println(dir);
				
				File uploads = new File(dir);
				File file = new File(uploads, fileName + type);
				
				try (InputStream fileContent = filePart.getInputStream();) {
				    Files.copy(fileContent, file.toPath());
				}
				image_location = "images/" + fileName + type; 
			}
			
			String name = request.getParameter("name");
			String description_short = request.getParameter("description_short");
			String description_long = request.getParameter("description_long");
			String cost_price = request.getParameter("cost_price");		
			String retail_price = request.getParameter("retail_price");
			String stock_quantity = request.getParameter("stock_quantity");
			String product_category = request.getParameter("product_category");
			int id = Integer.parseInt(request.getParameter("id"));

			udatabase.putDrink(name, description_short, description_long, cost_price, retail_price, stock_quantity, product_category, image_location, id);
			
			urlPath = "view/adminSuccessPage.jsp?type=drink&action=updated";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(urlPath);
		rd.forward(request, response);	
	}
}


