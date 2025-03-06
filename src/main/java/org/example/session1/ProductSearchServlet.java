package org.example.session1;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.session1.DAO.ProductDAO;
import org.example.session1.entity.Product;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "searchProduct", value = "/search-servlet")
public class ProductSearchServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<Product> products;

        if (keyword != null && !keyword.isEmpty()) {
            products = productDAO.getByName(keyword);
        } else {
            products = productDAO.getAllProducts();
        }
//        HttpSession session = req.getSession();
//        session.setAttribute("products", products);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String json = gson.toJson(products);
        resp.getWriter().write(json);
//        req.setAttribute("products", products);
//        RequestDispatcher dispatcher = req.getRequestDispatcher("product.jsp");
//        dispatcher.forward(req, resp);
    }

}
