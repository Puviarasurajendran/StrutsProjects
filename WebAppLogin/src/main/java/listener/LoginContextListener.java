package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class LoginContextListener
 *
 */
public class LoginContextListener implements ServletContextListener {


    public LoginContextListener() {
        // TODO Auto-generated constructor stub
    }

	
    public void contextInitialized(ServletContextEvent sce)  { 
         System.out.println("<><><><><><><><><><><>><LoginContextListener contextInitialized <><><><><><><><><><><><><><><><>");
           
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
         
    	System.out.println("<><><><><><><><><><><>><ContextListener contextDestroyed <><><><><><><><><><><><><><><><>");
    }
	
}
