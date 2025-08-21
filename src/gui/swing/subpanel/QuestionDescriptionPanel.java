package gui.swing.subpanel;

import gui.swing.UIStyleUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class QuestionDescriptionPanel extends JPanel {
    private JLabel topicNameLabel;
    private JTextField questionNameField;
    private JTextArea questionTextArea;

    public QuestionDescriptionPanel(String initialTopic) {
        setLayout(new BorderLayout(10, 10));
        UIStyleUtil.stylePanel(this);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        topicNameLabel = new JLabel(initialTopic);
        topicNameLabel.setForeground(UIStyleUtil.TEXT_COLOR);
        topicNameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(topicNameLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        UIStyleUtil.stylePanel(centerPanel);

        questionNameField = new JTextField();
        UIStyleUtil.styleTextField(questionNameField, "Question Name");
        centerPanel.add(questionNameField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        questionTextArea = new JTextArea(4, 40);
        UIStyleUtil.styleTextArea(questionTextArea);
        JScrollPane scrollPane = new JScrollPane(questionTextArea);
        UIStyleUtil.styleScrollPane(scrollPane, "Full Question Text");
        centerPanel.add(scrollPane);

        add(centerPanel, BorderLayout.CENTER);
    }

    public String getTopicName() {
        return topicNameLabel.getText();
    }

    public void setTopicName(String topicName) {
        topicNameLabel.setText(topicName);
    }

    public String getQuestionName() {
        return questionNameField.getText();
    }

    public void setQuestionName(String questionName) {
        questionNameField.setText(questionName);
    }

    public String getQuestionText() {
        return questionTextArea.getText();
    }

    public void setQuestionText(String questionText) {
        questionTextArea.setText(questionText);
    }

    public void setEditable(boolean editable) {
        questionNameField.setEditable(editable);
        questionTextArea.setEditable(editable);
    }
}
