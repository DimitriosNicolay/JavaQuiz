package gui.swing.components;

import javax.swing.*;
import java.awt.*;

public class NavBar extends JPanel {
    private JButton topicsBtn;
    private JButton questionBtn;
    private JButton quizBtn;
    private JButton statsBtn;

    public NavBar() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(new Color(51, 51, 51));
        initializeButtons();
    }

    private void initializeButtons() {
        // Create buttons
        topicsBtn = new JButton("Topics");
        questionBtn = new JButton("Questions");
        quizBtn = new JButton("Quiz");
        statsBtn = new JButton("Statistics");

        // Add buttons to panel
        add(topicsBtn);
        add(questionBtn);
        add(quizBtn);
        add(statsBtn);

        // Style buttons
        JButton[] buttons = {topicsBtn, questionBtn, quizBtn, statsBtn};
        for (JButton button : buttons) {
            button.setFocusPainted(false);
            button.setBackground(new Color(51, 51, 51));
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
        }
    }

    public void addNavigationActions(CardLayout cardLayout, JPanel mainPanel) {
        getTopicsBtn().addActionListener(e -> cardLayout.show(mainPanel, "topics"));
        getQuestionBtn().addActionListener(e -> cardLayout.show(mainPanel, "questions"));
        getQuizBtn().addActionListener(e -> cardLayout.show(mainPanel, "quiz"));
        getStatsBtn().addActionListener(e -> cardLayout.show(mainPanel, "stats"));
    }

    // Getters for buttons to add action listeners later
    public JButton getTopicsBtn() { return topicsBtn; }
    public JButton getQuestionBtn() { return questionBtn; }
    public JButton getQuizBtn() { return quizBtn; }
    public JButton getStatsBtn() { return statsBtn; }
}