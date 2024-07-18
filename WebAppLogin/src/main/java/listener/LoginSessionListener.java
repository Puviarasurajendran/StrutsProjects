package listener;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class LoginSessionListener
 *
 */
public class LoginSessionListener implements HttpSessionListener {


    public void sessionCreated(HttpSessionEvent se)  { 
    	System.out.println("<><<><><><><><><><><><>New sessionCreated<><><><><><><><><><><>><><>");
    	   
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
       
    	System.out.println("<><<><><><><><><><><><>Session Destroyed<><><><><><><><><><><>><><>");
 	   
    }
	
}
