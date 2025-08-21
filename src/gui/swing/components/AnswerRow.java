package GUI.Swing;

import gui.swing.UIStyleUtil;

import javax.swing.*;
import java.awt.*;

public class AnswerRow extends JPanel {
    private JLabel label;
    private JTextArea textArea;
    private JCheckBox checkBox;

    public AnswerRow(String labelText) {
        setLayout(new BorderLayout(8, 0));
        setOpaque(false);

        label = new JLabel(labelText);
        label.setForeground(UIStyleUtil.TEXT_COLOR);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));

        textArea = new JTextArea(2, 25);
        UIStyleUtil.styleTextArea(textArea);
        textArea.setMargin(new Insets(8, 5, 8, 5));  // adds padding top+bottom for more vertical centering
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        checkBox = new JCheckBox();
        checkBox.setBackground(UIStyleUtil.BACKGROUND_COLOR);

        add(label, BorderLayout.WEST);
        add(textArea, BorderLayout.CENTER);
        add(checkBox, BorderLayout.EAST);
    }

    public String getAnswerText() {
        return textArea.getText();
    }

    public void setAnswerText(String text) {
        textArea.setText(text);
    }

    public boolean isChecked() {
        return checkBox.isSelected();
    }

    public void setChecked(boolean checked) {
        checkBox.setSelected(checked);
    }

    public void setEditable(boolean editable) {
        textArea.setEditable(editable);
        checkBox.setEnabled(editable);
    }
}
