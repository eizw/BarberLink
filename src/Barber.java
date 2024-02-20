import java.awt.Component;
import java.util.*;

public class Barber extends User {
    private List<String> offDays = new ArrayList<String>();
    
    public Barber(String n, String p) {
    	super(n, p);
    }
    
    public void addOffDay(String date) {
    	offDays.add(date);
    }

	public ArrayList getOffDays() {
		return (ArrayList) offDays;
	}
}