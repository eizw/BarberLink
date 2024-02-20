import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BarberLoginView extends JFrame implements ActionListener {
	private JButton btn_login, btnNewButton;
	private JTextField adminpass;

	
	private Controller controller;
	/**
	 * Create the application.
	 */
	public BarberLoginView(Controller c) {
		controller = c;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Container pane = getContentPane();
		pane.setBackground(Color.BLACK);
		setTitle("Admin Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage(BarberLoginView.class.getResource("/img/logo.jpg")));
		setBounds(300, 300, 650, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		JLabel lbl_adminpass = new JLabel("Enter Admin Password");
		lbl_adminpass.setForeground(Color.ORANGE);
		lbl_adminpass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_adminpass.setBounds(41, 183, 187, 14);
		pane.add(lbl_adminpass);
		
		adminpass = new JPasswordField();
		adminpass.setFont(new Font("Tahoma", Font.BOLD, 11));
		adminpass.setBackground(Color.WHITE);
		adminpass.setBounds(41, 208, 187, 20);
		pane.add(adminpass);
		
		JLabel lbl_bg_signup = new JLabel("bg");
		lbl_bg_signup.setIcon(new ImageIcon(BarberLoginView.class.getResource("/img/logo.jpg")));
		lbl_bg_signup.setBounds(199, 11, 470, 501);
		pane.add(lbl_bg_signup);
		
		btn_login = new JButton("Login");
		btn_login.setForeground(Color.ORANGE);
		btn_login.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_login.setBackground(Color.DARK_GRAY);
		btn_login.setBounds(76, 253, 113, 40);
		btn_login.addActionListener(this);
		pane.add(btn_login);
		
		btnNewButton = new JButton("Back");
		btnNewButton.setForeground(Color.ORANGE);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setBounds(0, 0, 89, 23);
		btnNewButton.addActionListener(this);
		pane.add(btnNewButton);
	}
	
	public void clearFields() {
		adminpass.setText("");
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btnNewButton) {
			setVisible(false);
			clearFields();
			controller.displayLoginPage();
		}
		
		if (obj == btn_login) {
			if (controller.loginBarb(adminpass.getText())) {
				setVisible(false);
				clearFields();
				return;
			}
			JOptionPane.showMessageDialog(null, "Wrong username or password");
			
		}
	}

}
