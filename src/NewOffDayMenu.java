import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

public class NewOffDayMenu extends JFrame implements ActionListener {
	private final int WIDTH = 650;
    private final int HEIGHT = 600;
    private final Color BACKGROUND_COLOR = new Color(20, 20, 20);
    private final Color MENU_COLOR = new Color(250, 250, 250);
    private final String FONT_ROBOTO_PATH = "../fonts/Roboto-Light.ttf";
    
    private JLabel titleLbl, dateLbl;
    private JTextField dateFld;
    private Barber[] barbers;
    // 10, 1030, 11, 1130, 12
    private String[] dates = new String[7];
    // 0 = free, 1 = ada app, 2 = off day
    private int[] checkDays;
    private JButton[] daysBtn;
    private JButton submitBtn;
    private JPanel timePanel;

    private String currentDate;
    private Controller controller;
    private List<Appointment> apps;
    
    public NewOffDayMenu (Controller c) {
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
        JPanel legendPanel = new JPanel(new GridLayout(2, 2));
        JLabel[] legendLbl = new JLabel[2];
        JButton[] legendBtn = new JButton[2];
        legendBtn[0] = new JButton("");
        legendBtn[1] = new JButton("");
        legendBtn[0].setBackground(Color.LIGHT_GRAY);
        legendBtn[1].setBackground(Color.GRAY);
        legendLbl[0] = new JLabel("= There is an appointment", JLabel.LEFT);
        legendLbl[1] = new JLabel("= Already an Off Day", JLabel.LEFT);
        
        legendPanel.add(legendBtn[0]); legendPanel.add(legendLbl[0]);
        legendPanel.add(legendBtn[1]); legendPanel.add(legendLbl[1]);
        
        currentDate = dates[0];

        timePanel = new JPanel(new GridLayout(7, 1));
        refreshDays();
        submitBtn = new JButton("Set Off Day");
        
        
        JPanel submitPanel = new JPanel(new GridLayout(1, 2));
        submitBtn.addActionListener(this);
        submitPanel.add(legendPanel);
        submitPanel.add(submitBtn);
        
        pane.add(titleLbl, BorderLayout.NORTH);
        pane.add(timePanel, BorderLayout.CENTER);
        pane.add(submitPanel, BorderLayout.SOUTH);
    }
    
    private void refreshDays() {
		checkDays = controller.checkOffDays(dates);
		timePanel.removeAll();
		
		daysBtn = new JButton[7];
		for (int i = 0; i < 7; i++) {
			daysBtn[i] = new JButton(dates[i]);
			if (checkDays[i] != 0) {
				daysBtn[i].setEnabled(false);
				daysBtn[i].setBackground((checkDays[i] == 1) ? Color.LIGHT_GRAY : Color.GRAY);
			} else {
				daysBtn[i].setBackground(Color.WHITE);
			}
			timePanel.add(daysBtn[i]);
		}
    }
    
    public void actionPerformed(ActionEvent e) {
    	Object obj = e.getSource();
    	
    	if (obj == submitBtn) {
    		controller.addOffDay(currentDate);
    	}
    	
    	for (int i = 0; i < 7; i++) {
    		if (obj == daysBtn[i]) {
    			
    		} else if (checkDays[i] == 0){
    			daysBtn[i].setBackground(Color.WHITE);
    		}
    	}
    }
}
