package gui.swing;

import gui.swing.components.NavBar;
import gui.swing.delegations.TopicPanelDelegation;
import gui.swing.panel.QuestionPanel;
import gui.swing.panel.QuizPanel;
import gui.swing.panel.StatsPanel;
import gui.swing.panel.TopicPanel;
import persistence.DBManager;
import persistence.dao.TopicDAO;
import service.TopicService;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {

    private NavBar navigationBar;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private TopicPanel topicPanel;
    private QuestionPanel questionPanel;
    private QuizPanel quizPanel;
    private StatsPanel statsPanel;

    // Delegations
    private TopicPanelDelegation topicDelegation;
    // private QuestionPanelDelegation questionDelegation;
    // private QuizPanelDelegation quizDelegation;
    // private StatsPanelDelegation statsDelegation;

    public AppFrame() {
        // JFrame basic setup
        setTitle("Quiz");
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        navigationBar = new NavBar();

        // Add navigation and main panel to frame
        add(navigationBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Create actual panel instances
        topicPanel = new TopicPanel();
        questionPanel = new QuestionPanel();
        quizPanel = new QuizPanel();
        statsPanel = new StatsPanel();

        // Add same instances to CardLayout main panel with keys
        mainPanel.add(topicPanel, "topics");
        mainPanel.add(questionPanel, "questions");
        mainPanel.add(quizPanel, "quiz");
        mainPanel.add(statsPanel, "stats");

        // Create DB connection manager and DAOs
        DBManager dbManager = new DBManager();

        TopicDAO topicDAO = new TopicDAO(dbManager);
        TopicService topicService = new TopicService(topicDAO);

        // Create delegations passing panels and services
        topicDelegation = new TopicPanelDelegation(topicPanel, topicService);

        // TODO: Create and wire QuestionPanelDelegation, QuizPanelDelegation, StatsPanelDelegation similarly

        // Wire navigation bar buttons to switch cards
        navigationBar.addNavigationActions(cardLayout, mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppFrame().setVisible(true));
    }
}
