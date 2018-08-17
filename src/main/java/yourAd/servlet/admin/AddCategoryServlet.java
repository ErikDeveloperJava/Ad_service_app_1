package yourAd.servlet.admin;

import yourAd.manager.CategoryManager;
import yourAd.model.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/category/add")
public class AddCategoryServlet extends HttpServlet {

    private CategoryManager categoryManager;

    @Override
    public void init() throws ServletException {
        categoryManager = (CategoryManager)getServletContext().getAttribute("categoryManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryId = req.getParameter("categoryId");
        String name = req.getParameter("name");
        Category category = Category.builder()
                .name(name)
                .parent(Category.builder().id(categoryId == null ? null : Integer.parseInt(categoryId)).build())
                .build();
        categoryManager.add(category);
        resp.sendRedirect("/admin");
    }
}
