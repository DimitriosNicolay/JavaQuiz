package gui.swing.subpanel;

import gui.swing.UIStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class QuestionListSubpanel extends JPanel {

    private JList<String> questionList;
    private DefaultListModel<String> listModel;
    private Map<String, Integer> titleToIdMap = new HashMap<>(); // Map question title to ID

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

    public void setQuestions(List<persistence.dto.QuestionDTO> questions) {
        listModel.clear();
        titleToIdMap.clear();
        for (persistence.dto.QuestionDTO question : questions) {
            String title = question.getDescription();
            listModel.addElement(title);
            titleToIdMap.put(title, question.getId());
        }
    }

    public int getSelectedQuestionId() {
        String selectedTitle = questionList.getSelectedValue();
        if (selectedTitle == null) return -1;
        return titleToIdMap.getOrDefault(selectedTitle, -1);
    }

    public JList<String> getQuestionList() {
        return questionList;
    }

    public void clearSelection() {
        questionList.clearSelection();
    }
}
