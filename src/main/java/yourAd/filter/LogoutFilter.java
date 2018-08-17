package yourAd.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/logout")
public class LogoutFilter extends AbstractFilter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.getSession().invalidate();
        response.sendRedirect("/");
    }

    public void destroy() {

    }
}
