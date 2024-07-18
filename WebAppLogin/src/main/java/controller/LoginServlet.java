package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
        

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		System.out.println("<><<><><><><><><><><><> Inside Login Servlet Controller<><><><><><><><><><><>><><>");
		
		 PrintWriter out= response.getWriter();
         HttpSession session = request.getSession();
         
         String name=request.getParameter("username");
         String password=request.getParameter("password");

         session.setAttribute("name", name);
         session.setAttribute("password", password);
         
         System.out.println("Session ID: " + session.getId());
         System.out.println("Creation Time: " + session.getCreationTime());
         System.out.println("Last Accessed Time: " + session.getLastAccessedTime());
                                                
         out.println("<html><head>");
         out.println("<style>");
         out.println("body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; text-align: center; }");
         out.println(".container { border: 1px solid #ccc; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); border-radius: 10px; }");
         out.println("</style>");
         out.println("</head><body>");
         out.println("<div class='container'>");
         out.println("<h1 style='color: blue;'>Welcome to Zoho CRM API Management</h1>");
         out.println("<p style='color: green;'>Login successfully</p>");
         out.println("<a href=./logoutpage>logout</a>");
         out.println("</div>");
         out.println("</body></html>");
         
	}

}
