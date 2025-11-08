package com.mycompany.quillcart;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class WelcomePanel extends JPanel {
    private BufferedImage bgImage;

    public WelcomePanel(MainWindow frame) {
        setLayout(null); // absolute positioning
        setBackground(new Color(245, 245, 245));

        // Load background image
        try {
            URL bgUrl = getClass().getResource("/homepage.png");
            if (bgUrl == null) System.err.println("Image not found: /homepage.png");
            else bgImage = ImageIO.read(bgUrl);
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }

        // Rounded white professional box with shadow
        RoundedPanel contentBox = new RoundedPanel(20, new Color(255, 255, 255, 230));
        contentBox.setLayout(new GridBagLayout());
        contentBox.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        int boxWidth = 380;
        int boxHeight = 320;
        contentBox.setBounds(550, 150, boxWidth, boxHeight); // shifted a bit left

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 0, 12, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title - serif, bold
        gbc.gridy = 0;
        JLabel title = new JLabel("📚 Welcome to QuillCart", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 26));
        title.setForeground(new Color(30, 58, 138)); // deep blue
        contentBox.add(title, gbc);

        // Tagline - italic, gray
        gbc.gridy++;
        JLabel tagline = new JLabel("\"Your Cozy Online Bookstore\"", SwingConstants.CENTER);
        tagline.setFont(new Font("Georgia", Font.ITALIC, 18));
        tagline.setForeground(new Color(75, 85, 99)); // dark gray
        contentBox.add(tagline, gbc);

        // Owner Login button - professional color
        gbc.gridy++;
        JButton ownerBtn = new JButton("Owner Login");
        styleButton(ownerBtn, new Color(30, 58, 138), new Color(23, 45, 110), 280, 50);
        ownerBtn.addActionListener(e -> frame.switchPanel("OwnerLogin"));
        contentBox.add(ownerBtn, gbc);

        // Browse as Customer button - professional color
        gbc.gridy++;
        JButton browseBtn = new JButton("Browse as Customer");
        styleButton(browseBtn, new Color(20, 184, 166), new Color(14, 140, 125), 280, 50);
        browseBtn.addActionListener(e -> frame.switchPanel("UserRegistrationPanel"));
        contentBox.add(browseBtn, gbc);

        add(contentBox);
    }

    // Method to style buttons with professional colors, hover effect, and custom size
    private void styleButton(JButton button, Color normal, Color hover, int width, int height) {
        button.setFont(new Font("Georgia", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(normal);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, height));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normal);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Rounded panel with shadow
    static class RoundedPanel extends JPanel {
        private final int cornerRadius;
        private final Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            super();
            cornerRadius = radius;
            backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Shadow
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, cornerRadius, cornerRadius);

            // Rounded box
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, cornerRadius, cornerRadius);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
