package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Booking {

    // Booking status constants
    public static final String PENDING = "PENDING";
    public static final String CONFIRMED = "CONFIRMED";
    public static final String CANCELLED = "CANCELLED";

    // Private attributes
    private String bookingId;
    private Customer customer;
    private NowShowing showing;
    private List<String> selectedSeats;
    private double totalAmount;
    private String status;

    // Constructor
    public Booking(String bookingId,
                   Customer customer,
                   NowShowing showing,
                   List<String> selectedSeats) {

        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Booking ID cannot be empty");
        }

        this.bookingId = bookingId.trim();

        this.customer = Objects.requireNonNull(
                customer, "Customer cannot be null");

        this.showing = Objects.requireNonNull(
                showing, "Showing cannot be null");

        this.selectedSeats = validateSeats(selectedSeats);

        // Calculate total booking cost
        this.totalAmount =
                this.selectedSeats.size() * showing.getTicketPrice();

        // Default booking status
        this.status = PENDING;
    }

    // Validate selected seats
    private List<String> validateSeats(List<String> seats) {

        Objects.requireNonNull(
                seats, "Seat selection cannot be null");

        if (seats.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one seat must be selected");
        }

        List<String> seatCopy = new ArrayList<>();
        Set<String> uniqueSeats = new HashSet<>();

        for (String seat : seats) {

            if (seat == null || seat.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Seat identifier cannot be empty");
            }

            String cleanedSeat = seat.trim();

            if (!uniqueSeats.add(cleanedSeat)) {
                throw new IllegalArgumentException(
                        "Duplicate seat selected: " + cleanedSeat);
            }

            seatCopy.add(cleanedSeat);
        }

        return seatCopy;
    }

    // Getters
    public String getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public NowShowing getShowing() {
        return showing;
    }

    public List<String> getSelectedSeats() {
        return Collections.unmodifiableList(selectedSeats);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    // Get number of selected seats
    public int getNumberOfSeats() {
        return selectedSeats.size();
    }

    // Update booking status
    public void setStatus(String status) {

        if (!PENDING.equals(status)
                && !CONFIRMED.equals(status)
                && !CANCELLED.equals(status)) {

            throw new IllegalArgumentException(
                    "Invalid booking status");
        }

        this.status = status;
    }

    // Display booking information
    @Override
    public String toString() {

        return "Booking {" +
                "Booking ID='" + bookingId + '\'' +
                ", Customer=" + customer +
                ", Showing=" + showing +
                ", Selected Seats=" + selectedSeats +
                ", Number of Seats=" + getNumberOfSeats() +
                ", Total Amount=" + totalAmount +
                ", Status='" + status + '\'' +
                '}';
    }
}

