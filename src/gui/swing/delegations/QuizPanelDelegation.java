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
import java.util.stream.Collectors;

public class QuizPanelDelegation {

    private final QuizPanel quizPanel;
    private final QuizService quizService;
    private final TopicService topicService;
    private final QuestionService questionService; // Add this for topic filtering
    private int currentQuestionIndex = 0;
    private List<QuestionDTO> currentQuestions; // Store filtered questions

    public QuizPanelDelegation(QuizPanel quizPanel, QuizService quizService, TopicService topicService, QuestionService questionService) {
        this.quizPanel = quizPanel;
        this.quizService = quizService;
        this.topicService = topicService;
        this.questionService = questionService;
        this.currentQuestions = quizService.getQuestions(); // Initialize with all questions
        populateTopicComboBox();
        setupListeners();
        loadQuestion(currentQuestionIndex);
    }

    // Populate the topic combo box with topics from the database
    private void populateTopicComboBox() {
        try {
            List<TopicDTO> topics = topicService.getAllTopics();
            quizPanel.getAnswerStatusPanel().loadTopics(topics);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(quizPanel, "Failed to load topics: " + e.getMessage());
        }
    }

    // Set up action listeners for buttons
    private void setupListeners() {
        quizPanel.getButtonPanel().getButton("Next").addActionListener(e -> nextQuestion());
        quizPanel.getButtonPanel().getButton("Previous").addActionListener(e -> previousQuestion());
        quizPanel.getButtonPanel().getButton("Submit").addActionListener(e -> submitQuiz());

        // Add listener for topic selection
        quizPanel.getAnswerStatusPanel().getTopicComboBox().addActionListener(e -> onTopicChanged());
    }

    // Handle topic selection change
    private void onTopicChanged() {
        TopicDTO selectedTopic = quizPanel.getAnswerStatusPanel().getSelectedTopic();
        currentQuestionIndex = 0; // Reset to first question when topic changes

        try {
            if (selectedTopic == null) {
                // Load all questions
                currentQuestions = questionService.getAllQuestions();
            } else {
                // Load questions for specific topic
                currentQuestions = questionService.getQuestionsByTopic(selectedTopic.getId());
            }

            // Load the first question of the filtered set
            loadQuestion(currentQuestionIndex);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(quizPanel, "Failed to load questions for topic: " + e.getMessage());
            currentQuestions = List.of(); // Empty list on error
        }
    }

    // Load question and answers into the panel
    private void loadQuestion(int index) {
        if (currentQuestions.isEmpty()) {
            // No questions available
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

            // Fetch answers from service
            List<String> answerTexts = quizService.getAnswersForQuestion(question.getId());
            quizPanel.setAnswerTexts(answerTexts);

            quizPanel.setCheckedStates(null); // clear selection
            quizPanel.setAnswered(false);
            quizPanel.setAnswerExplanation("");
            quizPanel.setCorrectAnswer("");
        }
    }

    // Navigate to the next question
    private void nextQuestion() {
        if (currentQuestionIndex < currentQuestions.size() - 1) {
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
        }
    }

    // Navigate to the previous question
    private void previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            loadQuestion(currentQuestionIndex);
        }
    }

    private void submitQuiz() {
        // TODO: Implement quiz scoring logic
        JOptionPane.showMessageDialog(quizPanel, "Quiz submitted!");
    }

    // Delegation methods to QuizPanel
    public String getTopicName() {
        return quizPanel.getTopicName();
    }

    public void setTopicName(String topicName) {
        quizPanel.setTopicName(topicName);
    }

    public String getQuestionName() {
        return quizPanel.getQuestionName();
    }

    public void setQuestionName(String questionName) {
        quizPanel.setQuestionName(questionName);
    }

    public String getQuestionText() {
        return quizPanel.getQuestionText();
    }

    public void setQuestionText(String questionText) {
        quizPanel.setQuestionText(questionText);
    }

    public List<String> getAnswerTexts() {
        return quizPanel.getAnswerTexts();
    }

    public void setAnswerTexts(List<String> texts) {
        quizPanel.setAnswerTexts(texts);
    }

    public List<Boolean> getCheckedStates() {
        return quizPanel.getCheckedStates();
    }

    public void setCheckedStates(List<Boolean> checks) {
        quizPanel.setCheckedStates(checks);
    }

    public void setAnswered(boolean correct) {
        quizPanel.setAnswered(correct);
    }

    public void setAnswerExplanation(String explanation) {
        quizPanel.setAnswerExplanation(explanation);
    }

    public void setCorrectAnswer(String answer) {
        quizPanel.setCorrectAnswer(answer);
    }

    public QuizPanel getQuizPanel() {
        return quizPanel;
    }
}
