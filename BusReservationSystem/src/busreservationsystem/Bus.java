/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busreservationsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bus in the bus reservation system.
 * This class manages the seats and provides methods to interact with them.
 */
public class Bus implements Serializable {
    private List<Seat> seats;

    // Constructor to initialize Bus with number of rows
    public Bus(int numRows) {
        seats = new ArrayList<>();
        char[] columns = {'A', 'B', 'C', 'D'};

        // Initialize seats for each row and column
        for (int row = 1; row <= numRows; row++) {
            for (char column : columns) {
                seats.add(new Seat(row, column));
            }
        }
    }

    // Method to get all seats on the bus
    public List<Seat> getSeats() {
        return seats;
    }

    // Find seat by row & column
    public Seat findSeat(int row, char column) {
        for (Seat seat : seats) {
            if (seat.getRow() == row && seat.getColumn() == Character.toUpperCase(column)) {
                return seat; // return seat object if found
            }
        }
        return null; // return null if seat is not found
    }

    // Find a seat by its seat number
    public Seat findSeatByNo(String seatNo) {
        for (Seat seat : seats) {
            if (seat.toString().equals(seatNo)) {
                return seat; // return seat object if found
            }
        }
        return null; // return null if seat is not found
    }

    // Reserve a seat for a passenger
    public void reserveSeatForPassenger(Passenger passenger, String seatNo) {
        Seat seat = findSeatByNo(seatNo);
        if (seat != null && !seat.isReserved()) {
            seat.reserve(passenger);
        } else {
            throw new IllegalStateException("Seat is either already reserved or does not exist.");
        }
    }

    // Check if a seat is reserved
    public boolean isSeatReserved(String seatNo) {
        Seat seat = findSeatByNo(seatNo);
        return seat != null && seat.isReserved();
    }

    // Cancel a reservation
    public void cancelReservation(String seatNo) {
        Seat seat = findSeatByNo(seatNo);
        if (seat != null && seat.isReserved()) {
            seat.cancelReservation();
        } else {
            throw new IllegalStateException("Seat is either not reserved or does not exist.");
        }
    }
}
