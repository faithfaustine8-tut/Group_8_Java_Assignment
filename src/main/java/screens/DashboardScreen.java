package screens;

import app.Application;
import enums.Screens;
import models.BookingSelection;
import models.LoginAccount;
import models.Movie;
import models.Screening;
import services.Storage;

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
    private static final String BOOKING = "booking";
    private static final String PAYMENTS = "payments";
    private static final String ISSUED_TICKETS = "issued-tickets";

    private final CardLayout contentLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(contentLayout);
    private final JButton nowShowingButton = createNavigationButton("Now Showing");
    private final JButton bookingsButton = createNavigationButton("Bookings");
    private final JButton paymentsButton = createNavigationButton("Payments");
    private final JButton issuedTicketsButton = createNavigationButton("Issued Tickets");
    private final TheatrePanel theatrePanel = new TheatrePanel(this::showNowShowing, this::showScreening);
    private final BookingPanel bookingPanel = new BookingPanel(this::showPayments, this::showTheatre);
        private final PaymentsPanel paymentsPanel = new PaymentsPanel(
            this::finishPayment, this::showBooking, this::issueTickets);
        private final IssuedTicketsPanel issuedTicketsPanel = new IssuedTicketsPanel();
    private final JLabel staffLocation = new JLabel("NAALYA  ·  STAFF");
    private String staffFullName = "Unknown staff";
    private Screening currentScreening;
    private BookingSelection currentBooking;

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
        staffLocation.setFont(new Font("SansSerif", Font.BOLD, 10));
        staffLocation.setForeground(MUTED);
        staffLocation.setBorder(new EmptyBorder(4, 1, 0, 0));

        JLabel section = new JLabel("WORKSPACE");
        section.setFont(new Font("SansSerif", Font.BOLD, 10));
        section.setForeground(new Color(115, 130, 153));
        section.setBorder(new EmptyBorder(32, 1, 10, 0));

        top.add(brand);
        top.add(staffLocation);
        top.add(section);
        styleNavigationButton(nowShowingButton);
        styleNavigationButton(bookingsButton);
        styleNavigationButton(paymentsButton);
        styleNavigationButton(issuedTicketsButton);
        top.add(nowShowingButton);
        top.add(Box.createVerticalStrut(6));
        top.add(bookingsButton);
        top.add(Box.createVerticalStrut(6));
        top.add(paymentsButton);
        top.add(Box.createVerticalStrut(6));
        top.add(issuedTicketsButton);

        nowShowingButton.addActionListener(event -> showNowShowing());
        bookingsButton.addActionListener(event -> {
            if (currentScreening == null) {
                JOptionPane.showMessageDialog(this, "Select a movie and screening first.",
                        "No screening selected", JOptionPane.INFORMATION_MESSAGE);
                showNowShowing();
                return;
            }
            showBooking();
        });
        paymentsButton.addActionListener(event -> {
            if (currentBooking == null) {
                JOptionPane.showMessageDialog(this, "Select seats and proceed from Booking first.",
                        "No tickets selected", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            showPayments(currentBooking);
        });
        issuedTicketsButton.addActionListener(event -> showIssuedTickets());

        JButton logoutButton = createLogoutButton();
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
        contentPanel.add(bookingPanel, BOOKING);
        contentPanel.add(paymentsPanel, PAYMENTS);
        contentPanel.add(issuedTicketsPanel, ISSUED_TICKETS);
        return contentPanel;
    }

    private void issueTickets(BookingSelection booking) {
        Storage.issueTickets(booking, staffFullName);
        issuedTicketsPanel.refreshTickets();
    }

    private void showIssuedTickets() {
        issuedTicketsPanel.refreshTickets();
        contentLayout.show(contentPanel, ISSUED_TICKETS);
        updateNavigationState(issuedTicketsButton);
    }

    private void showMovie(Movie movie) {
        theatrePanel.setMovie(movie);
        contentLayout.show(contentPanel, THEATRE);
        updateNavigationState(null);
    }

    public void setStaffAccount(LoginAccount staffAccount) {
        staffLocation.setText("NAALYA  ·  STAFF  ·  " + staffAccount.getUsername());
        staffFullName = staffAccount.getFullName();
    }

    private void showScreening(Screening screening) {
        currentScreening = screening;
        currentBooking = null;
        bookingPanel.setScreening(screening);
        contentLayout.show(contentPanel, BOOKING);
        updateNavigationState(bookingsButton);
    }

    private void showBooking() {
        contentLayout.show(contentPanel, BOOKING);
        updateNavigationState(bookingsButton);
    }

    private void showPayments(BookingSelection booking) {
        currentBooking = booking;
        paymentsPanel.setBooking(booking);
        contentLayout.show(contentPanel, PAYMENTS);
        updateNavigationState(paymentsButton);
    }

    private void showTheatre() {
        contentLayout.show(contentPanel, THEATRE);
        updateNavigationState(null);
    }

    private void finishPayment() {
        currentBooking = null;
        currentScreening = null;
        showNowShowing();
    }

    private void showNowShowing() {
        contentLayout.show(contentPanel, NOW_SHOWING);
        updateNavigationState(nowShowingButton);
    }

    private void updateNavigationState(JButton activeButton) {
        for (JButton button : new JButton[]{nowShowingButton, bookingsButton, paymentsButton,
            issuedTicketsButton}) {
            boolean active = button == activeButton;
            button.setBackground(active ? new Color(39, 49, 69) : SIDEBAR);
            button.setForeground(active ? Color.WHITE : MUTED);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 3, 0, 0, active ? ACCENT : SIDEBAR),
                    new EmptyBorder(12, 13, 12, 10)
            ));
        }
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

    private JButton createLogoutButton() {
        Color normalForeground = new Color(232, 153, 164);
        Color normalBorder = new Color(94, 55, 69);
        Color hoverBackground = new Color(92, 40, 54);
        Color hoverForeground = new Color(255, 224, 228);
        JButton button = new JButton("Log out", new LogoutIcon(normalForeground));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(11);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(normalForeground);
        button.setBackground(new Color(31, 25, 39));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(normalBorder),
                new EmptyBorder(10, 13, 10, 13)
        ));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                button.setBackground(hoverBackground);
                button.setForeground(hoverForeground);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(174, 76, 96)),
                        new EmptyBorder(10, 13, 10, 13)
                ));
                button.setIcon(new LogoutIcon(hoverForeground));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                button.setBackground(new Color(31, 25, 39));
                button.setForeground(normalForeground);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(normalBorder),
                        new EmptyBorder(10, 13, 10, 13)
                ));
                button.setIcon(new LogoutIcon(normalForeground));
            }
        });
        return button;
    }

    private static class LogoutIcon implements Icon {
        private final Color color;

        private LogoutIcon(Color color) {
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return 17;
        }

        @Override
        public int getIconHeight() {
            return 17;
        }

        @Override
        public void paintIcon(Component component, Graphics graphics, int x, int y) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setColor(color);
            g.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawRoundRect(x + 1, y + 2, 8, 12, 2, 2);
            g.drawLine(x + 6, y + 8, x + 15, y + 8);
            g.drawLine(x + 11, y + 4, x + 15, y + 8);
            g.drawLine(x + 11, y + 12, x + 15, y + 8);
            g.dispose();
        }
    }

    private void styleNavigationButton(JButton button) {
        button.setForeground(MUTED);
        button.setBackground(SIDEBAR);
        button.setBorder(new EmptyBorder(12, 16, 12, 10));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
    }
}
