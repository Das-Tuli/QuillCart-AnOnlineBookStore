package com.mycompany.quillcart;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Utility class holding shared styling and helper methods for the UI.
 *
 * <p>Defining colours, fonts and frequently used component builders in a
 * single place makes it easy to adjust the visual identity of the whole
 * application. The pastel palette and consistent typography defined here
 * give the bookstore a modern, friendly feel without altering any of the
 * underlying business logic.</p>
 */
public final class UIX {
    /**
     * Light background colour for the main window. A subtle off‑white tone
     * reduces glare compared to pure white and provides soft contrast with
     * card panels.
     */
    static final Color BG_LIGHT = new Color(248, 250, 252);

    /**
     * Base color for panels. Cards are rendered with pure white so they
     * stand out against the pastel background.
     */
    static final Color CARD = new Color(255, 255, 255);

    /**
     * Primary accent color used on buttons and borders. This pastel purple
     * hue draws the eye without overwhelming the interface. A darker variant
     * is used for hover states.
     */
    static final Color ACCENT = new Color(156, 39, 176);
    static final Color ACCENT_DARK = new Color(123, 31, 162);

    /**
     * Typography definitions. Using a consistent family and sizing across
     * titles, subtitles and body text improves readability and cohesion. The
     * monospace font for receipts is defined closer to its usage.
     */
    static final Font TITLE = new Font("SansSerif", Font.BOLD, 26);
    static final Font SUBTITLE = new Font("SansSerif", Font.BOLD, 18);
    static final Font BODY = new Font("SansSerif", Font.PLAIN, 14);

    private UIX() {
        // Prevent instantiation.
    }

    /**
     * Creates a bold title label centred with consistent margins.
     *
     * @param text the text to display
     * @return a configured {@link JLabel}
     */
    static JLabel title(String text) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(TITLE);
        l.setBorder(new EmptyBorder(12, 0, 8, 0));
        return l;
    }

    /**
     * Wraps components into a card‑like panel with a titled border.
     *
     * <p>The returned panel uses a white background and a coloured border
     * derived from the primary accent. The title is rendered in the
     * subtitle font. Clients can add children to the panel using any
     * desired layout manager.</p>
     *
     * @param title text for the border caption
     * @return a {@link JPanel} with customised appearance
     */
    static JPanel section(String title) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(CARD);
        Border border = BorderFactory.createLineBorder(new Color(ACCENT.getRed(), ACCENT.getGreen(), ACCENT.getBlue(), 80));
        TitledBorder tb = new TitledBorder(border, title, TitledBorder.LEFT, TitledBorder.TOP, SUBTITLE, ACCENT_DARK);
        p.setBorder(tb);
        return p;
    }

    /**
     * Constructs a primary action button with accent colours and subtle
     * rollover effects. When hovered, the button darkens slightly to
     * communicate interactivity.
     *
     * @param text label for the button
     * @return a configured {@link JButton}
     */
    static JButton primary(String text) {
        JButton b = new JButton(text);
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(BODY);
        b.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(ACCENT_DARK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(ACCENT);
            }
        });
        return b;
    }

    /**
     * Builds a secondary or subtle action button. Secondary buttons do not
     * carry a background fill; they rely on the accent colour for their
     * text and border. Use this style for less prominent actions like
     * navigation or removal.
     *
     * @param text label for the button
     * @return a subtle styled {@link JButton}
     */
    static JButton subtle(String text) {
        JButton b = new JButton(text);
        b.setFont(BODY);
        b.setForeground(ACCENT_DARK);
        b.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        return b;
    }

    /**
     * Formats a number of taka as a currency string with the appropriate
     * symbol and two decimal places.
     *
     * @param amount amount in taka
     * @return a string like "৳123.45"
     */
    static String formatTaka(double amount) {
        return "৳" + String.format("%.2f", amount);
    }
}