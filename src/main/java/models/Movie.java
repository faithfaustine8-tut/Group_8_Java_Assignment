package models;
package models;

import java.time.Duration;
import java.time.LocalDate;


 class Movie {
    
   private String title; // Movie title
   private double cost; // Base ticket cost
   private String rating; //Content rating
   private LocalDate releaseDate; //Release date
   private Duration duration; //Runtime
   private String synopsis; // Short description
   private String genre; //Movie genre
   private String posterFileName; //Local poster image

public Movie (String title, double cost, String rating, LocalDate releaseDate, Duration duration, String synopsis, String genre, String posterFileName) {
  validateString (title, "Title");
  validateString (rating, "Rating");
  validateString (synopsis, "Synopsis");
  validateString (genre, "Genre");
  validateString (posterFileName, "Poster filename");

  if (cost < 0) {
    throw new IllegalArgumentException ("Base ticket cost cannot be negative");
  }
  if (releaseDate == null) {
    throw new IllegalArgumentException("Release date cannot be null");
  }
  if(duration == null || duration.isNegative () || duration.isZero() ) {
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
}

public String getTitle() {return title;}
public double getCost() {return cost;}
public String getRating() {return rating;}
public LocalDate getReleaseDate() {return releaseDate;}
public Duration getDuration() {return duration;}
public String getSynopsis() {return synopsis;}
public String getGenre() {return genre;}
public String getPosterFileName() {return posterFileName;}


public void setCost(double cost) {
  if (cost < 0) throw new IllegalArgumentException("Cost cannot be negative");
  this.cost = cost;
}

private void validateString(String value, String fieldName) {
  if (value == null || value.strip().isEmpty()) {
    throw new IllegalArgumentException(fieldName + "cannot be blank");
  }
}

@Override 
public String toString () {
  return String.format("Movie: %s(%s) | Genre: %s | Duration: %d mins | Price: $%.2f", title, rating, genre, duration.toMinutes(), cost);
}

}

