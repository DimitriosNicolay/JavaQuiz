package gui.swing.delegations;

import gui.swing.panel.QuestionPanel;

import java.util.List;

public interface QuestionPanelDelegation {
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

    void setAnswersEditable(boolean editable);

    QuestionPanel getQuestionPanel();
}
