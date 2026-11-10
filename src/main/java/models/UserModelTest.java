package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Quick manual test with dummy data only. */
public class UserModelTest {
    public static void main(String[] args) {
        Customer c = new Customer("C001", "Jane Doe", "0772 123 456");
        Administrator a = new Administrator("A001", "Admin One", "admin@example.com", "dummyAdmin");

        // Polymorphism: both are Users
        User[] users = { c, a };
        for (User u : users) {
            System.out.println(u + " -> " + u.getRole());
        }

        System.out.println(a.checkPassword("dummyAdmin")); // true
        System.out.println(a.checkPassword("wrong"));      // false
        System.out.println(c.getCustomerId());             // C001

        try {
            new Customer("C002", "   ", "0772123456");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        try {
            c.setPhone("abc");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        try {
            a.setEmail("not-an-email");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // ---------- Administrator movie and showing management ----------
        List<Movie> movies = new ArrayList<>();
        List<NowShowing> showings = new ArrayList<>();
        Movie movie = new Movie("Dummy Movie", "Action", "PG-13", 120, "Daily", "A dummy story");
        Theatre theatre = new Theatre("Hall 1", "Ground floor", List.of("A1", "A2", "A3"));

        a.addMovie(movies, movie);
        try {
            a.addMovie(movies, movie);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage()); // duplicate
        }

        // findMovieByTitle ignores case and returns null when not found
        System.out.println(a.findMovieByTitle(movies, "dummy movie") == movie); // true
        System.out.println(a.findMovieByTitle(movies, "No Such Movie"));        // null

        // equals / hashCode: same kind of user with the same ID
        Customer sameId = new Customer("C001", "Other Name", "0700000000");
        System.out.println(c.equals(sameId));                    // true
        System.out.println(c.hashCode() == sameId.hashCode());   // true
        System.out.println(c.equals(a));                         // false
        List<Customer> customers = new ArrayList<>();
        customers.add(c);
        System.out.println(customers.contains(sameId));          // true

        LocalDateTime tomorrow6pm = LocalDateTime.now().plusDays(1)
                .withHour(18).withMinute(0).withSecond(0).withNano(0);

        NowShowing first = a.scheduleShowing(showings, movie, theatre, tomorrow6pm);
        System.out.println("Scheduled price: " + first.getTicketPrice()); // 20000.0

        try {
            a.scheduleShowing(showings, movie, theatre, tomorrow6pm.plusHours(1)); // overlaps
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        NowShowing second = a.scheduleShowing(showings, movie, theatre, tomorrow6pm.plusHours(3)); // fine
        System.out.println("Upcoming showings: " + a.getUpcomingShowings(showings).size()); // 2

        try {
            a.scheduleShowing(showings, movie, theatre, LocalDateTime.now().minusDays(1)); // past
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            a.removeMovie(movies, showings, "Dummy Movie"); // still has upcoming showings
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println(a.cancelShowing(showings, first));  // true
        System.out.println(a.cancelShowing(showings, second)); // true
        System.out.println(a.removeMovie(movies, showings, "Dummy Movie")); // true
    }
}