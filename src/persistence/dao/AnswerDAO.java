package persistence.dao;

import persistence.DBManager;
import persistence.dto.AnswerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    private final DBManager dbManager;

    public AnswerDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public boolean insert(AnswerDTO answer) throws SQLException {
        String sql = "INSERT INTO Answers (question_id, title, description, is_correct) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, answer.getQuestionId());
            ps.setString(2, answer.getTitle());
            ps.setString(3, answer.getDescription());
            ps.setBoolean(4, answer.isCorrect());
            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    answer.setId(keys.getInt(1));
                }
            }
            return true;
        }
    }

    public boolean update(AnswerDTO answer) throws SQLException {
        String sql = "UPDATE Answers SET question_id = ?, title = ?, description = ?, is_correct = ? WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, answer.getQuestionId());
            ps.setString(2, answer.getTitle());
            ps.setString(3, answer.getDescription());
            ps.setBoolean(4, answer.isCorrect());
            ps.setInt(5, answer.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Answers WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public AnswerDTO getById(int id) throws SQLException {
        String sql = "SELECT id, question_id, title, description, is_correct FROM Answers WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new AnswerDTO(
                            rs.getInt("id"),
                            rs.getInt("question_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getBoolean("is_correct"));
                }
                return null;
            }
        }
    }

    public List<AnswerDTO> getByQuestion(int questionId) throws SQLException {
        List<AnswerDTO> answers = new ArrayList<>();
        String sql = "SELECT id, question_id, title, description, is_correct FROM Answers WHERE question_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    answers.add(new AnswerDTO(
                            rs.getInt("id"),
                            rs.getInt("question_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getBoolean("is_correct")));
                }
            }
        }
        return answers;
    }

    public List<String> getAnswerTitlesByQuestionId(int questionId) throws SQLException {
        List<String> answerTitles = new ArrayList<>();

        String sql = "SELECT title FROM Answers WHERE question_id = ? ORDER BY id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    answerTitles.add(rs.getString("title"));
                }
            }
        }
        return answerTitles;
    }
}
