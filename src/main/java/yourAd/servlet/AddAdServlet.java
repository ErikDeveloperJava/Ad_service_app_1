package yourAd.servlet;

import yourAd.form.AdForm;
import yourAd.form.ImageForm;
import yourAd.manager.AdManager;
import yourAd.manager.CategoryManager;
import yourAd.manager.ImageManager;
import yourAd.model.Ad;
import yourAd.model.Category;
import yourAd.model.User;
import yourAd.pages.Pages;
import yourAd.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = "/ad/add")
public class AddAdServlet extends HttpServlet implements Pages {

    private CategoryManager categoryManager;

    private AdManager adManager;

    private ImageManager imageManager;

    @Override
    public void init() throws ServletException {
        categoryManager = (CategoryManager)getServletContext().getAttribute("categoryManager");
        adManager = (AdManager) getServletContext().getAttribute("adManager");
        imageManager = (ImageManager) getServletContext().getAttribute("imageManager");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories",categoryManager.getAll());
        req.setAttribute("subCategories",categoryManager.getAllByChildrenListIsEmpty());
        req.getRequestDispatcher(ADD_AD).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdForm adForm = (AdForm) req.getAttribute("adForm");
        ImageForm imageForm = (ImageForm) req.getAttribute("imageForm");
        User user = (User) req.getSession().getAttribute("user");
        Ad ad = isValidData(adForm,req);
        if(ad == null){
            req.setAttribute("categories",categoryManager.getAll());
            req.setAttribute("subCategories",categoryManager.getAllByChildrenListIsEmpty());
            req.getRequestDispatcher(ADD_AD).forward(req,resp);
        }else if(imageForm.getBytes().length == 0){
            req.setAttribute("imageError","image is empty");
            req.setAttribute("categories",categoryManager.getAll());
            req.setAttribute("subCategories",categoryManager.getAllByChildrenListIsEmpty());
            req.getRequestDispatcher(ADD_AD).forward(req,resp);
        }else if(!ImageUtil.isValidFormat(imageForm.getContentType())){
            req.setAttribute("imageError","invalid image format");
            req.setAttribute("categories",categoryManager.getAll());
            req.setAttribute("subCategories",categoryManager.getAllByChildrenListIsEmpty());
            req.getRequestDispatcher(ADD_AD).forward(req,resp);
        }else {
            ad.setCreatedDate(new Date());
            ad.setUser(user);
            adManager.add(ad,imageForm,imageManager);
            resp.sendRedirect("/ad/one/" + ad.getId());
        }
    }

    private Ad isValidData(AdForm adForm,HttpServletRequest request){
        Ad ad = new Ad();
        double price;
        if(adForm.getTitle() == null || adForm.getTitle().length() < 2 || adForm.getTitle().length() > 255){
            request.setAttribute("titleError","in field title wrong data");
            return null;
        }else {
            ad.setTitle(adForm.getTitle());
        }
        if(adForm.getDescription() == null || adForm.getDescription().length() < 6){
            request.setAttribute("descriptionError","in field description wrong data");
            return null;
        }else {
            ad.setDescription(adForm.getDescription());
        }
        try {
            if((price = Double.parseDouble(adForm.getPrice())) < 100 || price > 100000000){
                throw new NumberFormatException();
            }else {
                ad.setPrice(price);
            }
        }catch (NumberFormatException e){
            request.setAttribute("priceError","in field price wrong data");
            return null;
        }
        if(categoryManager.getById(adForm.getCategoryId()) == null){
            request.setAttribute("categoryError","in field category wrong data");
            return null;
        }else {
            ad.setCategory(Category.builder().id(adForm.getCategoryId()).build());
        }
        return ad;
    }
}
