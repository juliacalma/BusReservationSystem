package BusReservationSystem;

import java.io.Serializable;


public class Passenger implements Serializable {
    private String fullName;
    private String contactNo;
    
    
    //Constructor to initialise Passenger
    public Passenger(String fullName, String contactNo) {
        setFullName(fullName);
        setContactNo(contactNo);
    }
    
    //Get method for full name
    public String getFullName() {
        return fullName;
    }
    
    //Set method for full name w/ validation
    public void setFullName(String fullName) {
        if (fullName.matches("^[a-zA-Z\\s]+$"))//expression to make sure string only contains alphabetic char (uppercase/lowercase)& spaces 
        {
            this.fullName = fullName;//set full name if valid
        } else {
            //throw exception if full name contains non-alphabetic characters
            throw new IllegalArgumentException("Invalid name. Please enter alphabetic characters and spaces only.");
        }
    }
    
    //Get method for contact number
    public String getContactNo() {
        return contactNo;
    }
    //Set method for contact number w/ validation
    public void setContactNo(String contactNo) {
        if (contactNo.matches("^\\d+$")) //expression to make sure string only contains numeric characters
        {
            this.contactNo = contactNo;//set contact no. if valid
        } else {
            //throw exception if contact number contains non-numeric characters
            throw new IllegalArgumentException("Invalid contact number. Please enter numeric characters only.");
        }
    }
    
    // Override toString method to display Passenger info
    @Override
    public String toString() {
        return fullName + " (" + contactNo + ")";//return formatted string representation of Passenger
    }
}
