package yourAd.servlet;

import yourAd.form.ImageForm;
import yourAd.manager.ImageManager;
import yourAd.model.User;
import yourAd.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/ad/image/upload")
public class ImageUploadServlet extends HttpServlet {

    private ImageManager imageManager;

    @Override
    public void init() throws ServletException {
        imageManager = (ImageManager) getServletContext().getAttribute("imageManager");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int adId = getAdId(req);
        if(adId == -1){
            resp.sendRedirect("/");
        }else {
            User user = (User)req.getSession().getAttribute("user");
            ImageForm imageForm = (ImageForm) req.getAttribute("imageForm");
            if(imageForm.getBytes().length == 0 || !ImageUtil.isValidFormat(imageForm.getContentType())){
                resp.sendRedirect("/");
            }else {
                imageManager.addALL(user.getUsername(),imageForm,adId);
                resp.sendRedirect("/ad/one/" + adId);
            }
        }
    }

    private int getAdId(HttpServletRequest request){
        int id;
        try {
            id = Integer.parseInt((String) request.getAttribute("adId"));
            if(id <= 0){
                id = -1;
            }
        }catch (NumberFormatException e){
            id = -1;
        }
        return id;
    }
}
