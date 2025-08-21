package gui.swing.delegations;

import gui.swing.panel.QuestionPanel;
import gui.swing.subpanel.QuestionDescriptionPanel;
import gui.swing.subpanel.AnswersPanel;

import java.util.List;

public class QuestionPanelDelegation {

    private final QuestionPanel questionPanel;

    public QuestionPanelDelegation(QuestionPanel questionPanel) {
        this.questionPanel = questionPanel;
    }

    public String getTopicName() {
        return questionPanel.getQuestionDescriptionPanel().getTopicName();
    }

    public void setTopicName(String topicName) {
        questionPanel.getQuestionDescriptionPanel().setTopicName(topicName);
    }

    public String getQuestionName() {
        return questionPanel.getQuestionDescriptionPanel().getQuestionName();
    }

    public void setQuestionName(String questionName) {
        questionPanel.getQuestionDescriptionPanel().setQuestionName(questionName);
    }

    public String getQuestionText() {
        return questionPanel.getQuestionDescriptionPanel().getQuestionText();
    }

    public void setQuestionText(String questionText) {
        questionPanel.getQuestionDescriptionPanel().setQuestionText(questionText);
    }

    public List<String> getAnswerTexts() {
        return questionPanel.getAnswersPanel().getAnswerTexts();
    }

    public void setAnswerTexts(List<String> texts) {
        questionPanel.getAnswersPanel().setAnswerTexts(texts);
    }

    public List<Boolean> getCheckedStates() {
        return questionPanel.getAnswersPanel().getCheckedStates();
    }

    public void setCheckedStates(List<Boolean> checks) {
        questionPanel.getAnswersPanel().setCheckedStates(checks);
    }

    public void setAnswersEditable(boolean editable) {
        questionPanel.getAnswersPanel().setCheckboxEditable(editable);
        questionPanel.getAnswersPanel().setTextFieldsEditable(editable);
    }

    public QuestionPanel getQuestionPanel() {
        return questionPanel;
    }
}
