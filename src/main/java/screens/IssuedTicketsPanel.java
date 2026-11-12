package screens;

import models.IssuedTicket;
import services.Storage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class IssuedTicketsPanel extends JPanel {
    private static final Color MIDNIGHT = new Color(16, 24, 40);
    private static final Color PANEL = new Color(23, 33, 52);
    private static final Color BORDER = new Color(55, 68, 91);
    private static final Color MUTED = new Color(166, 178, 197);
    private static final String[] COLUMNS = {
            "Ticket", "Movie", "Theatre", "Screening", "Seat", "Price (UGX)", "Teller", "Issued"
    };
    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");

    private final JLabel countLabel = new JLabel();
    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public IssuedTicketsPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(MIDNIGHT);
        setBorder(new EmptyBorder(30, 34, 28, 34));
        add(createHeader(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
        refreshTickets();
    }

    public void refreshTickets() {
        tableModel.setRowCount(0);
        for (IssuedTicket ticket : Storage.getIssuedTickets()) {
            tableModel.addRow(new Object[]{
                    ticket.ticketNumber(),
                    ticket.movieTitle(),
                    ticket.theatre(),
                    ticket.screeningTime().format(DATE_TIME_FORMAT),
                    ticket.seat(),
                    String.format("%,d", ticket.ticketPrice()),
                    ticket.tellerName(),
                    ticket.issuedAt().format(DATE_TIME_FORMAT)
            });
        }
        countLabel.setText(Storage.getIssuedTickets().size() + " tickets issued");
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel eyebrow = new JLabel("SALES RECORD");
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 10));
        eyebrow.setForeground(new Color(244, 190, 83));
        JLabel heading = new JLabel("Issued Tickets");
        heading.setFont(new Font("SansSerif", Font.BOLD, 30));
        heading.setForeground(Color.WHITE);
        countLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        countLabel.setForeground(MUTED);

        titles.add(eyebrow);
        titles.add(Box.createVerticalStrut(7));
        titles.add(heading);
        header.add(titles, BorderLayout.WEST);
        header.add(countLabel, BorderLayout.SOUTH);
        return header;
    }

    private JScrollPane createTable() {
        JTable table = new JTable(tableModel);
        table.setBackground(PANEL);
        table.setForeground(Color.WHITE);
        table.setGridColor(BORDER);
        table.setSelectionBackground(new Color(43, 62, 88));
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(34);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setBackground(new Color(30, 43, 65));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 11));
        table.getTableHeader().setReorderingAllowed(false);
        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getViewport().setBackground(PANEL);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
}