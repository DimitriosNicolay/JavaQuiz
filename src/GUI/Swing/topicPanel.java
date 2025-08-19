package GUI.Swing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

public class topicPanel extends JPanel {
    private JList<String> topicList;
    private DefaultListModel<String> listModel;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton newButton;
    private JTextField titleField;
    private JTextArea descriptionArea;

    public topicPanel() {
        setLayout(new BorderLayout());
        UIStyleUtil.stylePanel(this);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Right panel with list
        listModel = new DefaultListModel<>();
        topicList = new JList<>(listModel);
        UIStyleUtil.styleList(topicList);

        JScrollPane listScrollPane = new JScrollPane(topicList);
        UIStyleUtil.styleScrollPane(listScrollPane, "Topics");

        // Left panel with details
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

        // Button panel with flexible spacing
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        UIStyleUtil.stylePanel(buttonPanel);
        buttonPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        deleteButton = new JButton("Delete Topic");
        saveButton = new JButton("Save Changes");
        newButton = new JButton("New Topic");

        // Set fixed sizes for buttons
        Dimension buttonSize = new Dimension(120, 30);
        deleteButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);
        newButton.setPreferredSize(buttonSize);

        UIStyleUtil.styleRedButton(deleteButton);
        UIStyleUtil.styleButton(saveButton);
        UIStyleUtil.styleGreenButton(newButton);

        // Create center panel for save button with flexible spacing
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(saveButton);

        buttonPanel.add(deleteButton, BorderLayout.WEST);
        buttonPanel.add(centerPanel, BorderLayout.CENTER);
        buttonPanel.add(newButton, BorderLayout.EAST);

        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add components to main panel with reversed order
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                detailsPanel,
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