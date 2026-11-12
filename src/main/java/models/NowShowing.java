package models;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class NowShowing {

    
    private Movie movie;  // Composition
    public String name;
    private Theatre theatre;
    private LocalDateTime showingTime;
    private double ticketPrice;
    public NowShowing(Movie movie, Theatre theatre, 
                      LocalDateTime showingTime,
                      double ticketPrice) {

        this.movie = Objects.requireNonNull(movie,
                "Movie cannot be null");

        this.theatre = Objects.requireNonNull(theatre,
                "Theatre cannot be null");

        this.showingTime = Objects.requireNonNull(showingTime,
                "Showing time cannot be null");

        if (ticketPrice < 0 || !Double.isFinite(ticketPrice)) {
            throw new IllegalArgumentException(
                    "Ticket price cannot be negative or invalid");
        }

        this.ticketPrice = ticketPrice;
    }

    
    public Movie getMovie() {
        return movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public LocalDateTime getShowingTime() {
        return showingTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    // 4. Check whether showing is in the future
    public boolean isUpcoming() {
        return showingTime.isAfter(LocalDateTime.now());
    }

    
    @Override
    public String toString() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return "Now Showing {" +
                "Movie=" + movie +
                ", Theatre=" + theatre +
                ", Showing Time=" + showingTime.format(formatter) +
                ", Ticket Price=" + ticketPrice +
                '}';
    }
}

