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
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@WebServlet(name = "getProductByIdServlet", value = "/get-product-by-id")
public class GetProductByIdServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      try {
          int id = Integer.parseInt(req.getParameter("id"));
          Product product = productDAO.getById(id);
          req.setAttribute("product", product);
          RequestDispatcher rd = req.getRequestDispatcher("edit-product.jsp");
          rd.forward(req, resp);
      } catch (Exception e) {
          e.printStackTrace();
      }


//        req.setAttribute("image", base64Image);

    }
}
