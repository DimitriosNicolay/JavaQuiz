package service;

import persistence.dao.AnswerDAO;
import persistence.dto.QuestionDTO;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class QuizService {

    private final List<QuestionDTO> questions;
    private final AnswerDAO answerDAO;

    public QuizService(List<QuestionDTO> questions, AnswerDAO answerDAO) {
        this.questions = questions;
        this.answerDAO = answerDAO;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public List<String> getAnswersForQuestion(int questionId) {
        try {
            return answerDAO.getAnswerTitlesByQuestionId(questionId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
