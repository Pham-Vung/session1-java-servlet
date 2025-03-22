package org.example.session1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.session1.DAO.ProductDAO;
import org.example.session1.entity.Product;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "productUserServlet", value = "/product-user-servlet")
public class ProductUserServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products;
        products = productDAO.getUserProducts();

        req.setAttribute("products", products);
        RequestDispatcher dispatcher = req.getRequestDispatcher("product-user.jsp");
        dispatcher.forward(req, resp);
    }
}
