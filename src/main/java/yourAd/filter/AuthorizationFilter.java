package yourAd.filter;

import yourAd.model.User;
import yourAd.model.UserRole;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/user/*",
        "/ad/add","/ad/image/upload","/admin","/admin/**"},filterName = "filter1")
public class AuthorizationFilter extends AbstractFilter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            response.sendRedirect("/");
        }else {
            if(request.getRequestURI().split("/")[1].equals("admin") && user.getRole().equals(UserRole.USER)){
                response.sendRedirect("/");
            }else {
                filterChain.doFilter(request, response);
            }
        }
    }

    public void destroy() {

    }
}
