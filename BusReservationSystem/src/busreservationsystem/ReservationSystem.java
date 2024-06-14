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
    private Validator validator;

    public ReservationSystem(int numRows) {
        bus = new Bus(numRows);
        reservations = ReservationFileHandler.loadReservations();
        scanner = new Scanner(System.in);
        validator = new Validator();
    }

    public void displayAvailableSeats() {
        System.out.println("Available Seats:");

        List<String> reservedSeatNumbers = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservedSeatNumbers.add(reservation.getSeatNo());
        }

        for (int row = 1; row <= 25; row++) {
            System.out.printf("%2d ", row);
            for (char column : new char[]{'A', 'B', 'C', 'D'}) {
                String seatNo = row + String.valueOf(column);
                if (reservedSeatNumbers.contains(seatNo)) {
                    System.out.print("[X]  ");
                } else {
                    System.out.print("[ ]  ");
                }
            }
            System.out.println();
        }
    }

    public void reserveSeat() {
        Passenger passenger = null;

        while (true) {
            if (passenger == null) {
                passenger = validator.getPassengerInfo();
            }

            System.out.println("Enter Seat Number (e.g., 1A) or type 'exit' to cancel:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("Reservation process canceled.");
                break;
            }

            try {
                int row = Integer.parseInt(input.substring(0, input.length() - 1));
                char column = Character.toUpperCase(input.charAt(input.length() - 1));

                if (row < 1 || row > 25 || (column < 'A' || column > 'D')) {
                    System.out.println("Invalid seat selection. Please enter a valid seat number (e.g., 1A).");
                    continue;
                }

                String seatNo = row + String.valueOf(column);
                if (isSeatReserved(seatNo)) {
                    System.out.println("Seat " + seatNo + " is already reserved.");
                } else {
                    Seat seat = bus.findSeat(row, column);
                    if (seat != null) {
                        seat.reserve(passenger);
                        Reservation newReservation = new Reservation(passenger, seatNo);
                        reservations.add(newReservation);
                        saveReservations();
                        System.out.println("Seat " + seatNo + " reserved successfully.");
                    } else {
                        System.out.println("Invalid seat selection. Seat may not exist.");
                    }
                }

                while (true) {
                    System.out.println("Would you want to reserve another seat (yes/no):");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (choice.equals("yes")) {
                        break;
                    } else if (choice.equals("no")) {
                        System.out.println("Exiting reservation process.");
                        return;
                    } else {
                        System.out.println("Invalid response. Please input 'yes' or 'no'.");
                    }
                }
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Invalid input. Please enter a valid seat number (e.g., 7A).");
            }
        }
    }

    public void cancelReservation() {
        System.out.println("Enter Seat Number to cancel (e.g., 7A) or type 'exit' to cancel:");
        String input = scanner.nextLine().trim().toUpperCase();

        if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
            System.out.println("Cancellation process canceled.");
            return;
        }

        try {
            int row = Integer.parseInt(input.substring(0, input.length() - 1));
            char column = Character.toUpperCase(input.charAt(input.length() - 1));
            String seatNo = row + String.valueOf(column);

            if (isSeatReserved(seatNo)) {
                Seat seat = bus.findSeat(row, column);
                if (seat != null && seat.isReserved()) {
                    seat.cancelReservation();
                    reservations.removeIf(reservation -> reservation.getSeatNo().equals(seatNo));
                    saveReservations();
                    System.out.println("The reservation for seat " + seatNo + " has been successfully canceled.");
                } else {
                    System.out.println("Failed to cancel reservation. Seat " + seatNo + " may not be reserved.");
                }
            } else {
                System.out.println("Seat " + seatNo + " is not currently reserved.");
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            System.out.println("Invalid input. Please enter a valid seat number (e.g., 1A).");
        }
    }

    private boolean isSeatReserved(String seatNo) {
        for (Reservation reservation : reservations) {
            if (reservation.getSeatNo().equals(seatNo)) {
                return true;
            }
        }
        return false;
    }

    public void saveReservations() {
        ReservationFileHandler.saveReservations(reservations);
    }

    public void exit() {
        scanner.close();
        System.out.println("Exiting Bus Reservation System.");
        System.exit(0);
    }
}