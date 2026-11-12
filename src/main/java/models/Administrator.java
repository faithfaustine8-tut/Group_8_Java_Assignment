package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Administrator extends User {

    /** Fixed ticket price in UGX for every showing. */
    public static final double TICKET_PRICE = 20000;

    private String email;
    private String password; // dummy data only in this assignment

    public Administrator(String adminId, String name, String email, String password) {
        super(adminId, name);
        this.email = requireValidEmail(email);
        this.password = requireNonBlank(password, "Password");
    }

    public String getAdminId() {
        return getUserId();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = requireValidEmail(email);
    }

    public void setPassword(String password) {
        this.password = requireNonBlank(password, "Password");
    }

    // No getPassword(): other classes use checkPassword() instead.
    public boolean checkPassword(String attempt) {
        return password.equals(attempt);
    }

    // ---------- Movie management ----------

    /** Adds a movie. Rejects null and duplicate titles (ignoring case). */
    public void addMovie(List<Movie> movies, Movie movie) {
        Objects.requireNonNull(movies, "Movie list cannot be null");
        Objects.requireNonNull(movie, "Movie cannot be null");

        for (Movie existing : movies) {
            if (existing.getTitle().equalsIgnoreCase(movie.getTitle())) {
                throw new IllegalArgumentException("Movie already exists: " + movie.getTitle());
            }
        }
        movies.add(movie);
    }

    /**
     * Looks up a movie by title, ignoring case.
     * Returns the movie, or null if there is no movie with that title.
     */
    public Movie findMovieByTitle(List<Movie> movies, String title) {
        Objects.requireNonNull(movies, "Movie list cannot be null");
        String wanted = requireNonBlank(title, "Title");

        for (Movie m : movies) {
            if (m.getTitle().equalsIgnoreCase(wanted)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Removes the movie with the given title.
     * Returns true if it was found and removed, false if there is no such movie.
     * Refuses (IllegalStateException) if the movie still has upcoming showings.
     */
    public boolean removeMovie(List<Movie> movies, List<NowShowing> showings, String title) {
        Objects.requireNonNull(movies, "Movie list cannot be null");
        Objects.requireNonNull(showings, "Showing list cannot be null");
        String wanted = requireNonBlank(title, "Title");

        Movie found = findMovieByTitle(movies, wanted);
        if (found == null) {
            return false;
        }

        for (NowShowing s : showings) {
            if (s.getMovie() == found && s.isUpcoming()) {
                throw new IllegalStateException(
                        "Cannot remove a movie that still has upcoming showings: " + wanted);
            }
        }
        return movies.remove(found);
    }

    // ---------- Showing management ----------

    /**
     * Creates a showing at the fixed ticket price and adds it to the list.
     * The time must be in the future, and the showing must not overlap
     * another showing in the same theatre.
     */
    public NowShowing scheduleShowing(List<NowShowing> showings, Movie movie,
                                      Theatre theatre, LocalDateTime time) {
        Objects.requireNonNull(showings, "Showing list cannot be null");
        Objects.requireNonNull(movie, "Movie cannot be null");
        Objects.requireNonNull(theatre, "Theatre cannot be null");
        Objects.requireNonNull(time, "Showing time cannot be null");

        if (!time.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Showing time must be in the future");
        }

        LocalDateTime newEnd = time.plus(movie.getDuration());
        for (NowShowing existing : showings) {
            if (!existing.getTheatre().getName().equalsIgnoreCase(theatre.getName())) {
                continue; // different theatre, no clash
            }
            LocalDateTime existingStart = existing.getShowingTime();
            LocalDateTime existingEnd = existingStart.plus(existing.getMovie().getDuration());
            // Two time ranges overlap when each starts before the other ends
            if (time.isBefore(existingEnd) && existingStart.isBefore(newEnd)) {
                throw new IllegalStateException(
                        "Showing overlaps with another showing in " + theatre.getName());
            }
        }

        NowShowing showing = new NowShowing(movie, theatre, time, TICKET_PRICE);
        showings.add(showing);
        return showing;
    }

    /** Removes a showing. Returns true if it was in the list. */
    public boolean cancelShowing(List<NowShowing> showings, NowShowing showing) {
        Objects.requireNonNull(showings, "Showing list cannot be null");
        Objects.requireNonNull(showing, "Showing cannot be null");
        return showings.remove(showing);
    }

    /** Returns only the showings that have not started yet. */
    public List<NowShowing> getUpcomingShowings(List<NowShowing> showings) {
        Objects.requireNonNull(showings, "Showing list cannot be null");
        List<NowShowing> upcoming = new ArrayList<>();
        for (NowShowing s : showings) {
            if (s.isUpcoming()) {
                upcoming.add(s);
            }
        }
        return upcoming;
    }

    @Override
    public String getRole() {
        return "Administrator";
    }

    @Override
    public String toString() {
        // password intentionally not included
        return super.toString() + " [email=" + email + "]";
    }

    private static String requireValidEmail(String email) {
        String cleaned = requireNonBlank(email, "Email");
        if (!cleaned.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Email format is not valid: " + cleaned);
        }
        return cleaned;
    }
}