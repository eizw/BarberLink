import java.util.*;

public class Appointment {
    private String slotTime;
    private Customer cust;
    private Barber barber;
    private String feedback;
    // true = accepted, false = not-accepted yet
    private boolean status = false;
    // true = dh bayar, false = belum bayar
    private boolean paid = false;

    public Appointment(String d, Customer c, Barber b) {
        slotTime = d;
    	cust = c;
        barber = b;
    }
    
    public void acceptAppointment() {
    	status = true;
    }
    
    public void pay() {
    	
    }
    
    public void setFeedback(String fb, int rating) {
    	feedback = fb + "\n" + rating;
    }

    public Customer getCust() {
        return cust;
    }

    public Barber getBarber() {
        return barber;
    }
    
    public String getDate() {
    	return slotTime;
    }
    
    public String getFeedback() {
    	return feedback;
    }
    
    public boolean getStatus() {
    	return status;
    }
    
    public boolean isPaid() {
    	return paid;
    }
    
    public String toString() {
    	return slotTime;
    }
}