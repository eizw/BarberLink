import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class BarberMenu extends JFrame implements ActionListener {
    private final int WIDTH = 900;
    private final int HEIGHT = 680;
    private final Color BACKGROUND_COLOR = new Color(20, 20, 20);
    private final Color MENU_COLOR = new Color(250, 250, 250);
    private final String LOGO_PATH = "img/logo.jpg";
    private final String FONT_ROBOTO_PATH = "fonts/Roboto-Light.ttf";
    
    private final URL url = BarberMenu.class.getResource(LOGO_PATH);
    
    private JLabel logoLbl, welcomeLbl;
    // 0 = dashboard
    // 1 = check visit count
    // 2 = history
    // 3 = off days
    private JLabel[] headerLbl = new JLabel[3];
    // 0 = dashboard
    // 1 = check visit count
    private JButton[] sideBtn = new JButton[4];
    private JButton logoutBtn;
    private JPanel[] appCards;
    private JPanel[] histCards;
    private JButton[] acceptBtn, rejectBtn, reviewBtn;
    private JPanel appPanel;
    private JPanel vhistPanel;
    private JPanel histPanel;
    private JPanel offdayPanel;
    
    private JPanel timePanel;
    private JButton[] daysBtn;
    private int[] checkDays;
    private String[] dates = new String[7];
    private String currentDate;
    private JButton submitBtn;
    // 0 = dashboard, 1 = history, 2 = offday, 3 = profile
    private int currMenu = 0;
    private CardLayout mainLay = new CardLayout();
    private JPanel mainPanel;
    
    private Controller controller;
    private Barber currentUser;
    private List<Appointment> appCurr;
    private List<Appointment> appHist;
    
    public BarberMenu (Controller c) {
        controller = c;
        currentUser = (Barber) c.getCurrentUser();
    	// get the next 7 days
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	for (int i = 0; i < 7; i++) {
    		Calendar temp = Calendar.getInstance();
    		temp.add(Calendar.DATE, i);
    		dates[i] = formatter.format(temp.getTime());
    	}
    	currentDate = dates[0];
        
        Container pane = getContentPane();
        pane.setBackground(BACKGROUND_COLOR);
        pane.setLayout(new BorderLayout());
        ((JComponent)pane).setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        
        setSize(WIDTH, HEIGHT);
        setTitle("Barber Link");
        setResizable(false);
        setLocationRelativeTo(null);
        
        logoLbl = new JLabel("", JLabel.LEFT);
        headerLbl[0] = new JLabel("Welcome " + currentUser.getName() + "!", JLabel.LEFT);
        headerLbl[1] = new JLabel("Appointment History", JLabel.LEFT);
        headerLbl[2] = new JLabel("Off Days next 7 days", JLabel.LEFT);
        sideBtn[0] = new JButton("Dashboard");
        sideBtn[1] = new JButton("View Appointment History");
        sideBtn[2] = new JButton("View Off Days");
        sideBtn[3] = new JButton("View Profile                       ");
        for (int i = 0; i < 4; i++) {
            sideBtn[i].setHorizontalAlignment(SwingConstants.LEFT);
            sideBtn[i].addActionListener(this);
        }
        logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(this);
       
        //.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        
        
        
        //
        // SIDEBAR
        //
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setSize(500, JFrame.MAXIMIZED_VERT);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sidePanel.setBackground(BACKGROUND_COLOR);
        
        JPanel sideButtonPanel = new JPanel();
        sideButtonPanel.setBackground(BACKGROUND_COLOR);
        sideButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        sideButtonPanel.setLayout(new BoxLayout(sideButtonPanel, BoxLayout.PAGE_AXIS));
        //sideButtonPanel.setSize(300, JFrame.MAXIMIZED_VERT);
        for (int i = 0; i < 4; i++) sideButtonPanel.add(sideBtn[i]);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        sidePanel.add(logoLbl, BorderLayout.NORTH);
        sidePanel.add(sideButtonPanel, BorderLayout.CENTER);
        sidePanel.add(logoutBtn, BorderLayout.SOUTH);
        
        //
        // DASHBOARD 
        //
        JPanel dashPanel = new JPanel();
        dashPanel.setSize(900, HEIGHT);
        dashPanel.setBackground(MENU_COLOR);
        dashPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 10));
        dashPanel.setLayout(new BorderLayout(10, 10));
        dashPanel.add(headerLbl[0], BorderLayout.NORTH);
        

    	appPanel = new JPanel();
        appPanel.setBackground(MENU_COLOR);
        appPanel.setBounds(10, 10, 10, 10);
        appPanel.setLayout(new BoxLayout(appPanel, BoxLayout.Y_AXIS));

        JScrollPane appPane = new JScrollPane(appPanel);
        appPane.setBorder(BorderFactory.createEmptyBorder());
        
        
        
        
        dashPanel.add(appPane, BorderLayout.CENTER);
        
        //
        // VIEW HISTORY
        //
        vhistPanel = new JPanel();
        vhistPanel.setSize(900, HEIGHT);
        vhistPanel.setBackground(MENU_COLOR);
        vhistPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 10));
        vhistPanel.setLayout(new BorderLayout(10, 10));

    	histPanel = new JPanel();
        histPanel.setBackground(MENU_COLOR);
        histPanel.setBounds(10, 10, 10, 10);
        histPanel.setLayout(new BoxLayout(histPanel, BoxLayout.Y_AXIS));

        JScrollPane histPane = new JScrollPane(histPanel);
        histPane.setBorder(BorderFactory.createEmptyBorder());
        
        vhistPanel.add(headerLbl[1], BorderLayout.NORTH);
        vhistPanel.add(histPane, BorderLayout.CENTER);
        //
        // VIEW OFF DAYS
        //
        offdayPanel = new JPanel();
        offdayPanel.setSize(900, HEIGHT);
        offdayPanel.setBackground(MENU_COLOR);
        offdayPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 10));
        offdayPanel.setLayout(new BorderLayout(10, 10));
        
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
        
        offdayPanel.add(headerLbl[2], BorderLayout.NORTH);
        offdayPanel.add(timePanel, BorderLayout.CENTER);
        offdayPanel.add(submitPanel, BorderLayout.SOUTH);
        
        //
        // VIEW PROFILE
        //
        JPanel profPanel = new JPanel();
		profPanel.setBackground(Color.WHITE);
		profPanel.setForeground(Color.BLACK);
		profPanel.setBounds(0, 0, 140, 461);
		profPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(186, 96, 199, 41);
		profPanel.add(lblNewLabel);
		
		JTextField txt_username = new JTextField();
		txt_username.setEnabled(false);
		txt_username.setBounds(186, 148, 265, 34);
		profPanel.add(txt_username);
		txt_username.setColumns(10);
		txt_username.setText(currentUser.getName());
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setEnabled(false);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(186, 219, 199, 41);
		profPanel.add(lblPassword);
		
		JTextField txt_password = new JTextField();
		txt_password.setColumns(10);
		txt_password.setBounds(186, 271, 265, 34);
		txt_password.setText(currentUser.getPassword());
		profPanel.add(txt_password);

		
		try {
            ImageIcon logo = new ImageIcon(ImageIO.read(url).getScaledInstance(125, 125, Image.SCALE_DEFAULT));
            final Font roboto = Font.createFont(Font.TRUETYPE_FONT, BarberMenu.class.getResourceAsStream(FONT_ROBOTO_PATH));
            
            logoLbl.setIcon(logo);
            logoLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
            
            for (int i = 0; i < 3; i++) {
                headerLbl[i].setFont(roboto.deriveFont(48f));
                headerLbl[i].setBorder(BorderFactory.createEmptyBorder(30, 0, 35, 0));
            }
            for (int i = 0; i < 4; i++) {
                sideBtn[i].setFont(roboto.deriveFont(24f));
                sideBtn[i].setForeground(Color.WHITE);
                sideBtn[i].setBackground(BACKGROUND_COLOR);
                sideBtn[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            }
            
            submitBtn.setFont(roboto.deriveFont(14f));
            submitBtn.setBackground(Color.WHITE);
            submitBtn.setBorder(new RoundedBorder(20));
            
            logoutBtn.setFont(roboto.deriveFont(24f));
            
            logoutBtn.setForeground(Color.WHITE);
            logoutBtn.setBackground(BACKGROUND_COLOR);
            
            
        } catch (Exception e) {}
		
        mainPanel = new JPanel(mainLay);
        mainPanel.add("dashboard", dashPanel);
        mainPanel.add("history", vhistPanel);
        mainPanel.add("offday", offdayPanel);
        mainPanel.add("profile", profPanel);
        pane.add(sidePanel, BorderLayout.WEST);
        pane.add(mainPanel, BorderLayout.CENTER);
        mainLay.show(mainPanel, "dashboard");
    }
    
    // GET all appointments for current user
    public void getApps() {
    	ArrayList<Appointment> apps = controller.getAppList();

    	appCurr = new ArrayList<Appointment>();
    	appHist = new ArrayList<Appointment>();
    	Date now = new Date();
    	for (int i = 0; i < apps.size(); i++) {
        	Appointment curr = apps.get(i);
        	if (curr.getBarber() != currentUser) continue;
        	Date date;
			try {
				date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(curr.getDate());
	    		if (date.before(now)) {
	    			appHist.add(curr);
	    		} else {
	    			appCurr.add(curr);
	    		}
			} catch (ParseException e) {
				System.out.println(e);
			}
    	}
    	refreshApp();
    	refreshHist();
    }
    // GET all working days of current user
    public void refreshDays() {
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
				daysBtn[i].addActionListener(this);
			}
			timePanel.add(daysBtn[i]);
		}
		timePanel.setVisible(false);
		timePanel.setVisible(true);
    }
    
    //
    // REFRESH UPCOMING APPOINTMENTS
    //
    public void refreshApp() {
    	appPanel.removeAll();
        appCards = new JPanel[appCurr.size()];
        acceptBtn = new JButton[appCurr.size()];
        rejectBtn = new JButton[appCurr.size()];
        System.out.println("Refreshed applications");
        for (int i = 0; i < appCurr.size(); i++) {
        	Appointment curr = appCurr.get(i);
        	if (curr.getBarber() != currentUser) continue;
        	appCards[i] = new JPanel(new BorderLayout());
        	JLabel barbLbl = new JLabel("Appointment with : " + curr.getCust().getName(), JLabel.LEFT);
        	JLabel timeLbl = new JLabel("Starts : " + curr.getDate(), JLabel.LEFT);
        	JLabel reviewLbl = new JLabel((curr.getStatus()) ? "Status : Accepted" : "Status : Pending");
        	acceptBtn[i] = new JButton("Accept");
        	rejectBtn[i] = new JButton("Reject");
        	acceptBtn[i].addActionListener(this);
        	rejectBtn[i].addActionListener(this);
        	
        	try {
        		final Font roboto = Font.createFont(Font.TRUETYPE_FONT, BarberMenu.class.getResourceAsStream(FONT_ROBOTO_PATH));
        		
        		barbLbl.setFont(roboto.deriveFont(16f));
        		timeLbl.setFont(roboto.deriveFont(34f));
        		reviewLbl.setFont(roboto.deriveFont(14f));
        		
        		reviewLbl.setForeground((curr.getStatus()) ? Color.GREEN : Color.RED);
        	} catch (Exception e) {}
        	
        	JPanel btnPanel = new JPanel(new GridLayout(3, 1));
        	btnPanel.add(acceptBtn[i]);
        	btnPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        	btnPanel.add(rejectBtn[i]);
        	btnPanel.setBackground(Color.WHITE);
        	
        	appCards[i].add(barbLbl, BorderLayout.NORTH);
        	appCards[i].add(timeLbl, BorderLayout.CENTER);
        	appCards[i].add(reviewLbl, BorderLayout.SOUTH);
        	if (!curr.getStatus()) appCards[i].add(btnPanel, BorderLayout.EAST);
        	appCards[i].setBackground(MENU_COLOR);
        	appCards[i].setBorder(new RoundedBorder(20));
        	appCards[i].setMaximumSize(new Dimension(520, 125));
        	appPanel.add(appCards[i]);
        	appPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }
    }
    
    //
    // REFRESH HISTORY
    //
    public void refreshHist() {
    	histPanel.removeAll();
        histCards = new JPanel[appHist.size()];
        reviewBtn = new JButton[appHist.size()];
        System.out.println("Refreshed history");
        for (int i = 0; i < appHist.size(); i++) {
        	Appointment curr = appHist.get(i);
        	histCards[i] = new JPanel(new BorderLayout());
        	JLabel barbLbl = new JLabel("Appointment with : " + curr.getBarber().getName(), JLabel.LEFT);
        	JLabel timeStartLbl = new JLabel("Started : " + curr.getDate(), JLabel.LEFT);
        	JTextField reviewLbl = new JTextField();
        	reviewLbl.setEditable(false);
        	
        	histCards[i].add(barbLbl, BorderLayout.NORTH);
        	histCards[i].add(timeStartLbl, BorderLayout.CENTER);
        	histCards[i].add(reviewLbl, BorderLayout.SOUTH);

        	reviewBtn[i] = new JButton();
        	if (curr.getFeedback() == null) {
        		reviewBtn[i].setVisible(false);
        	} else {
        		
        		String[] temp = curr.getFeedback().split("\n");
        		String rating = temp[temp.length - 1];
        		String review = "";
        		for (int j = 0; j < temp.length - 1; j++) {
        			review += temp[j];
        		}
        		reviewLbl.setText(review);
        		reviewBtn[i].setText("Rated " + rating + ".0/5.0");
        		reviewBtn[i].setBorder(null);
        		reviewBtn[i].setEnabled(false);
        	}
        	reviewBtn[i].setBorder(new RoundedBorder(20));
        	reviewBtn[i].addActionListener(this);
        	histCards[i].add(reviewBtn[i], BorderLayout.EAST);
        	
        	histCards[i].setBackground(MENU_COLOR);
        	histCards[i].setBorder(new RoundedBorder(20));
        	histCards[i].setMaximumSize(new Dimension(520, 125));
        	
        	try {
        		final Font roboto = Font.createFont(Font.TRUETYPE_FONT, BarberMenu.class.getResourceAsStream(FONT_ROBOTO_PATH));
        		
        		barbLbl.setFont(roboto.deriveFont(16f));
        		timeStartLbl.setFont(roboto.deriveFont(28f));
        		reviewLbl.setFont(roboto.deriveFont(14f));
        		reviewBtn[i].setFont(roboto.deriveFont(12f));
        		reviewBtn[i].setBackground(Color.WHITE);
        		
        		reviewLbl.setForeground(Color.BLACK);
        	} catch (Exception e) {}
        	histPanel.add(histCards[i]);
        	histPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }
    }
    
    public void actionPerformed(ActionEvent e) {
    	Object obj = e.getSource();
    	
    	if (obj == logoutBtn) {
    		controller.logoutUser();
    		this.setVisible(false);
    	}
    	
    	if (obj == submitBtn) {
    		controller.addOffDay(currentDate);
    	}
    	
    	// cards
    	if (obj == sideBtn[0] && currMenu != 0) {
    		currMenu = 0;
    		mainLay.show(mainPanel, "dashboard");
    	}
    	if (obj == sideBtn[1] && currMenu != 1) {
    		currMenu = 1;
    		mainLay.show(mainPanel, "history");
    	}

    	if (obj == sideBtn[2] && currMenu != 2) {
    		currMenu = 2;
    		mainLay.show(mainPanel, "offday");
    	}

    	if (obj == sideBtn[3] && currMenu != 3) {
    		currMenu = 3;
    		mainLay.show(mainPanel, "profile");
    	}
    	
    	for (int i = 0; i < appCurr.size(); i++) {
    		if (obj == acceptBtn[i]) {
    			if (controller.acceptApp(appCurr.get(i))) {
    				acceptBtn[i].setVisible(false);
    				rejectBtn[i].setVisible(false);
    				getApps();
    			}
    		}
    	}
    	
    	for (int i = 0; i < appHist.size(); i++) {
    		if (obj == reviewBtn[i]) {
    			controller.displayNewReviewMenu(appHist.get(i));
    			this.setVisible(false);
    		}
    	}
    	

    	for (int i = 0; i < 7; i++) {
    		if (obj == daysBtn[i]) {
    			daysBtn[i].setBackground(Color.GREEN);
    		} else if (checkDays[i] == 0){
    			daysBtn[i].setBackground(Color.WHITE);
    		}
    	}
    }
}