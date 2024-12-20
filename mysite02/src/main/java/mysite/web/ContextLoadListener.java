package mysite.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

// @WebListener // ->web.xml에 설정할 예정
public class ContextLoadListener implements ServletContextListener {

    

    public void contextInitialized(ServletContextEvent sce)  {
    	ServletContext sc = sce.getServletContext();
    	String contextConfigLocation = sc.getInitParameter("contextConfigLocation");
    	
    	System.out.println("Application[Mysite02] starts..." + contextConfigLocation);
    }

    public void contextDestroyed(ServletContextEvent sce)  {
    	/* nothing */
    }
	
}
