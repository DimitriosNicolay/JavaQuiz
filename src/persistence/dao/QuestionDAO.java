package persistence.dao;

import persistence.DBManager;
import persistence.dto.QuestionDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private final DBManager dbManager;

    public QuestionDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public boolean insert(QuestionDTO question) throws SQLException {
        String sql = "INSERT INTO Questions (topic_id, title, description) VALUES (?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, question.getTopicId());
            ps.setString(2, question.getTitle());
            ps.setString(3, question.getDescription());
            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    question.setId(keys.getInt(1));
                }
            }
            return true;
        }
    }

    public boolean update(QuestionDTO question) throws SQLException {
        String sql = "UPDATE Questions SET topic_id = ?, title = ?, description = ? WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, question.getTopicId());
            ps.setString(2, question.getTitle());
            ps.setString(3, question.getDescription());
            ps.setInt(4, question.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Questions WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public QuestionDTO getById(int id) throws SQLException {
        String sql = "SELECT id, topic_id, title, description FROM Questions WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new QuestionDTO(
                            rs.getInt("id"),
                            rs.getInt("topic_id"),
                            rs.getString("title"),
                            rs.getString("description"));
                }
                return null;
            }
        }
    }

    public List<QuestionDTO> getByTopic(int topicId) throws SQLException {
        List<QuestionDTO> questions = new ArrayList<>();
        String sql = "SELECT id, topic_id, title, description FROM Questions WHERE topic_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, topicId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    questions.add(new QuestionDTO(
                            rs.getInt("id"),
                            rs.getInt("topic_id"),
                            rs.getString("title"),
                            rs.getString("description")));
                }
            }
        }
        return questions;
    }
}
