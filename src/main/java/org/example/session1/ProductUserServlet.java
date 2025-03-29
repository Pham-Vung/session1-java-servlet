package org.example.session1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.session1.DAO.ColorDAO;
import org.example.session1.DAO.ProductDAO;
import org.example.session1.DAO.SizeDAO;
import org.example.session1.entity.Color;
import org.example.session1.entity.Product;
import org.example.session1.entity.Size;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "productUserServlet", value = "/product-user-servlet")
public class ProductUserServlet extends HttpServlet {
    private ProductDAO productDAO;
    private SizeDAO sizeDAO;
    private ColorDAO colorDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        sizeDAO = new SizeDAO();
        colorDAO = new ColorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = null;
        try {
            products = productDAO.getUserProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Color> colors = colorDAO.getAllColors();

        List<Size> sizes = sizeDAO.getAllSizes();

        req.setAttribute("products", products);
        req.setAttribute("sizes", sizes);
        req.setAttribute("colors", colors);

        RequestDispatcher dispatcher = req.getRequestDispatcher("product-user2.jsp");
        dispatcher.forward(req, resp);
    }
}
