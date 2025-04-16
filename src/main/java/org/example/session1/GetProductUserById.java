package org.example.session1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.session1.DAO.ProductDAO;
import org.example.session1.entity.Product;

import java.io.IOException;

@WebServlet(name = "getProductUserById", value = "/get-product-user-by-id")
public class GetProductUserById extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productDAO.getProductUserById(id);
        req.setAttribute("product", product);
        RequestDispatcher rd = req.getRequestDispatcher("product-user-detail.jsp");
        rd.forward(req, resp);
    }
}
