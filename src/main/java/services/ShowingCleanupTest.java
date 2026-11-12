package services;

import models.Movie;
import models.NowShowing;
import models.Theatre;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Quick manual test with dummy data only. */
public class ShowingCleanupTest {
    public static void main(String[] args) {
        Movie movie = new Movie("Dummy Movie", "Action", "PG-13", 120, "Daily", "A dummy story");
        Theatre theatre = new Theatre("Hall 1", "Ground floor", List.of("A1", "A2"));
        LocalDateTime now = LocalDateTime.now();

        NowShowing ended = new NowShowing(movie, theatre, now.minusHours(3), 20000);      // finished 1 hour ago
        NowShowing inProgress = new NowShowing(movie, theatre, now.minusMinutes(30), 20000); // still playing
        NowShowing future = new NowShowing(movie, theatre, now.plusHours(2), 20000);      // not started

        List<NowShowing> showings = new ArrayList<>();
        showings.add(ended);
        showings.add(inProgress);
        showings.add(future);

        ShowingCleanupService cleaner = new ShowingCleanupService(showings,
                () -> System.out.println("Screen refresh requested"));

        System.out.println(cleaner.removeEndedShowings());   // 1
        System.out.println(showings.size());                 // 2
        System.out.println(showings.contains(ended));        // false
        System.out.println(showings.contains(inProgress));   // true

        // The timer cleans once immediately when started
        showings.add(new NowShowing(movie, theatre, now.minusHours(5), 20000));
        cleaner.start(60);                                   // prints "Screen refresh requested"
        System.out.println(showings.size());                 // 2
        System.out.println(cleaner.isRunning());             // true
        cleaner.stop();
        System.out.println(cleaner.isRunning());             // false
    }
}