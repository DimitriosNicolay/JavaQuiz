package gui.swing.delegations;

import gui.swing.panel.QuestionPanel;
import persistence.dto.QuestionDTO;
import persistence.dto.TopicDTO;
import service.QuestionService;
import service.TopicService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.SQLException;
import java.util.List;

public class QuestionPanelDelegation {

    private final QuestionPanel questionPanel;
    private final QuestionService questionService;
    private final TopicService topicService;  // Add topic service

    public QuestionPanelDelegation(QuestionPanel panel, QuestionService questionService, TopicService topicService) {
        this.questionPanel = panel;
        this.questionService = questionService;
        this.topicService = topicService;

        populateTopicComboBox();
        setupListeners();
        loadQuestions();
    }

    private void populateTopicComboBox() {
        try {
            List<TopicDTO> topics = topicService.getAllTopics();
            DefaultComboBoxModel<TopicDTO> model = new DefaultComboBoxModel<>();
            for (TopicDTO topic : topics) {
                model.addElement(topic);
            }
            questionPanel.getTopicComboBox().setModel(model);
            if (!topics.isEmpty()) {
                questionPanel.getTopicComboBox().setSelectedIndex(0); // select first by default
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(questionPanel, "Failed to load topics: " + e.getMessage());
        }
    }

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

        questionPanel.getButtonPanel().getButton("Save").addActionListener(e -> saveQuestion());
        questionPanel.getButtonPanel().getButton("New").addActionListener(e -> clearInputs());
        questionPanel.getButtonPanel().getButton("Delete").addActionListener(e -> deleteQuestion());

        // Add listener to topic combo box to reload questions when selection changes
        questionPanel.getTopicComboBox().addActionListener(e -> loadQuestions());
    }

    private void loadQuestions() {
        try {
            TopicDTO selectedTopic = (TopicDTO) questionPanel.getTopicComboBox().getSelectedItem();
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

    private void loadSelectedQuestion() {
        int selectedId = questionPanel.getQuestionListPanel().getSelectedQuestionId();
        if (selectedId <= 0) {
            clearInputs();
            return;
        }
        try {
            QuestionDTO question = questionService.getQuestionById(selectedId);
            if (question != null) {
                questionPanel.getQuestionDescriptionPanel().setQuestionDetails(question);
                List<String> answers = questionService.getAnswersForQuestion(selectedId);
                questionPanel.getAnswersPanel().setAnswers(answers);

                // Set topic combo box selection according to question topicId
                TopicDTO selectedTopic = (TopicDTO) questionPanel.getTopicComboBox().getSelectedItem();
                if (selectedTopic == null || selectedTopic.getId() != question.getTopicId()) {
                    DefaultComboBoxModel<TopicDTO> model = (DefaultComboBoxModel<TopicDTO>) questionPanel.getTopicComboBox().getModel();
                    for (int i = 0; i < model.getSize(); i++) {
                        TopicDTO topic = model.getElementAt(i);
                        if (topic.getId() == question.getTopicId()) {
                            questionPanel.getTopicComboBox().setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(questionPanel, "Failed to load question details: " + e.getMessage());
        }
    }

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

    private QuestionDTO collectQuestionFromUI() {
        QuestionDTO question = questionPanel.getQuestionDescriptionPanel().getQuestionFromInputs();
        TopicDTO selectedTopic = (TopicDTO) questionPanel.getTopicComboBox().getSelectedItem();
        if (selectedTopic != null) {
            question.setTopicId(selectedTopic.getId());
        }
        int selectedId = questionPanel.getQuestionListPanel().getSelectedQuestionId();
        if (selectedId > 0) {
            question.setId(selectedId);
        }
        return question;
    }

    private void clearInputs() {
        questionPanel.getQuestionDescriptionPanel().clearInputs();
        questionPanel.getAnswersPanel().clearInputs();
        questionPanel.getQuestionListPanel().clearSelection();
    }

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
