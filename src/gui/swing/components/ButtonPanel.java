package gui.swing.components;

import gui.swing.UIStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ButtonPanel extends JPanel {
    private List<JButton> buttons;

    public enum ButtonType { NORMAL, RED, GREEN }

    public ButtonPanel(List<String> labels, List<ButtonType> types) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        UIStyleUtil.stylePanel(this);

        buttons = new ArrayList<>();

        for (int i = 0; i < labels.size(); i++) {
            if (i > 0) {
                add(Box.createHorizontalGlue());
            }
            JButton btn = new JButton(labels.get(i));
            ButtonType type = types.get(i);
            switch (type) {
                case RED -> UIStyleUtil.styleRedButton(btn);
                case GREEN -> UIStyleUtil.styleGreenButton(btn);
                default -> UIStyleUtil.styleButton(btn);
            }
            buttons.add(btn);
            add(btn);
        }
    }

    public List<JButton> getButtons() {
        return buttons;
    }
}
