package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


public class UserSessionFilter extends HttpFilter implements Filter {
       
    public UserSessionFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		System.out.println("User Session filter Expires....... ");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	        HttpServletRequest req = (HttpServletRequest) request;
	        HttpSession session = req.getSession(false); // Get existing session, don't create one if it doesn't exist
	        
	        String username = null;
	        String password = null;
	        
	        if (session != null) {
	          
	            username = (String) session.getAttribute("name");
	            password = (String) session.getAttribute("password");
	        }
	        
	        if (username == null || password == null) {
	            username = request.getParameter("username");
	            password = request.getParameter("password");
	            

//	            if (username != null && password != null) {
//	                session = req.getSession(); // Create a new session
//	                session.setAttribute("name", username);
//	                session.setAttribute("password", password);
//	            }
	        }
	        
	        if ("admin".equals(username) && "12345".equals(password)) {
	            chain.doFilter(request, response); 
	        } 
	    
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
