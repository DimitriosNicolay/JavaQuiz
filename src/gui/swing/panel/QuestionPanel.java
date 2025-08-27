package gui.swing.panel;

import gui.swing.UIStyleUtil;
import gui.swing.components.ButtonPanel;
import gui.swing.components.DarkComboBox;
import gui.swing.subpanel.AnswersPanel;
import gui.swing.subpanel.QuestionListSubpanel;
import gui.swing.subpanel.QuestionDescriptionPanel;
import service.dto.TopicDTO;

import javax.swing.*;
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

    public QuestionPanel() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(600, 400));
        UIStyleUtil.stylePanel(this);

        // Initialize subcomponents
        questionListPanel = new QuestionListSubpanel();
        questionListPanel.setPreferredSize(new Dimension(200, 0));
        questionListPanel.setMinimumSize(new Dimension(150, 0));

        questionDescriptionPanel = new QuestionDescriptionPanel();

        // Initialize answers panel with 4 answer fields by default
        List<String> initialAnswerLabels = Arrays.asList("Answer 1:", "Answer 2:", "Answer 3:", "Answer 4:");
        answersPanel = new AnswersPanel(initialAnswerLabels);
        answersPanel.setTextFieldsEditable(true);
        answersPanel.setCheckboxEditable(true);

        // Initialize button panel with Delete, Save, and New buttons
        buttonPanel = new ButtonPanel(
                Arrays.asList("Delete", "Save", "New"),
                Arrays.asList(ButtonPanel.ButtonType.RED, ButtonPanel.ButtonType.NORMAL, ButtonPanel.ButtonType.GREEN)
        );

        // Create center panel to hold question description, answers, and buttons
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        UIStyleUtil.stylePanel(centerPanel);
        centerPanel.setMinimumSize(new Dimension(300, 200));

        centerPanel.add(questionDescriptionPanel, BorderLayout.NORTH);
        centerPanel.add(answersPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create right panel to hold the question list (with topic filter)
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        UIStyleUtil.stylePanel(rightPanel);
        rightPanel.add(questionListPanel, BorderLayout.CENTER); // Add the question list panel

        // Create split pane to separate center panel and question list panel
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
                            g.fillRect((getWidth() - 2) / 2, 0, 2, getHeight());
                        } else {
                            g.fillRect(0, (getHeight() - 2) / 2, getWidth(), 2);
                        }
                    }
                };
            }
        });
        add(splitPane, BorderLayout.CENTER);
    }

    // Getters for subcomponents
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

    // Delegate method to maintain compatibility with existing delegation code
    public DarkComboBox<TopicDTO> getTopicComboBox() {
        return questionListPanel.getQuestionComboBox();
    }
}
