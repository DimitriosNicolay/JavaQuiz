package gui.swing.delegations;

import gui.swing.panel.QuizPanel;
import persistence.dto.QuestionDTO;
import service.QuizService;

import javax.swing.*;
import java.util.List;

public class QuizPanelDelegation {

    private final QuizPanel quizPanel;
    private final QuizService quizService;
    private int currentQuestionIndex = 0;

    public QuizPanelDelegation(QuizPanel quizPanel, QuizService quizService) {
        this.quizPanel = quizPanel;
        this.quizService = quizService;
        setupListeners();
        loadQuestion(currentQuestionIndex);
    }

    // Set up action listeners for buttons
    private void setupListeners() {
        quizPanel.getButtonPanel().getButton("Next").addActionListener(e -> nextQuestion());
        quizPanel.getButtonPanel().getButton("Previous").addActionListener(e -> previousQuestion());
        quizPanel.getButtonPanel().getButton("Submit").addActionListener(e -> submitQuiz());
    }

    // Load question and answers into the panel
    private void loadQuestion(int index) {
        List<QuestionDTO> questions = quizService.getQuestions();
        if (index >= 0 && index < questions.size()) {
            QuestionDTO question = questions.get(index);
            quizPanel.setTopicName("Question " + (index + 1));
            quizPanel.setQuestionName(question.getTitle());        // use getTitle()
            quizPanel.setQuestionText(question.getDescription());   // use getDescription()

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
        if (currentQuestionIndex < quizService.getQuestions().size() - 1) {
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
