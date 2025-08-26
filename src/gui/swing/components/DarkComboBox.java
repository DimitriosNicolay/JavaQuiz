package gui.swing.components;

import javax.swing.*;
import java.awt.*;

public class DarkComboBox<E> extends JComboBox<E> {

    public DarkComboBox() {
        super();
        customize();
    }

    private void customize() {
        setBackground(new Color(45, 45, 45));  // dark background
        setForeground(Color.WHITE);            // white text
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    c.setBackground(new Color(75, 110, 175)); // selected background color
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(new Color(45, 45, 45));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        });
        setOpaque(true);
        setFocusable(true);
        setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
    }
}
