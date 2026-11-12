package services;

import models.Movie;
import models.BookingSelection;
import models.IssuedTicket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static final ArrayList<IssuedTicket> issuedTickets = new ArrayList<>();
    private static int nextTicketNumber = 1;

    public static synchronized List<IssuedTicket> getIssuedTickets() {
        return List.copyOf(issuedTickets);
    }

    public static synchronized void issueTickets(BookingSelection booking, String tellerName) {
        LocalDateTime issuedAt = LocalDateTime.now();
        for (String seat : booking.getSeats()) {
            IssuedTicket ticket = new IssuedTicket(
                    String.format("TKT-%06d", nextTicketNumber++),
                    booking.getScreening().getMovie().getTitle(),
                    booking.getScreening().getTheatreType(),
                    booking.getScreening().getStartTime(),
                    seat,
                    booking.getScreening().getTicketPrice(),
                    tellerName,
                    issuedAt
            );
            issuedTickets.add(ticket);
        }
    }

    // Array containing movies in the system
    public static Movie[] movies = {

        new Movie(
            "Avatar",
            "Science Fiction",
            "PG-13",
            162,
            "Daily at 2:00 PM",
            "A journey through the world of Pandora."
        ),

        new Movie(
            "Titanic",
            "Romance",
            "PG-13",
            195,
            "Daily at 4:00 PM",
            "A romantic story aboard a famous ship."
        ),

        new Movie(
            "The Avengers",
            "Action",
            "PG-13",
            143,
            "Daily at 6:00 PM",
            "Heroes come together to save the world."
        ),

        new Movie(
            "Spider-Man",
            "Action",
            "PG-13",
            121,
            "Daily at 8:00 PM",
            "A young hero protects his city.",
            "assets/brand-new-day.jpg"
        ),

        new Movie(
            "Black Panther",
            "Action",
            "PG-13",
            134,
            "Daily at 10:00 AM",
            "A king protects the nation of Wakanda."
        ),

        new Movie(
            "Frozen",
            "Animation",
            "PG",
            102,
            "Daily at 12:00 PM",
            "Two sisters discover the power of love."
        ),

        new Movie(
            "Inception",
            "Science Fiction",
            "PG-13",
            148,
            "Daily at 9:00 PM",
            "A team enters people's dreams."
        )
    };

}
