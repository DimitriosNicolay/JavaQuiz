package gui.swing;

import gui.swing.components.NavBar;
import gui.swing.delegations.QuestionPanelDelegation;
import gui.swing.delegations.TopicPanelDelegation;
import gui.swing.delegations.QuizPanelDelegation;
import gui.swing.panel.QuestionPanel;
import gui.swing.panel.QuizPanel;
import gui.swing.panel.StatsPanel;
import gui.swing.panel.TopicPanel;
import persistence.DBManager;
import persistence.dao.TopicDAO;
import persistence.dao.QuestionDAO;
import persistence.dao.AnswerDAO;
import service.TopicService;
import service.QuestionService;
import service.QuizService;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {

    // UI Components
    private NavBar navigationBar;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Panels
    private TopicPanel topicPanel;
    private QuestionPanel questionPanel;
    private QuizPanel quizPanel;
    private StatsPanel statsPanel;

    // Delegations
    private TopicPanelDelegation topicDelegation;
    private QuestionPanelDelegation questionDelegation;
    private QuizPanelDelegation quizDelegation;
    // private StatsPanelDelegation statsDelegation; // For later

    public AppFrame() {
        // Frame settings
        setTitle("Quiz");
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout setup
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        navigationBar = new NavBar();

        // Adding components to frame
        add(navigationBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Initialize panels
        topicPanel = new TopicPanel();
        questionPanel = new QuestionPanel();
        quizPanel = new QuizPanel();
        statsPanel = new StatsPanel();

        // Add panels to main panel with card layout
        mainPanel.add(topicPanel, "topics");
        mainPanel.add(questionPanel, "questions");
        mainPanel.add(quizPanel, "quiz");
        mainPanel.add(statsPanel, "stats");

        // Database Manager
        DBManager dbManager = new DBManager();

        // DAOs
        TopicDAO topicDAO = new TopicDAO(dbManager);
        QuestionDAO questionDAO = new QuestionDAO(dbManager);
        AnswerDAO answerDAO = new AnswerDAO(dbManager);

        // Services
        TopicService topicService = new TopicService(topicDAO);
        QuestionService questionService = new QuestionService(questionDAO, answerDAO);

        // Delegations
        topicDelegation = new TopicPanelDelegation(topicPanel, topicService);
        questionDelegation = new QuestionPanelDelegation(questionPanel, questionService, topicService);

        // Attempt to initialize QuizService with real questions, fallback to empty list on failure
        QuizService quizService;
        try {
            quizService = new QuizService(questionService.getAllQuestions(), answerDAO);
        } catch (Exception e) {
            quizService = new QuizService(new java.util.ArrayList<>(), answerDAO);
            JOptionPane.showMessageDialog(this, "Failed to load questions for quiz: " + e.getMessage());
        }
        quizDelegation = new QuizPanelDelegation(quizPanel, quizService, topicService, questionService);

        // TODO: Setup statsDelegation similarly when ready:
        // statsDelegation = new StatsPanelDelegation(statsPanel, statsService);

        navigationBar.addNavigationActions(cardLayout, mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppFrame().setVisible(true));
    }
}
