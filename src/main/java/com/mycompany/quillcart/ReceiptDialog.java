package com.mycompany.quillcart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

public class ReceiptDialog extends JDialog {

    public ReceiptDialog(Frame owner, String receiptText) {
        super(owner, "Receipt", true);
        setLayout(new BorderLayout(10, 10));

        // === Receipt Text Area ===
        JTextArea receiptArea = new JTextArea(receiptText, 20, 40);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptArea.setBackground(new Color(255, 255, 255)); // White background
        receiptArea.setForeground(new Color(0, 0, 0)); // Black text
        JScrollPane receiptScroll = new JScrollPane(receiptArea);

        add(receiptScroll, BorderLayout.CENTER);

        // === Buttons Panel ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton printButton = new JButton("Print");
        JButton copyButton = new JButton("Copy");
        JButton closeButton = new JButton("Close");
        buttonPanel.add(copyButton);
        buttonPanel.add(printButton);
        buttonPanel.add(closeButton);

        // === Memo Section ===
        JPanel memoSection = new JPanel(new BorderLayout());
        memoSection.setBorder(BorderFactory.createTitledBorder("Customer Memo"));
        JTextArea memoArea = new JTextArea(6, 30);
        memoArea.setLineWrap(true);
        memoArea.setWrapStyleWord(true);
        memoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        memoArea.setBackground(Color.WHITE);
        memoArea.setForeground(Color.BLACK);
        memoArea.setCaretColor(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(memoArea);
        memoSection.add(scrollPane, BorderLayout.CENTER);
        JButton submitMemoBtn = new JButton("Submit Memo");
        memoSection.add(submitMemoBtn, BorderLayout.SOUTH);

        // === Actions ===
        printButton.addActionListener(e -> {
            try {
                receiptArea.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ReceiptDialog.this,
                        "Printing failed: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        copyButton.addActionListener(e -> {
            receiptArea.selectAll();
            receiptArea.copy();
            JOptionPane.showMessageDialog(ReceiptDialog.this, "Receipt copied to clipboard.");
        });
        closeButton.addActionListener(e -> dispose());
        submitMemoBtn.addActionListener(e -> {
            String memoText = memoArea.getText().trim();
            if (!memoText.isEmpty()) {
                System.out.println("Customer Memo: " + memoText);
                JOptionPane.showMessageDialog(ReceiptDialog.this,
                        "Memo saved:\n" + memoText,
                        "Memo Submitted",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ReceiptDialog.this,
                        "Please write something before submitting.",
                        "Empty Memo",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Combine the button panel and memo section into a single south panel so
        // they are both displayed. Without this container only one component
        // would be visible due to BorderLayout constraints.
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(buttonPanel);
        southPanel.add(memoSection);
        add(southPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }
}
