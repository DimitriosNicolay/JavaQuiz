package gui.swing.subpanel;

import gui.swing.UIStyleUtil;

import javax.swing.*;
import java.awt.*;

public class QuestionListSubpanel extends JPanel {
    private JList<String> questionList;
    private DefaultListModel<String> listModel;

    public QuestionListSubpanel() {
        setLayout(new BorderLayout());
        UIStyleUtil.stylePanel(this);

        listModel = new DefaultListModel<>();
        questionList = new JList<>(listModel);
        UIStyleUtil.styleList(questionList);

        JScrollPane scrollPane = new JScrollPane(questionList);
        UIStyleUtil.styleScrollPane(scrollPane, "Questions");

        add(scrollPane, BorderLayout.CENTER);
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    public JList<String> getQuestionList() {
        return questionList;
    }
}
