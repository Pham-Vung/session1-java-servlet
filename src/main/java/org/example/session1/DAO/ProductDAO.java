package org.example.session1.DAO;

import org.example.session1.entity.Product;
import org.example.session1.util.DbConnect;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
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
        String query = "SELECT p.id, p.product_name, p.quantity, i.image_name AS image_name " +
                "FROM shopee_db2.product p LEFT JOIN shopee_db2.image i ON p.id = i.product_id " +
                "WHERE p.id = ?";
        Connection conn = dbConnect.getConnection();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Product product = null;
            List<String> imageNames = new ArrayList<>();
            while (resultSet.next()) {
                if (product == null) {
                    product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("product_name"));
                    product.setQuantity(resultSet.getInt("quantity"));
                }
                if (resultSet.getString("image_name") != null) {
                    imageNames.add(resultSet.getString("image_name"));
                }
            }

            if (product != null) {
                product.setImages(imageNames);
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        Connection connection = dbConnect.getConnection();
        List<String> images = new ArrayList<>();
        String selectImageQuery = "SELECT image_name FROM shopee_db2.image WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectImageQuery)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                images.add(resultSet.getString("image_name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        String deleteProductQuery = "DELETE FROM shopee_db2.product WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteProductQuery)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                for (String imageName : images) {
                    File file = new File("D:\\Workspace\\Java\\Java-servlet\\hoc-ngoai\\session1\\target\\session1-1.0-SNAPSHOT\\uploads" + imageName);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
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
        String productQuery = "INSERT INTO shopee_db2.product (product_name, quantity) VALUES (?, ?)";
        String imageQuery = "INSERT INTO shopee_db2.image (image_name, product_id) VALUES (?, ?)";
        try (PreparedStatement productStmt = connection.prepareStatement(productQuery, Statement.RETURN_GENERATED_KEYS)) {
            productStmt.setString(1, product.getName());
            productStmt.setInt(2, product.getQuantity());
            productStmt.executeUpdate();

            ResultSet generatedKeys = productStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int productId = generatedKeys.getInt(1);

                try (PreparedStatement imageStmt = connection.prepareStatement(imageQuery)) {
                    for (String imageName : product.getImages()) {
                        imageStmt.setString(1, imageName);
                        imageStmt.setInt(2, productId);
                        imageStmt.executeUpdate();
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

//        Connection connection = null;
//        PreparedStatement productStmt = null;
//        PreparedStatement imageStmt = null;
//
//        try {
//            connection = dbConnect.getConnection();
//            connection.setAutoCommit(false);
//
//            String productQuery = "INSERT INTO shopee_db2.product (product_name, quantity) VALUES (?, ?)";
//            productStmt = connection.prepareStatement(productQuery);
//            productStmt.setString(1, product.getName());
//            productStmt.setInt(2, product.getQuantity());
//            productStmt.executeUpdate();
//
//            List<String> images = product.getImages();
//            if (images != null && !images.isEmpty()) {
//                String imageQuery = "INSERT INTO shopee_db2.image (image_name, product_id) VALUES (?, ?)";
//                imageStmt = connection.prepareStatement(imageQuery);
//
//                for (String imageName : images) {
//                    imageStmt.setString(1, imageName);
//                    imageStmt.setInt(2, product.getId());
//                    imageStmt.addBatch();
//                }
//                imageStmt.executeBatch();
//            }
//
//            connection.commit();
//            return true;
//        } catch (SQLException e) {
//            if (connection != null) {
//                connection.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            if (productStmt != null) {
//                productStmt.close();
//            }
//            if (imageStmt != null) {
//                imageStmt.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        }
//        return false;
    }

    public boolean update(Product product, boolean isImageUpdated) {
//        Connection connection = dbConnect.getConnection();
//        String updateQuery = "UPDATE shopee_db2.product SET product_name = ?, quantity = ? WHERE id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
//            stmt.setString(1, product.getName());
//            stmt.setInt(2, product.getQuantity());
//            stmt.setInt(3, product.getId());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        String deleteImageQuery = "DELETE FROM shopee_db2.image WHERE product_id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(deleteImageQuery)) {
//            stmt.setInt(1, product.getId());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        String insertImageQuery = "INSERT INTO shoppe_db2.image (image_name, product_id) VALUES (?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(insertImageQuery)) {
//            for (String imageName : product.getImages()) {
//                stmt.setString(1, imageName);
//                stmt.setInt(2, product.getId());
//                stmt.addBatch();
//            }
//            stmt.executeBatch();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;

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
            if (isImageUpdated && product.getImages() != null && !product.getImages().isEmpty()) {
                String deleteImageQuery = "DELETE FROM shopee_db2.image WHERE product_id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(deleteImageQuery)) {
                    stmt.setInt(1, product.getId());
                    stmt.executeUpdate();
                }

                String insertImageQuery = "INSERT INTO shopee_db2.image (image_name, product_id) VALUES (?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(insertImageQuery)) {
                    for (String imageName : product.getImages()) {
                        stmt.setString(1, imageName);
                        stmt.setInt(2, product.getId());
                        stmt.addBatch();
                    }
                    stmt.executeBatch();
                }
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
}
