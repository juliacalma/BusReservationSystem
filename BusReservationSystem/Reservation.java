/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusReservationSystem;

/**
 *
 * @author juliacalma
 */

import java.io.Serializable;

public class Reservation implements Serializable {
    private Passenger passenger; //passenger associated with the reservation
    private String seatNo;       //seat number for reservation

    //Constructor to initialise Reservation w/ passenger & seat number
    public Reservation(Passenger passenger, String seatNo) {
        this.passenger = passenger; //set passenger for the reservation
        this.seatNo = seatNo;       //set seat number for the reservation
    }
    
    //Get method for passenger
    public Passenger getPassenger() {
        return passenger;
    }
    //Get method for seat number
    public String getSeatNo() {
        return seatNo;
    }
    
    //Method to display reservation info
    @Override
    public String toString() {
        return "Reservation{" +
                "passenger=" + passenger +
                ", seatNo='" + seatNo + '\'' +
                '}';
    }
}
