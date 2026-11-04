package screens;

import models.Movie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class NowShowingPanel extends JPanel {
    private static final Color MIDNIGHT = new Color(16, 24, 40);
    private static final Color PANEL = new Color(23, 33, 52);
    private static final Color BORDER = new Color(55, 68, 91);
    private static final Color MUTED = new Color(166, 178, 197);
    private static final Color ACCENT = new Color(226, 55, 75);

    private final Consumer<Movie> movieSelection;
    private final List<Movie> movies = createMovies();
    private final JTextField searchField = new JTextField();
    private final JComboBox<String> genreFilter = new JComboBox<>();
    private final JComboBox<String> ratingFilter = new JComboBox<>();
    private final JPanel movieGrid = new JPanel();
    private final JLabel emptyState = new JLabel("No movies match your search or filters.", SwingConstants.CENTER);
    private final CardLayout resultsLayout = new CardLayout();
    private final JPanel resultsPanel = new JPanel(resultsLayout);
    private final JScrollPane scrollPane = new JScrollPane(movieGrid);
    private int currentColumns;

    public NowShowingPanel(Consumer<Movie> movieSelection) {
        this.movieSelection = movieSelection;
        setLayout(new BorderLayout());
        setBackground(MIDNIGHT);
        setBorder(new EmptyBorder(30, 34, 28, 34));
        add(createHeader(), BorderLayout.NORTH);
        add(createResults(), BorderLayout.CENTER);
        installActions();
        updateMovieGrid();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel eyebrow = new JLabel("CINEMA OPERATIONS");
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 10));
        eyebrow.setForeground(new Color(244, 190, 83));
        JLabel heading = new JLabel("Now Showing");
        heading.setFont(new Font("SansSerif", Font.BOLD, 30));
        heading.setForeground(Color.WHITE);
        JLabel subtitle = new JLabel("Select a film to view its theatre and showtime availability.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        JPanel filters = new JPanel(new GridBagLayout());
        filters.setOpaque(false);
        filters.setBorder(new EmptyBorder(23, 0, 20, 0));
        styleSearchField(searchField);
        styleFilter(genreFilter);
        styleFilter(ratingFilter);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 0, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridx = 0;
        filters.add(searchField, constraints);
        constraints.weightx = 0;
        constraints.gridx = 1;
        filters.add(genreFilter, constraints);
        constraints.gridx = 2;
        constraints.insets = new Insets(0, 0, 0, 0);
        filters.add(ratingFilter, constraints);

        header.add(eyebrow);
        header.add(Box.createVerticalStrut(7));
        header.add(heading);
        header.add(Box.createVerticalStrut(6));
        header.add(subtitle);
        header.add(filters);
        populateFilterValues();
        return header;
    }

    private JPanel createResults() {
        movieGrid.setBackground(MIDNIGHT);
        movieGrid.setBorder(new EmptyBorder(2, 1, 18, 1));
        movieGrid.setLayout(new GridLayout(0, 3, 18, 18));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(MIDNIGHT);
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);

        emptyState.setFont(new Font("SansSerif", Font.PLAIN, 15));
        emptyState.setForeground(MUTED);
        resultsPanel.setOpaque(false);
        resultsPanel.add(scrollPane, "movies");
        resultsPanel.add(emptyState, "empty");
        scrollPane.getViewport().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                updateColumnCount();
            }
        });
        return resultsPanel;
    }

    private void installActions() {
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                updateMovieGrid();
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                updateMovieGrid();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                updateMovieGrid();
            }
        });
        genreFilter.addActionListener(event -> updateMovieGrid());
        ratingFilter.addActionListener(event -> updateMovieGrid());
    }

    private void populateFilterValues() {
        genreFilter.addItem("All genres");
        movies.stream().map(Movie::getGenre).distinct().sorted().forEach(genreFilter::addItem);
        ratingFilter.addItem("All ratings");
        movies.stream().map(Movie::getAgeRating).distinct().sorted().forEach(ratingFilter::addItem);
    }

    private void updateMovieGrid() {
        String query = searchField.getText().trim().toLowerCase(Locale.ROOT);
        String selectedGenre = (String) genreFilter.getSelectedItem();
        String selectedRating = (String) ratingFilter.getSelectedItem();
        List<Movie> filteredMovies = movies.stream()
                .filter(movie -> movie.getTitle().toLowerCase(Locale.ROOT).contains(query))
                .filter(movie -> selectedGenre == null || selectedGenre.equals("All genres")
                        || movie.getGenre().equals(selectedGenre))
                .filter(movie -> selectedRating == null || selectedRating.equals("All ratings")
                        || movie.getAgeRating().equals(selectedRating))
                .toList();

        movieGrid.removeAll();
        if (filteredMovies.isEmpty()) {
            resultsLayout.show(resultsPanel, "empty");
        } else {
            for (Movie movie : filteredMovies) {
                movieGrid.add(createMovieCard(movie));
            }
            updateColumnCount();
            resultsLayout.show(resultsPanel, "movies");
        }
        movieGrid.revalidate();
        movieGrid.repaint();
    }

    private JPanel createMovieCard(Movie movie) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(0, 0, 14, 0)
        ));
        JPanel poster = new PosterPanel(movie);
        poster.setPreferredSize(new Dimension(260, 190));

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setBorder(new EmptyBorder(13, 14, 0, 14));

        JLabel title = new JLabel(movie.getTitle());
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel genre = new JLabel(movie.getGenre() + "  ·  " + movie.getDurationMinutes() + " min");
        genre.setFont(new Font("SansSerif", Font.PLAIN, 12));
        genre.setForeground(MUTED);
        genre.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel showing = new JLabel(movie.getShowingInfo());
        showing.setFont(new Font("SansSerif", Font.PLAIN, 12));
        showing.setForeground(new Color(203, 212, 226));
        showing.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel rating = new JLabel(movie.getAgeRating());
        rating.setFont(new Font("SansSerif", Font.BOLD, 10));
        rating.setForeground(new Color(244, 190, 83));
        rating.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 97, 54)),
                new EmptyBorder(3, 7, 3, 7)
        ));
        rating.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton selectButton = new JButton("View theatres  →");
        selectButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        selectButton.setForeground(Color.WHITE);
        selectButton.setBackground(ACCENT);
        selectButton.setFocusPainted(false);
        selectButton.setBorder(new EmptyBorder(9, 12, 9, 12));
        selectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        selectButton.addActionListener(event -> movieSelection.accept(movie));

        details.add(title);
        details.add(Box.createVerticalStrut(5));
        details.add(genre);
        details.add(Box.createVerticalStrut(10));
        details.add(rating);
        details.add(Box.createVerticalStrut(9));
        details.add(showing);
        details.add(Box.createVerticalStrut(13));
        details.add(selectButton);
        card.add(poster, BorderLayout.NORTH);
        card.add(details, BorderLayout.CENTER);
        return card;
    }

    private void updateColumnCount() {
        int width = scrollPane.getViewport().getWidth();
        int columns = width >= 900 ? 3 : width >= 590 ? 2 : 1;
        if (columns != currentColumns) {
            currentColumns = columns;
            movieGrid.setLayout(new GridLayout(0, currentColumns, 18, 18));
            movieGrid.revalidate();
        }
    }

    private void styleSearchField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBackground(PANEL);
        field.setToolTipText("Search by movie title");
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(10, 12, 10, 12)
        ));
        field.setPreferredSize(new Dimension(200, 42));
    }

    private void styleFilter(JComboBox<String> filter) {
        filter.setFont(new Font("SansSerif", Font.PLAIN, 12));
        filter.setForeground(Color.WHITE);
        filter.setBackground(PANEL);
        filter.setPreferredSize(new Dimension(138, 42));
        filter.setBorder(BorderFactory.createLineBorder(BORDER));
        filter.setFocusable(false);
    }

    private List<Movie> createMovies() {
        return Arrays.asList(
                new Movie("After", "Drama", "13+", 108, "Today · 4 showtimes",
                        "A moving story set beneath a distant sky.", "assets/after.jpg"),
                new Movie("Archer", "Action", "16+", 112, "Today · 3 showtimes",
                        "A survivor faces a dangerous fight to make it home.", "assets/archer.jpg"),
                new Movie("Spider-Man: Brand New Day", "Action", "13+", 126, "Today · 2 showtimes",
                        "A masked hero faces a new challenge in the city.", "assets/brand-new-day.jpg"),
                new Movie("Dune 3", "Adventure", "PG", 98, "Today · 4 showtimes",
                        "A young explorer discovers a remarkable world beyond the forest.", "assets/dune3.jpg"),
                new Movie("Laugh Track", "Comedy", "13+", 101, "Today · 3 showtimes",
                        "A small comedy club gets one last chance to make it big."),
                new Movie("Deep Blue", "Drama", "PG", 119, "Today · 2 showtimes",
                        "A coastal community comes together to protect its home."),
                new Movie("Night Shift", "Thriller", "18+", 115, "Today · 3 showtimes",
                        "One overnight shift turns into a test of nerve and trust."),
                new Movie("Starlight Academy", "Family", "PG", 95, "Today · 5 showtimes",
                        "A curious class learns that teamwork can change everything."),
                new Movie("The Great Getaway", "Action", "13+", 122, "Today · 2 showtimes",
                        "An unlikely team plans an escape with everything on the line."),
                new Movie("Sunday at Home", "Family", "PG", 89, "Today · 3 showtimes",
                        "A warm-hearted story about family, food and making time for each other.")
        );
    }

    private static class PosterPanel extends JPanel {
        private final Movie movie;
        private final BufferedImage posterImage;
        private final Color[] palette = {
                new Color(52, 73, 110), new Color(105, 50, 76), new Color(50, 90, 91),
                new Color(91, 67, 45), new Color(67, 63, 108)
        };

        private PosterPanel(Movie movie) {
            this.movie = movie;
            setOpaque(true);
            posterImage = loadPoster(movie.getPosterPath());
        }

        private BufferedImage loadPoster(String posterPath) {
            if (posterPath == null) {
                return null;
            }
            try {
                return ImageIO.read(new File(posterPath));
            } catch (IOException exception) {
                System.err.println("Unable to load movie poster " + posterPath + ": " + exception.getMessage());
                return null;
            }
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            if (posterImage != null) {
                double scale = Math.max(
                        (double) getWidth() / posterImage.getWidth(),
                        (double) getHeight() / posterImage.getHeight()
                );
                int imageWidth = (int) Math.ceil(posterImage.getWidth() * scale);
                int imageHeight = (int) Math.ceil(posterImage.getHeight() * scale);
                int x = (getWidth() - imageWidth) / 2;
                int y = (getHeight() - imageHeight) / 2;
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(posterImage, x, y, imageWidth, imageHeight, this);
                g.dispose();
                return;
            }
            int colorIndex = Math.abs(movie.getTitle().hashCode()) % palette.length;
            g.setPaint(new GradientPaint(0, 0, palette[colorIndex], getWidth(), getHeight(),
                    new Color(22, 30, 47)));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(255, 255, 255, 22));
            g.fillOval(getWidth() - 112, -46, 156, 156);
            g.fillOval(getWidth() - 74, 34, 96, 96);
            g.setColor(new Color(255, 255, 255, 180));
            g.setFont(new Font("SansSerif", Font.BOLD, 10));
            g.drawString("MULTIPLEX  ·  NOW SHOWING", 14, getHeight() - 15);
            g.dispose();
        }
    }
}
