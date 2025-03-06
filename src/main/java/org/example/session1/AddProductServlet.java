package org.example.session1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.session1.DAO.ProductDAO;
import org.example.session1.entity.Product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "addProduct", value = "/add-product")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class AddProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        String path = getServletContext().getRealPath("") + File.separator + "uploads";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }

        List<String> images = new ArrayList<>();
        for (Part part : request.getParts()) {
            if (part.getName().equals("image") && part.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + getFileName(part);
                String filePath = path + File.separator + fileName;
                part.write(filePath);
                images.add(fileName);
            }
        }

        Product product = new Product(name, quantity, images);
        boolean result = productDAO.addProduct(product);
        if (result) {
            response.sendRedirect("product-servlet?error=false");
        } else {
            response.sendRedirect("product-servlet?error=true");
        }
    }

    public static String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
