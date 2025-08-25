package persistence.dao;

import persistence.DBManager;
import persistence.dto.TopicDTO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {
    private final DBManager dbManager;

    public TopicDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public boolean insert(TopicDTO topic) throws SQLException {
        String sql = "INSERT INTO Topics (title, description) VALUES (?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, topic.getTitle());
            ps.setString(2, topic.getDescription());
            int affected = ps.executeUpdate();
            if (affected == 0) {
                return false;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    topic.setId(keys.getInt(1)); // set generated id in DTO
                }
            }
            return true;
        }
    }

    public boolean update(TopicDTO topic) throws SQLException {
        String sql = "UPDATE Topics SET title = ?, description = ? WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, topic.getTitle());
            ps.setString(2, topic.getDescription());
            ps.setInt(3, topic.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Topics WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public TopicDTO getById(int id) throws SQLException {
        String sql = "SELECT id, title, description FROM Topics WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TopicDTO(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description")
                    );
                }
                return null;
            }
        }
    }

    public List<TopicDTO> getAll() throws SQLException {
        List<TopicDTO> topics = new ArrayList<>();
        String sql = "SELECT id, title, description FROM Topics";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                topics.add(new TopicDTO(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description")
                ));
            }
        }
        return topics;
    }
}
