package org.example.session1.DAO;

import org.example.session1.entity.Size;
import org.example.session1.util.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SizeDAO {
    private final DbConnect dbConnect = new DbConnect("jdbc:mysql://localhost:3306/shopee_db2", "root", "root");

    public List<Size> getAllSizes() {
        List<Size> sizes = new ArrayList<>();
        Connection connection = dbConnect.getConnection();
        String query = "SELECT id, size_name FROM shopee_db2.size";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sizes.add(new Size(rs.getInt("id"), rs.getString("size_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sizes;
    }

    public Size getSizeByName(String sizeName) {
        Connection connection = dbConnect.getConnection();
        String query = "SELECT * FROM shopee_db2.size WHERE size_name=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, sizeName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Size(rs.getInt("id"), rs.getString("size_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
