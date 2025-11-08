package com.mycompany.quillcart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OwnerPanel extends JPanel {
    private final MainWindow frame;
    private JComboBox<String> categoryCombo;
    private JComboBox<String> bookCombo;
    private JTextField stockField;

    private JTextField newTitleField;
    private JComboBox<String> newCategoryCombo;
    private JTextField newCategoryField;
    private JTextField newPriceField;
    private JTextField newStockField;

    private static final int FONT_SIZE = 16;
    private static final Color BUTTON_COLOR = new Color(40, 80, 160); // Dark blue
    private static final Color BUTTON_HOVER = new Color(30, 60, 120);  // Hover color

    public OwnerPanel(MainWindow frame) {
        this.frame = frame;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(60, 80, 140)); // Medium dark blue
        setBorder(new EmptyBorder(16, 16, 16, 16));

        // Top title
        JLabel titleLabel = UIX.title("Owner Console");
        titleLabel.setFont(titleLabel.getFont().deriveFont((float) FONT_SIZE + 4));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(40, 80, 160)); // Header dark blue
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(titleLabel, BorderLayout.NORTH);

        // Center panel for stock update and add book
        JPanel center = new JPanel(new GridLayout(1, 2, 16, 16));
        center.setOpaque(false);

        // --- Update Stock Section ---
        JPanel stockSec = UIX.section("Update Stock");
        stockSec.setBackground(Color.WHITE);
        stockSec.setBorder(BorderFactory.createLineBorder(new Color(40, 80, 160), 2, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        JLabel catLabel = new JLabel("Category:");
        catLabel.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        gbc.gridx = 0; gbc.gridy = row;
        stockSec.add(catLabel, gbc);

        categoryCombo = new JComboBox<>(frame.getCategoryBooks().keySet().toArray(new String[0]));
        categoryCombo.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        gbc.gridx = 1;
        stockSec.add(categoryCombo, gbc);

        row++;
        JLabel bookLabel = new JLabel("Book:");
        bookLabel.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        gbc.gridx = 0; gbc.gridy = row;
        stockSec.add(bookLabel, gbc);

        bookCombo = new JComboBox<>();
        bookCombo.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        gbc.gridx = 1;
        stockSec.add(bookCombo, gbc);

        row++;
        JLabel qtyLabel = new JLabel("Add Quantity:");
        qtyLabel.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        gbc.gridx = 0; gbc.gridy = row;
        stockSec.add(qtyLabel, gbc);

        stockField = new JTextField(6);
        stockField.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        gbc.gridx = 1;
        stockSec.add(stockField, gbc);

        row++;
        JButton addStockBtn = new JButton("Add Stock");
        styleButton(addStockBtn);
        gbc.gridx = 1; gbc.gridy = row;
        stockSec.add(addStockBtn, gbc);

        // --- Add New Book Section ---
        JPanel addBookSec = UIX.section("Add New Book");
        addBookSec.setBackground(Color.WHITE);
        addBookSec.setBorder(BorderFactory.createLineBorder(new Color(40, 80, 160), 2, true));

        GridBagConstraints g2 = new GridBagConstraints();
        g2.insets = new Insets(8, 8, 8, 8);
        g2.anchor = GridBagConstraints.WEST;
        int r2 = 0;

        JLabel lblTitle = new JLabel("Title:");
        lblTitle.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g2.gridx = 0; g2.gridy = r2;
        addBookSec.add(lblTitle, g2);

        newTitleField = new JTextField(16);
        newTitleField.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g2.gridx = 1;
        addBookSec.add(newTitleField, g2);

        r2++;
        JLabel lblCatCombo = new JLabel("Category:");
        lblCatCombo.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g2.gridx = 0; g2.gridy = r2;
        addBookSec.add(lblCatCombo, g2);

        newCategoryCombo = new JComboBox<>();
        newCategoryCombo.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g2.gridx = 1;
        addBookSec.add(newCategoryCombo, g2);

        r2++;
        JLabel lblCatField = new JLabel("Or New Category:");
        lblCatField.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g2.gridx = 0; g2.gridy = r2;
        addBookSec.add(lblCatField, g2);

        newCategoryField = new JTextField(12);
        newCategoryField.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g2.gridx = 1;
        addBookSec.add(newCategoryField, g2);

        r2++;
        JLabel lblPrice = new JLabel("Price (৳):");
        lblPrice.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g2.gridx = 0; g2.gridy = r2;
        addBookSec.add(lblPrice, g2);

        newPriceField = new JTextField(8);
        newPriceField.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g2.gridx = 1;
        addBookSec.add(newPriceField, g2);

        r2++;
        JLabel lblStock = new JLabel("Initial Stock:");
        lblStock.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g2.gridx = 0; g2.gridy = r2;
        addBookSec.add(lblStock, g2);

        newStockField = new JTextField(6);
        newStockField.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g2.gridx = 1;
        addBookSec.add(newStockField, g2);

        r2++;
        JButton addBookBtn = new JButton("Add Book");
        styleButton(addBookBtn);
        g2.gridx = 1; g2.gridy = r2;
        addBookSec.add(addBookBtn, g2);

        center.add(stockSec);
        center.add(addBookSec);
        add(center, BorderLayout.CENTER);

        // --- Bottom panel ---
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);

        JButton viewHistory = new JButton("Customer Purchases");
        styleButton(viewHistory);
        bottom.add(viewHistory);

        JButton logoutBtn = new JButton("Logout");
        styleButton(logoutBtn);
        bottom.add(logoutBtn);

        add(bottom, BorderLayout.SOUTH);

        // Listeners
        categoryCombo.addActionListener(e -> updateBooks());
        updateBooks();
        refreshNewCategoryCombo();

        addStockBtn.addActionListener(e -> addStockAction());
        addBookBtn.addActionListener(e -> addBookAction());
        logoutBtn.addActionListener(e -> frame.switchPanel("Welcome"));
        viewHistory.addActionListener(e -> frame.openHistory("OwnerPanel"));
    }

    // Style all buttons with dark blue and hover
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
    }

    // Supporting methods: refreshAll, refreshNewCategoryCombo, updateBooks, addStockAction, addBookAction
    void refreshAll() {
        categoryCombo.setModel(new DefaultComboBoxModel<>(frame.getCategoryBooks().keySet().toArray(new String[0])));
        updateBooks();
        refreshNewCategoryCombo();
    }

    private void refreshNewCategoryCombo() {
        newCategoryCombo.removeAllItems();
        for (String k : frame.getCategoryBooks().keySet()) newCategoryCombo.addItem(k);
        newCategoryCombo.setSelectedIndex(-1);
    }

    private void updateBooks() {
        bookCombo.removeAllItems();
        String selectedCategory = (String) categoryCombo.getSelectedItem();
        if (selectedCategory != null) {
            for (Book b : frame.getCategoryBooks().get(selectedCategory)) {
                bookCombo.addItem(b.title + " (Stock: " + b.stock + ")");
            }
        }
    }

    private void addStockAction() {
        String selectedCategory = (String) categoryCombo.getSelectedItem();
        int selectedIndex = bookCombo.getSelectedIndex();
        if (selectedCategory == null || selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select a category and a book.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String quantityStr = stockField.getText().trim();
        if (!quantityStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Enter a valid positive integer.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int quantity = Integer.parseInt(quantityStr);
        Book book = frame.getCategoryBooks().get(selectedCategory).get(selectedIndex);
        book.stock += quantity;
        JOptionPane.showMessageDialog(this, "Stock updated for \"" + book.title + "\". New stock: " + book.stock);
        stockField.setText("");
        updateBooks();
    }

    private void addBookAction() {
        String title = newTitleField.getText().trim();
        String catFromCombo = (String) newCategoryCombo.getSelectedItem();
        String catFromField = newCategoryField.getText().trim();
        String category = !catFromField.isEmpty() ? catFromField : catFromCombo;

        String priceStr = newPriceField.getText().trim();
        String stockStr = newStockField.getText().trim();

        if (title.isEmpty() || category == null || category.trim().isEmpty() ||
                !priceStr.matches("\\d+(\\.\\d{1,2})?") || !stockStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                    "Fill Title, Category and valid Price (e.g., 499.99) and Stock (integer).",
                    "Invalid input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double priceTaka = Double.parseDouble(priceStr);
        int stock = Integer.parseInt(stockStr);
        Book b = new Book(title, category.trim(), priceTaka, stock);
        frame.addNewBook(category.trim(), b);
        JOptionPane.showMessageDialog(this, "Added book \"" + title + "\" in category \"" + category + "\".");

        newTitleField.setText("");
        newCategoryField.setText("");
        newPriceField.setText("");
        newStockField.setText("");
        refreshAll();
    }
}
