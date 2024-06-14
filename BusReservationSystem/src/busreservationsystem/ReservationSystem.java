package busreservationsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
*This class is in charge of controlling the system, handling user interactions, and coordinating operations involving seats, 
*reservations, and passengers. 
*/
public class ReservationSystem {
    private Bus bus;
    private List<Reservation> reservations;
    private Scanner scanner;

    //Constructor to initialise the reservation system with a bus & load existing reservations
    public ReservationSystem(int numRows) {
        bus = new Bus(numRows);
        reservations = ReservationFileHandler.loadReservations();
        scanner = new Scanner(System.in);
    }
    
    //Method to display available seats in the bus
    public void displayAvailableSeats() {
        System.out.println("Available Seats:");

        List<String> reservedSeatNumbers = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservedSeatNumbers.add(reservation.getSeatNo());
        }

        for (int row = 1; row <= 25; row++) {
            System.out.printf("%2d ", row); //print row number
            for (char column : new char[]{'A', 'B', 'C', 'D'}) {
                String seatNo = row + String.valueOf(column);
                if (reservedSeatNumbers.contains(seatNo)) {
                    System.out.print("[X]  "); //indicator if seat is reserved
                } else {
                    System.out.print("[ ]  "); //indicator if seat is available
                }
            }
            System.out.println(); //move to the next line after printing seats for the current row
        }
    }

    public void reserveSeat() {
    Passenger passenger = null; //initialise passenger variable

    while (true) {
        //Ask for the passenger info if it's not already obtained
        if (passenger == null) {
            System.out.println("Enter Passenger Full Name:");
            String fullName = getValidFullName();

            System.out.println("Enter Passenger Contact Number:");
            String contactNo = getValidContactNo();

            passenger = new Passenger(fullName, contactNo); //create the passenger object
        }

        System.out.println("Enter Seat Number (e.g., 1A) or type 'exit' to cancel:");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
            System.out.println("Reservation process canceled.");
            break; //exit the reservation process
        }

        try {
            //Extract row number from input (e.g '7A' to 7)
            int row = Integer.parseInt(input.substring(0, input.length() - 1));
            
            //Extract column character from input & convert to uppercase (e.g '7A' to A)
            char column = Character.toUpperCase(input.charAt(input.length() - 1));

            //Validate the row & column range (1-25 rows, A-D columns)
            if (row < 1 || row > 25 || (column < 'A' || column > 'D')) {
                System.out.println("Invalid seat selection. Please enter a valid seat number (e.g., 1A).");
                continue;
            }
            //Combine row & column to from string (e.g 7+'A' to '7A)
            String seatNo = row + String.valueOf(column);
            //check if seat is alr reserved
            if (isSeatReserved(seatNo)) {
                System.out.println("Seat " + seatNo + " is already reserved.");
            } else {
                
                Seat seat = bus.findSeat(row, column);//find seat objecr corresponding to the seat number
                //Check if seat object is  found
                if (seat != null) {
                    seat.reserve(passenger); //reserve the seat for passenger
                    Reservation newReservation = new Reservation(passenger, seatNo);
                    reservations.add(newReservation);
                    saveReservations(); //save reservations to file
                    System.out.println("Seat " + seatNo + " reserved successfully.");
                } else {
                    System.out.println("Invalid seat selection. Seat may not exist.");
                }
            }

            while (true) {
                System.out.println("Would you want to reserve another seat (yes/no):");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (choice.equals("yes")) {
                    break; //continue with another reservation
                } else if (choice.equals("no")) {
                    System.out.println("Exiting reservation process.");
                    return; //exit the reservation process
                } else {
                    System.out.println("Invalid response. Please input 'yes' or 'no'.");
                }
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            //Handle exceptions if input is invalid (e.g. 'A7')
            System.out.println("Invalid input. Please enter a valid seat number (e.g., 7A).");
        }
    }
}


    //Method to get & validate passenger information
    private Passenger getPassengerInfo() {
        System.out.println("Enter Passenger Full Name:");
        String fullName = getValidFullName();

        System.out.println("Enter Passenger Contact Number:");
        String contactNo = getValidContactNo();

        return new Passenger(fullName, contactNo);
    }
    
    // Method to validate & get a valid full name
    private String getValidFullName() {
        while (true) {
            String fullName = scanner.nextLine().trim();
            if (fullName.matches("^[a-zA-Z\\s]+$")) //expression to make sure string only contains alphabetic char (uppercase/lowercase)& spaces 
            {
                return fullName;
            } else {
                System.out.println("Invalid name. Please enter alphabetic characters and spaces only.");
            }
        }
    }
    
    // Method to validate & obtain a valid contact number
    private String getValidContactNo() {
        while (true) {
            String contactNo = scanner.nextLine().trim();
            if (contactNo.matches("^\\d+$"))  //expression to make sure string only contains numeric characters
            {
                return contactNo;
            } else {
                System.out.println("Invalid contact number. Please enter numeric characters only.");
            }
        }
    }

    //Method to cancel a reservation
    public void cancelReservation() {
        System.out.println("Enter Seat Number to cancel (e.g., 7A) or type 'exit' to cancel:");
        String input = scanner.nextLine().trim().toUpperCase(); //convert input to uppercase

        if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
            System.out.println("Cancellation process canceled.");
            return; //return w/o further processing
        }

        try {
            //Extract row number from input (e.g '7A' to 7)
            int row = Integer.parseInt(input.substring(0, input.length() - 1));
            
            //Extract column character from input & convert to uppercase (e.g '7A' to A)
            char column = Character.toUpperCase(input.charAt(input.length() - 1));

            //Combine row & column to form the seat number string (e.g 7 + 'A' to "7A")
            String seatNo = row + String.valueOf(column);
            //Check if seat is reserved
            if (isSeatReserved(seatNo)) {
                //find seat object corresponding to seat number
                Seat seat = bus.findSeat(row, column);
                
                //check if seat object is found & is currently reserved
                if (seat != null && seat.isReserved()) {
                    seat.cancelReservation(); // cancel reservation for seat
                    reservations.removeIf(reservation -> reservation.getSeatNo().equals(seatNo)); //remove corresponding reservation from the list
                    saveReservations(); //save updated reservations list to the file
                    System.out.println("The reservation for seat " +seatNo+ " has been successfully canceled.");
                } else {
                    System.out.println("Failed to cancel reservation. Seat " + seatNo + " may not be reserved.");
                }
            } else {
                System.out.println("Seat " + seatNo + " is not currently reserved.");
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            //Handle exceptions if input is invalid (e.g. 'A7')
            System.out.println("Invalid input. Please enter a valid seat number (e.g., 1A).");
        }
    }
    
    //Method to check if a seat is reserved
    private boolean isSeatReserved(String seatNo) {
        for (Reservation reservation : reservations) {
            if (reservation.getSeatNo().equals(seatNo)) {
                return true;
            }
        }
        return false;
    }
    
    //Method to save reservations to file
    public void saveReservations() {
        ReservationFileHandler.saveReservations(reservations);
    }
    
    //Method to exit the reservation system
    public void exit() {
        scanner.close();
        System.out.println("Exiting Bus Reservation System.");
        System.exit(0);
    }
}
