package gui.swing.subpanel;

import gui.swing.UIStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnswersPanel extends JPanel {
    private final List<GUI.Swing.AnswerRow> answerRows = new ArrayList<>();

    public AnswersPanel(List<String> answerLabels) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        UIStyleUtil.stylePanel(this);

        for (String label : answerLabels) {
            GUI.Swing.AnswerRow row = new GUI.Swing.AnswerRow(label);
            answerRows.add(row);
            add(row);
            add(Box.createRigidArea(new Dimension(0, 8)));
        }
    }

    public List<String> getAnswerTexts() {
        List<String> texts = new ArrayList<>();
        for (GUI.Swing.AnswerRow row : answerRows) {
            texts.add(row.getAnswerText());
        }
        return texts;
    }

    public List<Boolean> getCheckedStates() {
        List<Boolean> checks = new ArrayList<>();
        for (GUI.Swing.AnswerRow row : answerRows) {
            checks.add(row.isChecked());
        }
        return checks;
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

    public void setEditable(boolean editable) {
        for (GUI.Swing.AnswerRow row : answerRows) {
            row.setEditable(editable);
        }
    }
}
