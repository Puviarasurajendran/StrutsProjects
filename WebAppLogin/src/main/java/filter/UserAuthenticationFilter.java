package filter;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class UserAuthenticationFilter
 */
public class UserAuthenticationFilter extends HttpFilter implements Filter {
       
    public UserAuthenticationFilter() {

    }

	public void destroy() {
		System.out.println("><><>><><><><><><><><><><><><> Login Filter destroy<><><><><><><><><><>");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
		
		System.out.println("<><><><><><><><><><><><> inside Login App Filter <><><><><><><><><><><><><>");
		
		PrintWriter out = response.getWriter();
		
		String username =request.getParameter("username");
		String password=request.getParameter("password");
		
        
		if(username.equals("admin")&&password.equals("12345")) {
		chain.doFilter(request, response);
		}
		else {
			   out.println("<html><head>");
	            out.println("<style>");
	            out.println("body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; text-align: center; }");
	            out.println(".container { border: 1px solid #ccc; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); border-radius: 10px; }");
	            out.println("</style>");
	            out.println("</head><body>");
	            out.println("<div class='container'>");
	            out.println("<h1 style='color: red;'>Sorry, you are not an authorized user</h1>");
	            out.println("<p style='color: green;'>Please try Again</p>");
	            out.println("<a href='login.jsp'>Back to login</a>");
	            out.println("</div>");
	            out.println("</body></html>");

	            out.close();
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("><><>><><><><><><><><><><><><> Login Filter check<><><><><><><><><><>");
	}
   
}
