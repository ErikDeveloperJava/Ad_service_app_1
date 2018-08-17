package yourAd.servlet;

import yourAd.manager.AdManager;
import yourAd.manager.CategoryManager;
import yourAd.manager.ImageManager;
import yourAd.model.Ad;
import yourAd.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/ad/one/*")
public class AdServlet extends HttpServlet implements Pages {

    private AdManager adManager;
    private CategoryManager categoryManager;
    private ImageManager imageManager;

    @Override
    public void init() throws ServletException {
        adManager = (AdManager) getServletContext().getAttribute("adManager");
        categoryManager = (CategoryManager) getServletContext().getAttribute("categoryManager");
        imageManager = (ImageManager) getServletContext().getAttribute("imageManager");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int adId = getAdId(req);
        Ad ad = adManager.getById(adId);
        if(ad == null){
            resp.sendRedirect("/");
        }else {
            ad.setCategory(categoryManager.getByAdId(ad.getId()));
            req.setAttribute("ad",ad);
            req.setAttribute("images",imageManager.getAllByAdId(ad.getId()));
            req.setAttribute("categories",categoryManager.getAll());
            req.getRequestDispatcher(AD).forward(req,resp);
        }
    }

    private int getAdId(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        String[] array = requestURI.split("/");
        int id;
        try {
            id = Integer.parseInt(array[3]);
            if(id <= 0){
                id = -1;
            }
        }catch (NumberFormatException e){
            id = -1;
        }
        return id;
    }
}
