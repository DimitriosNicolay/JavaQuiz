package gui.swing.delegations;

import gui.swing.panel.QuestionPanel;
import service.dto.QuestionDTO;
import service.dto.TopicDTO;
import service.QuestionService;
import service.TopicService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class QuestionPanelDelegation {

    private final QuestionPanel questionPanel;
    private final QuestionService questionService;
    private final TopicService topicService;
    private boolean isUpdatingComboBox = false; // Flag to prevent listener conflicts

    public QuestionPanelDelegation(QuestionPanel panel, QuestionService questionService, TopicService topicService) {
        this.questionPanel = panel;
        this.questionService = questionService;
        this.topicService = topicService;
        populateTopicComboBox();
        setupListeners();
        loadQuestions();
    }

    // Populate the topic combo box with topics from the database
    private void populateTopicComboBox() {
        try {
            List<TopicDTO> topics = topicService.getAllTopics();
            questionPanel.getQuestionListPanel().loadTopics(topics);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(questionPanel, "Failed to load topics: " + e.getMessage());
        }
    }

    // Setup listeners for UI components
    private void setupListeners() {
        questionPanel.getQuestionListPanel().getQuestionList()
                .addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            loadSelectedQuestion();
                        }
                    }
                });

        // Button listeners
        questionPanel.getButtonPanel().getButton("Save").addActionListener(e -> saveQuestion());
        questionPanel.getButtonPanel().getButton("New").addActionListener(e -> clearInputs());
        questionPanel.getButtonPanel().getButton("Delete").addActionListener(e -> deleteQuestion());

        // Topic filter combo box listener - check flag before executing
        questionPanel.getQuestionListPanel().getQuestionComboBox().addActionListener(e -> {
            if (!isUpdatingComboBox) {
                loadQuestions();
            }
        });
    }

    // Load questions based on selected topic
    private void loadQuestions() {
        try {
            TopicDTO selectedTopic = (TopicDTO) questionPanel.getQuestionListPanel().getQuestionComboBox().getSelectedItem();
            List<QuestionDTO> questions;
            if (selectedTopic == null) {
                questions = questionService.getAllQuestions();
            } else {
                questions = questionService.getQuestionsByTopic(selectedTopic.getId());
            }
            questionPanel.getQuestionListPanel().setQuestions(questions);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(questionPanel, "Failed to load questions: " + e.getMessage());
        }
    }

    // Load details of the selected question into the UI
    private void loadSelectedQuestion() {
        QuestionDTO selectedQuestion = questionPanel.getQuestionListPanel().getQuestionList().getSelectedValue();

        if (selectedQuestion == null) {
            clearInputs();
            return;
        }

        try {
            // Load complete question details from database
            QuestionDTO question = questionService.getQuestionById(selectedQuestion.getId());
            if (question != null) {
                // Set question details in the description panel
                questionPanel.getQuestionDescriptionPanel().setQuestionDetails(question);

                // Load answers for the question
                List<String> answers = questionService.getAnswersForQuestion(selectedQuestion.getId());
                questionPanel.getAnswersPanel().setAnswers(answers);

                // Find and set the topic name based on the question's topic ID
                TopicDTO selectedTopic = findTopicById(question.getTopicId());
                if (selectedTopic != null) {
                    questionPanel.getQuestionDescriptionPanel().setTopicName(selectedTopic.getTitle());

                    // Set flag to prevent combo box listener from firing
                    isUpdatingComboBox = true;
                    questionPanel.getQuestionListPanel().getQuestionComboBox().setSelectedItem(selectedTopic);
                    isUpdatingComboBox = false;
                } else {
                    questionPanel.getQuestionDescriptionPanel().setTopicName("");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(questionPanel, "Failed to load question details: " + e.getMessage());
        }
    }

    // Helper method to find topic by ID from the combo box items
    private TopicDTO findTopicById(int topicId) {
        JComboBox<TopicDTO> comboBox = questionPanel.getQuestionListPanel().getQuestionComboBox();
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            TopicDTO topic = comboBox.getItemAt(i);
            if (topic != null && topic.getId() == topicId) {
                return topic;
            }
        }
        return null;
    }

    // Save or update question based on current inputs
    private void saveQuestion() {
        try {
            QuestionDTO question = collectQuestionFromUI();
            boolean success = questionService.saveQuestion(question);
            if (success) {
                JOptionPane.showMessageDialog(questionPanel, "Question saved successfully.");
                loadQuestions();
                clearInputs();
            } else {
                JOptionPane.showMessageDialog(questionPanel, "Failed to save question.");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(questionPanel, "Validation error: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(questionPanel, "Database error: " + e.getMessage());
        }
    }

    // Collect question data from UI inputs
    private QuestionDTO collectQuestionFromUI() {
        QuestionDTO question = questionPanel.getQuestionDescriptionPanel().getQuestionFromInputs();
        TopicDTO selectedTopic = (TopicDTO) questionPanel.getQuestionListPanel().getQuestionComboBox().getSelectedItem();
        if (selectedTopic != null) {
            question.setTopicId(selectedTopic.getId());
        }
        int selectedId = questionPanel.getQuestionListPanel().getSelectedQuestionId();
        if (selectedId > 0) {
            question.setId(selectedId);
        }
        return question;
    }

    // Clear all input fields and selections
    private void clearInputs() {
        questionPanel.getQuestionDescriptionPanel().clearInputs();
        questionPanel.getAnswersPanel().clearInputs();
        questionPanel.getQuestionListPanel().clearSelection();

        // Set flag to prevent combo box listener from firing
        isUpdatingComboBox = true;
        questionPanel.getQuestionListPanel().getQuestionComboBox().setSelectedIndex(0); // Reset to "Please select a topic"
        isUpdatingComboBox = false;
    }

    // Delete the selected question
    private void deleteQuestion() {
        int selectedId = questionPanel.getQuestionListPanel().getSelectedQuestionId();
        if (selectedId <= 0) return;

        int confirm = JOptionPane.showConfirmDialog(questionPanel,
                "Are you sure you want to delete this question?",
                "Delete Question",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = questionService.deleteQuestion(selectedId);
                if (success) {
                    JOptionPane.showMessageDialog(questionPanel, "Question deleted successfully.");
                    loadQuestions();
                    clearInputs();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(questionPanel, "Failed to delete question: " + e.getMessage());
            }
        }
    }
}
