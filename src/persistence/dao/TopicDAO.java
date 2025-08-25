package persistence.dao;

import persistence.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {
    private DBManager dbManager;

    private final String SQL_INSERT = "INSERT INTO Topics (title, description) VALUES (?, ?)";
    private final String SQL_UPDATE = "UPDATE Topics SET title = ?, description = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM Topics WHERE id = ?";
    private final String SQL_SELECT_ALL = "SELECT id, title, description FROM Topics";
    private final String SQL_SELECT_BY_ID = "SELECT id, title, description FROM Topics WHERE id = ?";

    public TopicDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public boolean insert(String title, String description) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setString(1, title);
            ps.setString(2, description);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(int id, String title, String description) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setInt(3, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Returns a simple result list of array [id, title, description]
    public List<String[]> getAll() {
        List<String[]> result = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String[] row = new String[3];
                row[0] = String.valueOf(rs.getInt("id"));
                row[1] = rs.getString("title");
                row[2] = rs.getString("description");
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String[] getById(int id) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new String[]{
                            String.valueOf(rs.getInt("id")),
                            rs.getString("title"),
                            rs.getString("description")
                    };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
