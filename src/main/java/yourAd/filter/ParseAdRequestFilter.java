package yourAd.filter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import yourAd.form.AdForm;
import yourAd.form.ImageForm;
import yourAd.manager.CategoryManager;
import yourAd.model.Ad;
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

@WebFilter(urlPatterns = "/ad/add",filterName = "filter2")
public class ParseAdRequestFilter extends AbstractFilter {

    private static final String FILE_PATH = "C:\\Users\\Erik\\IdeaProjects\\java-web\\YourAd\\src\\main\\resources\\img-config.properties";
    private Properties properties;
    private static final String METHOD = "POST";
    private CategoryManager categoryManager;

    public void init(FilterConfig filterConfig) throws ServletException {
        properties = PropertiesUtil.load(FILE_PATH);
        categoryManager = (CategoryManager) filterConfig.getServletContext().getAttribute("categoryManager");
    }

    public void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if(!request.getMethod().equals(METHOD)){
            filterChain.doFilter(request,response);
        }else {
            if(!ServletFileUpload.isMultipartContent(request)){
                request.setAttribute("categories",categoryManager.getAll());
                request.setAttribute("subCategories",categoryManager.getAllByChildrenListIsEmpty());
                request.setAttribute("imageError","request does not multipart");
                request.getRequestDispatcher(ADD_AD).forward(request,response);
            }else {
                ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
                fileUpload.setFileSizeMax(Long.parseLong(properties.getProperty("request.max.size")));
                fileUpload.setSizeMax(Long.parseLong(properties.getProperty("img.max.size")));
                try {
                    List<FileItem> items = fileUpload.parseRequest(request);
                    AdForm ad = new AdForm();
                    ImageForm img = new ImageForm();
                    for (FileItem item : items) {
                        if(item.isFormField()) {
                            if (item.getFieldName().equals("title")) {
                                ad.setTitle(item.getString());
                            } else if (item.getFieldName().equals("description")){
                                ad.setDescription(item.getString());
                            } else if (item.getFieldName().equals("price")){
                                ad.setPrice(item.getString());
                            }else {
                                ad.setCategoryId(Integer.parseInt(item.getString()));
                            }
                        }else {
                            img.setBytes(item.get());
                            img.setContentType(item.getContentType());
                            img.setName(item.getName());
                        }
                    }
                    request.setAttribute("adForm",ad);
                    request.setAttribute("imageForm",img);
                    filterChain.doFilter(request,response);
                } catch (FileUploadException e) {
                    request.setAttribute("categories",categoryManager.getAll());
                    request.setAttribute("subCategories",categoryManager.getAllByChildrenListIsEmpty());
                    request.setAttribute("imageError","invalid request");
                    request.getRequestDispatcher(ADD_AD).forward(request,response);
                }
            }
        }
    }

    public void destroy() {

    }
}
