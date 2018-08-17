package yourAd.filter;

import yourAd.manager.CategoryManager;
import yourAd.manager.UserManager;
import yourAd.model.User;
import yourAd.model.UserRole;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/login")
public class AuthenticationFilter extends AbstractFilter{

    private UserManager userManager;

    private CategoryManager categoryManager;

    public void init(FilterConfig filterConfig) throws ServletException {
        userManager = (UserManager) filterConfig.getServletContext().getAttribute("userManager");
        categoryManager = (CategoryManager) filterConfig.getServletContext().getAttribute("categoryManager");
    }

    public void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user;
        if((user = userManager.getByUsername(username)) == null){
            request.setAttribute("categories",categoryManager.getAll());
            request.setAttribute("loginError","You entered the wrong username");
            request.getRequestDispatcher(LOGIN_REGISTER).forward(request,response);
        }else if (!user.getPassword().equals(password)){
            request.setAttribute("categories",categoryManager.getAll());
            request.setAttribute("loginError","You entered the wrong password");
            request.getRequestDispatcher(LOGIN_REGISTER).forward(request,response);
        }else {
            request.getSession().setAttribute("user",user);
            response.sendRedirect(user.getRole() == UserRole.ADMIN ? "/admin" : "/");
        }
    }

    public void destroy() {

    }
}

