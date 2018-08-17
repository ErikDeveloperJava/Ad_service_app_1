package yourAd.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/login-register")
public class LoginRegisterFilter extends AbstractFilter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Object user = request.getSession().getAttribute("user");
        if(user != null){
            response.sendRedirect("/");
        }else {
            filterChain.doFilter(request,response);
        }
    }

    public void destroy() {

    }
}
