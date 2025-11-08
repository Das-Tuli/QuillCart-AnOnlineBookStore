package com.mycompany.quillcart;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class UserRegistrationPanel extends JPanel {

    private final MainWindow frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    // In-memory user store
    private static final Map<String, String> registeredUsers = new HashMap<>();

    // Colors & fonts
    private static final Color MAIN_BG = new Color(0x339999); // medium-dark teal
    private static final Color MENU_BG = new Color(0x004040); // dark teal
    private static final Color PRIMARY_BTN_BG = new Color(0x00CED1); // bright teal
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font FIELD_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);

    public UserRegistrationPanel(MainWindow frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(MAIN_BG);
        buildLoginForm();
    }

    private JPanel createMenuPanel(String primaryButtonText, Runnable primaryAction) {
        JPanel menu = new JPanel();
        menu.setBackground(MENU_BG);
        menu.setPreferredSize(new Dimension(220, 0)); // menu width
        menu.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST; // top-left alignment
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome title
        JLabel welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setFont(TITLE_FONT);
        welcomeLabel.setForeground(TEXT_COLOR);
        menu.add(welcomeLabel, gbc);

        // Primary button below title
        gbc.gridy++;
        JButton primaryBtn = new JButton(primaryButtonText);
        primaryBtn.setFont(BUTTON_FONT);
        primaryBtn.setBackground(Color.WHITE);
        primaryBtn.setForeground(MENU_BG);
        menu.add(primaryBtn, gbc);
        primaryBtn.addActionListener(e -> primaryAction.run());

        // Back button below primary
        gbc.gridy++;
        JButton backBtn = new JButton("Back");
        backBtn.setFont(BUTTON_FONT);
        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(MENU_BG);
        menu.add(backBtn, gbc);
        backBtn.addActionListener(e -> frame.switchPanel("Welcome"));

        // Push buttons to top if menu height grows
        gbc.weighty = 1.0;
        gbc.gridy++;
        menu.add(Box.createVerticalGlue(), gbc);

        return menu;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(MAIN_BG);
        return panel;
    }

    public void buildLoginForm() {
        removeAll();
        setLayout(new BorderLayout());

        // Left menu
        JPanel menu = createMenuPanel("Register", this::buildRegisterForm);
        add(menu, BorderLayout.WEST);

        // Right content
        JPanel panel = createFormPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("User Login", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy++;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(LABEL_FONT);
        userLabel.setForeground(TEXT_COLOR);
        panel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(FIELD_FONT);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(LABEL_FONT);
        passLabel.setForeground(TEXT_COLOR);
        panel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(FIELD_FONT);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Login button (highlighted)
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(BUTTON_FONT);
        loginBtn.setBackground(PRIMARY_BTN_BG);
        loginBtn.setForeground(Color.WHITE);
        panel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!registeredUsers.containsKey(username)) {
                JOptionPane.showMessageDialog(this, "No account found. Please register first.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!registeredUsers.get(username).equals(password)) {
                JOptionPane.showMessageDialog(this, "Incorrect password.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.switchPanel("CustomerPanel");
            }
        });

        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void buildRegisterForm() {
        removeAll();
        setLayout(new BorderLayout());

        // Left menu
        JPanel menu = createMenuPanel("Login", this::buildLoginForm);
        add(menu, BorderLayout.WEST);

        // Right content
        JPanel panel = createFormPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("User Registration", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy++;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(LABEL_FONT);
        userLabel.setForeground(TEXT_COLOR);
        panel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(FIELD_FONT);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(LABEL_FONT);
        passLabel.setForeground(TEXT_COLOR);
        panel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(FIELD_FONT);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Register button (highlighted)
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(BUTTON_FONT);
        registerBtn.setBackground(PRIMARY_BTN_BG);
        registerBtn.setForeground(Color.WHITE);
        panel.add(registerBtn, gbc);

        registerBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (registeredUsers.containsKey(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                registeredUsers.put(username, password);
                JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                buildLoginForm();
            }
        });

        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void refresh() {
        buildLoginForm();
    }
}
