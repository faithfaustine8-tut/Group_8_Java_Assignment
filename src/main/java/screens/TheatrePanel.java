package screens;

import models.Movie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TheatrePanel extends JPanel {
    private static final Color MIDNIGHT = new Color(16, 24, 40);
    private static final Color PANEL = new Color(23, 33, 52);
    private static final Color BORDER = new Color(55, 68, 91);
    private static final Color MUTED = new Color(166, 178, 197);
    private static final Color ACCENT = new Color(226, 55, 75);

    private final Runnable backAction;
    private final JLabel title = new JLabel();
    private final JLabel metadata = new JLabel();
    private final JLabel synopsis = new JLabel();

    public TheatrePanel(Runnable backAction) {
        this.backAction = backAction;
        setLayout(new BorderLayout());
        setBackground(MIDNIGHT);
        setBorder(new EmptyBorder(30, 34, 30, 34));
        add(createContent(), BorderLayout.NORTH);
    }

    public void setMovie(Movie movie) {
        title.setText(movie.getTitle());
        metadata.setText(movie.getGenre() + "  ·  " + movie.getAgeRating() + "  ·  "
                + movie.getDurationMinutes() + " min  ·  " + movie.getShowingInfo());
        synopsis.setText("<html><div style='width:640px'>" + movie.getSynopsis() + "</div></html>");
    }

    private JPanel createContent() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JButton backButton = new JButton("←  Back to Now Showing");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        backButton.setForeground(MUTED);
        backButton.setBackground(MIDNIGHT);
        backButton.setBorder(new EmptyBorder(8, 0, 8, 0));
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(event -> backAction.run());

        JLabel eyebrow = new JLabel("THEATRE & SHOWTIME AVAILABILITY");
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 10));
        eyebrow.setForeground(new Color(244, 190, 83));
        eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        metadata.setFont(new Font("SansSerif", Font.PLAIN, 13));
        metadata.setForeground(MUTED);
        metadata.setAlignmentX(Component.LEFT_ALIGNMENT);
        synopsis.setFont(new Font("SansSerif", Font.PLAIN, 14));
        synopsis.setForeground(new Color(203, 212, 226));
        synopsis.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(backButton);
        content.add(Box.createVerticalStrut(22));
        content.add(eyebrow);
        content.add(Box.createVerticalStrut(8));
        content.add(title);
        content.add(Box.createVerticalStrut(8));
        content.add(metadata);
        content.add(Box.createVerticalStrut(16));
        content.add(synopsis);
        content.add(Box.createVerticalStrut(34));
        content.add(createShowtimeSection());
        return content;
    }

    private JPanel createShowtimeSection() {
        JPanel section = new JPanel(new BorderLayout(0, 16));
        section.setOpaque(false);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        JLabel sectionTitle = new JLabel("Available theatres");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 19));
        sectionTitle.setForeground(Color.WHITE);
        JLabel hint = new JLabel("Today's sample schedule · confirm availability at the counter");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        hint.setForeground(MUTED);
        hint.setBorder(new EmptyBorder(5, 0, 0, 0));
        heading.add(sectionTitle);
        heading.add(hint);

        JPanel theatres = new JPanel(new GridLayout(0, 2, 16, 16));
        theatres.setOpaque(false);
        theatres.add(createTheatreCard("Theatre 1", "Standard · 120 seats",
                new String[]{"12:30 PM", "3:45 PM", "7:00 PM"}));
        theatres.add(createTheatreCard("Theatre 2", "Premium · 84 seats",
                new String[]{"1:15 PM", "4:30 PM", "8:15 PM"}));
        section.add(heading, BorderLayout.NORTH);
        section.add(theatres, BorderLayout.CENTER);
        return section;
    }

    private JPanel createTheatreCard(String name, String details, String[] showtimes) {
        JPanel card = new JPanel();
        card.setBackground(PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(18, 18, 18, 18)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel theatreName = new JLabel(name);
        theatreName.setFont(new Font("SansSerif", Font.BOLD, 16));
        theatreName.setForeground(Color.WHITE);
        JLabel theatreDetails = new JLabel(details);
        theatreDetails.setFont(new Font("SansSerif", Font.PLAIN, 12));
        theatreDetails.setForeground(MUTED);
        theatreDetails.setBorder(new EmptyBorder(5, 0, 16, 0));

        JPanel times = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        times.setOpaque(false);
        for (String showtime : showtimes) {
            JLabel time = new JLabel(showtime);
            time.setFont(new Font("SansSerif", Font.BOLD, 11));
            time.setForeground(Color.WHITE);
            time.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(86, 101, 125)),
                    new EmptyBorder(7, 9, 7, 9)
            ));
            times.add(time);
        }

        JLabel status = new JLabel("SCHEDULE PREVIEW");
        status.setFont(new Font("SansSerif", Font.BOLD, 9));
        status.setForeground(ACCENT);
        status.setBorder(new EmptyBorder(16, 0, 0, 0));

        card.add(theatreName);
        card.add(theatreDetails);
        card.add(times);
        card.add(status);
        return card;
    }
}
