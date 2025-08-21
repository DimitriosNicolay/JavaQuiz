package gui.swing.subpanel;

import gui.swing.components.AnswerRow;
import gui.swing.UIStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnswersPanel extends JPanel {

    private final List<AnswerRow> answerRows = new ArrayList<>();

    public AnswersPanel(List<String> answerLabels) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        UIStyleUtil.stylePanel(this);

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

    public void setAnswerTexts(List<String> texts) {
        for (int i = 0; i < answerRows.size(); i++) {
            if (i < texts.size()) {
                answerRows.get(i).setAnswerText(texts.get(i));
            } else {
                answerRows.get(i).setAnswerText("");
            }
        }
    }

    public void setCheckedStates(List<Boolean> checks) {
        for (int i = 0; i < answerRows.size(); i++) {
            if (i < checks.size()) {
                answerRows.get(i).setChecked(checks.get(i));
            } else {
                answerRows.get(i).setChecked(false);
            }
        }
    }

    // Allow setting checkboxes editable to true/false for user selection
    public void setCheckboxEditable(boolean editable) {
        for (AnswerRow row : answerRows) {
            row.setCheckboxEditable(editable);
        }
    }

    // Allow setting text fields editable true/false (for question editing vs quiz mode)
    public void setTextFieldsEditable(boolean editable) {
        for (AnswerRow row : answerRows) {
            row.setTextFieldEditable(editable);
        }
    }

    // Optional getter for all answer rows to add listeners, etc.
    public List<AnswerRow> getAnswerRows() {
        return answerRows;
    }
}
