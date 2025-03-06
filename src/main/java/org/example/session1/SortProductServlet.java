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

@WebServlet(name = "sortProductServlet", value = "/sort-product-servlet")
public class SortProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sort = req.getParameter("sort");
        List<Product> products = productDAO.getBySort(sort);
//        req.setAttribute("products", products);
//        HttpSession session = req.getSession();
//        session.setAttribute("products", products);
//        resp.sendRedirect("product-servlet");
//        req.getRequestDispatcher("product.jsp").forward(req, resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String json = gson.toJson(products);
        resp.getWriter().write(json);
    }
}
