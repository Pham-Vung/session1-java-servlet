package org.example.session1;

import jakarta.servlet.RequestDispatcher;
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

import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "getProductByIdServlet", value = "/get-product-by-id")
public class GetProductByIdServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = Integer.parseInt(req.getParameter("id"));

            Product product = productDAO.getById(id);
            req.setAttribute("product", product);

            List<Size> allSizes = sizeDAO.getAllSizes();
            List<Color> allColors = colorDAO.getAllColors();
            req.setAttribute("allSizes", allSizes);
            req.setAttribute("allColors", allColors);

            List<String> selectedSizes = new ArrayList<>();
            for (Size size : product.getSizes()) {
                System.out.println(size.getName());
                selectedSizes.add(size.getName());
            }
            req.setAttribute("selectedSizes", selectedSizes);

            List<String> selectedColors = new ArrayList<>();
            for (Color color : product.getColors()) {
                System.out.println(color.getName());
                selectedColors.add(color.getName());
            }
            req.setAttribute("selectedColors", selectedColors);


            RequestDispatcher rd = req.getRequestDispatcher("edit-product.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        req.setAttribute("image", base64Image);

    }
}
