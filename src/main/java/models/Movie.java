package models;

public class Movie {
    private final String title;
    private final String genre;
    private final String ageRating;
    private final int durationMinutes;
    private final String showingInfo;
    private final String synopsis;
    private final String posterPath;

    public Movie(String title, String genre, String ageRating, int durationMinutes,
                 String showingInfo, String synopsis) {
        this(title, genre, ageRating, durationMinutes, showingInfo, synopsis, null);
    }

    public Movie(String title, String genre, String ageRating, int durationMinutes,
                 String showingInfo, String synopsis, String posterPath) {
        this.title = title;
        this.genre = genre;
        this.ageRating = ageRating;
        this.durationMinutes = durationMinutes;
        this.showingInfo = showingInfo;
        this.synopsis = synopsis;
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getShowingInfo() {
        return showingInfo;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getPosterPath() {
        return posterPath;
    }
}