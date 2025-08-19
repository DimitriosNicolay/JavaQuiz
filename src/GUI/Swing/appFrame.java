package GUI.Swing;

import javax.swing.*;
import java.awt.*;

public class appFrame extends JFrame {

    private navBar navigationBar;

    public appFrame() throws HeadlessException {
        setTitle("Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        navigationBar = new navBar();
        add(navigationBar, BorderLayout.NORTH);

        setVisible(true);
        setLocationRelativeTo(null);
    }

public static void main(String[] args) throws HeadlessException {
SwingUtilities.invokeLater(() -> new appFrame());
}
}
