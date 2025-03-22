package org.example.session1;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.session1.DAO.ProductDAO;

import java.io.IOException;

@WebServlet(name = "deleteProductServlet", value = "/delete-product-servlet")
public class DeleteProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean result = productDAO.delete(id);
        if (result) {
            resp.sendRedirect("product-servlet");
        }
    }
}
