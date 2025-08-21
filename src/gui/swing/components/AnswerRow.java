package gui.swing.components;

import gui.swing.UIStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;

public class AnswerRow extends JPanel {
    private final JCheckBox checkBox;
    private final JLabel label;
    private final JTextField textField;

    public AnswerRow(String labelText) {
        setLayout(new BorderLayout(5, 0));  // small horizontal gap for spacing
        setOpaque(false);

        checkBox = new JCheckBox();
        checkBox.setOpaque(false);

        label = new JLabel(labelText);
        label.setForeground(UIStyleUtil.TEXT_COLOR);
        label.setPreferredSize(new Dimension(70, 25)); // fixed width for label

        textField = new JTextField();
        textField.setEditable(false);
        textField.setForeground(UIStyleUtil.TEXT_COLOR);
        textField.setBackground(UIStyleUtil.COMPONENT_COLOR);
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Compose left side panel with label and text field horizontally
        JPanel leftPanel = new JPanel(new BorderLayout(5, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(label, BorderLayout.WEST);
        leftPanel.add(textField, BorderLayout.CENTER);

        // Add left panel and checkbox to main panel
        add(leftPanel, BorderLayout.CENTER);
        add(checkBox, BorderLayout.EAST);
    }

    public boolean isChecked() {
        return checkBox.isSelected();
    }

    public void setChecked(boolean selected) {
        checkBox.setSelected(selected);
    }

    public String getAnswerText() {
        return textField.getText();
    }

    public void setAnswerText(String text) {
        textField.setText(text);
    }

    // Control checkbox enabled/disabled
    public void setCheckboxEditable(boolean editable) {
        checkBox.setEnabled(editable);
    }

    // Control text field editable or not
    public void setTextFieldEditable(boolean editable) {
        textField.setEditable(editable);
    }

    public void addCheckboxListener(ItemListener listener) {
        checkBox.addItemListener(listener);
    }
}
