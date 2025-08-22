package gui.swing.panel;

import gui.swing.UIStyleUtil;
import gui.swing.components.ButtonPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.util.Arrays;

public class TopicPanel extends JPanel {
    private final ButtonPanel buttonPanel;
    private JList<String> topicList;
    private DefaultListModel<String> listModel;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton newButton;
    private JTextField titleField;
    private JTextArea descriptionArea;

    public TopicPanel() {
        setLayout(new BorderLayout());
        UIStyleUtil.stylePanel(this);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Right panel with list
        listModel = new DefaultListModel<>();
        topicList = new JList<>(listModel);
        UIStyleUtil.styleList(topicList);

        JScrollPane listScrollPane = new JScrollPane(topicList);
        UIStyleUtil.styleScrollPane(listScrollPane, "Topics");

        // Left details panel with details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        UIStyleUtil.stylePanel(detailsPanel);

        titleField = new JTextField();
        UIStyleUtil.styleTextField(titleField, "Title");

        descriptionArea = new JTextArea();
        UIStyleUtil.styleTextArea(descriptionArea);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        UIStyleUtil.styleScrollPane(descScrollPane, "Description");

        detailsPanel.add(titleField, BorderLayout.NORTH);
        detailsPanel.add(descScrollPane, BorderLayout.CENTER);

        // Button panel

        buttonPanel = new ButtonPanel(
            Arrays.asList("New", "Save", "Delete"),
            Arrays.asList(
                ButtonPanel.ButtonType.NORMAL,
                ButtonPanel.ButtonType.GREEN,
                ButtonPanel.ButtonType.RED
            )
        );

        // create left main Panel
        JPanel leftMainPanel = new JPanel(new BorderLayout());
        UIStyleUtil.stylePanel(leftMainPanel);
        leftMainPanel.add(detailsPanel, BorderLayout.CENTER);
        leftMainPanel.add(buttonPanel, BorderLayout.SOUTH);
        leftMainPanel.setMinimumSize(new Dimension(400, 600));

        // Add components to main panel with reversed order
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                leftMainPanel,
                listScrollPane
        );
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerSize(4);
        splitPane.setBorder(null);
        splitPane.setBackground(UIStyleUtil.BACKGROUND_COLOR);

        // Style the divider
        splitPane.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    {
                        setBackground(UIStyleUtil.BACKGROUND_COLOR);
                    }
                    public void setBorder(Border b) {}
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(UIStyleUtil.BACKGROUND_COLOR);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        g.setColor(new Color(80, 80, 80));
                        g.fillRect(0, 0, getSize().width, getSize().height);
                    }
                };
            }
        });

        add(splitPane, BorderLayout.CENTER);
    }
}