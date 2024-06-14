/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busreservationsystem;

/**
 *
 * @author juliacalma
 */



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {
    private static final String URL = "jdbc:derby:bus_reservation;create=true"; // URL of the embedded Derby database
    private Connection conn;
    private static final Logger LOGGER = Logger.getLogger(DBManager.class.getName());

    static {
        try {
            // Load the Derby driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Derby driver not found", e);
        }
    }

    public DBManager() {
        establishConnection();
        Runtime.getRuntime().addShutdownHook(new Thread(this::closeConnections));
    }

    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        System.out.println(dbManager.getConnection());
    }

    public Connection getConnection() {
        return this.conn;
    }

    private static void createTableIfNotExists() throws SQLException {
    String createTableSQL = "CREATE TABLE reservations ("
            + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
            + "fullName VARCHAR(100), "
            + "contactNo VARCHAR(15), "
            + "seatNo VARCHAR(5))";

    try (Connection conn = DriverManager.getConnection(URL);
         PreparedStatement pstmt = conn.prepareStatement(createTableSQL)) {
        pstmt.execute();
    } catch (SQLException e) {
        if (e.getSQLState().equals("X0Y32")) {
            // Table already exists, do nothing
            LOGGER.info("Table 'reservations' already exists");
        } else {
            throw e;
        }
    }
}


    // Establish connection
    public void establishConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(URL);
                LOGGER.info("Connected to database at " + URL);
                createTableIfNotExists(); // Create table if not exists during connection establishment
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Failed to establish connection to database", ex);
            }
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 45000 && "08006".equals(ex.getSQLState())) {
                    // Derby shutdown expected exception
                    LOGGER.info("Derby database shut down normally");
                } else {
                    LOGGER.log(Level.SEVERE, "Failed to close database connection", ex);
                }
            }
        }
    }

    public void saveReservation(Passenger passenger, String seatNo) {
        String sql = "INSERT INTO reservations (fullName, contactNo, seatNo) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, passenger.getFullName());
            pstmt.setString(2, passenger.getContactNo());
            pstmt.setString(3, seatNo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to save reservation", e);
        }
    }

    public List<Reservation> loadReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Passenger passenger = new Passenger(rs.getString("fullName"), rs.getString("contactNo"));
                String seatNo = rs.getString("seatNo");
                reservations.add(new Reservation(passenger, seatNo));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load reservations", e);
        }
        return reservations;
    }

    public void deleteReservation(String seatNo) {
        String sql = "DELETE FROM reservations WHERE seatNo = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, seatNo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete reservation", e);
        }
    }
}
