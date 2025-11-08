package com.mycompany.quillcart;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomerPanel extends JPanel {
    private final MainWindow frame;
    private JComboBox<String> categoryCombo;
    private JComboBox<String> bookCombo;
    private JLabel stockLabel;
    private JLabel priceLabel;
    private JTextField quantityField;
    private JTextField promoCodeField;
    private DefaultListModel<String> cartListModel;
    private JList<String> cartList;
    private final Map<Book, Integer> cart = new LinkedHashMap<>();

    public CustomerPanel(MainWindow frame) {
        this.frame = frame;
      setLayout(new BorderLayout(20, 20));
    setBackground(new Color(0x339999)); // medium-dark teal
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JLabel title = new JLabel("Customer Shopping", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
       title.setForeground(Color.WHITE); 
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1, 2, 20, 20));
        content.setOpaque(false);

        // Left side
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        topPanel.add(new JLabel("Category:"), gbc);
        categoryCombo = new JComboBox<>(frame.getCategoryBooks().keySet().toArray(new String[0]));
        categoryCombo.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1;
        topPanel.add(categoryCombo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        topPanel.add(new JLabel("Book:"), gbc);
        bookCombo = new JComboBox<>();
        bookCombo.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1;
        topPanel.add(bookCombo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        topPanel.add(new JLabel("Stock Available:"), gbc);
        stockLabel = new JLabel("-");
        gbc.gridx = 1;
        topPanel.add(stockLabel, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        topPanel.add(new JLabel("Price (৳):"), gbc);
        priceLabel = new JLabel("-");
        gbc.gridx = 1;
        topPanel.add(priceLabel, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        topPanel.add(new JLabel("Quantity:"), gbc);
        quantityField = new JTextField(10);
        quantityField.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1;
        topPanel.add(quantityField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        topPanel.add(new JLabel("Promo Code:"), gbc);
        promoCodeField = new JTextField(10);
        promoCodeField.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1;
        topPanel.add(promoCodeField, gbc);

        row++;
        gbc.gridx = 1; gbc.gridy = row;
        JButton addToCartBtn = new JButton("Add to Cart");
        addToCartBtn.setPreferredSize(new Dimension(150, 40));
        addToCartBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addToCartBtn.setBackground(new Color(60, 120, 200));
        addToCartBtn.setForeground(Color.WHITE);
        addToCartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addToCartBtn.addActionListener(e -> addToCartAction());
        topPanel.add(addToCartBtn, gbc);

        content.add(topPanel);

        // Right side
        JPanel cartPanel = new JPanel(new BorderLayout(10, 10));
        cartPanel.setOpaque(false);

        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        cartList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(cartList);
        cartPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel cartBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartBtns.setOpaque(false);

        JButton removeBtn = new JButton("Remove");
        JButton checkoutBtn = new JButton("Checkout");
        JButton historyBtn = new JButton("View History");
        JButton backBtn = new JButton("Back");

        removeBtn.setPreferredSize(new Dimension(100, 30));
        checkoutBtn.setPreferredSize(new Dimension(120, 40));
        historyBtn.setPreferredSize(new Dimension(120, 30));
        backBtn.setPreferredSize(new Dimension(100, 30));

        removeBtn.setBackground(new Color(200, 50, 50));
        checkoutBtn.setBackground(new Color(60, 120, 200));
        historyBtn.setBackground(new Color(200, 200, 200));
        backBtn.setBackground(new Color(200, 200, 200));

        removeBtn.setForeground(Color.WHITE);
        checkoutBtn.setForeground(Color.WHITE);
        historyBtn.setForeground(Color.BLACK);
        backBtn.setForeground(Color.BLACK);

        removeBtn.addActionListener(e -> removeSelected());
        checkoutBtn.addActionListener(e -> checkoutAction());
        historyBtn.addActionListener(e -> frame.openHistory("CustomerPanel"));
        backBtn.addActionListener(e -> {
            clearCart();
            frame.switchPanel("Welcome");
        });

        cartBtns.add(historyBtn);
        cartBtns.add(removeBtn);
        cartBtns.add(checkoutBtn);
        cartBtns.add(backBtn);
        cartPanel.add(cartBtns, BorderLayout.SOUTH);

        content.add(cartPanel);
        add(content, BorderLayout.CENTER);

        categoryCombo.addActionListener(e -> updateBooks());
        bookCombo.addActionListener(e -> updateBookInfo());
        updateBooks();
    }

    // ===== Public now so MainWindow can clear the cart after finalize =====
    public void clearCart() {
        cart.clear();
        if (cartListModel != null) cartListModel.clear();
        if (promoCodeField != null) promoCodeField.setText("");
        if (quantityField != null) quantityField.setText("");
    }

    void refreshAll() {
        categoryCombo.setModel(new DefaultComboBoxModel<>(frame.getCategoryBooks().keySet().toArray(new String[0])));
        updateBooks();
        refreshCartList();
    }

    private void updateBooks() {
        bookCombo.removeAllItems();
        String category = (String) categoryCombo.getSelectedItem();
        if (category == null) return;
        for (Book b : frame.getCategoryBooks().get(category)) bookCombo.addItem(b.title);
        updateBookInfo();
    }

    private void updateBookInfo() {
        String category = (String) categoryCombo.getSelectedItem();
        int index = bookCombo.getSelectedIndex();
        if (category == null || index < 0) {
            stockLabel.setText("-");
            priceLabel.setText("-");
            return;
        }
        Book book = frame.getCategoryBooks().get(category).get(index);
        stockLabel.setText(String.valueOf(getAvailableStock(book)));
        priceLabel.setText(String.format("%.2f", book.priceTaka));
    }

   private void checkoutAction() {
    if (cart.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Your cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String entered = promoCodeField.getText().trim();
    boolean promoApplied = false;

    if (!entered.isEmpty()) { // If user entered a promo code
        if (entered.equalsIgnoreCase(frame.getPromoCode())) {
            promoApplied = true; // Correct promo code, 10% discount will apply
        } else {
            JOptionPane.showMessageDialog(this, "The promo code is incorrect.", 
                                          "Invalid Promo Code", JOptionPane.WARNING_MESSAGE);
            promoCodeField.setText(""); // Optional: clear wrong promo code
            return; // Stop checkout if promo code is wrong
        }
    }

    // Hand off current cart + promo state to MainWindow
    frame.setPendingOrder(new LinkedHashMap<>(cart), promoApplied, entered);

    // Go to checkout panel (will collect address etc.)
    frame.switchPanel("Checkout");
}


    private int getAvailableStock(Book book) {
        int inCart = cart.getOrDefault(book, 0);
        return book.stock - inCart;
    }

    private void addToCartAction() {
        String quantityStr = quantityField.getText().trim();
        if (!quantityStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Enter a valid positive quantity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int quantity = Integer.parseInt(quantityStr);
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(this, "Quantity must be greater than zero.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String category = (String) categoryCombo.getSelectedItem();
        int index = bookCombo.getSelectedIndex();
        if (category == null || index < 0) {
            JOptionPane.showMessageDialog(this, "Please select a category and book first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Book book = frame.getCategoryBooks().get(category).get(index);
        int available = getAvailableStock(book);
        if (available < quantity) {
            JOptionPane.showMessageDialog(this, "Not enough stock available. Remaining: " + available,
                    "Stock Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        cart.put(book, cart.getOrDefault(book, 0) + quantity);
        refreshCartList();
        quantityField.setText("");
        updateBookInfo();
    }

    private void removeSelected() {
        int idx = cartList.getSelectedIndex();
        if (idx < 0) return;

        // Find the Book corresponding to selected line
        int i = 0;
        Book toRemove = null;
        for (Book b : cart.keySet()) {
            if (i == idx) {
                toRemove = b;
                break;
            }
            i++;
        }
        if (toRemove != null) {
            cart.remove(toRemove);
            refreshCartList();
            updateBookInfo();
        }
    }

    private void refreshCartList() {
        cartListModel.clear();
        for (Map.Entry<Book, Integer> entry : cart.entrySet()) {
            Book b = entry.getKey();
            int qty = entry.getValue();
            cartListModel.addElement(b.title + "  ×" + qty + "  —  " + String.format("%.2f", b.priceTaka * qty));
        }
    }
}
