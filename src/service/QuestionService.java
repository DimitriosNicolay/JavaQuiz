package service;

import persistence.dao.QuestionDAO;
import persistence.dao.AnswerDAO;
import service.dto.QuestionDTO;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class QuestionService {

    private final QuestionDAO questionDAO;
    private final AnswerDAO answerDAO;

    public QuestionService(QuestionDAO questionDAO, AnswerDAO answerDAO) {
        this.questionDAO = questionDAO;
        this.answerDAO = answerDAO;
    }

    public List<QuestionDTO> getAllQuestions() throws SQLException {
        return questionDAO.getAll();
    }

    public QuestionDTO getQuestionById(int id) throws SQLException {
        return questionDAO.getById(id);
    }

    public boolean deleteQuestion(int id) throws SQLException {
        return questionDAO.delete(id);
    }

    public boolean saveQuestion(QuestionDTO question) throws SQLException {
        validateQuestion(question);
        if (question.getId() <= 0) {
            return questionDAO.insert(question);
        } else {
            return questionDAO.update(question);
        }
    }

    public List<String> getAnswersForQuestion(int questionId) {
        try {
            return answerDAO.getAnswerTitlesByQuestionId(questionId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void validateQuestion(QuestionDTO question) {
        if (question.getTitle() == null || question.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Question title cannot be empty");
        }
        if (question.getDescription() == null || question.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Question description cannot be empty");
        }
    }

    public List<QuestionDTO> getQuestionsByTopic(int topicId) throws SQLException {
        return questionDAO.getByTopicId(topicId);
    }

}
