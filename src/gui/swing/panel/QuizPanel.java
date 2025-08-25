package gui.swing.panel;

import gui.swing.UIStyleUtil;
import gui.swing.components.ButtonPanel;
import gui.swing.subpanel.AnswersPanel;
import gui.swing.subpanel.AnswerStatusPanel;
import gui.swing.subpanel.QuestionDescriptionPanel;

import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class QuizPanel extends JPanel {

    private final QuestionDescriptionPanel questionDescriptionPanel;
    private final AnswersPanel answersPanel;
    private final AnswerStatusPanel answerStatusPanel;
    private final ButtonPanel buttonPanel;

    public QuizPanel() {
        UIStyleUtil.stylePanel(this);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 600));

        questionDescriptionPanel = new QuestionDescriptionPanel();
        questionDescriptionPanel.setEditable(false);

        List<String> initialLabels = Arrays.asList("Answer 1:", "Answer 2:", "Answer 3:", "Answer 4:");
        answersPanel = new AnswersPanel(initialLabels);

        // Allow users to click checkboxes for selecting answers
        answersPanel.setCheckboxEditable(true);

        // Keep answer texts read-only during quiz
        answersPanel.setTextFieldsEditable(false);

        answerStatusPanel = new AnswerStatusPanel();

        buttonPanel = new ButtonPanel(
                Arrays.asList("Previous", "Next", "Submit"),
                Arrays.asList(ButtonPanel.ButtonType.NORMAL, ButtonPanel.ButtonType.NORMAL, ButtonPanel.ButtonType.GREEN)
        );

        // Left side panel: question + answers + buttons
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        UIStyleUtil.stylePanel(leftPanel);
        leftPanel.add(questionDescriptionPanel, BorderLayout.NORTH);
        leftPanel.add(answersPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
        leftPanel.setMinimumSize(new Dimension(400, 600));

        answerStatusPanel.setPreferredSize(new Dimension(280, 0));
        answerStatusPanel.setMinimumSize(new Dimension(280, 600));

        // Create horizontal split pane with custom styled divider
        JSplitPane splitPane = getJSplitPane(leftPanel);

        add(splitPane, BorderLayout.CENTER);
    }

    private JSplitPane getJSplitPane(JPanel leftPanel) {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, answerStatusPanel);
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerSize(7);
        splitPane.setDividerLocation(550); // initial divider location in pixels
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setBorder(null);

        splitPane.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(UIStyleUtil.BACKGROUND_COLOR);
                        g.fillRect(0, 0, getWidth(), getHeight());

                        if (getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
                            g.setColor(new Color(80, 80, 80));
                            int x = (getWidth() - 4) / 2;
                            g.fillRect(x, 0, 4, getHeight());
                        } else {
                            g.setColor(new Color(80, 80, 80));
                            int y = (getHeight() - 4) / 2;
                            g.fillRect(0, y, getWidth(), 4);
                        }
                    }
                };
            }
        });
        return splitPane;
    }

    // --- Public getter/setter methods for delegation ---

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

    public List<String> getAnswerTexts() {
        return answersPanel.getAnswerTexts();
    }

    public void setAnswerTexts(List<String> texts) {
        answersPanel.setAnswers(texts);
    }

    public List<Boolean> getCheckedStates() {
        return answersPanel.getCheckedStates();
    }

    public void setCheckedStates(List<Boolean> checks) {
        answersPanel.setCheckedStates(checks);
    }

    public void setAnswered(boolean correct) {
        answerStatusPanel.setQuestionResult(correct);
    }

    public void setAnswerExplanation(String explanation) {
        answerStatusPanel.setExplanationText(explanation);
    }

    public void setCorrectAnswer(String answer) {
        answerStatusPanel.setCorrectAnswerText(answer);
    }

    // Accessors for sub-panels (optional)

    public QuestionDescriptionPanel getQuestionDescriptionPanel() {
        return questionDescriptionPanel;
    }

    public AnswersPanel getAnswersPanel() {
        return answersPanel;
    }

    public AnswerStatusPanel getAnswerStatusPanel() {
        return answerStatusPanel;
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }
}
