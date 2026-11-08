package models;

import java.util.List;
import java.util.Objects;

public class BookingSelection {
    private final Screening screening;
    private final List<String> seats;

    public BookingSelection(Screening screening, List<String> seats) {
        this.screening = Objects.requireNonNull(screening);
        this.seats = List.copyOf(seats);
        if (this.seats.isEmpty()) {
            throw new IllegalArgumentException("At least one seat must be selected.");
        }
    }

    public Screening getScreening() {
        return screening;
    }

    public List<String> getSeats() {
        return seats;
    }

    public int getTotalAmount() {
        return screening.getTicketPrice() * seats.size();
    }
}
