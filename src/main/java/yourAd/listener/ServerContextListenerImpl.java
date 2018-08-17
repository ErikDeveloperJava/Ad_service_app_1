package yourAd.listener;

import yourAd.db.ConnectionProvider;
import yourAd.manager.AdManager;
import yourAd.manager.CategoryManager;
import yourAd.manager.ImageManager;
import yourAd.manager.UserManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServerContextListenerImpl implements ServletContextListener {

    public void contextInitialized(ServletContextEvent context) {
        context.getServletContext().setAttribute("userManager",new UserManager());
        context.getServletContext().setAttribute("categoryManager",new CategoryManager());
        context.getServletContext().setAttribute("imageManager",new ImageManager());
        context.getServletContext().setAttribute("adManager",new AdManager());
    }

    public void contextDestroyed(ServletContextEvent context) {
        ConnectionProvider.getInstance().clear();
    }
}
