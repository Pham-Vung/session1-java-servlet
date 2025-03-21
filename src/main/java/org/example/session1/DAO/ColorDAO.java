package org.example.session1.DAO;

import org.example.session1.entity.Color;
import org.example.session1.util.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColorDAO {
    private final DbConnect dbConnect = new DbConnect("jdbc:mysql://localhost:3306/shopee_db2", "root", "root");

    public List<Color> getAllColors() {
        List<Color> colors = new ArrayList<>();
        Connection connection = dbConnect.getConnection();
        String query = "SELECT id, color_name FROM shopee_db2.color";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                colors.add(new Color(rs.getInt("id"), rs.getString("color_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colors;
    }
}
