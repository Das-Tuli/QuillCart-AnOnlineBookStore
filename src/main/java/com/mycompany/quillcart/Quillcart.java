package com.mycompany.quillcart;

import javax.swing.*;
import javax.swing.UIManager;
import java.awt.*;

public class Quillcart {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new MainWindow();

            JLabel titleLabel = new JLabel("📚 QuillCart", JLabel.CENTER);
            titleLabel.setFont(new Font("Georgia", Font.BOLD, 26));
            titleLabel.setForeground(new Color(30, 144, 255)); // Dodger Blue
            frame.add(titleLabel, BorderLayout.NORTH);

            frame.setVisible(true);
        });
    }
}
