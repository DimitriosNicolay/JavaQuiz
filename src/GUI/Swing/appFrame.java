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
        setMinimumSize(new Dimension(800,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Implement components
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        navigationBar = new navBar();

        // Adding and Alignment of components
        add(navigationBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // implement panels
        topicPanel = new topicPanel();
        questionPanel = new questionPanel();
        quizPanel = new quizPanel();
        statsPanel = new statsPanel();

        // Add panels to mainPanel
        mainPanel.add(new topicPanel(), "topics");
        mainPanel.add(new questionPanel(), "questions");
        mainPanel.add(new quizPanel(), "quiz");
        mainPanel.add(new statsPanel(), "stats");

        add(mainPanel, BorderLayout.CENTER);

        // Add action listeners to navBar buttons to switch cards
        navigationBar.addNavigationActions(cardLayout, mainPanel);
    }

public static void main(String[] args) throws HeadlessException {
SwingUtilities.invokeLater(() -> new appFrame().setVisible(true));
}
}

