package com.mycompany.quillcart;

import javax.swing.*;
import java.awt.*;

public class OwnerLoginPanel extends JPanel {

    private final MainWindow frame;

    public OwnerLoginPanel(MainWindow frame) {
        this.frame = frame;

        // --- Medium dark blue background for the panel ---
        setLayout(new BorderLayout());
        setBackground(new Color(60, 80, 140)); // medium dark blue

        // --- Left dark blue menu ---
        JPanel leftMenu = new JPanel();
        leftMenu.setBackground(new Color(20, 30, 90)); // dark blue
        leftMenu.setPreferredSize(new Dimension(260, 0)); // slightly wider menu
        leftMenu.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(25, 15, 25, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome Title
        gbc.gridy = 0;
        JLabel welcomeTitle = new JLabel("Welcome", SwingConstants.CENTER);
        welcomeTitle.setFont(new Font("Georgia", Font.BOLD, 24));
        welcomeTitle.setForeground(Color.WHITE);
        leftMenu.add(welcomeTitle, gbc);

        // Spacer
        gbc.gridy++;
        gbc.weighty = 1;
        leftMenu.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;

        // Back Button
        gbc.gridy++;
        JButton backBtn = new JButton("Back");
        styleSideButton(backBtn, new Color(40, 80, 160), new Color(30, 60, 120)); // professional hover
        backBtn.addActionListener(e -> frame.switchPanel("Welcome"));
        leftMenu.add(backBtn, gbc);

        add(leftMenu, BorderLayout.WEST);

        // --- Right login form ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(60, 80, 140));
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(15, 20, 15, 20);
        fgbc.gridx = 0;
        fgbc.fill = GridBagConstraints.HORIZONTAL;

        // Owner Login Title
        fgbc.gridy = 0;
        JLabel title = new JLabel("Owner Login", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        fgbc.gridwidth = 2;
        formPanel.add(title, fgbc);

        // Password Label
        fgbc.gridy++;
        fgbc.gridwidth = 1;
        JLabel pwdLabel = new JLabel("Password:");
        pwdLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
        pwdLabel.setForeground(Color.WHITE);
        formPanel.add(pwdLabel, fgbc);

        // Password Field
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Georgia", Font.PLAIN, 18));
        fgbc.gridx = 1;
        formPanel.add(passwordField, fgbc);

        // Login Button (small width, professional)
        fgbc.gridy++;
        fgbc.gridx = 0;
        fgbc.gridwidth = 2;
        JButton loginBtn = new JButton("Login");
        styleSideButton(loginBtn, new Color(40, 80, 160), new Color(30, 60, 120));
        loginBtn.setPreferredSize(new Dimension(160, 45)); // consistent with back button

        loginBtn.addActionListener(e -> {
            String typed = new String(passwordField.getPassword());
            if (typed.equals(frame.getOwnerPassword())) {
                passwordField.setText("");
                frame.switchPanel("OwnerPanel");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        });
        formPanel.add(loginBtn, fgbc);

        add(formPanel, BorderLayout.CENTER);
    }

    // Professional button styling with hover
    private void styleSideButton(JButton button, Color normal, Color hover) {
        button.setFont(new Font("Georgia", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(normal);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // subtle border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normal);
            }
        });
    }
}
