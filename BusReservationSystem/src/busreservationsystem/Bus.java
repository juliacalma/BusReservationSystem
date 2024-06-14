/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busreservationsystem;

/**
 *
 * @author juliacalma
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bus in the bus reservation system.
 * This class manages the seats and provides methods to interact with them.
 */
public class Bus implements Serializable {
    private List<Seat> seats;
    
    //Constructor to initialise Bus w/ number of rows
    public Bus(int numRows) {
        seats = new ArrayList<>();
        char[] columns = {'A', 'B', 'C', 'D'};

        // Initialise seats for each row and column
        for (int row = 1; row <= numRows; row++) {
            for (char column : columns) {
                seats.add(new Seat(row, column));
            }
        }

        // Load reservations from file if available
        List<Reservation> reservations = ReservationFileHandler.loadReservations();
        if (reservations != null) {
            //Apply existing reservations to corresponding seats 
            for (Reservation reservation : reservations) {
                String seatNo = reservation.getSeatNo();
                Seat seat = findSeatByNo(seatNo);
                if (seat != null) {
                    seat.reserve(reservation.getPassenger());
                }
            }
        }
    }
    
    //Method to get all seats on the bus
    public List<Seat> getSeats() {
        return seats; //returns list of seats in the bus
    }
    
    //Find seat by row & column
    public Seat findSeat(int row, char column) {
        for (Seat seat : seats) {
            if (seat.getRow() == row && seat.getColumn() == Character.toUpperCase(column)) {
                return seat; //return seat object if found
            }
        }
        return null;  //return seat if not found
    }
    
    //Find a seat by its seat number
    public Seat findSeatByNo(String seatNo) // seat No: seat number as a string (e.g. '7A')
    {
        for (Seat seat : seats) {
            if (seat.toString().equals(seatNo)) {
                return seat; //return seat object if found
            }
        }
        return null;  //return seat if not found
    }
}
