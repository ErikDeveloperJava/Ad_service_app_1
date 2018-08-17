package yourAd.servlet.admin;

import yourAd.manager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/user/delete/*")
public class DeleteUserServlet extends HttpServlet {

    private UserManager userManager;

    @Override
    public void init() throws ServletException {
        userManager = (UserManager) getServletContext().getAttribute("userManager");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = getUserId(req);
        userManager.deleteById(id);
        resp.sendRedirect("/admin");
    }

    private int getUserId(HttpServletRequest request){
        int id;
        String[] array = request.getRequestURI().split("/");
        try {
            id = Integer.parseInt(array[4]);
            if(id <= 0){
                id = -1;
            }
        }catch (NumberFormatException e){
            id = -1;
        }
        return id;
    }
}
