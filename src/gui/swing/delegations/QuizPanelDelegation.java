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

    private void setupListeners() {
        quizPanel.getButtonPanel().getButton("Next").addActionListener(e -> nextQuestion());
        quizPanel.getButtonPanel().getButton("Previous").addActionListener(e -> previousQuestion());
        quizPanel.getButtonPanel().getButton("Submit").addActionListener(e -> submitQuiz());
    }

    private void loadQuestion(int index) {
        List<QuestionDTO> questions = quizService.getQuestions();
        if (index >= 0 && index < questions.size()) {
            QuestionDTO question = questions.get(index);
            quizPanel.setTopicName("Question " + (index + 1));
            quizPanel.setQuestionName(question.getTitle());        // use getTitle()
            quizPanel.setQuestionText(question.getDescription());   // use getDescription()

            // Assuming your QuizService has a method to fetch answers by question id
            List<String> answerTexts = quizService.getAnswersForQuestion(question.getId());
            quizPanel.setAnswerTexts(answerTexts);

            quizPanel.setCheckedStates(null); // clear selection
            quizPanel.setAnswered(false);
            quizPanel.setAnswerExplanation("");
            quizPanel.setCorrectAnswer("");
        }
    }

    private void nextQuestion() {
        if (currentQuestionIndex < quizService.getQuestions().size() - 1) {
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
        // Implement your scoring and submission logic here
        JOptionPane.showMessageDialog(quizPanel, "Quiz submitted!");
    }

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
