package models;

import java.time.LocalDateTime;
import java.util.Objects;

public record IssuedTicket(
        String ticketNumber,
        String movieTitle,
        String theatre,
        LocalDateTime screeningTime,
        String seat,
        int ticketPrice,
        String tellerName,
        LocalDateTime issuedAt) {

    public IssuedTicket {
        Objects.requireNonNull(ticketNumber);
        Objects.requireNonNull(movieTitle);
        Objects.requireNonNull(theatre);
        Objects.requireNonNull(screeningTime);
        Objects.requireNonNull(seat);
        Objects.requireNonNull(tellerName);
        Objects.requireNonNull(issuedAt);
        if (ticketPrice <= 0) {
            throw new IllegalArgumentException("Ticket price must be greater than zero.");
        }
    }
}