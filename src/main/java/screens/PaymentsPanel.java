package screens;

import models.BookingSelection;
import models.Screening;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class PaymentsPanel extends JPanel {
    private static final Color MIDNIGHT = new Color(16, 24, 40);
    private static final Color PANEL = new Color(23, 33, 52);
    private static final Color BORDER = new Color(55, 68, 91);
    private static final Color MUTED = new Color(166, 178, 197);
    private static final Color ACCENT = new Color(226, 55, 75);
    private static final DateTimeFormatter SHOWTIME_FORMAT =
            DateTimeFormatter.ofPattern("EEE, d MMM yyyy · h:mm a");
    private static final String PAYMENT_VIEW = "payment";
    private static final String CONFIRMATION_VIEW = "confirmation";

    private final Runnable returnAction;
    private final Runnable backAction;
    private final Consumer<BookingSelection> ticketIssueAction;
    private final CardLayout viewLayout = new CardLayout();
    private final JPanel views = new JPanel(viewLayout);
    private final JLabel movieValue = new JLabel();
    private final JLabel theatreValue = new JLabel();
    private final JLabel screeningValue = new JLabel();
    private final JLabel seatsValue = new JLabel();
    private final JLabel ticketsValue = new JLabel();
    private final JLabel totalValue = new JLabel();
    private final JPanel methodDetails = new JPanel(new CardLayout());
    private final CardLayout methodLayout = (CardLayout) methodDetails.getLayout();
    private final ButtonGroup methodGroup = new ButtonGroup();
    private final JRadioButton cashButton = new JRadioButton("Cash");
    private final JRadioButton airtelButton = new JRadioButton("Airtel Money");
    private final JRadioButton mtnButton = new JRadioButton("MTN Mobile Money");
    private final JLabel cashAmountValue = new JLabel();
    private BookingSelection booking;
    private boolean ticketsIssued;

    public PaymentsPanel(Runnable returnAction, Runnable backAction) {
        this(returnAction, backAction, booking -> { });
    }

    public PaymentsPanel(Runnable returnAction, Runnable backAction,
                         Consumer<BookingSelection> ticketIssueAction) {
        this.returnAction = returnAction;
        this.backAction = backAction;
        this.ticketIssueAction = ticketIssueAction;
        setLayout(new BorderLayout());
        setBackground(MIDNIGHT);
        setBorder(new EmptyBorder(25, 30, 25, 30));
        views.setOpaque(false);
        views.add(createPaymentView(), PAYMENT_VIEW);
        views.add(createConfirmationView(), CONFIRMATION_VIEW);
        add(views, BorderLayout.CENTER);
    }

    public void setBooking(BookingSelection booking) {
        this.booking = booking;
        ticketsIssued = false;
        Screening screening = booking.getScreening();
        movieValue.setText(screening.getMovie().getTitle());
        theatreValue.setText(screening.getTheatreType());
        screeningValue.setText(screening.getStartTime().format(SHOWTIME_FORMAT));
        seatsValue.setText(String.join(", ", booking.getSeats()));
        ticketsValue.setText(Integer.toString(booking.getSeats().size()));
        totalValue.setText("UGX " + String.format("%,d", booking.getTotalAmount()));
        cashAmountValue.setText("Amount due: " + totalValue.getText());
        cashButton.setSelected(true);
        methodLayout.show(methodDetails, "Cash");
        viewLayout.show(views, PAYMENT_VIEW);
    }

    private JPanel createPaymentView() {
        JPanel page = new JPanel(new BorderLayout(0, 20));
        page.setOpaque(false);
        page.add(createHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(18, 0));
        body.setOpaque(false);
        body.add(createBookingSummary(), BorderLayout.CENTER);
        body.add(createPaymentOptions(), BorderLayout.EAST);
        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JButton backButton = new JButton("←  Back to booking");
        styleTextButton(backButton);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(event -> backAction.run());

        JLabel eyebrow = new JLabel("PAYMENT");
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 10));
        eyebrow.setForeground(new Color(244, 190, 83));
        eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel heading = new JLabel("Complete your booking");
        heading.setFont(new Font("SansSerif", Font.BOLD, 27));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel subtitle = new JLabel("Review the tickets and select a payment method.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(backButton);
        header.add(Box.createVerticalStrut(10));
        header.add(eyebrow);
        header.add(Box.createVerticalStrut(5));
        header.add(heading);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);
        return header;
    }

    private JPanel createBookingSummary() {
        JPanel card = createCard();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER), new EmptyBorder(20, 20, 20, 20)));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTHWEST;

        JLabel title = new JLabel("Ticket summary");
        title.setFont(new Font("SansSerif", Font.BOLD, 17));
        title.setForeground(Color.WHITE);
        constraints.insets = new Insets(0, 0, 17, 0);
        card.add(title, constraints);
        addSummaryRow(card, constraints, "MOVIE", movieValue);
        addSummaryRow(card, constraints, "THEATRE", theatreValue);
        addSummaryRow(card, constraints, "SCREENING", screeningValue);
        addSummaryRow(card, constraints, "SELECTED SEATS", seatsValue);
        addSummaryRow(card, constraints, "NUMBER OF TICKETS", ticketsValue);

        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER);
        constraints.gridy++;
        constraints.insets = new Insets(5, 0, 13, 0);
        card.add(separator, constraints);
        totalValue.setFont(new Font("SansSerif", Font.BOLD, 21));
        totalValue.setForeground(new Color(244, 190, 83));
        constraints.gridy++;
        constraints.insets = new Insets(0, 0, 0, 0);
        card.add(totalValue, constraints);
        constraints.gridy++;
        constraints.weighty = 1;
        card.add(Box.createVerticalGlue(), constraints);
        return card;
    }

    private void addSummaryRow(JPanel card, GridBagConstraints constraints,
                               String labelText, JLabel value) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 9));
        label.setForeground(MUTED);
        value.setFont(new Font("SansSerif", Font.PLAIN, 13));
        value.setForeground(Color.WHITE);
        value.setBorder(new EmptyBorder(3, 0, 13, 0));
        constraints.gridy++;
        constraints.insets = new Insets(0, 0, 3, 0);
        card.add(label, constraints);
        constraints.gridy++;
        constraints.insets = new Insets(0, 0, 6, 0);
        card.add(value, constraints);
    }

    private JPanel createPaymentOptions() {
        JPanel card = createCard();
        card.setPreferredSize(new Dimension(275, 0));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Payment method");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(title);
        card.add(Box.createVerticalStrut(13));

        JPanel choices = new JPanel(new GridLayout(0, 1, 0, 7));
        choices.setOpaque(false);
        addMethodChoice(choices, cashButton, "Cash");
        addMethodChoice(choices, airtelButton, "Airtel Money");
        addMethodChoice(choices, mtnButton, "MTN Mobile Money");
        card.add(choices);
        card.add(Box.createVerticalStrut(14));

        methodDetails.setOpaque(false);
        methodDetails.add(createMethodDetails("Cash payment",
                "Collect the amount shown in the ticket summary from the customer."), "Cash");
        methodDetails.add(createMethodDetails("Airtel Money",
                "Complete the transfer using your approved process, then enter an optional reference."),
                "Airtel Money");
        methodDetails.add(createMethodDetails("MTN Mobile Money (MoMo)",
                "Complete the transfer using your approved process, then enter an optional reference."),
                "MTN Mobile Money");
        methodDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(methodDetails);
        card.add(Box.createVerticalGlue());

        JButton completeButton = new JButton("Complete Payment");
        completeButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        completeButton.setForeground(Color.WHITE);
        completeButton.setBackground(ACCENT);
        completeButton.setFocusPainted(false);
        completeButton.setBorder(new EmptyBorder(12, 12, 12, 12));
        completeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        completeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        completeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        completeButton.addActionListener(event -> {
            if (booking != null) {
                if (!ticketsIssued) {
                    ticketIssueAction.accept(booking);
                    ticketsIssued = true;
                }
                viewLayout.show(views, CONFIRMATION_VIEW);
            }
        });
        card.add(completeButton);
        JLabel disclaimer = new JLabel("Demo only · no payment is processed");
        disclaimer.setFont(new Font("SansSerif", Font.PLAIN, 9));
        disclaimer.setForeground(MUTED);
        disclaimer.setBorder(new EmptyBorder(8, 0, 0, 0));
        disclaimer.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(disclaimer);
        return card;
    }

    private void addMethodChoice(JPanel choices, JRadioButton button, String method) {
        methodGroup.add(button);
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(PANEL);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(9, 10, 9, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(event -> methodLayout.show(methodDetails, method));
        choices.add(button);
    }

    private JPanel createMethodDetails(String titleText, String description) {
        JPanel details = new JPanel();
        details.setBackground(new Color(30, 43, 65));
        details.setBorder(new EmptyBorder(12, 11, 12, 11));
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        JLabel title = new JLabel(titleText);
        title.setFont(new Font("SansSerif", Font.BOLD, 12));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel note = new JLabel("<html><div style='width:220px'>" + description + "</div></html>");
        note.setFont(new Font("SansSerif", Font.PLAIN, 10));
        note.setForeground(MUTED);
        note.setBorder(new EmptyBorder(6, 0, 0, 0));
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        details.add(title);
        details.add(note);
        if (!titleText.equals("Cash payment")) {
            JTextField reference = new JTextField();
            reference.setFont(new Font("SansSerif", Font.PLAIN, 11));
            reference.setForeground(MUTED);
            reference.setCaretColor(Color.WHITE);
            reference.setBackground(PANEL);
            reference.setToolTipText("Optional transaction reference; do not enter a PIN");
            reference.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER), new EmptyBorder(7, 8, 7, 8)));
            reference.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
            reference.setAlignmentX(Component.LEFT_ALIGNMENT);
            String placeholder = "Transaction/reference (optional)";
            reference.setText(placeholder);
            reference.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event) {
                    if (reference.getText().equals(placeholder)) {
                        reference.setText("");
                        reference.setForeground(Color.WHITE);
                    }
                }

                @Override
                public void focusLost(FocusEvent event) {
                    if (reference.getText().isBlank()) {
                        reference.setText(placeholder);
                        reference.setForeground(MUTED);
                    }
                }
            });
            details.add(Box.createVerticalStrut(9));
            details.add(reference);
        } else {
            cashAmountValue.setFont(new Font("SansSerif", Font.BOLD, 11));
            cashAmountValue.setForeground(new Color(244, 190, 83));
            cashAmountValue.setBorder(new EmptyBorder(8, 0, 0, 0));
            cashAmountValue.setAlignmentX(Component.LEFT_ALIGNMENT);
            details.add(cashAmountValue);
        }
        return details;
    }

    private JPanel createConfirmationView() {
        JPanel page = new JPanel(new GridBagLayout());
        page.setOpaque(false);
        JPanel confirmation = createCard();
        confirmation.setLayout(new BoxLayout(confirmation, BoxLayout.Y_AXIS));
        confirmation.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER), new EmptyBorder(30, 34, 30, 34)));
        confirmation.setPreferredSize(new Dimension(420, 290));

        JLabel check = new JLabel("✓");
        check.setFont(new Font("SansSerif", Font.BOLD, 42));
        check.setForeground(new Color(91, 205, 151));
        check.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel("Payment confirmed");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel message = new JLabel("<html><div style='text-align:center;width:320px'>"
                + "This is a simulated confirmation. No payment has been processed.</div></html>");
        message.setFont(new Font("SansSerif", Font.PLAIN, 12));
        message.setForeground(MUTED);
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton done = new JButton("Return to Now Showing");
        done.setFont(new Font("SansSerif", Font.BOLD, 12));
        done.setForeground(Color.WHITE);
        done.setBackground(ACCENT);
        done.setFocusPainted(false);
        done.setBorder(new EmptyBorder(11, 16, 11, 16));
        done.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        done.setAlignmentX(Component.CENTER_ALIGNMENT);
        done.addActionListener(event -> returnAction.run());

        confirmation.add(check);
        confirmation.add(Box.createVerticalStrut(12));
        confirmation.add(title);
        confirmation.add(Box.createVerticalStrut(9));
        confirmation.add(message);
        confirmation.add(Box.createVerticalStrut(23));
        confirmation.add(done);
        page.add(confirmation);
        return page;
    }

    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER), new EmptyBorder(18, 18, 18, 18)));
        return card;
    }

    private void styleTextButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(MUTED);
        button.setBackground(MIDNIGHT);
        button.setBorder(new EmptyBorder(6, 0, 6, 0));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
