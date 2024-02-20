import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Controller {
    private BarberLink app;
    
    // VIEWS
    private LoginView loginPage;
    private BarberLoginView barberLoginPage;
    private SignUpView signupPage;
    
    private UserMenu userMenu;
    private NewAppMenu newAppMenu;
    private NewReviewMenu newReviewMenu;
    private PaymentView paymentView;
    private OnlineBankingView onlineBankingView;
    private EWalletView eWalletView;
    private CardPaymentView cardPaymentView;
    
    private BarberMenu barberMenu;
    private NewOffDayMenu offDayMenu;
    
    private User currentUser;
    
    public Controller() {
		app = new BarberLink();
		currentUser = app.getCustomer("test");
		
		loginPage = new LoginView(this);
		barberLoginPage = new BarberLoginView(this);
		signupPage = new SignUpView(this);
		paymentView = new PaymentView(this);
    }
    
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.displayLoginPage();
	}
	
	// VIEWS
	public void displayLoginPage() { 
		loginPage.setVisible(true);
	}
	public void displaySignupPage() {
		signupPage.setVisible(true);
	}
	public void displayBarberLoginPage() {
		barberLoginPage.setVisible(true);
	}
	public void displayPaymentPage() {
		paymentView.setVisible(true);
        onlineBankingView = new OnlineBankingView(this);
        eWalletView = new EWalletView(this);
        cardPaymentView = new CardPaymentView(this);
	}
	public void showOnlineBankingView() {
        onlineBankingView.setVisible(true);
    }
	public void showEWalletView() {
        eWalletView.setVisible(true);
    }
	public void showCardPaymentView() {
        cardPaymentView.setVisible(true);
    }
	
	// USER VIEWS
	public void displayNewAppMenu() {
		newAppMenu.setCount(((Customer) currentUser).getVisitCount());
		newAppMenu.setVisible(true);
	}
	public void displayNewReviewMenu(Appointment a) {
        newReviewMenu.setAppointment(a);
        newReviewMenu.setVisible(true);
	}
	
	// BARBER VIEWS
	public void displayOffDayMenu() {
		offDayMenu.setVisible(true);
	}
	public void initMenu() {
		if (currentUser instanceof Customer) {
	        userMenu = new UserMenu(this);
	        newAppMenu = new NewAppMenu(this);
	        newReviewMenu = new NewReviewMenu(this);
	        userMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        newAppMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        newReviewMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        userMenu.getApps();
            userMenu.setVisible(true);
		} else {
			barberMenu = new BarberMenu(this);
			offDayMenu = new NewOffDayMenu(this);
	        barberMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        barberMenu.getApps();
	        barberMenu.setVisible(true);
		}
	}

    public boolean loginCust(String name, String password) {
    	Customer c = app.getCustLogin(name, password);
    	
        if (c != null) {
        	
        	currentUser = c;
    		initMenu();
            return true;
        }
        return false;
    }
    public boolean loginBarb(String password) {
    	Barber b = app.getBarbLogin(password);
    	
        if (b != null) {
        	currentUser = b;
    		initMenu();
            return true;
        }
        return false;
    }
    
    public boolean registerCustomer(String n, String p) {
    	if (app.regCustomer(n, p)) {
    		JOptionPane.showMessageDialog(null, "User " + n + " has been registered!");
        	signupPage.setVisible(false);
        	loginPage.setVisible(true);
        	return true;
    	}
		JOptionPane.showMessageDialog(null, "User already exists.");
    	return false;
    }
    
    public boolean logoutUser() {
    	if (currentUser==null) return false;
    	
    	int input = JOptionPane.showConfirmDialog(null,
                "Logout?", "Barber Link - Confirm", 
                JOptionPane.OK_CANCEL_OPTION);
    	
    	if (input == 0) {
    		currentUser = null;
    		loginPage.setVisible(true);
    		return true;
    	}
    	return false;
    }
    
    
    // USER CONTROLLER
    private Barber temp;
    private String currentSlot;
    public void newApp(Barber b, String t) {
		int count = ((Customer) currentUser).getVisitCount();
    	temp = b;
    	currentSlot = t;

		if (count > 0 && count % 10 == 0) {
			payApp();
		} else {
	    	int input = JOptionPane.showConfirmDialog(null, 
	                "Proced to payment?", "Barber Link - Confirm", 
	                JOptionPane.OK_CANCEL_OPTION);
	    	if (input == 0) {
	        	temp = b;
	        	currentSlot = t;
            	newAppMenu.setVisible(false);
        		displayPaymentPage();
    		}
    	}
	}
    
    public void payApp() {
    	int input = JOptionPane.showConfirmDialog(null, 
                "Confirm Booking?", "Barber Link - Confirm", 
                JOptionPane.OK_CANCEL_OPTION);
    	if (input == 0) {
    		app.newAppointment((Customer) currentUser, temp, currentSlot);
    		JOptionPane.showMessageDialog(null, "Appointment has been created!");
    		userMenu.getApps();
    		userMenu.setVisible(true);
    	}
    }
    
    public void newReview(Appointment a, String feedback, int rating) {
    	int input = JOptionPane.showConfirmDialog(null, 
                "Leave Review?", "Barber Link - Confirm", 
                JOptionPane.OK_CANCEL_OPTION);
    	if (input == 0) {
    		app.newReview(a, feedback, rating);
    		newReviewMenu.setVisible(false);
    		userMenu.getApps();
    		userMenu.setVisible(true);
    	}
    }
    
    // BARBER CONTROLLER
    public boolean acceptApp(Appointment a) {
    	if (!(currentUser instanceof Barber)) return false;
    	
    	int input = JOptionPane.showConfirmDialog(null, 
                "Accept Appointment?", "Barber Link - Confirm", 
                JOptionPane.OK_CANCEL_OPTION);
    	if (input == 0) {
    		app.acceptAppointment(a);
    		return true;
    	}
    	return false;
    }
    
    public boolean[] checkSlots(String date, Barber b) {
    	boolean[] temp = new boolean[5];
    	List<Appointment> apps = app.getAppList();
    	
    	for (int i = 0; i < apps.size(); i++) {
    		Appointment curr = apps.get(i);
    		// check if appointment uses barber
    		if (curr.getBarber().equals(b)) {
    			// check if appointment uses slot
        		if (curr.getDate().substring(0, 10).equals(date)) {
        			String time = curr.getDate().substring(11, 16);
        			int hour = Integer.parseInt(time.substring(0, 2));
        			int minute = Integer.parseInt(time.substring(3, 5));
        			// make that slot unavailable
        			temp[((hour-10)*2) + minute/30] = true;
        		}
    		}
    	}
    	
    	return temp;
    }
    
    public int[] checkOffDays(String[] dates) {
    	// true = occupied
    	int[] temp = new int[7];
    	List<Appointment> apps = app.getAppList();
    	Stack<Integer> visited = new Stack<Integer>();
    	
    	for (int i = 0; i < 7; i++) {
    		if (((Barber) currentUser).getOffDays().contains(dates[i])) {
    			temp[0] = 2;
    			continue;
    		}
        	for (int j = 0; j < apps.size(); j++) {
        		if (visited.contains(j)) continue;
        		if (apps.get(j).getBarber() != (Barber) currentUser) continue;
        		String curr = apps.get(j).getDate();
    			if (curr.substring(0, 10).equals(dates[i])) {
    				temp[i] = 1;
    				visited.push(j);
    				break;
    			}
        	}
		}
    	return temp;
    }
    
    public void addOffDay(String date) {
    	int input = JOptionPane.showConfirmDialog(null, 
                "Set " + date + " as an Off Day?", "Barber Link - Confirm", 
                JOptionPane.OK_CANCEL_OPTION);
    	if (input == 0) {
        	app.addOffDay((Barber) currentUser, date);
        	barberMenu.refreshDays();
    	}
    }
    
    public ArrayList<Barber> getBarbers() {
    	return app.getBarbers();
    }
    public ArrayList<Appointment> getAppList() {
    	return app.getAppList();
    }
    
    public User getCurrentUser() {
    	return currentUser;
    }
}