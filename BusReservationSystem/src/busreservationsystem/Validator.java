/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busreservationsystem;

/**
 *
 * @author juliacalma
 */



public class Validator {

    public static boolean isValidFullName(String fullName) {
        return fullName.matches("^[a-zA-Z\\s]+$");
    }

    public static boolean isValidContactNo(String contactNo) {
        return contactNo.matches("^\\d+$");
    }

    public static boolean isValidSeatNo(String seatNo) {
        return seatNo.matches("^[1-9][0-9]*[A-D]$");
    }
}
