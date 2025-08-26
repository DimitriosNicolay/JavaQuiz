package gui.swing.subpanel;

import service.dto.QuestionDTO;
import gui.swing.UIStyleUtil;

import javax.swing.*;
import java.awt.*;

public class QuestionDescriptionPanel extends JPanel {

    private JTextField questionNameField;
    private JTextArea questionDescriptionArea;
    private JTextField topicNameField;

    public QuestionDescriptionPanel() {
        setLayout(new BorderLayout(10, 10));  // spacing between components

        UIStyleUtil.stylePanel(this);          // style panel with background color

        // Create and style topic field with titled border
        topicNameField = new JTextField(30);
        UIStyleUtil.styleTextField(topicNameField, "Topic Name");

        // Create and style question name field with titled border
        questionNameField = new JTextField(30);
        UIStyleUtil.styleTextField(questionNameField, "Question Name");

        // Create and style question description area, wrapped in scroll pane with titled border
        questionDescriptionArea = new JTextArea(5, 30);
        questionDescriptionArea.setLineWrap(true);
        questionDescriptionArea.setWrapStyleWord(true);
        UIStyleUtil.styleTextArea(questionDescriptionArea);

        JScrollPane descriptionScrollPane = new JScrollPane(questionDescriptionArea);
        UIStyleUtil.styleScrollPane(descriptionScrollPane, "Description");

        // Organize inputs in vertical box layout with spacing
        Box contentBox = Box.createVerticalBox();
        contentBox.add(topicNameField);
        contentBox.add(Box.createVerticalStrut(10));
        contentBox.add(questionNameField);
        contentBox.add(Box.createVerticalStrut(10));
        contentBox.add(descriptionScrollPane);

        add(contentBox, BorderLayout.CENTER);
    }

    public QuestionDTO getQuestionFromInputs() {
        QuestionDTO dto = new QuestionDTO();
        dto.setTitle(questionNameField.getText().trim());
        dto.setDescription(questionDescriptionArea.getText().trim());
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
        topicNameField.setEditable(editable);
    }

    public void clearInputs() {
        questionNameField.setText("");
        questionDescriptionArea.setText("");
        topicNameField.setText("");
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
