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
            "Avatar | Fire And Ash",
            "Science Fiction",
            "PG-13",
            162,
            "Daily at 2:00 PM",
            "A journey through the world of Pandora.",
            "assets/avatar-fire-and-ash.jpeg"
        ),

        new Movie(
            "Titanic",
            "Romance",
            "PG-13",
            195,
            "Daily at 4:00 PM",
            "A romantic story aboard a famous ship.",
            "assets/titanic.jpeg"
        ),

        new Movie(
            "The Avengers",
            "Action",
            "PG-13",
            143,
            "Daily at 6:00 PM",
            "Heroes come together to save the world.",
            "assets/the-avengers.jpeg"
        ),

        new Movie(
            "Spider-Man | Brand New Day",
            "Action",
            "PG-13",
            121,
            "Daily at 8:00 PM",
            "A young hero protects his city.",
            "assets/brand-new-day.jpg"
        ),

        new Movie(
            "Dune 3",
            "Action",
            "PG-13",
            134,
            "Daily at 10:00 AM",
            "A journey through the desert planet of Arrakis.",
            "assets/dune3.jpg"
        ),

        new Movie(
            "Frozen",
            "Animation",
            "PG",
            102,
            "Daily at 12:00 PM",
            "Two sisters discover the power of love.",
            "assets/frozen.jpeg"
        ),

        new Movie(
            "Inception",
            "Science Fiction",
            "PG-13",
            148,
            "Daily at 9:00 PM",
            "A team enters people's dreams.",
            "assets/inception.jpg"
        )
    };

}
