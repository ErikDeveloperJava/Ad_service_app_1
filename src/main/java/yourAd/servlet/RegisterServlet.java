package yourAd.servlet;

import yourAd.manager.CategoryManager;
import yourAd.manager.UserManager;
import yourAd.model.User;
import yourAd.model.UserRole;
import yourAd.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet implements Pages {

    private UserManager userManager;

    private CategoryManager categoryManager;

    @Override
    public void init() throws ServletException {
        userManager = (UserManager) getServletContext().getAttribute("userManager");
        categoryManager = (CategoryManager) getServletContext().getAttribute("categoryManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = isValidData(req);
        if(user == null){
            req.setAttribute("categories",categoryManager.getAll());
            req.getRequestDispatcher(LOGIN_REGISTER).forward(req,resp);
        }else if(userManager.getByUsername(user.getUsername()) != null){
            req.setAttribute("categories",categoryManager.getAll());
            req.setAttribute("usernameError","such username already exists");
            req.getRequestDispatcher(LOGIN_REGISTER).forward(req,resp);
        }else {
            user.setRole(UserRole.USER);
            userManager.add(user);
            resp.sendRedirect("/login-register");
        }
    }

    private User isValidData(HttpServletRequest request){
        User user = new User();
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(name == null || name.length() < 2 || name.length() > 255){
            request.setAttribute("nameError","in field name wrong data");
            return null;
        }else {
            user.setName(name);
        }
        if(surname == null || surname.length() < 4 || surname.length() > 255){
            request.setAttribute("surnameError","in field surname wrong data");
            return null;
        }else {
            user.setSurname(surname);
        }
        if(username == null || username.length() < 2 || username.length() > 255){
            request.setAttribute("usernameError","in field username wrong data");
            return null;
        }else {
            user.setUsername(username);
        }
        if(password == null || password.length() < 2 || password.length() > 255){
            request.setAttribute("passwordError","in field password wrong data");
            return null;
        }else {
            user.setPassword(password);
        }
        return user;
    }
}
