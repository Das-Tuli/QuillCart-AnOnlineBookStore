package com.mycompany.quillcart;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainWindow extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    private final Map<String, java.util.List<Book>> categoryBooks = new LinkedHashMap<>();
    private final java.util.List<Purchase> purchaseHistory = new ArrayList<>();
    private int purchaseSeq = 1;

    private final String ownerPassword = "owner123";
    private final String promoCode = "MetroElite";

    private final WelcomePanel welcomePanel = new WelcomePanel(this);
    private final OwnerLoginPanel ownerLoginPanel = new OwnerLoginPanel(this);
    private final OwnerPanel ownerPanel = new OwnerPanel(this);
    private final CustomerPanel customerPanel = new CustomerPanel(this);
    private final PurchaseHistoryPanel historyPanel = new PurchaseHistoryPanel(this, "Customer Purchases");
    private final UserRegistrationPanel userRegistrationPanel = new UserRegistrationPanel(this);
    private final CheckoutPanel checkoutPanel = new CheckoutPanel(this);

    private String historyReturnTo = "OwnerPanel";

    // ===== Pending order handoff =====
    private Map<Book, Integer> pendingCart;
    private boolean pendingPromoApplied;
    private String pendingPromoCode;

    public MainWindow() {
        setTitle("Online Bookstore");
        setSize(1000, 660);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeBooks();

        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(ownerLoginPanel, "OwnerLogin");
        mainPanel.add(ownerPanel, "OwnerPanel");
        mainPanel.add(customerPanel, "CustomerPanel");
        mainPanel.add(historyPanel, "History");
        mainPanel.add(userRegistrationPanel, "UserRegistrationPanel");
        mainPanel.add(checkoutPanel, "Checkout");

        add(mainPanel);
        cardLayout.show(mainPanel, "Welcome");
        setVisible(true);
    }

    private void initializeBooks() {
        categoryBooks.put("Biography", new ArrayList<>(Arrays.asList(
                new Book("Steve Jobs", "Biography", 899.00, 5),
                new Book("Becoming", "Biography", 780.00, 3),
                new Book("Educated", "Biography", 820.00, 4),
                new Book("The Diary of a Young Girl", "Biography", 520.00, 6),
                new Book("Long Walk to Freedom", "Biography", 950.00, 7)
        )));
        categoryBooks.put("Mystery & Thriller", new ArrayList<>(Arrays.asList(
                new Book("Gone Girl", "Mystery & Thriller", 650.00, 7),
                new Book("The Girl with the Dragon Tattoo", "Mystery & Thriller", 620.00, 4),
                new Book("The Da Vinci Code", "Mystery & Thriller", 740.00, 5),
                new Book("Sherlock Holmes", "Mystery & Thriller", 580.00, 8),
                new Book("In the Woods", "Mystery & Thriller", 610.00, 6)
        )));
        categoryBooks.put("Poetry", new ArrayList<>(Arrays.asList(
                new Book("The Sun and Her Flowers", "Poetry", 450.00, 10),
                new Book("Milk and Honey", "Poetry", 420.00, 6),
                new Book("The Waste Land", "Poetry", 390.00, 5),
                new Book("Leaves of Grass", "Poetry", 430.00, 7),
                new Book("Ariel", "Poetry", 410.00, 4)
        )));
        categoryBooks.put("Fantasy", new ArrayList<>(Arrays.asList(
                new Book("Harry Potter and the Sorcerer's Stone", "Fantasy", 990.00, 8),
                new Book("The Hobbit", "Fantasy", 880.00, 5),
                new Book("Game of Thrones", "Fantasy", 1150.00, 6),
                new Book("The Name of the Wind", "Fantasy", 1080.00, 7),
                new Book("Mistborn", "Fantasy", 920.00, 4)
        )));
        categoryBooks.put("Fiction", new ArrayList<>(Arrays.asList(
                new Book("To Kill a Mockingbird", "Fiction", 640.00, 9),
                new Book("The Great Gatsby", "Fiction", 520.00, 12),
                new Book("1984", "Fiction", 690.00, 8),
                new Book("Pride and Prejudice", "Fiction", 560.00, 7),
                new Book("The Catcher in the Rye", "Fiction", 630.00, 5)
        )));
    }

    public Map<String, java.util.List<Book>> getCategoryBooks() {
        return categoryBooks;
    }

    public java.util.List<Purchase> getPurchaseHistory() {
        return purchaseHistory;
    }

    public String getOwnerPassword() {
        return ownerPassword;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public Purchase addPurchase(Map<Book, Integer> cart, double totalTaka, boolean promoApplied, String promo) {
        Purchase p = new Purchase(purchaseSeq++, cart, totalTaka, promoApplied, promo);
        purchaseHistory.add(0, p);
        return p;
    }

    public void switchPanel(String name) {
        if ("OwnerPanel".equals(name)) ownerPanel.refreshAll();
        if ("CustomerPanel".equals(name)) customerPanel.refreshAll();
        if ("History".equals(name)) historyPanel.refresh();
        if ("UserRegistrationPanel".equals(name)) userRegistrationPanel.refresh();
        cardLayout.show(mainPanel, name);
    }

    public void openHistory(String returnToPanelName) {
        this.historyReturnTo = returnToPanelName;
        switchPanel("History");
    }

    public void goBackFromHistory() {
        switchPanel(historyReturnTo);
    }

    public void addNewBook(String category, Book book) {
        categoryBooks.computeIfAbsent(category, k -> new ArrayList<>()).add(book);
    }

    // ====== pending order handoff API ======
    public void setPendingOrder(Map<Book, Integer> cart, boolean promoApplied, String promo) {
        this.pendingCart = (cart == null) ? null : new LinkedHashMap<>(cart);
        this.pendingPromoApplied = promoApplied;
        this.pendingPromoCode = (promo == null ? "" : promo);
    }

    // ====== finalize + auto-show printable receipt ======
    public void finalizePendingOrderAndPrint() {
        if (pendingCart == null || pendingCart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items to checkout.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate total & decrement stock
        double total = 0.0;
        for (Map.Entry<Book, Integer> e : pendingCart.entrySet()) {
            Book b = e.getKey();
            int qty = e.getValue();
            total += b.priceTaka * qty;
            b.stock -= qty; // update inventory
        }

        // Save purchase
        Purchase purchase = addPurchase(new LinkedHashMap<>(pendingCart), total, pendingPromoApplied, pendingPromoCode);

        // Clear cart and refresh views
        customerPanel.clearCart();     // ensure CustomerPanel.clearCart() is public
        ownerPanel.refreshAll();
        customerPanel.refreshAll();
        historyPanel.refresh();

        // Confirmation message (restored)
        JOptionPane.showMessageDialog(this, "Order has been placed successfully!");

        // Build & show printable receipt
        String receipt = buildReceiptText(purchase);
        showReceiptDialog(receipt);

        // Clear pending
        pendingCart = null;
        pendingPromoApplied = false;
        pendingPromoCode = null;
    }

    // ---------- Helpers to read Purchase safely regardless of field/getter names ----------
    @SuppressWarnings("unchecked")
    private Map<Book, Integer> getPurchaseItems(Purchase p) {
        try {
            // Prefer getters
            for (String mName : new String[]{"getItems", "getCart", "getCarts", "getItemMap"}) {
                try {
                    java.lang.reflect.Method m = p.getClass().getMethod(mName);
                    Object val = m.invoke(p);
                    if (val instanceof Map) return (Map<Book, Integer>) val;
                } catch (NoSuchMethodException ignore) {}
            }
            // Common field names
            for (String fname : new String[]{"items", "cart", "carts", "itemMap"}) {
                try {
                    java.lang.reflect.Field f = p.getClass().getDeclaredField(fname);
                    f.setAccessible(true);
                    Object val = f.get(p);
                    if (val instanceof Map) return (Map<Book, Integer>) val;
                } catch (NoSuchFieldException ignore) {}
            }
        } catch (Exception e) { /* fall through */ }
        return java.util.Collections.emptyMap();
    }

    private String getPurchasePromoCode(Purchase p) {
        try {
            // Prefer getters
            for (String mName : new String[]{"getPromo", "getPromoCode", "getCoupon", "getCouponCode"}) {
                try {
                    java.lang.reflect.Method m = p.getClass().getMethod(mName);
                    Object val = m.invoke(p);
                    return val == null ? "" : String.valueOf(val);
                } catch (NoSuchMethodException ignore) {}
            }
            // Common field names
            for (String fname : new String[]{"promo", "promoCode", "promocode", "coupon", "couponCode"}) {
                try {
                    java.lang.reflect.Field f = p.getClass().getDeclaredField(fname);
                    f.setAccessible(true);
                    Object val = f.get(p);
                    return val == null ? "" : String.valueOf(val);
                } catch (NoSuchFieldException ignore) {}
            }
        } catch (Exception e) { /* fall through */ }
        return "";
    }

    private boolean isPurchasePromoApplied(Purchase p) {
        try {
            // Prefer getters
            for (String mName : new String[]{"isPromoApplied", "getPromoApplied"}) {
                try {
                    java.lang.reflect.Method m = p.getClass().getMethod(mName);
                    Object val = m.invoke(p);
                    if (val instanceof Boolean) return (Boolean) val;
                } catch (NoSuchMethodException ignore) {}
            }
            // Common field names
            for (String fname : new String[]{"promoApplied", "hasPromo", "appliedPromo"}) {
                try {
                    java.lang.reflect.Field f = p.getClass().getDeclaredField(fname);
                    f.setAccessible(true);
                    Object val = f.get(p);
                    if (val instanceof Boolean) return (Boolean) val;
                } catch (NoSuchFieldException ignore) {}
            }
        } catch (Exception e) { /* fall through */ }
        return false;
    }
   public CustomerPanel getCustomerPanel() {
    return customerPanel;
}
 

    private int getPurchaseId(Purchase p) {
        try {
            // Prefer getters
            for (String mName : new String[]{"getId", "getOrderId"}) {
                try {
                    java.lang.reflect.Method m = p.getClass().getMethod(mName);
                    Object val = m.invoke(p);
                    if (val instanceof Number) return ((Number) val).intValue();
                } catch (NoSuchMethodException ignore) {}
            }
            // Common field names
            for (String fname : new String[]{"id", "orderId"}) {
                try {
                    java.lang.reflect.Field f = p.getClass().getDeclaredField(fname);
                    f.setAccessible(true);
                    Object val = f.get(p);
                    if (val instanceof Number) return ((Number) val).intValue();
                } catch (NoSuchFieldException ignore) {}
            }
        } catch (Exception e) { /* fall through */ }
        return -1;
    }

    private double getPurchaseTotal(Purchase p) {
        try {
            // Prefer getters
            for (String mName : new String[]{"getTotalTaka", "getTotal", "getAmount", "getGrandTotal"}) {
                try {
                    java.lang.reflect.Method m = p.getClass().getMethod(mName);
                    Object val = m.invoke(p);
                    if (val instanceof Number) return ((Number) val).doubleValue();
                } catch (NoSuchMethodException ignore) {}
            }
            // Common field names
            for (String fname : new String[]{"totalTaka", "total", "amount", "grandTotal"}) {
                try {
                    java.lang.reflect.Field f = p.getClass().getDeclaredField(fname);
                    f.setAccessible(true);
                    Object val = f.get(p);
                    if (val instanceof Number) return ((Number) val).doubleValue();
                } catch (NoSuchFieldException ignore) {}
            }
        } catch (Exception e) { /* fall through */ }
        return 0.0;
    }

    // ---------- Build receipt text using helpers (no direct field assumptions) ----------
    private String buildReceiptText(Purchase p) {
        int id = getPurchaseId(p);
        Map<Book, Integer> items = getPurchaseItems(p);
        double total = getPurchaseTotal(p);
        boolean promoApplied = isPurchasePromoApplied(p);
        String promo = getPurchasePromoCode(p);

        StringBuilder sb = new StringBuilder();
        sb.append("=== QuillCart Receipt ===\n");
        sb.append("Order #: ").append(id < 0 ? "" : id).append("\n");
        sb.append("Date: ").append(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
          .append("\n\n");
        sb.append(String.format("%-28s %5s %10s\n", "Item", "Qty", "Subtotal"));
        sb.append("-----------------------------------------------\n");
        for (Map.Entry<Book, Integer> e : items.entrySet()) {
            Book b = e.getKey();
            int qty = e.getValue();
            double sub = b.priceTaka * qty;
            sb.append(String.format("%-28s %5d %10.2f\n", b.title, qty, sub));
        }
        sb.append("-----------------------------------------------\n");
        sb.append(String.format("%-34s %10.2f\n", "Total (৳):", total));
        if (promoApplied) {
            sb.append("Promo Applied: ").append(promo == null ? "" : promo).append("\n");
        }
        sb.append("==============================\n");
        return sb.toString();
    }

    private void showReceiptDialog(String receiptText) {
        JDialog dlg = new JDialog(this, "Receipt", true);
        dlg.setLayout(new BorderLayout(10, 10));
        JTextArea area = new JTextArea(receiptText);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        dlg.add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton copyBtn = new JButton("Copy");
        JButton printBtn = new JButton("Print");
        JButton closeBtn = new JButton("Close");
        btns.add(copyBtn);
        btns.add(printBtn);
        btns.add(closeBtn);
        dlg.add(btns, BorderLayout.SOUTH);

        copyBtn.addActionListener(e -> {
            area.selectAll();
            area.copy();
            area.select(0, 0);
            JOptionPane.showMessageDialog(dlg, "Receipt copied to clipboard.");
        });

        printBtn.addActionListener(e -> {
            try {
                area.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Printing failed: " + ex.getMessage(),
                        "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        closeBtn.addActionListener(e -> dlg.dispose());

        dlg.setSize(520, 520);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }
}  