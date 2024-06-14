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

public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {
        setTitle("Bus Reservation System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome to Bus Reservation System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));

        // Load an image
        ImageIcon icon = new ImageIcon("pexels-olgalioncat-7245323.jpg"); // Specify the path to your image
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(700, 500, Image.SCALE_SMOOTH); // Adjust the width and height
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);

        // Add components to the frame
        setLayout(new BorderLayout());
        add(welcomeLabel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);

        // Display the frame
        setVisible(true);

        // Wait for 3 seconds before closing the welcome screen and opening the main UI
        new Timer(3000, e -> {
            new ReservationSystemUI(); // Open the main reservation system UI
            dispose(); // Close the welcome screen
        }).start();
    }

    public static void main(String[] args) {
        new WelcomeScreen();
    }
}
