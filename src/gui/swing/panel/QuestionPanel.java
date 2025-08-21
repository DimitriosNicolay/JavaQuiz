package gui.swing.panel;

import gui.swing.UIStyleUtil;
import gui.swing.components.ButtonPanel;
import gui.swing.subpanel.AnswersPanel;
import gui.swing.subpanel.QuestionListSubpanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class QuestionPanel extends JPanel {
    private GUI.Swing.QuestionDescriptionPanel questionDescriptionPanel;
    private AnswersPanel answersPanel;
    private QuestionListSubpanel questionListPanel;
    private ButtonPanel buttonPanel;

    public QuestionPanel() {

        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(600, 400));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Style main panel
        UIStyleUtil.stylePanel(this);

        // Initialize subpanels
        questionDescriptionPanel = new GUI.Swing.QuestionDescriptionPanel("Default Topic");

        List<String> initialAnswerLabels = Arrays.asList("Answer 1:", "Answer 2:", "Answer 3:", "Answer 4:");
        answersPanel = new AnswersPanel(initialAnswerLabels);

        questionListPanel = new QuestionListSubpanel();
        questionListPanel.setPreferredSize(new Dimension(200, 0));

        buttonPanel = new ButtonPanel(
                Arrays.asList("Delete", "Save", "New"),
                Arrays.asList(ButtonPanel.ButtonType.RED, ButtonPanel.ButtonType.NORMAL, ButtonPanel.ButtonType.GREEN)
        );

        // Center panel: question description on top, answers below
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        UIStyleUtil.stylePanel(centerPanel);

        centerPanel.setMinimumSize(new Dimension(300, 200));
        questionListPanel.setMinimumSize(new Dimension(150, 0));



        // Add subpanels to centerpanel
        centerPanel.add(questionDescriptionPanel, BorderLayout.NORTH);
        centerPanel.add(answersPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);




        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                centerPanel,
                questionListPanel
        );
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerSize(4);
        splitPane.setBorder(null);
        splitPane.setBackground(UIStyleUtil.BACKGROUND_COLOR);

        // Style the divider
        splitPane.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    {
                        setBackground(UIStyleUtil.BACKGROUND_COLOR);
                    }
                    public void setBorder(Border b) {}
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(UIStyleUtil.BACKGROUND_COLOR);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        g.setColor(new Color(80, 80, 80));
                        g.fillRect(0, 0, getSize().width, getSize().height);
                    }
                };
            }
        });

        add(splitPane, BorderLayout.CENTER);
    }

    // Delegate all getters/setters to QuestionDescriptionPanel

    public String getTopicName() {
        return questionDescriptionPanel.getTopicName();
    }

    public void setTopicName(String topicName) {
        questionDescriptionPanel.setTopicName(topicName);
    }

    public String getQuestionName() {
        return questionDescriptionPanel.getQuestionName();
    }

    public void setQuestionName(String questionName) {
        questionDescriptionPanel.setQuestionName(questionName);
    }

    public String getQuestionText() {
        return questionDescriptionPanel.getQuestionText();
    }

    public void setQuestionText(String questionText) {
        questionDescriptionPanel.setQuestionText(questionText);
    }

    // Delegate answer methods

    public List<String> getAnswerTexts() {
        return answersPanel.getAnswerTexts();
    }

    public void setAnswerTexts(List<String> texts) {
        answersPanel.setAnswerTexts(texts);
    }

    public List<Boolean> getCheckedStates() {
        return answersPanel.getCheckedStates();
    }

    public void setCheckedStates(List<Boolean> checks) {
        answersPanel.setCheckedStates(checks);
    }

    public void setAnswersEditable(boolean editable) {
        answersPanel.setEditable(editable);
    }

    // Expose subpanels for further customizations if needed

    public GUI.Swing.QuestionDescriptionPanel getQuestionDescriptionPanel() {
        return questionDescriptionPanel;
    }

    public AnswersPanel getAnswersPanel() {
        return answersPanel;
    }

    public QuestionListSubpanel getQuestionListPanel() {
        return questionListPanel;
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }
}
