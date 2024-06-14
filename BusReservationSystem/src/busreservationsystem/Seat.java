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

public class Seat implements Serializable {
    private int row;             //row no of seat
    private char column;         //column letter of seat
    private boolean reserved;    //boolean to see if seat is reserved or not
    private Passenger passenger; //passenger assigned to the seat (if reserved) 

    public Seat(int row, char column) {
        this.row = row;
        this.column = Character.toUpperCase(column);  //convert column letters to uppercase
        this.reserved = false;                        //initialise the seat to not reserved
        this.passenger = null;                        //initialise 'no passenger' assigned
    }
    
    //Get method for row
    public int getRow() {
        return row;
    }
    //Get method for column
    public char getColumn() {
        return column;
    }
    
    //Method to check if the seat is reserved
    public boolean isReserved() {
        return reserved;
    }
    //Get method for passenger assigned to the seat
    public Passenger getPassenger() {
        return passenger;
    }
    
    //Method to reserve seat for passenger
    public void reserve(Passenger passenger) {
        this.passenger = passenger;  //reserve passanger to seat
        this.reserved = true;        //set seat as reserved
    }
    
    // Method to cancel the reservation of seat
    public void cancelReservation() {
        this.passenger = null; //remove the reserved passenger
        this.reserved = false; //mark seat as not reserved
    }

    //Method to display Seat number
    @Override
    public String toString() {
        return row + String.valueOf(column); //return string representation of the seat (e.g. '7A')
    }
}
