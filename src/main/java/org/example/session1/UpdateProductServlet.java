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

@WebServlet(name = "updateProductServlet", value = "/update-product-servlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class UpdateProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("product-name");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        int id = Integer.parseInt(req.getParameter("id-product"));
        boolean isImageUpdated = Boolean.parseBoolean(req.getParameter("isImageUpdated"));

        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        List<String> imagePaths = new ArrayList<>();

        for (Part part : req.getParts()) {
            if (part.getName().equals("image") && part.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + AddProductServlet.getFileName(part);
                String filePath = uploadPath + File.separator + fileName;
                part.write(filePath);
                imagePaths.add(fileName);
            }
        }

        Product product = new Product(id, name, quantity, imagePaths);
        boolean result = productDAO.update(product, isImageUpdated);
        if (result) {
            resp.sendRedirect("product-servlet");
        }
    }
}