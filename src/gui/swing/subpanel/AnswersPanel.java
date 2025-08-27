package gui.swing.subpanel;

import gui.swing.components.AnswerRow;
import gui.swing.UIStyleUtil;
import gui.swing.components.DarkComboBox;
import service.dto.TopicDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnswersPanel extends JPanel {

    private final List<AnswerRow> answerRows = new ArrayList<>();
    private JCheckBox[] checkBoxes;
    private DarkComboBox<TopicDTO> answerComboBox;

    public AnswersPanel(List<String> answerLabels) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        UIStyleUtil.stylePanel(this);

        answerComboBox = new DarkComboBox<>();
        answerComboBox.setPreferredSize(new Dimension(220, 30));

        answerComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

                if (value == null) {
                    setText("All topics");
                    c.setForeground(new Color(150, 150, 150));
                } else if (value instanceof TopicDTO) {
                    TopicDTO topic = (TopicDTO) value;
                    setText(topic.getTitle());
                    c.setForeground(UIStyleUtil.TEXT_COLOR);
                } else {
                    c.setForeground(UIStyleUtil.TEXT_COLOR);
                }
                c.setBackground(isSelected ? new Color(80, 80, 80) : UIStyleUtil.COMPONENT_COLOR);
                return c;
            }
        });

        for (String label : answerLabels) {
            AnswerRow row = new AnswerRow(label);
            answerRows.add(row);
            add(row);
            add(Box.createRigidArea(new Dimension(0, 8)));
        }
    }

    public List<String> getAnswerTexts() {
        List<String> texts = new ArrayList<>();
        for (AnswerRow row : answerRows) {
            texts.add(row.getAnswerText());
        }
        return texts;
    }

    public List<Boolean> getCheckedStates() {
        List<Boolean> states = new ArrayList<>();
        for (AnswerRow row : answerRows) {
            states.add(row.isChecked());
        }
        return states;
    }

    public void setAnswers(List<String> texts) {
        for (int i = 0; i < answerRows.size(); i++) {
            if (i < texts.size()) {
                answerRows.get(i).setAnswerText(texts.get(i));
            } else {
                answerRows.get(i).setAnswerText("");
            }
        }
    }

    public void setCheckedStates(List<Boolean> checks) {
        if (checks == null || checkBoxes == null) {
            // If null, uncheck all checkboxes or reset state
            if (checkBoxes != null) {
                for (JCheckBox cb : checkBoxes) {
                    cb.setSelected(false);
                }
            }
            return;
        }

        int min = Math.min(checks.size(), checkBoxes.length);
        for (int i = 0; i < min; i++) {
            checkBoxes[i].setSelected(checks.get(i));
        }
    }

    public void setCheckboxEditable(boolean editable) {
        for (AnswerRow row : answerRows) {
            row.setCheckboxEditable(editable);
        }
    }

    public void setTextFieldsEditable(boolean editable) {
        for (AnswerRow row : answerRows) {
            row.setTextFieldEditable(editable);
        }
    }

    public void clearInputs() {
        for (AnswerRow row : answerRows) {
            row.setAnswerText("");
            row.setChecked(false);
        }
    }

    public List<AnswerRow> getAnswerRows() {
        return answerRows;
    }

}
