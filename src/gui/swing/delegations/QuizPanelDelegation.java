package gui.swing.delegations;

import gui.swing.panel.QuizPanel;
import service.dto.QuestionDTO;
import service.dto.TopicDTO;
import service.QuizService;
import service.TopicService;
import service.QuestionService;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class QuizPanelDelegation {

    private final QuizPanel quizPanel;
    private final QuizService quizService;
    private final TopicService topicService;
    private final QuestionService questionService;
    private int currentQuestionIndex = 0;
    private List<QuestionDTO> currentQuestions;

    public QuizPanelDelegation(QuizPanel quizPanel, QuizService quizService, TopicService topicService, QuestionService questionService) {
        this.quizPanel = quizPanel;
        this.quizService = quizService;
        this.topicService = topicService;
        this.questionService = questionService;
        this.currentQuestions = quizService.getQuestions();
        populateTopicComboBox();
        setupListeners();
        loadQuestion(currentQuestionIndex);
    }

    private void populateTopicComboBox() {
        try {
            List<TopicDTO> topics = topicService.getAllTopics();
            quizPanel.getAnswerStatusPanel().loadTopics(topics);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(quizPanel, "Failed to load topics: " + e.getMessage());
        }
    }

    // CHANGED FROM PRIVATE TO PUBLIC
    public void refreshTopicComboBox() {
        try {
            List<TopicDTO> topics = topicService.getAllTopics();
            quizPanel.getAnswerStatusPanel().loadTopics(topics);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(quizPanel, "Failed to refresh topics: " + e.getMessage());
        }
    }

    private void setupListeners() {
        quizPanel.getButtonPanel().getButton("Next").addActionListener(e -> nextQuestion());
        quizPanel.getButtonPanel().getButton("Previous").addActionListener(e -> previousQuestion());
        quizPanel.getButtonPanel().getButton("Submit").addActionListener(e -> submitQuiz());

        quizPanel.getAnswerStatusPanel().getTopicComboBox().addActionListener(e -> onTopicChanged());
    }

    private void onTopicChanged() {
        TopicDTO selectedTopic = quizPanel.getAnswerStatusPanel().getSelectedTopic();
        currentQuestionIndex = 0;

        try {
            if (selectedTopic == null) {
                currentQuestions = questionService.getAllQuestions();
            } else {
                currentQuestions = questionService.getQuestionsByTopic(selectedTopic.getId());
            }

            loadQuestion(currentQuestionIndex);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(quizPanel, "Failed to load questions for topic: " + e.getMessage());
            currentQuestions = List.of();
        }
    }

    private void loadQuestion(int index) {
        if (currentQuestions.isEmpty()) {
            quizPanel.setTopicName("No Questions Available");
            quizPanel.setQuestionName("");
            quizPanel.setQuestionText("No questions found for the selected topic.");
            quizPanel.setAnswerTexts(List.of());
            return;
        }

        if (index >= 0 && index < currentQuestions.size()) {
            QuestionDTO question = currentQuestions.get(index);
            quizPanel.setTopicName("Question " + (index + 1) + " of " + currentQuestions.size());
            quizPanel.setQuestionName(question.getTitle());
            quizPanel.setQuestionText(question.getDescription());

            List<String> answerTexts = quizService.getAnswersForQuestion(question.getId());
            quizPanel.setAnswerTexts(answerTexts);

            quizPanel.setCheckedStates(null);
            quizPanel.setAnswered(false);
            quizPanel.setAnswerExplanation("");
            quizPanel.setCorrectAnswer("");
        }
    }

    private void nextQuestion() {
        if (currentQuestionIndex < currentQuestions.size() - 1) {
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
        }
    }

    private void previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            loadQuestion(currentQuestionIndex);
        }
    }

    private void submitQuiz() {
        JOptionPane.showMessageDialog(quizPanel, "Quiz submitted!");
    }

    // All the delegation methods remain the same...
    public String getTopicName() { return quizPanel.getTopicName(); }
    public void setTopicName(String topicName) { quizPanel.setTopicName(topicName); }
    public String getQuestionName() { return quizPanel.getQuestionName(); }
    public void setQuestionName(String questionName) { quizPanel.setQuestionName(questionName); }
    public String getQuestionText() { return quizPanel.getQuestionText(); }
    public void setQuestionText(String questionText) { quizPanel.setQuestionText(questionText); }
    public List<String> getAnswerTexts() { return quizPanel.getAnswerTexts(); }
    public void setAnswerTexts(List<String> texts) { quizPanel.setAnswerTexts(texts); }
    public List<Boolean> getCheckedStates() { return quizPanel.getCheckedStates(); }
    public void setCheckedStates(List<Boolean> checks) { quizPanel.setCheckedStates(checks); }
    public void setAnswered(boolean correct) { quizPanel.setAnswered(correct); }
    public void setAnswerExplanation(String explanation) { quizPanel.setAnswerExplanation(explanation); }
    public void setCorrectAnswer(String answer) { quizPanel.setCorrectAnswer(answer); }
    public QuizPanel getQuizPanel() { return quizPanel; }
}
