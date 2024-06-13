package BusReservationSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading of reservations to/from a text file.
 */

public class ReservationFileHandler {
    private static final String FILE_PATH = "/Users/juliacalma/NetBeansProjects/MyFirstProject/src/BusReservationSystem/reservations.txt";

    //Saves a single reservation to the reservations file
    public static void saveReservation(Reservation reservation){
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        try (FileWriter fileWriter = new FileWriter(FILE_PATH);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            
            //Write each reservation as a line in the file
            for (Reservation res : reservations) {
                printWriter.println(res.getPassenger().getFullName() + "," +
                                     res.getPassenger().getContactNo() + "," +
                                     res.getSeatNo());
            }
            System.out.println("Reservation is saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reservation: " + e.getMessage());
        }
    }
    
    //Loads reservation from the resevation file
    public static List<Reservation> loadReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try (FileReader fileReader = new FileReader(FILE_PATH);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            //Read each line from the file
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                //Extract reservation details from the line
                if (parts.length == 3) {
                    String fullName = parts[0];
                    String contactNo = parts[1];
                    String seatNo = parts[2];
                    //Create passenger and reservation objects
                    Passenger passenger = new Passenger(fullName, contactNo);
                    Reservation reservation = new Reservation(passenger, seatNo);
                    reservations.add(reservation);
                }
            }
            System.out.println("Reservations loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }
        return reservations; //returm list of loaded reservation
    }
    
    //Saves list of reservation to reservations file
    public static void saveReservations(List<Reservation> reservations) {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            //Write each reservation as a line in the file
            for (Reservation res : reservations) {
                printWriter.println(res.getPassenger().getFullName() + "," +
                                     res.getPassenger().getContactNo() + "," +
                                     res.getSeatNo());
            }
            System.out.println("Reservations saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }
}
