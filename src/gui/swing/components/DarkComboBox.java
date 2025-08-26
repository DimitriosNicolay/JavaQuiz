package gui.swing.components;

import gui.swing.UIStyleUtil;

import javax.swing.*;

public class DarkComboBox<E> extends JComboBox<E> {

    public DarkComboBox() {
        super();
        UIStyleUtil.styleComboBox(this, null);
    }

    public DarkComboBox(String title) {
        super();
        UIStyleUtil.styleComboBox(this, title);
    }

    public DarkComboBox(E[] items) {
        super(items);
        UIStyleUtil.styleComboBox(this, null);
    }

    public DarkComboBox(E[] items, String title) {
        super(items);
        UIStyleUtil.styleComboBox(this, title);
    }
}
