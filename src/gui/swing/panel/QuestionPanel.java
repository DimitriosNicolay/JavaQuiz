package gui.swing.panel;

import gui.swing.UIStyleUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class QuestionPanel extends JPanel{
    private JList<String> questionList;
    private DefaultListModel<String> listModel;


    public QuestionPanel() {
        setLayout(new BorderLayout());
        UIStyleUtil.stylePanel(this);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Right dropdown menu with topics

        // Right panel with list containing the questions
        listModel = new DefaultListModel<>();
        questionList = new JList<>(listModel);
        UIStyleUtil.styleList(questionList);

        JScrollPane listScrollPane = new JScrollPane(questionList);
        UIStyleUtil.styleScrollPane(listScrollPane, "Questions");
        // Top left panel containing topic, question name and question

        // Bottom Left panel containing 4 possible answers and checkboxes to mark the correct one
    }
}
