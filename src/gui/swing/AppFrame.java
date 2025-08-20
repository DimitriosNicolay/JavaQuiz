package gui.swing;

import gui.swing.components.NavBar;
import gui.swing.panel.QuestionPanel;
import gui.swing.panel.QuizPanel;
import gui.swing.panel.StatsPanel;
import gui.swing.panel.TopicPanel;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {

    // Components of the main application frame
    private NavBar navigationBar;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private TopicPanel topicPanel;
    private QuestionPanel questionPanel;
    private QuizPanel quizPanel;
    private StatsPanel statsPanel;



    public AppFrame() throws HeadlessException {

        // Set up the main application frame
        setTitle("Quiz");
        setMinimumSize(new Dimension(800,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Implement components
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        navigationBar = new NavBar();

        // Adding and Alignment of components
        add(navigationBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // implement panels
        topicPanel = new TopicPanel();
        questionPanel = new QuestionPanel();
        quizPanel = new QuizPanel();
        statsPanel = new StatsPanel();

        // Add panels to mainPanel
        mainPanel.add(new TopicPanel(), "topics");
        mainPanel.add(new QuestionPanel(), "questions");
        mainPanel.add(new QuizPanel(), "quiz");
        mainPanel.add(new StatsPanel(), "stats");

        add(mainPanel, BorderLayout.CENTER);

        // Add action listeners to navBar buttons to switch cards
        navigationBar.addNavigationActions(cardLayout, mainPanel);
    }

public static void main(String[] args) throws HeadlessException {
SwingUtilities.invokeLater(() -> new AppFrame().setVisible(true));
}
}

