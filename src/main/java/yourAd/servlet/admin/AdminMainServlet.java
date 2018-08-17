package yourAd.servlet.admin;

import yourAd.manager.UserManager;
import yourAd.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin")
public class AdminMainServlet extends HttpServlet implements Pages {

    private UserManager userManager;

    @Override
    public void init() throws ServletException {
        userManager = (UserManager)getServletContext().getAttribute("userManager");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users",userManager.getAll());
        req.getRequestDispatcher(ADMIN).forward(req,resp);
    }
}
