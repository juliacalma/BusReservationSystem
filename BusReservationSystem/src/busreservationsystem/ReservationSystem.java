package busreservationsystem;


/**
 * This class is in charge of controlling the system, handling user interactions, and coordinating operations involving seats,
 * reservations, and passengers.
 */

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ReservationSystem {
    private Bus bus;
    private List<Reservation> reservations;
    private DBManager dbManager;
    private Validator validator;

    public ReservationSystem(int numRows) {
        bus = new Bus(numRows);
        dbManager = new DBManager();
        validator = new Validator();
        reservations = dbManager.loadReservations();
        initializeBusSeats();
    }

    private void initializeBusSeats() {
        for (Reservation reservation : reservations) {
            bus.reserveSeatForPassenger(reservation.getPassenger(), reservation.getSeatNo());
        }
    }

    public void displayAvailableSeats(JTextArea seatArea) {
        seatArea.append("Available Seats:\n");

        List<String> reservedSeatNumbers = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservedSeatNumbers.add(reservation.getSeatNo());
        }

        for (int row = 1; row <= 25; row++) {
            seatArea.append(String.format("%2d ", row));
            for (char column : new char[]{'A', 'B', 'C', 'D'}) {
                String seatNo = row + String.valueOf(column);
                if (reservedSeatNumbers.contains(seatNo)) {
                    seatArea.append("[X]  ");
                } else {
                    seatArea.append("[ ]  ");
                }
            }
            seatArea.append("\n");
        }
    }

    public void reserveSeat(Passenger passenger, String seatNo) {
        seatNo = seatNo.toUpperCase(); // Convert seat number to uppercase

        if (bus.isSeatReserved(seatNo)) {
            JOptionPane.showMessageDialog(null, "Seat " + seatNo + " is already reserved.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            bus.reserveSeatForPassenger(passenger, seatNo);
            Reservation newReservation = new Reservation(passenger, seatNo);
            reservations.add(newReservation);
            dbManager.saveReservation(passenger, seatNo); // Save reservation to database

            JOptionPane.showMessageDialog(null, "Seat " + seatNo + " reserved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void cancelReservation(String seatNo) {
        seatNo = seatNo.toUpperCase(); // Convert seat number to uppercase

        if (bus.isSeatReserved(seatNo)) {
            bus.cancelReservation(seatNo);
            final String finalSeatNo = seatNo; // Make a final copy of seatNo
            reservations.removeIf(reservation -> reservation.getSeatNo().equals(finalSeatNo));
            dbManager.deleteReservation(seatNo); // Delete reservation from database
            JOptionPane.showMessageDialog(null, "The reservation for seat " + seatNo + " has been successfully canceled.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Seat " + seatNo + " is not currently reserved.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exit() {
        System.out.println("Exiting Bus Reservation System.");
        System.exit(0);
    }

    public Validator getValidator() {
        return validator;
    }
}
