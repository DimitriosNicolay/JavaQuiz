package gui.swing.delegations;

import gui.swing.panel.QuizPanel;

import java.util.List;

public class QuizPanelDelegation {

    private final QuizPanel quizPanel;

    public QuizPanelDelegation(QuizPanel quizPanel) {
        this.quizPanel = quizPanel;
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
