package gui.swing.panel;

import gui.swing.UIStyleUtil;
import gui.swing.components.ButtonPanel;
import gui.swing.components.DarkComboBox;
import gui.swing.subpanel.AnswersPanel;
import gui.swing.subpanel.QuestionListSubpanel;
import gui.swing.subpanel.QuestionDescriptionPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class QuestionPanel extends JPanel {

    private final QuestionDescriptionPanel questionDescriptionPanel;
    private final AnswersPanel answersPanel;
    private final QuestionListSubpanel questionListPanel;
    private final ButtonPanel buttonPanel;
    private final DarkComboBox topicComboBox;  // Add DarkComboBox here

    public QuestionPanel() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(600, 400));
        UIStyleUtil.stylePanel(this);

        questionListPanel = new QuestionListSubpanel();
        questionListPanel.setPreferredSize(new Dimension(200, 0));

        questionDescriptionPanel = new QuestionDescriptionPanel();

        List<String> initialAnswerLabels = Arrays.asList("Answer 1:", "Answer 2:", "Answer 3:", "Answer 4:");
        answersPanel = new AnswersPanel(initialAnswerLabels);
        answersPanel.setTextFieldsEditable(true);
        answersPanel.setCheckboxEditable(true);

        buttonPanel = new ButtonPanel(
                Arrays.asList("Delete", "Save", "New"),
                Arrays.asList(ButtonPanel.ButtonType.RED, ButtonPanel.ButtonType.NORMAL, ButtonPanel.ButtonType.GREEN)
        );

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        UIStyleUtil.stylePanel(centerPanel);
        centerPanel.setMinimumSize(new Dimension(300, 200));
        questionListPanel.setMinimumSize(new Dimension(150, 0));

        centerPanel.add(questionDescriptionPanel, BorderLayout.NORTH);
        centerPanel.add(answersPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create panel to hold the topic combo box above the question list
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout(5,5));
        UIStyleUtil.stylePanel(rightPanel);

        topicComboBox = new DarkComboBox<>();
        topicComboBox.setPreferredSize(new Dimension(200, 30));
        rightPanel.add(topicComboBox, BorderLayout.NORTH);
        rightPanel.add(questionListPanel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, centerPanel, rightPanel);
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);
        splitPane.setBackground(UIStyleUtil.BACKGROUND_COLOR);
        splitPane.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    {
                        setBackground(UIStyleUtil.BACKGROUND_COLOR);
                    }
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(UIStyleUtil.BACKGROUND_COLOR);
                        g.fillRect(0, 0, getWidth(), getHeight());
                        g.setColor(new Color(80, 80, 80));
                        if (getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
                            g.fillRect((getWidth() - 8) / 2, 0, 8, getHeight());
                        } else {
                            g.fillRect(0, (getHeight() - 8) / 2, getWidth(), 8);
                        }
                    }
                };
            }
        });
        add(splitPane, BorderLayout.CENTER);
    }

    public QuestionDescriptionPanel getQuestionDescriptionPanel() {
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

    public DarkComboBox getTopicComboBox() {
        return topicComboBox;
    }
}
