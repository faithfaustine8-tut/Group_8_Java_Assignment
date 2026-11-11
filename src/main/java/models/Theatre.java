package models;


import java.util.List;
import java.util.ArrayList;

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
