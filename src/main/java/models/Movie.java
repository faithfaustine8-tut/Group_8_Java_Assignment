package models;

import java.time.Duration;
import java.time.LocalDate;

public class Movie {
    private final String title;
    private double cost;
    private final String rating;
    private final LocalDate releaseDate;
    private final Duration duration;
    private final String synopsis;
    private final String genre;
    private final String posterFileName;
    private final String showingInfo;

    public Movie(String title, String genre, String rating, int durationMinutes,
                 String showingInfo, String synopsis) {
        this(title, genre, rating, durationMinutes, showingInfo, synopsis, null);
    }

    public Movie(String title, String genre, String rating, int durationMinutes,
                 String showingInfo, String synopsis, String posterFileName) {
        this(title, 0.0, rating, null, Duration.ofMinutes(durationMinutes), synopsis,
                genre, posterFileName, showingInfo);
    }

    public Movie(String title, double cost, String rating, LocalDate releaseDate,
                 Duration duration, String synopsis, String genre, String posterFileName) {
        this(title, cost, rating, releaseDate, duration, synopsis, genre,
                posterFileName, "Daily");
    }

    public Movie(String title, double cost, String rating, LocalDate releaseDate,
                 Duration duration, String synopsis, String genre, String posterFileName,
                 String showingInfo) {
        validateString(title, "Title");
        validateString(rating, "Rating");
        validateString(synopsis, "Synopsis");
        validateString(genre, "Genre");
        validateString(showingInfo, "Showing info");
        validateCost(cost);
        if (duration == null || duration.isNegative() || duration.isZero()) {
            throw new IllegalArgumentException("Duration must be a positive time frame");
        }

        this.title = title;
        this.cost = cost;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.synopsis = synopsis;
        this.genre = genre;
        this.posterFileName = posterFileName;
        this.showingInfo = showingInfo;
    }

    public String getTitle() {
        return title;
    }

    public double getCost() {
        return cost;
    }

    public String getRating() {
        return rating;
    }

    public String getAgeRating() {
        return rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getDurationMinutes() {
        return Math.toIntExact(duration.toMinutes());
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getGenre() {
        return genre;
    }

    public String getPosterFileName() {
        return posterFileName;
    }

    public String getPosterPath() {
        return posterFileName;
    }

    public String getShowingInfo() {
        return showingInfo;
    }

    public void setCost(double cost) {
        validateCost(cost);
        this.cost = cost;
    }

    private static void validateCost(double cost) {
        if (!Double.isFinite(cost) || cost < 0) {
            throw new IllegalArgumentException("Base ticket cost must be finite and non-negative");
        }
    }

    private static void validateString(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
    }

    @Override
    public String toString() {
        return String.format("Movie: %s(%s) | Genre: %s | Duration: %d mins | Price: $%.2f",
                title, rating, genre, duration.toMinutes(), cost);
    }
}
