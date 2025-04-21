package org.example.session1.DAO;

import org.example.session1.entity.Color;
import org.example.session1.entity.Product;
import org.example.session1.entity.Size;
import org.example.session1.util.DbConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductDAO {
    private final DbConnect dbConnect = new DbConnect("jdbc:mysql://localhost:3306/shopee_db2", "root", "root");

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection conn = dbConnect.getConnection();
        String query = "SELECT * FROM shopee_db2.product";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("product_name"));
                product.setQuantity(resultSet.getInt("quantity"));
                products.add(product);
            }
            dbConnect.closeConnection(conn);
        } catch (SQLException e) {
            dbConnect.closeConnection(conn);
            throw new RuntimeException(e);
        }
        return products;
    }

    public List<Product> getByName(String name) {
        List<Product> products = new ArrayList<>();
        Connection conn = dbConnect.getConnection();
        String query = "SELECT * FROM shopee_db2.product WHERE product_name LIKE ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("product_name"));
                product.setQuantity(resultSet.getInt("quantity"));
                products.add(product);
            }
            dbConnect.closeConnection(conn);
        } catch (SQLException e) {
            dbConnect.closeConnection(conn);
            throw new RuntimeException(e);
        }
        return products;
    }

    public Product getById(int id) {
        String productQuery = "SELECT id, product_name, quantity FROM shopee_db2.product WHERE id = ?";

        Connection conn = dbConnect.getConnection();
        Product product = null;

        try (PreparedStatement stmtProduct = conn.prepareStatement(productQuery)) {
            stmtProduct.setInt(1, id);
            ResultSet rsProduct = stmtProduct.executeQuery();

            if (rsProduct.next()) {
                product = new Product();
                product.setId(rsProduct.getInt("id"));
                product.setName(rsProduct.getString("product_name"));
                product.setQuantity(rsProduct.getInt("quantity"));
                product.setImages(getImagesByProductId(conn, id));
                product.setColors(getColorsByProductId(conn, id));
                product.setSizes(getSizesByProductId(conn, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    private List<Size> getSizesByProductId(Connection conn, int id) throws SQLException {
        List<Size> sizes = new ArrayList<>();
        String query = "SELECT s.id, s.size_name FROM product_size ps JOIN size s ON ps.size_id = s.id WHERE ps.product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Size size = new Size();
                size.setId(rs.getInt("id"));
                size.setName(rs.getString("size_name"));
                sizes.add(size);
            }
        }
        return sizes;
    }

    private List<Color> getColorsByProductId(Connection conn, int id) throws SQLException {
        List<Color> colors = new ArrayList<>();
        String query = "SELECT c.id, c.color_name FROM shopee_db2.product_color pc JOIN shopee_db2.color c ON pc.color_id = c.id WHERE pc.product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Color color = new Color();
                color.setId(rs.getInt("id"));
                color.setName(rs.getString("color_name"));
                colors.add(color);
            }
        }
        return colors;
    }

    private List<String> getImagesByProductId(Connection conn, int id) throws SQLException {
        List<String> images = new ArrayList<>();
        String query = "SELECT image_name FROM shopee_db2.image WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                images.add(rs.getString("image_name"));
            }
        }
        return images;
    }

    public boolean delete(int id) {
        Connection connection = dbConnect.getConnection();
        try {
            connection.setAutoCommit(false);

            deleteProductImages(connection, id);
            deleteProductColors(connection, id);
            deleteProductSizes(connection, id);

            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM shopee_db2.product WHERE id = ?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void deleteProductSizes(Connection connection, int id) throws SQLException {
        String query = "DELETE FROM shopee_db2.product_size WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private void deleteProductColors(Connection connection, int id) throws SQLException {
        String query = "DELETE FROM shopee_db2.product_color WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private void deleteProductImages(Connection connection, int id) throws SQLException {
        String query = "DELETE FROM shopee_db2.image WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Product> getBySort(String sort) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";

        if ("byName".equals(sort)) {
            query += " ORDER BY product_name ASC";// từ a->z
        }
        if ("byQuantity".equals(sort)) {
            query += " ORDER BY quantity ASC";
        }

        Connection conn = dbConnect.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("product_name"));
                product.setQuantity(resultSet.getInt("quantity"));
                products.add(product);
            }
            dbConnect.closeConnection(conn);
        } catch (SQLException e) {
            dbConnect.closeConnection(conn);
            throw new RuntimeException(e);
        }
        return products;
    }

    public boolean addProduct(Product product) {
        Connection connection = dbConnect.getConnection();
        try {
            connection.setAutoCommit(false);

            int productId = insertProduct(connection, product);
            if (productId == -1) {
                connection.rollback();
                return false;
            }

            addProductImages(connection, productId, product.getImages());
            addProductSizes(connection, productId, product.getSizes());
            addProductColors(connection, productId, product.getColors());
            connection.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void addProductSizes(Connection connection, int productId, List<Size> sizes) throws SQLException {
        String query = "INSERT INTO shopee_db2.product_size (product_id, size_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Size size : sizes) {
                stmt.setInt(1, productId);
                stmt.setInt(2, size.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private int insertProduct(Connection connection, Product product) throws SQLException {
        String query = "INSERT INTO shopee_db2.product (product_name, quantity) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getQuantity());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    private void addProductImages(Connection connection, int productId, List<String> images) throws SQLException {
        String query = "INSERT INTO shopee_db2.image (image_name, product_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (String image : images) {
                stmt.setString(1, image);
                stmt.setInt(2, productId);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private void addProductColors(Connection connection, int productId, List<Color> colors) throws SQLException {
        String query = "INSERT INTO shopee_db2.product_color (product_id, color_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Color color : colors) {
                stmt.setInt(1, productId);
                stmt.setInt(2, color.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public boolean update(Product product, boolean isImageUpdated) {
        Connection connection = dbConnect.getConnection();
        boolean result = false;

        try {
            connection.setAutoCommit(false);

            String updateQuery = "UPDATE shopee_db2.product SET product_name = ?, quantity = ? WHERE id = ?";
            try (PreparedStatement productStmt = connection.prepareStatement(updateQuery)) {
                productStmt.setString(1, product.getName());
                productStmt.setInt(2, product.getQuantity());
                productStmt.setInt(3, product.getId());
                productStmt.executeUpdate();
            }

            deleteProductColors(connection, product.getId());
            deleteProductSizes(connection, product.getId());

            addProductColors(connection, product.getId(), product.getColors());
            addProductSizes(connection, product.getId(), product.getSizes());

            if (isImageUpdated && product.getImages() != null && !product.getImages().isEmpty()) {
                deleteProductImages(connection, product.getId());
                addProductImages(connection, product.getId(), product.getImages());
            }
            connection.commit();
            result = true;
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<Product> getUserProducts() throws SQLException {
        Connection connection = dbConnect.getConnection();
        String query = "SELECT " +
                "p.id AS product_id, p.product_name, p.quantity, " +
                "GROUP_CONCAT(DISTINCT i.image_name ORDER BY i.image_name SEPARATOR ',') AS images, " +
                "GROUP_CONCAT(DISTINCT c.color_name ORDER BY c.color_name SEPARATOR ',') AS colors, " +
                "GROUP_CONCAT(DISTINCT s.size_name ORDER BY s.size_name SEPARATOR ',') AS sizes " +
                "FROM shopee_db2.product p " +
                "LEFT JOIN shopee_db2.image i ON p.id = i.product_id " +
                "LEFT JOIN shopee_db2.product_color pc ON p.id = pc.product_id " +
                "LEFT JOIN shopee_db2.color c ON pc.color_id = c.id " +
                "LEFT JOIN shopee_db2.product_size ps ON p.id = ps.product_id " +
                "LEFT JOIN shopee_db2.size s ON ps.size_id = s.id " +
                "GROUP BY p.id;";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Product> products = new ArrayList<>();

        while (rs.next()) {
            int productId = rs.getInt("product_id");
            String productName = rs.getString("product_name");
            int quantity = rs.getInt("quantity");

            Product product = new Product(productId, productName, quantity);
            String images = rs.getString("images");
//            if (images != null) {
//                List<String> imageList = new ArrayList<>(Arrays.asList(images.split(",")));
//                product.setImages(imageList);
//            }

            String colors = rs.getString("colors");
//            if (colors != null) {
//                List<Color> colorList = new ArrayList<>();
//                for (String color : colors.split(",")) {
//                    colorList.add(new Color(color));
//                }
//                product.setColors(colorList);
//            }

            String sizes = rs.getString("sizes");
//            if (sizes != null) {
//                List<Size> sizeList = new ArrayList<>();
//                for (String sizeName : sizes.split(",")) {
//                    sizeList.add(new Size(sizeName));
//                }
//                product.setSizes(sizeList);
//            }

            moreProductInformation(product, images, colors, sizes);

            products.add(product);
        }
        return products;
    }

    private void moreProductInformation(Product product, String images, String colors, String sizes) {
        if (images != null) {
            List<String> imageList = new ArrayList<>(Arrays.asList(images.split(",")));
            product.setImages(imageList);
        }

        if (colors != null) {
            List<Color> colorList = new ArrayList<>();
            for (String color : colors.split(",")) {
                colorList.add(new Color(color));
            }
            product.setColors(colorList);
        }

        if (sizes != null) {
            List<Size> sizeList = new ArrayList<>();
            for (String sizeName : sizes.split(",")) {
                sizeList.add(new Size(sizeName));
            }
            product.setSizes(sizeList);
        }
    }

    public Product getProductUserById(int id) {
        Connection connection = dbConnect.getConnection();
        String query = "SELECT p.id AS product_id, p.product_name, p.quantity, " +
                "GROUP_CONCAT(DISTINCT i.image_name ORDER BY i.image_name SEPARATOR ',') AS images, " +
                "GROUP_CONCAT(DISTINCT c.color_name ORDER BY c.color_name SEPARATOR ',') AS colors, " +
                "GROUP_CONCAT(DISTINCT s.size_name ORDER BY s.size_name SEPARATOR ',') AS sizes " +
                "FROM shopee_db2.product p " +
                "LEFT JOIN shopee_db2.image i ON p.id = i.product_id " +
                "LEFT JOIN shopee_db2.product_color pc ON p.id = pc.product_id " +
                "LEFT JOIN shopee_db2.color c ON pc.color_id = c.id " +
                "LEFT JOIN shopee_db2.product_size ps ON p.id = ps.product_id " +
                "LEFT JOIN shopee_db2.size s ON ps.size_id = s.id " +
                "WHERE p.id = ? " +
                "GROUP BY p.id";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                Product product = new Product(id, productName, quantity);

                String images = rs.getString("images");
//                if (images != null) {
//                    List<String> imageList = new ArrayList<>(Arrays.asList(images.split(",")));
//                    product.setImages(imageList);
//                }

                String colors = rs.getString("colors");
//                if (colors != null) {
//                    List<Color> colorList = new ArrayList<>();
//                    for (String color : colors.split(",")) {
//                        colorList.add(new Color(color));
//                    }
//                    product.setColors(colorList);
//                }

                String sizes = rs.getString("sizes");
//                if (sizes != null) {
//                    List<Size> sizeList = new ArrayList<>();
//                    for (String size : sizes.split(",")) {
//                        sizeList.add(new Size(size));
//                    }
//                    product.setSizes(sizeList);
//                }
                moreProductInformation(product, images, colors, sizes);

                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Product> getByNameUser(String keyword) throws SQLException {
        List<Product> productList = new ArrayList<>();

        String query = "SELECT " +
                "p.id AS product_id, " +
                "p.product_name, " +
                "p.quantity, " +
                "GROUP_CONCAT(DISTINCT i.image_name ORDER BY i.image_name SEPARATOR ',') AS images, " +
                "GROUP_CONCAT(DISTINCT c.color_name ORDER BY c.color_name SEPARATOR ',') AS colors, " +
                "GROUP_CONCAT(DISTINCT s.size_name ORDER BY s.size_name SEPARATOR ',') AS sizes " +
                "FROM shopee_db2.product p " +
                "LEFT JOIN shopee_db2.image i ON p.id = i.product_id " +
                "LEFT JOIN shopee_db2.product_color pc ON p.id = pc.product_id " +
                "LEFT JOIN shopee_db2.color c ON pc.color_id = c.id " +
                "LEFT JOIN shopee_db2.product_size ps ON p.id = ps.product_id " +
                "LEFT JOIN shopee_db2.size s ON ps.size_id = s.id " +
                "WHERE p.product_name LIKE CONCAT('%', ?, '%') " +
                "GROUP BY p.id";

        Connection connection = dbConnect.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, keyword);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getInt("product_id"), rs.getString("product_name"), rs.getInt("quantity"));
                String images = rs.getString("images");
//               if (images != null) {
//                   List<String> imageList = new ArrayList<>(Arrays.asList(images.split(",")));
//                   product.setImages(imageList);
//               }

                String colors = rs.getString("colors");
//               if (colors != null) {
//                   List<Color> colorList = new ArrayList<>();
//                   for (String color : colors.split(",")) {
//                       colorList.add(new Color(color));
//                   }
//                   product.setColors(colorList);
//               }

                String sizes = rs.getString("sizes");
//               if (sizes != null) {
//                   List<Size> sizeList = new ArrayList<>();
//                   for (String size : sizes.split(",")) {
//                       sizeList.add(new Size(size));
//                   }
//                   product.setSizes(sizeList);
//               }
                moreProductInformation(product, images, colors, sizes);

                productList.add(product);
            }
        }
        return productList;
    }
}
