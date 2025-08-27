package gui.swing.subpanel;

import gui.swing.UIStyleUtil;
import gui.swing.components.DarkComboBox;
import service.dto.TopicDTO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class AnswerStatusPanel extends JPanel {

    private JLabel correctAnswerLabel;
    private JTextArea explanationArea;
    private JLabel questionResultLabel;
    private DarkComboBox<TopicDTO> topicComboBox;

    public AnswerStatusPanel() {
        setLayout(new BorderLayout());
        UIStyleUtil.stylePanel(this);
        setMinimumSize(new Dimension(600,400));

        // Create topic combo box
        topicComboBox = new DarkComboBox<>();
        topicComboBox.setPreferredSize(new Dimension(200, 30));

        // Custom renderer to display topic titles and handle the "All Topics" prompt
        topicComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

                if (value == null) {
                    setText("All Topics");
                    c.setForeground(new Color(150, 150, 150));
                } else if (value instanceof TopicDTO) {
                    TopicDTO topic = (TopicDTO) value;
                    setText(topic.getTitle());
                    c.setForeground(UIStyleUtil.TEXT_COLOR);
                } else {
                    c.setForeground(UIStyleUtil.TEXT_COLOR);
                }
                c.setBackground(isSelected ? new Color(80, 80, 80) : UIStyleUtil.COMPONENT_COLOR);
                return c;
            }
        });

        // Initialize with "All Topics" selected (null = all topics)
        topicComboBox.addItem(null);
        topicComboBox.setSelectedIndex(0);

        // Topic selection panel
        JPanel topicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        topicPanel.setBackground(UIStyleUtil.BACKGROUND_COLOR);
        topicPanel.setMinimumSize(new Dimension(250,40));

        topicPanel.add(topicComboBox);

        // Panel for the middle part (correct answer + result)
        JPanel middlePanel = new JPanel(new BorderLayout());
        setMinimumSize(new Dimension(800,600));
        middlePanel.setBackground(UIStyleUtil.BACKGROUND_COLOR);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        correctAnswerLabel = new JLabel("The correct answer is: ");
        correctAnswerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        correctAnswerLabel.setForeground(UIStyleUtil.TEXT_COLOR);
        correctAnswerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        middlePanel.add(correctAnswerLabel, BorderLayout.NORTH);

        questionResultLabel = new JLabel("", SwingConstants.CENTER);
        questionResultLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        questionResultLabel.setForeground(UIStyleUtil.TEXT_COLOR);
        middlePanel.add(questionResultLabel, BorderLayout.CENTER);

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

        // Add topic selection at the top
        topicPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        mainPanel.add(topicPanel);

        // Add some spacing
        mainPanel.add(Box.createVerticalStrut(5));

        // Add middlePanel (answers summary)
        middlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // fixed height for answer/result
        mainPanel.add(middlePanel);

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

    // Method to load topics into the combo box
    public void loadTopics(List<TopicDTO> topics) {
        topicComboBox.removeAllItems();
        topicComboBox.addItem(null); // "All Topics" item
        for (TopicDTO topic : topics) {
            topicComboBox.addItem(topic);
        }
        topicComboBox.setSelectedIndex(0);
    }

    // Get the selected topic (null means all topics)
    public TopicDTO getSelectedTopic() {
        return (TopicDTO) topicComboBox.getSelectedItem();
    }

    // Get the topic combo box for adding listeners
    public DarkComboBox<TopicDTO> getTopicComboBox() {
        return topicComboBox;
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
