import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
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

class Theatre
 {
  private  String name; // Audtorium name
  private String location; //Location
  private int capacity; //Maximum number of sets
  private List<String> availableSeats; //Seat indentifiers

  public Theatre(String name, String location, List<String>availableSeats) {
    if (name == null || name.strip().isEmpty()) throw new IllegalArgumentException("Name cannot be balnk");
    if (location == null || location.strip().isEmpty()) throw new IllegalArgumentException("Location cannot be blank");
    if (availableSeats == null || availableSeats.isEmpty()) {
      throw new IllegalArgumentException("Seat inventory must contain atleast one seat");
    }

    this.name = name;
    this.location = location;
    this.availableSeats = new ArrayList<>(availableSeats);
    this.capacity = availableSeats.size();
  }

  public String getName() {return name;}
  public String getLocation() {return location;}
  public int getCapacity() {return capacity;}
  public List<String> getAvailableSeats() { return new ArrayList<>(this.availableSeats);} 
  
  public void setName (String name) {
    if (name == null || name.strip().isEmpty()) throw new IllegalArgumentException("Name cannot be blank");
    this.name = name;
  }

  public void setLocation (String location) {
    if (location == null || location.strip().isEmpty()) throw new IllegalArgumentException("Location cannot be blank");
    this.location = location;
  }

  @Override 
  public String toString () {
    return String.format("Theatre: %s (%s) | Capacity: %d seats", name, location, capacity);
  }
  }
