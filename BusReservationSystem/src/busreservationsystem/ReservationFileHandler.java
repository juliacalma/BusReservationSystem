package busreservationsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading of reservations to/from a text file.
 */

public class ReservationFileHandler {
    private static final String FILE_PATH = "reservations.txt";

public static void saveReservation(Reservation reservation) {
    try {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file, true); // true for append mode
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);

        // Write reservation details to the file
        printWriter.println(reservation.getPassenger().getFullName() + "," +
                             reservation.getPassenger().getContactNo() + "," +
                             reservation.getSeatNo());

        printWriter.close(); // Close the PrintWriter
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
