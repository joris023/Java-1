package com.example.eind_opdracht.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Showing implements Serializable {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String title;
    private int seats;
    /*private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");*/
    private String seatsLeft;
    private List<Place> takenSeats; // List to track taken seats for this showing
    private Boolean sixteenPlus;

    // Constructor to initialize LocalDateTime for start and end
    public Showing(LocalDateTime startDateTime, LocalDateTime endDateTime, String title, int seats, Boolean sixteenPlus) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.title = title;
        this.seats = seats;
        this.takenSeats = new ArrayList<>(); // Initialize as empty
        this.sixteenPlus = sixteenPlus;
    }

    // Method to add a taken seat
    public void addTakenSeat(Place place) {
        if (!takenSeats.contains(place)) {
            takenSeats.add(place);
        }
    }

    public void setSixteenPlus(Boolean sixteenPlus) {
        this.sixteenPlus = sixteenPlus;
    }

    public Boolean getSixteenPlus() {
        return sixteenPlus;
    }

    // Method to remove a seat (in case of cancellation, for instance)
    public void removeTakenSeat(Place place) {
        takenSeats.remove(place);
    }

    // Method to check if a seat is taken
    public boolean isSeatTaken(Place place) {
        return takenSeats.contains(place);
    }

    // Getter for takenSeats
    public List<Place> getTakenSeats() {
        return takenSeats;
    }

    // Other getters and setters as needed
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    // Getter for endDateTime as LocalDateTime
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    // Getter for startDateTime as String for display
    public String getFormattedStartDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return formatter.format(startDateTime);
    }

    // Getter for endDateTime as String for display
    public String getFormattedEndDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return formatter.format(endDateTime);
    }

    public String getTitle() {
        return title;
    }

    public int getSeats() {
        return seats;
    }

    public String getSeatsLeft() {
        return seats - takenSeats.size() + "/" + seats;
    }
}
