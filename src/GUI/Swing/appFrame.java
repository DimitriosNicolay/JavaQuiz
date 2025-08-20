package GUI.Swing;

import javax.swing.*;
import java.awt.*;

public class appFrame extends JFrame {

    // Components of the main application frame
    private navBar navigationBar;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private topicPanel topicPanel;
    private questionPanel questionPanel;
    private quizPanel quizPanel;
    private statsPanel statsPanel;



    public appFrame() throws HeadlessException {

        // Set up the main application frame
        setTitle("Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the navigation bar at the top
        navigationBar = new navBar();
        add(navigationBar, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        topicPanel = new topicPanel();
        questionPanel = new questionPanel();
        quizPanel = new quizPanel();
        statsPanel = new statsPanel();

        // Initialize panels
        mainPanel.add(new topicPanel(), "topics");
        mainPanel.add(new questionPanel(), "questions");
        mainPanel.add(new quizPanel(), "quiz");
        mainPanel.add(new statsPanel(), "stats");

        add(mainPanel, BorderLayout.CENTER);

        // Add action listeners to navBar buttons to switch cards
        navigationBar.addNavigationActions(cardLayout, mainPanel);

        // Basic frame setup
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setLocationRelativeTo(null);
    }

public static void main(String[] args) throws HeadlessException {
SwingUtilities.invokeLater(() -> new appFrame());
}
}

