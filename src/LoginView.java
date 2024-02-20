import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.io.File;

public class LoginView extends JFrame implements ActionListener {

	private JFrame frmBarberlink;
	private JTextField txt_username;
	private JTextField pass;
	private JButton btn_login, btn_admin, btnNewButton;
	
	
	private Controller controller;
	/**
	 * Create the application.
	 */
	public LoginView(Controller c) {
		controller = c;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Container pane = getContentPane();
		pane.setBackground(Color.BLACK);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginView.class.getResource("/img/logo.jpg")));
		setTitle("BarberLink");
		setBounds(300, 300, 650, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane.setLayout(null);
		
		btn_admin = new JButton("Barber");
		btn_admin.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_admin.setForeground(Color.ORANGE);
		btn_admin.setBackground(Color.DARK_GRAY);
		btn_admin.setBounds(0, 11, 89, 23);
		btn_admin.addActionListener(this);
		pane.add(btn_admin);
		
		JLabel lbl_username = new JLabel("Enter Username");
		lbl_username.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_username.setForeground(Color.ORANGE);
		lbl_username.setBounds(21, 194, 120, 14);
		pane.add(lbl_username);
		
		txt_username = new JTextField();
		txt_username.setBackground(Color.WHITE);
		txt_username.setBounds(21, 219, 187, 20);
		pane.add(txt_username);
		txt_username.setColumns(10);
		
		JLabel lbl_pass = new JLabel("Enter Password");
		lbl_pass.setForeground(Color.ORANGE);
		lbl_pass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_pass.setBounds(21, 260, 120, 14);
		pane.add(lbl_pass);
		
		pass = new JPasswordField();
		pass.setFont(new Font("Tahoma", Font.BOLD, 11));
		pass.setBackground(Color.WHITE);
		pass.setBounds(21, 285, 187, 20);
		pane.add(pass);
		
		btn_login = new JButton("Login");
		btn_login.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_login.setBackground(Color.DARK_GRAY);
		btn_login.setForeground(Color.ORANGE);
		btn_login.setBounds(52, 336, 108, 40);
		btn_login.addActionListener(this);
		pane.add(btn_login);
		
		btnNewButton = new JButton("Sign Up");
		btnNewButton.setForeground(Color.ORANGE);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setBounds(52, 387, 108, 23);
		btnNewButton.addActionListener(this);
		pane.add(btnNewButton);
		
		JLabel lbl_bg = new JLabel("");
		lbl_bg.setIcon(new ImageIcon(LoginView.class.getResource("/img/login bg 2.jpg")));
		lbl_bg.setBounds(10, 5, 634, 461);
		pane.add(lbl_bg);
	}
	
	public void clearFields() {
		txt_username.setText("");
		pass.setText("");
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btn_admin) {
			setVisible(false);
			clearFields();
			controller.displayBarberLoginPage();
		}
		
		if (obj == btn_login) {
			if (controller.loginCust(txt_username.getText(), pass.getText())) {
                setVisible(false);
                clearFields();
                return;
			}
			JOptionPane.showMessageDialog(null, "Password doesn't exist");
		}
		
		if (obj == btnNewButton) {
			controller.displaySignupPage();
			clearFields();
			setVisible(false);
		}
	}
}