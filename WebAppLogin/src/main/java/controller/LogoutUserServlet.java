package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class LogoutUserServlet
 */
public class LogoutUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		System.out.println("<><<><><><><><><><><><> Inside Logout Servlet Controller<><><><><><><><><><><>><><>");
		
		HttpSession session= request.getSession();
		
		String name=  (String) session.getAttribute("name");
	    String password = (String) session.getAttribute("password");
		
	    System.out.println("user name :"+name);
	    System.out.println("password :"+password);
	    
	    session.invalidate();
	    
	    PrintWriter out= response.getWriter();
	    
        out.println("<html><head>");
        out.println("<style>");
        out.println("body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; text-align: center; }");
        out.println(".container { border: 1px solid #ccc; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); border-radius: 10px; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class='container'>");
        out.println("<h1>Thank you</h1>");
        out.println("<p style='color: green;'>Logout successfully</p>");
        out.println("</div>");
        out.println("</body></html>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
