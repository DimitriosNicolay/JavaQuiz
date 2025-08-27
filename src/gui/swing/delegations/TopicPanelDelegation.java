package gui.swing.delegations;

import service.dto.TopicDTO;
import gui.swing.panel.TopicPanel;
import service.TopicService;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.SQLException;
import java.util.List;

public class TopicPanelDelegation {

    private final TopicPanel topicPanel;
    private final TopicService topicService;

    private QuestionPanelDelegation questionPanelDelegation;
    private QuizPanelDelegation quizPanelDelegation;

    public TopicPanelDelegation(TopicPanel panel, TopicService service) {
        this.topicPanel = panel;
        this.topicService = service;
        setupListeners();
        loadAllTopics();
    }

    public void setQuestionPanelDelegation(QuestionPanelDelegation delegation) {
        this.questionPanelDelegation = delegation;
    }

    public void setQuizPanelDelegation(QuizPanelDelegation delegation) {
        this.quizPanelDelegation = delegation;
    }

    // Action listeners for buttons and list selection
    private void setupListeners() {
        topicPanel.getTopicList().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedTopic();
                }
            }
        });

        topicPanel.getDeleteButton().addActionListener(e -> deleteSelectedTopic());
        topicPanel.getSaveButton().addActionListener(e -> saveTopic());
        topicPanel.getNewButton().addActionListener(e -> topicPanel.clearInputs());
    }

    private void loadAllTopics() {
        try {
            List<TopicDTO> topics = topicService.getAllTopics();
            topicPanel.setTopics(topics);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(topicPanel, "Failed to load topics: " + e.getMessage());
        }
    }

    private void loadSelectedTopic() {
        int selectedId = topicPanel.getSelectedTopicId();
        try {
            TopicDTO topic = topicService.getTopicById(selectedId);
            if (topic != null) {
                topicPanel.setTopicDetails(topic);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(topicPanel, "Failed to load topic details: " + e.getMessage());
        }
    }

    private void deleteSelectedTopic() {
        int selectedId = topicPanel.getSelectedTopicId();
        if (selectedId <= 0) return;

        int confirm = JOptionPane.showConfirmDialog(topicPanel, "Are you sure you want to delete this topic?", "Delete Topic", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (topicService.deleteTopic(selectedId)) {
                    JOptionPane.showMessageDialog(topicPanel, "Topic deleted successfully.");
                    loadAllTopics();
                    topicPanel.clearInputs();

                    // ADD THESE LINES HERE:
                    if (questionPanelDelegation != null) {
                        questionPanelDelegation.refreshTopicComboBox();
                    }
                    if (quizPanelDelegation != null) {
                        quizPanelDelegation.refreshTopicComboBox();
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(topicPanel, "Failed to delete topic: " + e.getMessage());
            }
        }
    }

    private void saveTopic() {
        TopicDTO topic = topicPanel.getTopicFromInputs();
        try {
            boolean success = topicService.saveTopic(topic);
            if (success) {
                JOptionPane.showMessageDialog(topicPanel, "Topic saved successfully.");
                loadAllTopics();
                topicPanel.clearInputs();

                // ADD THESE LINES HERE:
                if (questionPanelDelegation != null) {
                    questionPanelDelegation.refreshTopicComboBox();
                }
                if (quizPanelDelegation != null) {
                    quizPanelDelegation.refreshTopicComboBox();
                }
            } else {
                JOptionPane.showMessageDialog(topicPanel, "Failed to save topic.");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(topicPanel, "Validation error: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(topicPanel, "Error saving topic: " + e.getMessage());
        }
    }
}
