import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
 class Movie {
    
   private String title; // Movie title
   private double cost; // Base ticket cost
   private String rating; //Content rating
   private LocalDate releasDate; //Release date
   private Duration duration; //Runtime
   private String synopsis; // Short description
   private String genre; //Movie genre
   private String posterFileName; //Local poster image


}

class Theatre extends Movie
 {
    
  private  String name; // Audtorium name
  private String location; //Location
  private int capacity; //Maximum number of sets
  private List<String> availableSeats; //Seat indentifiers
  
}