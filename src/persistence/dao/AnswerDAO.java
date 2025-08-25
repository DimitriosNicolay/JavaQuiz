package persistence.dao;

import persistence.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    private DBManager dbManager;

    private final String SQL_INSERT = "INSERT INTO Answers (question_id, title, description, is_correct) VALUES (?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE Answers SET question_id = ?, title = ?, description = ?, is_correct = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM Answers WHERE id = ?";
    private final String SQL_SELECT_ALL_BY_QUESTION = "SELECT id, question_id, title, description, is_correct FROM Answers WHERE question_id = ?";
    private final String SQL_SELECT_BY_ID = "SELECT id, question_id, title, description, is_correct FROM Answers WHERE id = ?";

    public AnswerDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public boolean insert(int questionId, String title, String description, boolean isCorrect) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setInt(1, questionId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setBoolean(4, isCorrect);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(int id, int questionId, String title, String description, boolean isCorrect) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, questionId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setBoolean(4, isCorrect);
            ps.setInt(5, id);
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

    // Each String[] contains id, question_id, title, description, is_correct
    public List<String[]> getAllByQuestion(int questionId) {
        List<String[]> result = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL_BY_QUESTION)) {
            ps.setInt(1, questionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[5];
                    row[0] = String.valueOf(rs.getInt("id"));
                    row[1] = String.valueOf(rs.getInt("question_id"));
                    row[2] = rs.getString("title");
                    row[3] = rs.getString("description");
                    row[4] = String.valueOf(rs.getBoolean("is_correct"));
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
                            String.valueOf(rs.getInt("question_id")),
                            rs.getString("title"),
                            rs.getString("description"),
                            String.valueOf(rs.getBoolean("is_correct"))
                    };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
