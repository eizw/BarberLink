import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.List;

public class UserMenu extends JFrame implements ActionListener {
	private final long ONE_MINUTE_IN_MILLIS = 60000;
    private final int WIDTH = 900;
    private final int HEIGHT = 680;
    private final Color BACKGROUND_COLOR = new Color(20, 20, 20);
    private final Color MENU_COLOR = new Color(250, 250, 250);
    private final String LOGO_PATH = "img/logo.jpg";
    private final String FONT_ROBOTO_PATH = "fonts/Roboto-Light.ttf";
    
    private final URL url = UserMenu.class.getResource(LOGO_PATH);
    
    private JLabel logoLbl, visitLbl;
    private JLabel[] headerLbl = new JLabel[2];
    // 0 = dashboard
    // 1 = check visit count
    private JButton[] sideBtn = new JButton[3];
    private JButton appBtn, logoutBtn;
    private JPanel[] appCards;
    private JPanel[] histCards;
    private JButton[] reviewBtn; 
    private JPanel appPanel;
    private JPanel vhistPanel;
    private JPanel histPanel;
    // 0 = dashboard, 1 = profile
    private int currMenu = 0;
    private CardLayout mainLay = new CardLayout();
    private JPanel mainPanel;
    
    private Controller controller;
    private Customer currentUser;
    private List<Appointment> appCurr;
    private List<Appointment> appHist;
    
    public UserMenu (Controller c) {
        controller = c;
        currentUser = (Customer) c.getCurrentUser();
        
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
        sideBtn[0] = new JButton("Dashboard");
        sideBtn[1] = new JButton("View Appointment History");
        sideBtn[2] = new JButton("View Profile                       ");
        sideBtn[0].setHorizontalAlignment(SwingConstants.LEFT);
        sideBtn[1].setHorizontalAlignment(SwingConstants.LEFT);
        sideBtn[2].setHorizontalAlignment(SwingConstants.LEFT);
        sideBtn[0].addActionListener(this);
        sideBtn[1].addActionListener(this);
        sideBtn[2].addActionListener(this);
        logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(this);
        
        appBtn = new JButton("+ New Booking");
        appBtn.setBackground(Color.WHITE);
        appBtn.setBorder(new RoundedBorder(15));
        appBtn.addActionListener(this);
        //appBtn.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        
        
        
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
        for (int i = 0; i < 3; i++) sideButtonPanel.add(sideBtn[i]);
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
        appPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        
        
        
        dashPanel.add(appPane, BorderLayout.CENTER);
        appBtn.setPreferredSize(new Dimension(150, 40));
        JPanel temp = new JPanel(new BorderLayout());
        temp.setBackground(MENU_COLOR);
        temp.add(appBtn, BorderLayout.EAST);
        dashPanel.add(temp, BorderLayout.SOUTH);
        
        //
        // VIEW HISTORY
        //
        vhistPanel = new JPanel();
        vhistPanel.setSize(900, HEIGHT);
        vhistPanel.setBackground(MENU_COLOR);
        vhistPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 10));
        vhistPanel.setLayout(new BorderLayout(10, 10));
        vhistPanel.add(headerLbl[1], BorderLayout.NORTH);
        

    	histPanel = new JPanel();
        histPanel.setBackground(MENU_COLOR);
        histPanel.setBounds(10, 10, 10, 10);
        histPanel.setLayout(new BoxLayout(histPanel, BoxLayout.Y_AXIS));

        JScrollPane histPane = new JScrollPane(histPanel);
        histPane.setBorder(BorderFactory.createEmptyBorder());
        
        
        
        
        vhistPanel.add(histPane, BorderLayout.CENTER);
        appBtn.setPreferredSize(new Dimension(150, 40));
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
		

		visitLbl = new JLabel("Haircuts done: " + currentUser.getVisitCount());
		visitLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		visitLbl.setBounds(186, 299, 265, 34);
		profPanel.add(visitLbl);

		
		try {
            ImageIcon logo = new ImageIcon(ImageIO.read(url).getScaledInstance(125, 125, Image.SCALE_DEFAULT));
            final Font roboto = Font.createFont(Font.TRUETYPE_FONT, UserMenu.class.getResourceAsStream(FONT_ROBOTO_PATH));
            
            logoLbl.setIcon(logo);
            logoLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
            
            for (int i = 0; i < 2; i++) {
                headerLbl[i].setFont(roboto.deriveFont(48f));
                headerLbl[i].setBorder(BorderFactory.createEmptyBorder(30, 0, 35, 0));
            }
            for (int i = 0; i < 3; i++) {
                sideBtn[i].setFont(roboto.deriveFont(24f));
                sideBtn[i].setForeground(Color.WHITE);
                sideBtn[i].setBackground(BACKGROUND_COLOR);
                sideBtn[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            }
            
            logoutBtn.setFont(roboto.deriveFont(24f));
            appBtn.setFont(roboto.deriveFont(Font.BOLD, 14f));
            
            logoutBtn.setForeground(Color.WHITE);
            logoutBtn.setBackground(BACKGROUND_COLOR);
            
            
        } catch (Exception e) {}
		
        mainPanel = new JPanel(mainLay);
        mainPanel.add("dashboard", dashPanel);
        mainPanel.add("history", vhistPanel);
        mainPanel.add("profile", profPanel);
        pane.add(sidePanel, BorderLayout.WEST);
        pane.add(mainPanel, BorderLayout.CENTER);
        mainLay.show(mainPanel, "dashboard");
    }
    
    // GET all appointments for current user
    public void getApps() {
    	visitLbl.setText("Haircuts done: " + currentUser.getVisitCount());
    	ArrayList<Appointment> apps = controller.getAppList();

    	appCurr = new ArrayList<Appointment>();
    	appHist = new ArrayList<Appointment>();
    	Date now = new Date();
    	for (int i = 0; i < apps.size(); i++) {
        	Appointment curr = apps.get(i);
        	if (curr.getCust() != currentUser) continue;
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
    
    public void refreshApp() {
    	appPanel.removeAll();
        appCards = new JPanel[appCurr.size()];
        System.out.println("Refreshed applications");
        for (int i = 0; i < appCurr.size(); i++) {
        	Appointment curr = appCurr.get(i);
        	appCards[i] = new JPanel(new BorderLayout());
        	JLabel barbLbl = new JLabel("Appointment with : " + curr.getBarber().getName(), JLabel.LEFT);
        	JLabel timeLbl = new JLabel("Starts : " + curr.getDate(), JLabel.LEFT);
        	JLabel statLbl = new JLabel((curr.getStatus()) ? "Status : Accepted" : "Status : Pending");
        	
        	try {
        		final Font roboto = Font.createFont(Font.TRUETYPE_FONT, UserMenu.class.getResourceAsStream(FONT_ROBOTO_PATH));
        		
        		barbLbl.setFont(roboto.deriveFont(16f));
        		timeLbl.setFont(roboto.deriveFont(36f));
        		statLbl.setFont(roboto.deriveFont(14f));
        		
        		statLbl.setForeground((curr.getStatus()) ? Color.GREEN : Color.RED);
        	} catch (Exception e) {}
        	
        	appCards[i].add(barbLbl, BorderLayout.NORTH);
        	appCards[i].add(timeLbl, BorderLayout.CENTER);
        	appCards[i].add(statLbl, BorderLayout.SOUTH);
        	
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
        	JLabel statLbl = new JLabel("Status : Done");
        	histCards[i].add(barbLbl, BorderLayout.NORTH);
        	histCards[i].add(timeStartLbl, BorderLayout.CENTER);
        	histCards[i].add(statLbl, BorderLayout.SOUTH);

        	reviewBtn[i] = new JButton();
        	if (curr.getFeedback() == null) {
        		reviewBtn[i].setText("Give Review");
        	} else {
        		String[] temp = curr.getFeedback().split("\n");
        		String rating = temp[temp.length - 1];
        		statLbl.setText("Status : Reviewed");
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
        		final Font roboto = Font.createFont(Font.TRUETYPE_FONT, UserMenu.class.getResourceAsStream(FONT_ROBOTO_PATH));
        		
        		barbLbl.setFont(roboto.deriveFont(16f));
        		timeStartLbl.setFont(roboto.deriveFont(28f));
        		statLbl.setFont(roboto.deriveFont(14f));
        		reviewBtn[i].setFont(roboto.deriveFont(12f));
        		reviewBtn[i].setBackground(Color.WHITE);
        		
        		statLbl.setForeground(Color.ORANGE);
        	} catch (Exception e) {}
        	histPanel.add(histCards[i]);
        	histPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }
    }
    
    public void actionPerformed(ActionEvent e) {
    	Object obj = e.getSource();
    	
    	if (obj == appBtn) {
    		controller.displayNewAppMenu();
    		this.setVisible(false);
    	}
    	
    	if (obj == logoutBtn) {
    		controller.logoutUser();
    		this.setVisible(false);
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
    		mainLay.show(mainPanel, "profile");
    	}
    	
    	for (int i = 0; i < appHist.size(); i++) {
    		if (obj == reviewBtn[i]) {
    			controller.displayNewReviewMenu(appHist.get(i));
    			this.setVisible(false);
    		}
    	}
    }
}