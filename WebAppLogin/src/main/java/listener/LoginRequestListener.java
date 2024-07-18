package listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;

/**
 * Application Lifecycle Listener implementation class LoginRequestListener
 *
 */
public class LoginRequestListener implements ServletRequestListener {


    public void requestInitialized(ServletRequestEvent sre)  { 
                System.out.println("<>><><><><><><><><><><><><<><><> New Request created  <><><><><><><><><><>");
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent sre)  { 
    	System.out.println("<>><><><><><><><><><><><><<><><>  Request Destroyed  <><><><><><><><><><>");
    }
	
}
