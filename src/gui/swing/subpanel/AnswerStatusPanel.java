package gui.swing.subpanel;

import gui.swing.UIStyleUtil;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class AnswerStatusPanel extends JPanel {

    private JLabel correctAnswerLabel;
    private JTextArea explanationArea;
    private JLabel questionResultLabel;

    public AnswerStatusPanel() {
        setLayout(new BorderLayout());
        UIStyleUtil.stylePanel(this);
        setMinimumSize(new Dimension(600,400));

        // Panel for the top part (correct answer + result)
        JPanel topPanel = new JPanel(new BorderLayout());
        setMinimumSize(new Dimension(800,600));
        topPanel.setBackground(UIStyleUtil.BACKGROUND_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        correctAnswerLabel = new JLabel("The correct answer is: ");
        correctAnswerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        correctAnswerLabel.setForeground(UIStyleUtil.TEXT_COLOR);
        correctAnswerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topPanel.add(correctAnswerLabel, BorderLayout.NORTH);

        questionResultLabel = new JLabel("", SwingConstants.CENTER);
        questionResultLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        questionResultLabel.setForeground(UIStyleUtil.TEXT_COLOR);
        topPanel.add(questionResultLabel, BorderLayout.CENTER);

        // Explanation text area setup with styling
        explanationArea = new JTextArea();
        explanationArea.setEditable(false);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        explanationArea.setForeground(UIStyleUtil.TEXT_COLOR);
        explanationArea.setBackground(UIStyleUtil.COMPONENT_COLOR);
        explanationArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane explanationScrollPane = new JScrollPane(explanationArea);
        // Use the same styled border as defined in your UIStyleUtil for scroll panes
        UIStyleUtil.styleScrollPane(explanationScrollPane, "Explanation");
        setMinimumSize(new Dimension(800,600));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(UIStyleUtil.BACKGROUND_COLOR);

        // Add topPanel (answers summary)
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // fixed height for answer/result
        mainPanel.add(topPanel);

        // Add explanationScrollPane (explanation text)
        mainPanel.add(explanationScrollPane);

        add(mainPanel, BorderLayout.CENTER);

        // Overall border matching other UI panels
        Border mainBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(UIStyleUtil.TEXT_COLOR),
                        "Answer",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("SansSerif", Font.PLAIN, 12),
                        UIStyleUtil.TEXT_COLOR
                ),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
        setBorder(mainBorder);
    }

    public void setCorrectAnswerText(String text) {
        correctAnswerLabel.setText("The correct answer is: " + text);
    }

    public void setExplanationText(String text) {
        explanationArea.setText(text);
        explanationArea.setCaretPosition(0);
    }

    public void setQuestionResult(boolean correct) {
        if (correct) {
            questionResultLabel.setText("Correct");
            questionResultLabel.setForeground(UIStyleUtil.GREEN_COLOR);
        } else {
            questionResultLabel.setText("Incorrect");
            questionResultLabel.setForeground(UIStyleUtil.RED_COLOR);
        }
    }
}
