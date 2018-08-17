package yourAd.servlet;

import yourAd.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/resources/images/*")
public class ImageOutputStreamServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = getFileName(req);
        if(fileName == null || !ImageUtil.exists(fileName)){
            resp.sendRedirect("/");
        }else {
            resp.getOutputStream().write(ImageUtil.getBytes(fileName));
        }
    }

    private String getFileName(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        String[] array= requestURI.split("/");
        if(array.length < 6){
            return null;
        }
        return array[3] + "\\" + array[4] + "\\" + array[5];
    }
}
