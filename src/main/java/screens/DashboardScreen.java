package screens;

import app.Application;
import enums.Screens;
import models.Movie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardScreen extends JPanel {
    private static final Color MIDNIGHT = new Color(16, 24, 40);
    private static final Color SIDEBAR = new Color(12, 19, 33);
    private static final Color MUTED = new Color(166, 178, 197);
    private static final Color ACCENT = new Color(226, 55, 75);
    private static final Color GOLD = new Color(244, 190, 83);
    private static final String NOW_SHOWING = "now-showing";
    private static final String THEATRE = "theatre";

    private final CardLayout contentLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(contentLayout);
    private final JButton nowShowingButton = createNavigationButton("Now Showing");
    private final JButton bookingsButton = createNavigationButton("Bookings");
    private final JButton paymentsButton = createNavigationButton("Payments");
    private final TheatrePanel theatrePanel = new TheatrePanel(this::showNowShowing);

    public DashboardScreen(Application app) {
        setLayout(new BorderLayout());
        setBackground(MIDNIGHT);
        add(createSidebar(app), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
        showNowShowing();
    }

    private JPanel createSidebar(Application app) {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(238, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(43, 55, 76)));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(new EmptyBorder(30, 20, 20, 20));

        JLabel brand = new JLabel("MULTIPLEX");
        brand.setFont(new Font("SansSerif", Font.BOLD, 18));
        brand.setForeground(GOLD);
        JLabel location = new JLabel("NAALYA  ·  STAFF");
        location.setFont(new Font("SansSerif", Font.BOLD, 10));
        location.setForeground(MUTED);
        location.setBorder(new EmptyBorder(4, 1, 0, 0));

        JLabel section = new JLabel("WORKSPACE");
        section.setFont(new Font("SansSerif", Font.BOLD, 10));
        section.setForeground(new Color(115, 130, 153));
        section.setBorder(new EmptyBorder(32, 1, 10, 0));

        top.add(brand);
        top.add(location);
        top.add(section);
        styleNavigationButton(nowShowingButton);
        styleNavigationButton(bookingsButton);
        styleNavigationButton(paymentsButton);
        top.add(nowShowingButton);
        top.add(Box.createVerticalStrut(6));
        top.add(bookingsButton);
        top.add(Box.createVerticalStrut(6));
        top.add(paymentsButton);

        bookingsButton.setEnabled(false);
        paymentsButton.setEnabled(false);
        bookingsButton.setToolTipText("Bookings navigation will be available soon");
        paymentsButton.setToolTipText("Payments navigation will be available soon");
        nowShowingButton.addActionListener(event -> showNowShowing());

        JButton logoutButton = createNavigationButton("Logout");
        logoutButton.setForeground(new Color(242, 151, 162));
        logoutButton.addActionListener(event -> app.showScreen(Screens.LOGIN_SCREEN));
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(14, 14, 18, 14));
        bottom.add(new JSeparator(), BorderLayout.NORTH);
        bottom.add(logoutButton, BorderLayout.CENTER);

        sidebar.add(top, BorderLayout.NORTH);
        sidebar.add(bottom, BorderLayout.SOUTH);
        return sidebar;
    }

    private JPanel createContent() {
        NowShowingPanel nowShowingPanel = new NowShowingPanel(this::showMovie);
        contentPanel.setBackground(MIDNIGHT);
        contentPanel.add(nowShowingPanel, NOW_SHOWING);
        contentPanel.add(theatrePanel, THEATRE);
        return contentPanel;
    }

    private void showMovie(Movie movie) {
        theatrePanel.setMovie(movie);
        contentLayout.show(contentPanel, THEATRE);
        updateNavigationState(false);
    }

    private void showNowShowing() {
        contentLayout.show(contentPanel, NOW_SHOWING);
        updateNavigationState(true);
    }

    private void updateNavigationState(boolean showingMovies) {
        nowShowingButton.setBackground(showingMovies ? new Color(39, 49, 69) : SIDEBAR);
        nowShowingButton.setForeground(showingMovies ? Color.WHITE : MUTED);
        nowShowingButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0,
                        showingMovies ? ACCENT : SIDEBAR),
                new EmptyBorder(12, 13, 12, 10)
        ));
    }

    private JButton createNavigationButton(String text) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        return button;
    }

    private void styleNavigationButton(JButton button) {
        button.setForeground(MUTED);
        button.setBackground(SIDEBAR);
        button.setBorder(new EmptyBorder(12, 16, 12, 10));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
    }
}
