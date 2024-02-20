import java.util.*;
import java.time.*;

public class Customer extends User {
    private int visitCount;
    private List<Appointment> history;

    public Customer(String n, String p, int v) {
        super(n, p);
        visitCount = (v > 0) ? v : 0;
    }
    public int getVisitCount() {
        return visitCount;
    }
    
    public void addVisit() {
    	visitCount++;
    }
    
    public ArrayList<Appointment> getHistory() {
    	return (ArrayList) history;
    }
}