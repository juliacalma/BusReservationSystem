/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busreservationsystem;

/**
 *
 * @author juliacalma
 */

import java.util.Scanner;
public class Validator {
     private Scanner scanner;

    public Validator() {
        this.scanner = new Scanner(System.in);
    }

    // Method to get & validate passenger information
    public Passenger getPassengerInfo() {
        System.out.println("Enter Passenger Full Name:");
        String fullName = getValidFullName();

        System.out.println("Enter Passenger Contact Number:");
        String contactNo = getValidContactNo();

        return new Passenger(fullName, contactNo);
    }
    
    // Method to validate & get a valid full name
    public String getValidFullName() {
        while (true) {
            String fullName = scanner.nextLine().trim();
            if (fullName.matches("^[a-zA-Z\\s]+$")) {
                return fullName;
            } else {
                System.out.println("Invalid name. Please enter alphabetic characters and spaces only.");
            }
        }
    }
    
    // Method to validate & obtain a valid contact number
    public String getValidContactNo() {
        while (true) {
            String contactNo = scanner.nextLine().trim();
            if (contactNo.matches("^\\d+$")) {
                return contactNo;
            } else {
                System.out.println("Invalid contact number. Please enter numeric characters only.");
            }
        }
    }
    
}
