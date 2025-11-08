package com.mycompany.quillcart;

import javax.swing.*;

/**
 * A small standalone example demonstrating the custom UIX components
 * with the Nimbus look and feel. Running this class will display a
 * simple window containing a primary and subtle button, letting you
 * instantly verify that the styling works without external
 * dependencies.
 */
public class ExampleUI {
    public static void main(String[] args) {
        // Set Nimbus LAF to provide a modern default appearance.
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
            // If Nimbus is unavailable, the default look and feel will be used.
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("UIX Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new java.awt.GridLayout(1, 2, 10, 10));
            frame.getContentPane().setBackground(UIX.BG_LIGHT);

            JButton primary = UIX.primary("Primary");
            JButton subtle = UIX.subtle("Subtle");

            frame.add(primary);
            frame.add(subtle);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}