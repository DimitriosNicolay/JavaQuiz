package gui.swing.panel;

import service.dto.TopicDTO;
import gui.swing.UIStyleUtil;
import gui.swing.components.ButtonPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicPanel extends JPanel {
    private final ButtonPanel buttonPanel;
    private JList<String> topicList;
    private DefaultListModel<String> listModel;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton newButton;
    private JTextField titleField;
    private JTextArea descriptionArea;

    // Map to link topic titles to TopicDTO objects
    private Map<String, TopicDTO> topicMap = new HashMap<>();

    public TopicPanel() {
        setLayout(new BorderLayout());
        UIStyleUtil.stylePanel(this);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        listModel = new DefaultListModel<>();
        topicList = new JList<>(listModel);
        UIStyleUtil.styleList(topicList);

        JScrollPane listScrollPane = new JScrollPane(topicList);
        UIStyleUtil.styleScrollPane(listScrollPane, "Topics");

        // Details panel with title and description fields
        JPanel detailsPanel = new JPanel(new BorderLayout());
        UIStyleUtil.stylePanel(detailsPanel);

        titleField = new JTextField();
        UIStyleUtil.styleTextField(titleField, "Title");

        descriptionArea = new JTextArea();
        UIStyleUtil.styleTextArea(descriptionArea);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        UIStyleUtil.styleScrollPane(descScrollPane, "Description");

        detailsPanel.add(titleField, BorderLayout.NORTH);
        detailsPanel.add(descScrollPane, BorderLayout.CENTER);

        // Initialize ButtonPanel with New, Save, Delete buttons
        buttonPanel = new ButtonPanel(
                List.of("New", "Save", "Delete"),
                List.of(
                        ButtonPanel.ButtonType.NORMAL,
                        ButtonPanel.ButtonType.GREEN,
                        ButtonPanel.ButtonType.RED
                )
        );

        newButton = buttonPanel.getButton("New");
        saveButton = buttonPanel.getButton("Save");
        deleteButton = buttonPanel.getButton("Delete");

        // Compose left main panel with details and buttons
        JPanel leftMainPanel = new JPanel(new BorderLayout());
        UIStyleUtil.stylePanel(leftMainPanel);
        leftMainPanel.add(detailsPanel, BorderLayout.CENTER);
        leftMainPanel.add(buttonPanel, BorderLayout.SOUTH);
        leftMainPanel.setMinimumSize(new Dimension(400, 600));

        // Create and style split pane
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                leftMainPanel,
                listScrollPane
        );
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerSize(4);
        splitPane.setBorder(null);
        splitPane.setBackground(UIStyleUtil.BACKGROUND_COLOR);
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

    // Return the topic list component for adding listeners
    public JList<String> getTopicList() {
        return topicList;
    }

    // Return buttons
    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getNewButton() {
        return newButton;
    }

    // Populate the list with topic titles from DTOs and keep map updated
    public void setTopics(List<TopicDTO> topics) {
        topicMap.clear();
        listModel.clear();
        for (TopicDTO topic : topics) {
            String title = topic.getTitle();
            topicMap.put(title, topic);
            listModel.addElement(title);
        }
    }

    // Get the ID of the currently selected topic by mapping the selected title
    public int getSelectedTopicId() {
        String selectedTitle = topicList.getSelectedValue();
        TopicDTO dto = topicMap.get(selectedTitle);
        return (dto != null) ? dto.getId() : -1;
    }

    // Fill the details fields from a TopicDTO
    public void setTopicDetails(TopicDTO topic) {
        if (topic != null) {
            titleField.setText(topic.getTitle());
            descriptionArea.setText(topic.getDescription());
        }
    }

    // Create a TopicDTO from the current inputs, including the selected topic's ID if any
    public TopicDTO getTopicFromInputs() {
        TopicDTO dto = new TopicDTO();
        dto.setTitle(titleField.getText());
        dto.setDescription(descriptionArea.getText());
        int selectedId = getSelectedTopicId();
        if (selectedId > 0) {
            dto.setId(selectedId);
        }
        return dto;
    }

    // Clear all input fields and selection
    public void clearInputs() {
        titleField.setText("");
        descriptionArea.setText("");
        topicList.clearSelection();
    }
}
