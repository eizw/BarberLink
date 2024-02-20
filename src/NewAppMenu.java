import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.SimpleDateFormat;

import javax.swing.*;

public class NewAppMenu extends JFrame implements ActionListener {
	private final int WIDTH = 650;
    private final int HEIGHT = 500;
    private final Color BACKGROUND_COLOR = new Color(20, 20, 20);
    private final Color MENU_COLOR = new Color(250, 250, 250);
    private final String FONT_ROBOTO_PATH = "../fonts/Roboto-Light.ttf";
    
    private JLabel titleLbl, currLbl, barbLbl, dateLbl, visitLbl;
    private JComboBox barberCmb, dateCmb;
    private JTextField dateFld;
    private Barber[] barbers;
    // 10, 1030, 11, 1130, 12
    private String[] dates = new String[7];
    private String[] timeSlots = { "10:00", "10:30", "11:00", "11:30", "12:00" };
    private boolean[] slotCheck;
    private JButton[] slotBtn = new JButton[5];
    private JButton dateBtn, submitBtn;

    private String currentDate;
    private String currentSlot;
    private Barber currentBarber;
    private Controller controller;
    
    public NewAppMenu (Controller c) {
    	controller = c;
    	// get the next 7 days
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	for (int i = 0; i < 7; i++) {
    		Calendar temp = Calendar.getInstance();
    		temp.add(Calendar.DATE, i);
    		dates[i] = formatter.format(temp.getTime());
    	}
    	// get barbers
    	ArrayList<Barber> barbs = c.getBarbers();
    	barbers = new Barber[barbs.size()];
    	for (int i = 0; i < barbs.size(); i++) {
    		barbers[i] = barbs.get(i);
    	}
    	
    	Container pane = getContentPane();
        pane.setBackground(BACKGROUND_COLOR);
        pane.setLayout(new BorderLayout());
        ((JComponent)pane).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
    	
    	setSize(WIDTH, HEIGHT);
        setTitle("Barber Link - New Appointment");
        setResizable(false);
        setLocationRelativeTo(null);
        
        titleLbl = new JLabel("New Appointment", JLabel.CENTER);
        dateCmb = new JComboBox(dates);
        dateBtn = new JButton("Check date");
        barberCmb = new JComboBox(barbers);
        barbLbl = new JLabel("Select Barber:");
        dateLbl = new JLabel("Select Day");
        
        dateCmb.addActionListener(this);
        barberCmb.addActionListener(this);
        currentDate = dates[0];
        currentBarber = barbers[0];
        
        for (int i = 0; i < 5; i++) {
        	slotBtn[i] = new JButton(timeSlots[i]);
        	slotBtn[i].setBackground(Color.WHITE);
        	slotBtn[i].addActionListener(this);
        }
        currLbl = new JLabel("", JLabel.LEFT);
        submitBtn = new JButton("Book Appointment");
        
        JPanel menuPanel = new JPanel(new BorderLayout(10, 10));
        JPanel slotPanel = new JPanel(new BorderLayout(0, 10));
        JPanel timePanel = new JPanel(new GridLayout(1, 5));

        JPanel barbPanel = new JPanel(new GridLayout(2, 1));
        JPanel datePanel = new JPanel(new GridLayout(2, 1));
        
        for (int i = 0; i < 5; i++) {
        	timePanel.add(slotBtn[i]);
        }
        
        datePanel.add(dateLbl); datePanel.add(dateCmb);
        slotPanel.add(datePanel, BorderLayout.NORTH);
        slotPanel.add(timePanel, BorderLayout.CENTER);
        
        barbPanel.add(barbLbl); barbPanel.add(barberCmb);
        menuPanel.add(barbPanel, BorderLayout.NORTH);
        menuPanel.add(slotPanel, BorderLayout.CENTER);
        
        JPanel submitPanel = new JPanel(new GridLayout(1, 2));
        submitBtn.addActionListener(this);
        submitPanel.add(currLbl); submitPanel.add(submitBtn);
        
        visitLbl = new JLabel();
        
        JPanel botPanel = new JPanel(new GridLayout(2, 1));
        botPanel.add(submitPanel);
        botPanel.add(visitLbl);
        
        
        try {
        	
        } catch (Exception e) { 
        	
        }
        pane.add(titleLbl, BorderLayout.NORTH);
        pane.add(menuPanel, BorderLayout.CENTER);
        pane.add(botPanel, BorderLayout.SOUTH);
        
        refreshSlots();
    }
    
    public void setCount(int n) {
    	if (n%10==0 && n > 0) {
    		visitLbl.setText("You've reached a free haircut!");
    		visitLbl.setForeground(Color.GREEN);
    	} else {
        	visitLbl.setText("Visits needed for next free haircut: " + (10 - (n%10)));
    		visitLbl.setForeground(Color.RED);
    	}
    }
    
    public void refreshSlots() {
		currLbl.setText(currentDate);
		slotCheck = controller.checkSlots(currentDate, currentBarber);
		for (int i = 0; i < 5; i++) {
			if (slotCheck[i]) {
				slotBtn[i].setBackground(Color.LIGHT_GRAY);
				slotBtn[i].setEnabled(false);
			} else {
    			slotBtn[i].setBackground(Color.WHITE);
				slotBtn[i].setEnabled(true);
			}
		}
		
    }
    
    public void actionPerformed(ActionEvent e) {
    	Object obj = e.getSource();
    	
    	if (obj == barberCmb) {
    		currentBarber = (Barber) barberCmb.getSelectedItem();
    		refreshSlots();
    	}
    	
    	if (obj == dateCmb) {
    		currentDate = (String) dateCmb.getSelectedItem();
    		refreshSlots();
    	}
    	
    	if (obj == submitBtn) {
    		controller.newApp(currentBarber, currentSlot);
    	}
    	
    	for (int i = 0; i < 5; i++) {
    		if (obj == slotBtn[i]) {
    			currentSlot = currentDate + " " + timeSlots[i];
    			currLbl.setText(currentSlot);
    			slotBtn[i].setBackground(Color.GREEN);
    		} else if (!slotCheck[i]){
    			slotBtn[i].setBackground(Color.WHITE);
    		}
    	}
    }
}
