package service;

import persistence.dao.TopicDAO;
import persistence.dto.TopicDTO;

import java.sql.SQLException;
import java.util.List;

public class TopicService {
    private final TopicDAO topicDAO;

    public TopicService(TopicDAO topicDAO) {
        this.topicDAO = topicDAO;
    }

    public List<TopicDTO> getAllTopics() throws SQLException {
        return topicDAO.getAll();
    }

    public TopicDTO getTopicById(int id) throws SQLException {
        return topicDAO.getById(id);
    }

    public boolean deleteTopic(int id) throws SQLException {
        return topicDAO.delete(id);
    }

    public boolean saveTopic(TopicDTO topic) throws SQLException {
        validateTopic(topic);
        if (topic.getId() <= 0) {
            return topicDAO.insert(topic);
        } else {
            return topicDAO.update(topic);
        }
    }

    private void validateTopic(TopicDTO topic) {
        if (topic.getTitle() == null || topic.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Topic title cannot be empty");
        }
    }
}
