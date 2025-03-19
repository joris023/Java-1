package com.example.eind_opdracht.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Ticket implements Serializable {
    private String purchaseDate;
    private String customerName;
    private Showing showing;
    private List<Place> places;
    private String showingName;
    private String numberOfTickets;
    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Ticket(LocalDateTime purchaseDate, String customerName, Showing showing, List<Place> places) {
        this.purchaseDate = formatter.format(purchaseDate);
        this.customerName = customerName;
        this.showing = showing;
        this.places = places;
        this.numberOfTickets = String.valueOf(places.size());
    }

    public Ticket(String customerName, Showing showing, List<Place> places) {
        this.customerName = customerName;
        this.showing = showing;
        this.places = places;
    }

    public String getShowingName() {
        return showing.getStartDateTime() + " " + showing.getTitle();
    }

    public String getShowingTitle() {return showing.getTitle();}

    public String getShowingStartTime() {return showing.getStartDateTime().format(formatter);}

    public String getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setShowing(Showing showing) {
        this.showing = showing;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Showing getShowing() {
        return showing;
    }

}
