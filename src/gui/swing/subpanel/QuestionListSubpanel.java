package gui.swing.subpanel;

import gui.swing.UIStyleUtil;
import gui.swing.components.DarkComboBox;
import service.dto.QuestionDTO;
import service.dto.TopicDTO;
import gui.swing.subpanel.QuestionDescriptionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuestionListSubpanel extends JPanel {
    private JList<QuestionDTO> questionList;
    private DefaultListModel<QuestionDTO> listModel;
    private DarkComboBox<TopicDTO> questionComboBox;

    public QuestionListSubpanel() {
        setLayout(new BorderLayout());
        UIStyleUtil.stylePanel(this);

        // Initialize the combo box for TopicDTO objects
        questionComboBox = new DarkComboBox<>();
        questionComboBox.setPreferredSize(new Dimension(220, 30));

        // Custom renderer to display topic titles and handle the "Please select" prompt
        questionComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

                if (value == null) {
                    setText("Please select a topic");
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

        // Create a top panel to hold the combo box with left alignment and padding
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        UIStyleUtil.stylePanel(topPanel);
        topPanel.add(questionComboBox);

        add(topPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        questionList = new JList<>(listModel);
        UIStyleUtil.styleList(questionList);

        // Custom renderer to display question titles in the list
        questionList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof QuestionDTO) {
                    QuestionDTO question = (QuestionDTO) value;
                    setText(question.getTitle());
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(questionList);
        UIStyleUtil.styleScrollPane(scrollPane, "Questions");

        add(scrollPane, BorderLayout.CENTER);

        // Initialize with empty selection (null = "Please select a topic")
        questionComboBox.addItem(null);
        questionComboBox.setSelectedIndex(0);
    }

    // Method to load topics into the combo box
    public void loadTopics(List<TopicDTO> topics) {
        questionComboBox.removeAllItems();
        questionComboBox.addItem(null); // "Please select a topic" item
        for (TopicDTO topic : topics) {
            questionComboBox.addItem(topic);
        }
        questionComboBox.setSelectedIndex(0);
    }

    // Method to populate the question list with QuestionDTO objects
    public void setQuestions(List<QuestionDTO> questions) {
        listModel.clear();
        for (QuestionDTO question : questions) {
            listModel.addElement(question);
        }
    }

    // Method to get the ID of the currently selected question
    public int getSelectedQuestionId() {
        QuestionDTO selectedQuestion = questionList.getSelectedValue();
        return selectedQuestion != null ? selectedQuestion.getId() : -1;
    }

    // Method to clear the current selection
    public void clearSelection() {
        questionList.clearSelection();
    }

    public DefaultListModel<QuestionDTO> getListModel() {
        return listModel;
    }

    public JList<QuestionDTO> getQuestionList() {
        return questionList;
    }

    // Return the topic filter combo box (replaces the old getTopicComboBox)
    public DarkComboBox<TopicDTO> getQuestionComboBox() {
        return questionComboBox;
    }

    // Method to set up the question selection listener
    public void setQuestionSelectionListener(QuestionDescriptionPanel questionDescriptionPanel) {
        questionList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Only handle final selection
                QuestionDTO selectedQuestion = questionList.getSelectedValue();
                if (selectedQuestion != null) {
                    // Update the question details in the description panel
                    questionDescriptionPanel.setQuestionDetails(selectedQuestion);

                    // Find and set the topic name based on the question's topic ID
                    TopicDTO selectedTopic = findTopicById(selectedQuestion.getTopicId());
                    if (selectedTopic != null) {
                        questionDescriptionPanel.setTopicName(selectedTopic.getTitle());
                        // Also update the combo box selection
                        questionComboBox.setSelectedItem(selectedTopic);
                    }
                } else {
                    // Clear fields when no question is selected
                    questionDescriptionPanel.clearInputs();
                    questionComboBox.setSelectedIndex(0); // Select "Please select a topic"
                }
            }
        });
    }

    // Helper method to find topic by ID from the combo box items
    private TopicDTO findTopicById(int topicId) {
        for (int i = 0; i < questionComboBox.getItemCount(); i++) {
            TopicDTO topic = questionComboBox.getItemAt(i);
            if (topic != null && topic.getId() == topicId) {
                return topic;
            }
        }
        return null;
    }
}
