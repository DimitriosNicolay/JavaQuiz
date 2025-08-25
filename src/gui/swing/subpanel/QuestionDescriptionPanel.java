package gui.swing.subpanel;

import persistence.dto.QuestionDTO;

import javax.swing.*;
import java.awt.*;

public class QuestionDescriptionPanel extends JPanel {

    private JTextField questionNameField;
    private JTextArea questionDescriptionArea;
    private JTextField topicNameField;

    public QuestionDescriptionPanel() {
        setLayout(new BorderLayout());

        topicNameField = new JTextField(30);

        JLabel nameLabel = new JLabel("Question Name:");
        questionNameField = new JTextField(30);

        JLabel descriptionLabel = new JLabel("Description:");
        questionDescriptionArea = new JTextArea(5, 30);
        questionDescriptionArea.setLineWrap(true);
        questionDescriptionArea.setWrapStyleWord(true);

        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(questionNameField, BorderLayout.CENTER);

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(new JScrollPane(questionDescriptionArea), BorderLayout.CENTER);

        add(namePanel, BorderLayout.NORTH);
        add(descriptionPanel, BorderLayout.CENTER);
    }

    public QuestionDTO getQuestionFromInputs() {
        QuestionDTO dto = new QuestionDTO();
        dto.setTitle(questionNameField.getText().trim());
        dto.setDescription(questionDescriptionArea.getText().trim());
        // set topicId if you have input for it, or elsewhere
        return dto;
    }

    public void setQuestionDetails(QuestionDTO question) {
        if (question != null) {
            questionNameField.setText(question.getTitle());
            questionDescriptionArea.setText(question.getDescription());
        } else {
            clearInputs();
        }
    }

    public void setEditable(boolean editable) {
        questionNameField.setEditable(editable);
        questionDescriptionArea.setEditable(editable);
    }


    public void clearInputs() {
        questionNameField.setText("");
        questionDescriptionArea.setText("");
    }

    public String getTopicName() {
        return topicNameField.getText();
    }

    public void setTopicName(String topicName) {
        topicNameField.setText(topicName);
    }

    public String getQuestionName() {
        return questionNameField.getText();
    }

    public void setQuestionName(String questionName) {
        questionNameField.setText(questionName);
    }

    public String getQuestionText() {
        return questionDescriptionArea.getText();
    }

    public void setQuestionText(String text) {
        questionDescriptionArea.setText(text);
    }


}
