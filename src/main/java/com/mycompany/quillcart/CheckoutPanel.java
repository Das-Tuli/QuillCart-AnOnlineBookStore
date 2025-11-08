package com.mycompany.quillcart;

import javax.swing.*;
import java.awt.*;

public class CheckoutPanel extends JPanel {
    private final MainWindow frame;
    private JTextField fullNameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;

    public CheckoutPanel(MainWindow frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // --- Background color ---
        setBackground(new Color(0x339999)); // medium teal

        // --- Left menu panel ---
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(0x2A8080)); // darker shade
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(200, 0));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Checkout");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton clearCartBtn = new JButton("Clear Cart");
        clearCartBtn.setFont(new Font("Arial", Font.BOLD, 16));
        clearCartBtn.setBackground(new Color(0x2A8080));
        clearCartBtn.setForeground(Color.WHITE);
        clearCartBtn.setFocusPainted(false);
        clearCartBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        clearCartBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        clearCartBtn.addActionListener(e -> {
            if (frame.getCustomerPanel() != null) {
                frame.getCustomerPanel().clearCart();
            }
            frame.switchPanel("CustomerPanel");
        });

        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // spacing
        menuPanel.add(clearCartBtn);

        add(menuPanel, BorderLayout.WEST);

        // --- Right form panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        // Full Name
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(labelFont);
        fullNameLabel.setForeground(Color.WHITE);
        formPanel.add(fullNameLabel, gbc);

        gbc.gridx = 1;
        fullNameField = new JTextField(20);
        fullNameField.setFont(fieldFont);
        formPanel.add(fullNameField, gbc);

        // Phone
        gbc.gridx = 0; gbc.gridy++;
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(labelFont);
        phoneLabel.setForeground(Color.WHITE);
        formPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        phoneField = new JTextField(20);
        phoneField.setFont(fieldFont);
        formPanel.add(phoneField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy++;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(Color.WHITE);
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        emailField.setFont(fieldFont);
        formPanel.add(emailField, gbc);

        // Address
        gbc.gridx = 0; gbc.gridy++;
        JLabel addressLabel = new JLabel("Shipping Address:");
        addressLabel.setFont(labelFont);
        addressLabel.setForeground(Color.WHITE);
        formPanel.add(addressLabel, gbc);

        gbc.gridx = 1;
        addressField = new JTextField(20);
        addressField.setFont(fieldFont);
        formPanel.add(addressField, gbc);

        // Place Order button
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.setFont(new Font("Arial", Font.BOLD, 16));
        placeOrderBtn.setBackground(new Color(0x339999));
        placeOrderBtn.setForeground(Color.WHITE);
        placeOrderBtn.setFocusPainted(false);
        placeOrderBtn.setPreferredSize(new Dimension(180, 40));
        placeOrderBtn.addActionListener(e -> placeOrder());

        formPanel.add(placeOrderBtn, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    private void placeOrder() {
        String fullName = fullNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        if (fullName.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!phone.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "Phone number must be exactly 11 digits.", "Invalid Phone", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Enter a valid email address.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (address.length() < 5) {
            JOptionPane.showMessageDialog(this, "Enter a valid shipping address (at least 5 characters).", "Invalid Address", JOptionPane.ERROR_MESSAGE);
            return;
        }

        frame.finalizePendingOrderAndPrint();
        clearFields();
    }

    private void clearFields() {
        fullNameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
    }
}
