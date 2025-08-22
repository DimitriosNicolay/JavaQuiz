package gui.swing.delegations;

import gui.swing.panel.QuizPanel;

import java.util.List;

public interface QuizPanelDelegation {
    String getTopicName();

    void setTopicName(String topicName);

    String getQuestionName();

    void setQuestionName(String questionName);

    String getQuestionText();

    void setQuestionText(String questionText);

    List<String> getAnswerTexts();

    void setAnswerTexts(List<String> texts);

    List<Boolean> getCheckedStates();

    void setCheckedStates(List<Boolean> checks);

    void setAnswered(boolean correct);

    void setAnswerExplanation(String explanation);

    void setCorrectAnswer(String answer);

    QuizPanel getQuizPanel();
}
