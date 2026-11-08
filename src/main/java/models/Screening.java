package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Screening {
    private final Movie movie;
    private final String theatreType;
    private final LocalDateTime startTime;
    private final int ticketPrice;
    private final String availabilityStatus;

    public Screening(Movie movie, String theatreType, LocalDateTime startTime,
                     int ticketPrice, String availabilityStatus) {
        this.movie = Objects.requireNonNull(movie);
        this.theatreType = Objects.requireNonNull(theatreType);
        this.startTime = Objects.requireNonNull(startTime);
        if (ticketPrice <= 0) {
            throw new IllegalArgumentException("Ticket price must be greater than zero.");
        }
        this.ticketPrice = ticketPrice;
        this.availabilityStatus = Objects.requireNonNull(availabilityStatus);
    }

    public Movie getMovie() {
        return movie;
    }

    public String getTheatreType() {
        return theatreType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }
}
