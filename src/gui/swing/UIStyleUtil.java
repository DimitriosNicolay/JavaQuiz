package gui.swing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

// Utility class for consistent UI styling across the application
public class UIStyleUtil {
    // Standard colors used throughout the UI
    public static final Color BACKGROUND_COLOR = new Color(45, 45, 45);  // Dark background
    public static final Color COMPONENT_COLOR = new Color(60, 60, 60);   // Slightly lighter for components
    public static final Color TEXT_COLOR = new Color(200, 200, 200);     // Light gray text
    public static final Color GREEN_COLOR = new Color(76, 175, 80);  // A pleasant green
    public static final Color RED_COLOR = new Color(244, 67, 54);    // A strong red

    // Applies standard button styling with neutral colors
    public static void styleButton(JButton button) {
        button.setBackground(COMPONENT_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);         // Remove focus outline
        button.setBorderPainted(false);        // Remove border
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 30));
    }

    // Styles a button with red background
    public static void styleRedButton(JButton button) {
        styleButton(button);
        button.setBackground(RED_COLOR);
    }

    // Styles a button with green background
    public static void styleGreenButton(JButton button) {
        styleButton(button);
        button.setBackground(GREEN_COLOR);
    }

    // Styles a text field with a titled border and dark theme colors
    public static void styleTextField(JTextField field, String title) {
        field.setBackground(COMPONENT_COLOR);
        field.setForeground(TEXT_COLOR);
        field.setCaretColor(TEXT_COLOR);
        field.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(TEXT_COLOR),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.PLAIN, 12),
                TEXT_COLOR
        ));
    }

    // Applies dark theme colors to text areas
    public static void styleTextArea(JTextArea area) {
        area.setBackground(COMPONENT_COLOR);
        area.setForeground(TEXT_COLOR);
        area.setCaretColor(TEXT_COLOR);
    }

    // Styles JList with dark theme colors and selection highlighting
    public static void styleList(JList<?> list) {
        list.setBackground(COMPONENT_COLOR);
        list.setForeground(TEXT_COLOR);
        list.setSelectionBackground(new Color(80, 80, 80));
        list.setSelectionForeground(TEXT_COLOR);
    }

    // Styles a scroll pane with titled border and padding
    public static void styleScrollPane(JScrollPane scrollPane, String title) {
        scrollPane.getViewport().setBackground(COMPONENT_COLOR);
        scrollPane.setBackground(COMPONENT_COLOR);

        // Create titled border with text color
        Border lineBorder = BorderFactory.createLineBorder(TEXT_COLOR);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                lineBorder,
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.PLAIN, 12),
                TEXT_COLOR
        );

        // Add padding inside the titled border
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border compoundBorder = BorderFactory.createCompoundBorder(titledBorder, emptyBorder);

        scrollPane.setBorder(compoundBorder);
    }

    // Sets the background color for panels
    public static void stylePanel(JPanel panel) {
        panel.setBackground(BACKGROUND_COLOR);
    }

    // Styles JComboBox with dark theme consistent with other components
    public static void styleComboBox(JComboBox<?> comboBox, String title) {
        comboBox.setBackground(COMPONENT_COLOR);
        comboBox.setForeground(TEXT_COLOR);
        comboBox.setFocusable(true);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // Set a flat line border or optional titled border if title provided
        Border border = BorderFactory.createLineBorder(TEXT_COLOR);
        if (title != null && !title.isEmpty()) {
            border = BorderFactory.createTitledBorder(
                    border,
                    title,
                    TitledBorder.LEADING,
                    TitledBorder.TOP,
                    new Font("SansSerif", Font.PLAIN, 12),
                    TEXT_COLOR
            );
        }
        comboBox.setBorder(border);

        // Customize list cell renderer for consistent dropdown list styling
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8)); // Add padding

                if (value != null && "Please select a topic".equals(value.toString())) {
                    c.setForeground(new Color(150, 150, 150)); // Lighter gray for prompt
                } else {
                    c.setForeground(TEXT_COLOR);
                }
                c.setBackground(isSelected ? new Color(80, 80, 80) : COMPONENT_COLOR);
                return c;
            }
        });
    }
}
