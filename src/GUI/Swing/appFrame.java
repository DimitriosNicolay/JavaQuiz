package GUI.Swing;

import javax.swing.*;
import java.awt.*;

public class appFrame extends JFrame {

    // Components of the main application frame
    private navBar navigationBar;
    private topicPanel topicPanel;

    public appFrame() throws HeadlessException {

        // Set up the main application frame
        setTitle("Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Set the navigation bar at the top
        navigationBar = new navBar();
        add(navigationBar, BorderLayout.NORTH);

        // Create and add topic panel as default
        topicPanel = new topicPanel();
        add(topicPanel, BorderLayout.CENTER);

        setVisible(true);
        setLocationRelativeTo(null);
    }

public static void main(String[] args) throws HeadlessException {
SwingUtilities.invokeLater(() -> new appFrame());
}
}

