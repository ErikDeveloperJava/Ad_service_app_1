package yourAd.filter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import yourAd.form.ImageForm;
import yourAd.util.PropertiesUtil;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@WebFilter(urlPatterns = "/ad/image/upload", filterName = "filter3")
public class ParseImageUploadRequestFilter extends AbstractFilter {

    private static final String FILE_PATH = "C:\\Users\\Erik\\IdeaProjects\\java-web\\YourAd\\src\\main\\resources\\img-config.properties";
    private Properties properties;

    public void init(FilterConfig filterConfig) throws ServletException {
        properties = PropertiesUtil.load(FILE_PATH);
    }

    public void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            response.sendRedirect("/");
        } else {
            ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
            fileUpload.setFileSizeMax(Long.parseLong(properties.getProperty("request.max.size")));
            fileUpload.setSizeMax(Long.parseLong(properties.getProperty("img.max.size")));
            try {
                List<FileItem> items = fileUpload.parseRequest(request);
                ImageForm img = new ImageForm();
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        img.setBytes(item.get());
                        img.setContentType(item.getContentType());
                        img.setName(item.getName());
                    }else {
                        request.setAttribute("adId",item.getString());
                    }
                }
                request.setAttribute("imageForm", img);
                filterChain.doFilter(request, response);
            } catch (FileUploadException e) {
                response.sendRedirect("/");
            }
        }
    }

    public void destroy() {

    }
}
