/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busreservationsystem;

/**
 *
 * @author juliacalma
 */

/*
 * 'Main' class represents the main entry point of the Bus Reservation System application.
 */

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ReservationSystem reservationSystem;

    //Main method to start the Bus Reservation System
    public static void main(String[] args) {
        initialiseReservationSystem(); //initialise the reservation system

        while (true) {
            displayMenu(); //display the main menu
            int choice = getPassengerChoice(); //get user's menu choice

            //Process the user's choice using a switch statement
            switch (choice) {
                case 1:
                    reservationSystem.displayAvailableSeats(); //Display seats available
                    break;
                case 2:
                    reservationSystem.reserveSeat(); //Reserve a seat
                    break;
                case 3:
                    reservationSystem.cancelReservation(); //Cancel a reservation
                    break;
                case 4:
                    reservationSystem.saveReservations(); //Save reservations and exit
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
    
    //Method to initialise the reservation system
    private static void initialiseReservationSystem() {
        reservationSystem = new ReservationSystem(25); //Initialise with 25 rows
       
    }

    //Method to display the main menu options
    private static void displayMenu() {
        System.out.println("=== Bus Reservation System ===");
        System.out.println("1. Display Seats Available");
        System.out.println("2. Reserve Seat");
        System.out.println("3. Cancel Reservation");
        System.out.println("4. Save Reservation and Exit");
        System.out.println("Please input your selection:");
    }

    //Method to get & validate the user's menu choice
    private static int getPassengerChoice() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim()); //read & parse user input
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    
}
