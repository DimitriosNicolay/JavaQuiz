package persistence.dao;

import persistence.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private DBManager dbManager;

    private final String SQL_INSERT = "INSERT INTO Questions (topic_id, title, description) VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE Questions SET topic_id = ?, title = ?, description = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM Questions WHERE id = ?";
    private final String SQL_SELECT_ALL_BY_TOPIC = "SELECT id, topic_id, title, description FROM Questions WHERE topic_id = ?";
    private final String SQL_SELECT_BY_ID = "SELECT id, topic_id, title, description FROM Questions WHERE id = ?";

    public QuestionDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public boolean insert(int topicId, String title, String description) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setInt(1, topicId);
            ps.setString(2, title);
            ps.setString(3, description);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(int id, int topicId, String title, String description) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, topicId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setInt(4, id);
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

    // Each String[] contains id, topic_id, title, description
    public List<String[]> getAllByTopic(int topicId) {
        List<String[]> result = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL_BY_TOPIC)) {
            ps.setInt(1, topicId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[4];
                    row[0] = String.valueOf(rs.getInt("id"));
                    row[1] = String.valueOf(rs.getInt("topic_id"));
                    row[2] = rs.getString("title");
                    row[3] = rs.getString("description");
                    result.add(row);
                }
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
                            String.valueOf(rs.getInt("topic_id")),
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
