/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busreservationsystem;

/**
 *
 * @author juliacalma
 */





import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReservationSystemUI extends JFrame {
    private Bus bus;
    private List<Reservation> reservations;
    private DBManager dbManager;
    private JTextArea availableSeatsTextArea;

    public ReservationSystemUI() {
        dbManager = new DBManager();
        reservations = dbManager.loadReservations();
        bus = new Bus(25);
        initializeBusSeats();

        setTitle("Bus Reservation System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel with background image
        ImageIcon bgIcon = new ImageIcon("pexels-olgalioncat-7245323.jpg");
        Image bgImage = bgIcon.getImage().getScaledInstance(700, 500, Image.SCALE_SMOOTH);
        BackgroundPanel mainPanel = new BackgroundPanel(bgImage);
        mainPanel.setLayout(new BorderLayout());

        // Create and add the components to the main panel
        JLabel titleLabel = new JLabel("Bus Reservation System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);

        JButton displaySeatsButton = new JButton("Display Seats Available");
        displaySeatsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAvailableSeats();
            }
        });

        JButton reserveSeatButton = new JButton("Reserve Seat");
        reserveSeatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reserveSeat();
            }
        });

        JButton cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelReservation();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitReservationSystem();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.add(displaySeatsButton);
        buttonPanel.add(reserveSeatButton);
        buttonPanel.add(cancelReservationButton);
        buttonPanel.add(exitButton);

        // Add available seats display
        availableSeatsTextArea = new JTextArea(15, 20);
        availableSeatsTextArea.setEditable(false);
        displayAvailableSeatsInTextArea();

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(new JScrollPane(availableSeatsTextArea), BorderLayout.CENTER);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void initializeBusSeats() {
        for (Reservation reservation : reservations) {
            bus.reserveSeatForPassenger(reservation.getPassenger(), reservation.getSeatNo());
        }
    }

    private void displayAvailableSeats() {
        StringBuilder availableSeats = new StringBuilder("Available Seats:\n");

        for (int row = 1; row <= 25; row++) {
            availableSeats.append(String.format("%2d ", row)); // Add row numbers
            for (char column : new char[]{'A', 'B', 'C', 'D'}) {
                String seatNo = row + String.valueOf(column);
                if (bus.isSeatReserved(seatNo)) {
                    availableSeats.append("[X] ");
                } else {
                    availableSeats.append("[ ] ");
                }
            }
            availableSeats.append("\n");
        }

        availableSeatsTextArea.setText(availableSeats.toString());
    }

    private void displayAvailableSeatsInTextArea() {
        StringBuilder availableSeats = new StringBuilder("Available Seats:\n");

        for (int row = 1; row <= 25; row++) {
            availableSeats.append(String.format("%2d ", row)); // Add row numbers
            for (char column : new char[]{'A', 'B', 'C', 'D'}) {
                String seatNo = row + String.valueOf(column);
                if (bus.isSeatReserved(seatNo)) {
                    availableSeats.append("[X] ");
                } else {
                    availableSeats.append("[ ] ");
                }
            }
            availableSeats.append("\n");
        }

        availableSeatsTextArea.setText(availableSeats.toString());
    }

    private void reserveSeat() {
        JTextField fullNameField = new JTextField();
        JTextField contactNoField = new JTextField();
        JTextField seatNoField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Full Name:"));
        panel.add(fullNameField);
        panel.add(new JLabel("Contact Number:"));
        panel.add(contactNoField);
        panel.add(new JLabel("Seat Number:"));
        panel.add(seatNoField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Reserve Seat", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String fullName = fullNameField.getText();
            String contactNo = contactNoField.getText();
            String seatNo = seatNoField.getText().toUpperCase(); // Make seat number case-insensitive

            if (Validator.isValidFullName(fullName) && Validator.isValidContactNo(contactNo) && Validator.isValidSeatNo(seatNo)) {
                Passenger passenger = new Passenger(fullName, contactNo);
                if (bus.isSeatReserved(seatNo)) {
                    JOptionPane.showMessageDialog(this, "Seat " + seatNo + " is already reserved.");
                } else {
                    bus.reserveSeatForPassenger(passenger, seatNo);
                    dbManager.saveReservation(passenger, seatNo);
                    displayAvailableSeatsInTextArea(); // Update available seats
                    JOptionPane.showMessageDialog(this, "Seat " + seatNo + " reserved successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your details and try again.");
            }
        }
    }

    private void cancelReservation() {
        String seatNo = JOptionPane.showInputDialog(this, "Enter Seat Number to cancel (e.g., 7A):").toUpperCase();

        if (seatNo != null && Validator.isValidSeatNo(seatNo)) {
            if (bus.isSeatReserved(seatNo)) {
                bus.cancelReservation(seatNo);
                reservations.removeIf(reservation -> reservation.getSeatNo().equals(seatNo));
                dbManager.deleteReservation(seatNo);
                displayAvailableSeatsInTextArea(); // Update available seats
                JOptionPane.showMessageDialog(this, "The reservation for seat " + seatNo + " has been successfully canceled.");
            } else {
                JOptionPane.showMessageDialog(this, "Seat " + seatNo + " is not currently reserved.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid seat number. Please try again.");
        }
    }

    private void exitReservationSystem() {
        dbManager.closeConnections();
        System.exit(0);
    }

    public static void main(String[] args) {
        new ReservationSystemUI();
    }
}

