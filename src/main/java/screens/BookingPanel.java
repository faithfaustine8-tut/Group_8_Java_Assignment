package screens;

import models.BookingSelection;
import models.Screening;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;
import java.time.format.DateTimeFormatter;

public class BookingPanel extends JPanel {
    private static final Color MIDNIGHT = new Color(16, 24, 40);
    private static final Color PANEL = new Color(23, 33, 52);
    private static final Color BORDER = new Color(55, 68, 91);
    private static final Color MUTED = new Color(166, 178, 197);
    private static final Color ACCENT = new Color(226, 55, 75);
    private static final Color AVAILABLE = new Color(44, 115, 91);
    private static final Color BOOKED = new Color(75, 84, 99);
    private static final Color SELECTED = new Color(58, 112, 190);
    private static final DateTimeFormatter SHOWTIME_FORMAT =
            DateTimeFormatter.ofPattern("EEE, d MMM yyyy · h:mm a");
    private static final String[] ROWS = {"A", "B", "C", "D", "E", "F"};
    private static final int SEATS_PER_ROW = 8;
    private static final Set<String> BOOKED_SEATS = Set.of(
            "A3", "A4", "B6", "C2", "C7", "D4", "E1", "E2", "F5", "F6"
    );

    private final Consumer<BookingSelection> proceedAction;
    private final Runnable backAction;
    private final Set<String> selectedSeats = new LinkedHashSet<>();
    private final Map<String, JButton> seatButtons = new HashMap<>();
    private final JLabel movieTitle = new JLabel();
    private final JLabel screeningDetails = new JLabel();
    private final JLabel selectedSeatSummary = new JLabel();
    private final JLabel totalLabel = new JLabel();
    private final JButton continueButton = new JButton("Proceed to payment  →");
    private Screening screening;

    public BookingPanel(Consumer<BookingSelection> proceedAction, Runnable backAction) {
        this.proceedAction = proceedAction;
        this.backAction = backAction;
        setLayout(new BorderLayout(0, 22));
        setBackground(MIDNIGHT);
        setBorder(new EmptyBorder(25, 30, 25, 30));
        add(createHeader(), BorderLayout.NORTH);
        add(createBody(), BorderLayout.CENTER);
        refreshSummary();
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
        selectedSeats.clear();
        movieTitle.setText(screening.getMovie().getTitle());
        screeningDetails.setText(screening.getTheatreType() + "  ·  "
                + screening.getStartTime().format(SHOWTIME_FORMAT) + "  ·  UGX "
                + String.format("%,d", screening.getTicketPrice()) + " per ticket");
        updateSeatButtons();
        refreshSummary();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JButton backButton = new JButton("←  Back to theatre");
        styleTextButton(backButton);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(event -> backAction.run());

        JLabel eyebrow = new JLabel("BOOKING  ·  SEAT SELECTION");
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 10));
        eyebrow.setForeground(new Color(244, 190, 83));
        eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);
        movieTitle.setFont(new Font("SansSerif", Font.BOLD, 25));
        movieTitle.setForeground(Color.WHITE);
        movieTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        screeningDetails.setFont(new Font("SansSerif", Font.PLAIN, 12));
        screeningDetails.setForeground(MUTED);
        screeningDetails.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(backButton);
        header.add(Box.createVerticalStrut(10));
        header.add(eyebrow);
        header.add(Box.createVerticalStrut(5));
        header.add(movieTitle);
        header.add(Box.createVerticalStrut(5));
        header.add(screeningDetails);
        return header;
    }

    private JPanel createBody() {
        JPanel body = new JPanel(new BorderLayout(18, 0));
        body.setOpaque(false);
        body.add(createSeatArea(), BorderLayout.CENTER);
        body.add(createSummaryCard(), BorderLayout.EAST);
        return body;
    }

    private JPanel createSeatArea() {
        JPanel area = new JPanel(new BorderLayout(0, 16));
        area.setBackground(PANEL);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER), new EmptyBorder(16, 14, 14, 14)));

        JLabel screen = new JLabel("SCREEN", SwingConstants.CENTER);
        screen.setFont(new Font("SansSerif", Font.BOLD, 10));
        screen.setForeground(MUTED);
        screen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(90, 106, 130)),
                new EmptyBorder(0, 0, 8, 0)));
        area.add(screen, BorderLayout.NORTH);

        JPanel seatRows = new JPanel();
        seatRows.setOpaque(false);
        seatRows.setLayout(new BoxLayout(seatRows, BoxLayout.Y_AXIS));
        for (String row : ROWS) {
            JPanel seatRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
            seatRow.setOpaque(false);
            JLabel rowLabel = new JLabel(row);
            rowLabel.setForeground(MUTED);
            rowLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
            rowLabel.setPreferredSize(new Dimension(15, 30));
            seatRow.add(rowLabel);
            for (int number = 1; number <= SEATS_PER_ROW; number++) {
                String seat = row + number;
                JButton seatButton = new JButton(Integer.toString(number));
                seatButton.setPreferredSize(new Dimension(34, 30));
                seatButton.setFont(new Font("SansSerif", Font.BOLD, 10));
                seatButton.setFocusPainted(false);
                seatButton.setBorder(BorderFactory.createLineBorder(new Color(88, 106, 129)));
                seatButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                seatButton.addActionListener(event -> toggleSeat(seat));
                seatButtons.put(seat, seatButton);
                seatRow.add(seatButton);
            }
            seatRows.add(seatRow);
        }
        JPanel seatContainer = new JPanel(new GridBagLayout());
        seatContainer.setOpaque(false);
        seatContainer.add(seatRows);
        area.add(seatContainer, BorderLayout.CENTER);
        area.add(createLegend(), BorderLayout.SOUTH);
        return area;
    }

    private JPanel createLegend() {
        JPanel legend = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        legend.setOpaque(false);
        legend.add(createLegendItem("Available", AVAILABLE));
        legend.add(createLegendItem("Booked", BOOKED));
        legend.add(createLegendItem("Selected", SELECTED));
        return legend;
    }

    private JPanel createLegendItem(String text, Color color) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        item.setOpaque(false);
        JLabel swatch = new JLabel(" ");
        swatch.setOpaque(true);
        swatch.setBackground(color);
        swatch.setPreferredSize(new Dimension(12, 12));
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 10));
        label.setForeground(MUTED);
        item.add(swatch);
        item.add(label);
        return item;
    }

    private JPanel createSummaryCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PANEL);
        card.setPreferredSize(new Dimension(205, 0));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER), new EmptyBorder(17, 15, 17, 15)));

        JLabel heading = new JLabel("Your selection");
        heading.setFont(new Font("SansSerif", Font.BOLD, 16));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel selectedLabel = new JLabel("SELECTED SEATS");
        selectedLabel.setFont(new Font("SansSerif", Font.BOLD, 9));
        selectedLabel.setForeground(MUTED);
        selectedLabel.setBorder(new EmptyBorder(19, 0, 6, 0));
        selectedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectedSeatSummary.setFont(new Font("SansSerif", Font.PLAIN, 12));
        selectedSeatSummary.setForeground(new Color(220, 228, 239));
        selectedSeatSummary.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setBorder(new EmptyBorder(12, 0, 12, 0));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        continueButton.setFont(new Font("SansSerif", Font.BOLD, 11));
        continueButton.setForeground(Color.WHITE);
        continueButton.setBackground(ACCENT);
        continueButton.setFocusPainted(false);
        continueButton.setBorder(new EmptyBorder(11, 9, 11, 9));
        continueButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        continueButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        continueButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        continueButton.addActionListener(event -> {
            if (screening != null && !selectedSeats.isEmpty()) {
                proceedAction.accept(new BookingSelection(screening, new ArrayList<>(selectedSeats)));
            }
        });

        JLabel hint = new JLabel("<html><div style='width:165px'>Choose one or more available seats to continue.</div></html>");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 10));
        hint.setForeground(MUTED);
        hint.setBorder(new EmptyBorder(10, 0, 0, 0));
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(heading);
        card.add(selectedLabel);
        card.add(selectedSeatSummary);
        card.add(separator);
        card.add(totalLabel);
        card.add(Box.createVerticalStrut(18));
        card.add(continueButton);
        card.add(hint);
        return card;
    }

    private void toggleSeat(String seat) {
        if (BOOKED_SEATS.contains(seat)) {
            return;
        }
        if (!selectedSeats.add(seat)) {
            selectedSeats.remove(seat);
        }
        updateSeatButtons();
        refreshSummary();
    }

    private void updateSeatButtons() {
        for (Map.Entry<String, JButton> entry : seatButtons.entrySet()) {
            String seat = entry.getKey();
            JButton button = entry.getValue();
            boolean booked = BOOKED_SEATS.contains(seat);
            boolean selected = selectedSeats.contains(seat);
            button.setEnabled(!booked);
            button.setBackground(booked ? BOOKED : selected ? SELECTED : AVAILABLE);
            button.setForeground(booked ? new Color(167, 174, 185) : Color.WHITE);
            button.setToolTipText(booked ? seat + " · booked"
                    : selected ? seat + " · selected" : seat + " · available");
            button.setCursor(Cursor.getPredefinedCursor(
                    booked ? Cursor.DEFAULT_CURSOR : Cursor.HAND_CURSOR));
        }
    }

    private void refreshSummary() {
        selectedSeatSummary.setText(selectedSeats.isEmpty()
                ? "No seats selected" : String.join(", ", selectedSeats));
        int total = screening == null ? 0 : screening.getTicketPrice() * selectedSeats.size();
        totalLabel.setText("UGX " + String.format("%,d", total));
        continueButton.setEnabled(screening != null && !selectedSeats.isEmpty());
        continueButton.setToolTipText(continueButton.isEnabled()
                ? null : "Select at least one available seat");
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
