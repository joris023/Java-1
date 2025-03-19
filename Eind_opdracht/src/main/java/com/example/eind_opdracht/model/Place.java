package com.example.eind_opdracht.model;

import java.io.Serializable;

public class Place implements Serializable {
    private int row;
    private int seatNumber;

    public Place(int row, int seatNumber) {
        this.row = row;
        this.seatNumber = seatNumber;
    }

    public int getRow() {
        return row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;
        Place place = (Place) o;
        return row == place.row && seatNumber == place.seatNumber;
    }

/*    @Override
    public int hashCode() {
        return Objects.hash(row, seatNumber);
    }*/
}
