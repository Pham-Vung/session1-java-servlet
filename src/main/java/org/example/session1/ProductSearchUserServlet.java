package org.example.session1;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.session1.DAO.ProductDAO;
import org.example.session1.entity.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "searchProductUser", value = "/search-product-user")
public class ProductSearchUserServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String keyword = req.getParameter("keyword");
        List<Product> products;
        try {
            if (keyword != null && !keyword.isEmpty()) {
                products = productDAO.getByNameUser(keyword);
            } else {
                products = productDAO.getUserProducts();
            }
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            String json = gson.toJson(products);
            resp.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
