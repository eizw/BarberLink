
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

public class BarberLink {
    
    private List<Customer> custs = new ArrayList<Customer>();
    private List<Barber> barbs = new ArrayList<Barber>();
    private List<Appointment> apps = new ArrayList<Appointment>();

    public BarberLink() {
    	scanData();
    }
    
    // Find Customer
    public Customer getCustLogin(String n, String p) {
    	for (int i = 0; i < custs.size(); i++) {
    		Customer curr = custs.get(i);
    		if (curr.getName().equals(n)) {
    			if (curr.getPassword().equals(p)) {
        			return curr;
        		}
    		}
    	}
    	return null;
    }
    public Barber getBarbLogin(String p) {
    	for (int i = 0; i < custs.size(); i++) {
			if (barbs.get(i).getPassword().equals(p)) {
    			return barbs.get(i);
    		}
    	}
    	return null;
    }
    
    public boolean regCustomer(String n, String p) {
    	if (getCustomer(n) == null) {
        	custs.add(new Customer(n, p, 0));
    		return true;
    	}
    	return false;
    }
    
    public void addBarber(Barber b) {
    	barbs.add(b);
    }
    public void addBarber(String n, String p) {
    	barbs.add(new Barber(n, p));
    }
    
    public void addOffDay(Barber b, String date) {
    	Barber temp = getBarber(b.getName());
    	temp.addOffDay(date);
    }
    
    // New Appointment
    public void newAppointment(Appointment a) {
    	apps.add(a);
    }
    public void newAppointment(Customer c, Barber b, String t) {
    	Appointment a = new Appointment(t, c, b);
    	System.out.println("Created new appointent at " + t);
    	apps.add(a);
    	c.addVisit();
    }
    
    public void newAppointment(String cName, String bName, String t) {
    	Barber b = getBarber(bName);
    	Customer c = getCustomer(cName);
    	apps.add(new Appointment(t, c, b));
    }

    // Confirm Appointment
    public void acceptAppointment(Appointment a) {
    	a.acceptAppointment();
    }
    
    // Review Appointment
    public void newReview(Appointment a, String fb, int rating) {
		a.setFeedback(fb, rating);
    }

    // GETTERS
    public Appointment getAppointment(Appointment a) {
    	for (int i = 0; i < apps.size(); i++) {
    		if (apps.get(i)==a) return apps.get(i);
    	}
    	return null;
    }
    public Customer getCustomer(String n) {
    	for (int i = 0; i < custs.size(); i++) {
    		Customer curr = custs.get(i);
    		if (curr.getName().equals(n)) {
    			return curr;
    		}
    	}
    	return null;
    }
    public Barber getBarber(String n) {
    	for (int i = 0; i < barbs.size(); i++) {
    		Barber curr = barbs.get(i);
    		if (curr.getName().equals(n)) {
    			return curr;
    		}
    	}
    	return null;
    }
    
    public ArrayList<Customer> getCustomers() {
    	return (ArrayList) custs;
    }
    public ArrayList<Barber> getBarbers() {
    	return (ArrayList) barbs;
    }
    public ArrayList<Appointment> getAppList() {
    	return (ArrayList) apps;
    }
    
    private void scanData() {
    	try {
    		File custFile = new File("src/data/Users.txt");
    		File barbFile = new File("src/data/Barbers.txt");
    		File appsFile = new File("src/data/Appointments.txt");
    		
    		Scanner sc = new Scanner(custFile);
    		while (sc.hasNextLine()) {
    			String name = sc.nextLine(), pass = sc.nextLine();
    			int visitCount = Integer.parseInt(sc.nextLine());
    			Customer curr = new Customer(name, pass, visitCount);
    			custs.add(curr);
    		}
    		
    		sc = new Scanner(barbFile);
    		while (sc.hasNextLine()) {
    			String name = sc.nextLine(), pass = sc.nextLine();
    			Barber curr = new Barber(name, pass);
    			barbs.add(curr);
    		}

    		sc = new Scanner(appsFile);
    		while (sc.hasNextLine()) {
    			Customer c = getCustomer(sc.nextLine());
    			Barber b = getBarber(sc.nextLine());
    			String t = sc.nextLine();
    			Appointment curr = new Appointment(t, c, b);
    			apps.add(curr);
    		}
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
}

class RoundedBorder implements Border {

    private int radius;


    RoundedBorder(int radius) {
        this.radius = radius;
    }


    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}