import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpView extends JFrame implements ActionListener {

	private JTextField txt_createuser;
	private JTextField passwordField;
	private JButton btn_createacc, btnNewButton;


	private Controller controller;
	
	/**
	 * Create the application.
	 */
	public SignUpView(Controller c) {
		controller = c;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Container pane = getContentPane();
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignUpView.class.getResource("/img/logo.jpg")));
		pane.setBackground(Color.BLACK);
		setLayout(null);
		
		JLabel lbl_Createusername = new JLabel("Create Username");
		lbl_Createusername.setForeground(Color.ORANGE);
		lbl_Createusername.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_Createusername.setBounds(35, 117, 120, 14);
		pane.add(lbl_Createusername);
		
		txt_createuser = new JTextField();
		txt_createuser.setColumns(10);
		txt_createuser.setBackground(Color.WHITE);
		txt_createuser.setBounds(35, 142, 187, 20);
		pane.add(txt_createuser);
		
		JLabel lbl_Createpass = new JLabel("Create Password");
		lbl_Createpass.setForeground(Color.ORANGE);
		lbl_Createpass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_Createpass.setBounds(35, 183, 120, 14);
		pane.add(lbl_Createpass);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordField.setBackground(Color.WHITE);
		passwordField.setBounds(35, 208, 187, 20);
		pane.add(passwordField);
		
		btn_createacc = new JButton("Create Account");
		btn_createacc.setForeground(Color.ORANGE);
		btn_createacc.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_createacc.setBackground(Color.DARK_GRAY);
		btn_createacc.setBounds(55, 259, 141, 40);
		btn_createacc.addActionListener(this);
		pane.add(btn_createacc);
		
		JLabel lbl_bg_signup = new JLabel("bg");
		lbl_bg_signup.setIcon(new ImageIcon(SignUpView.class.getResource("/img/logo.jpg")));
		lbl_bg_signup.setBounds(207, 11, 470, 501);
		pane.add(lbl_bg_signup);
		
		btnNewButton = new JButton("Back");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setForeground(Color.ORANGE);
		btnNewButton.setBounds(0, 11, 89, 23);
		btnNewButton.addActionListener(this);
		pane.add(btnNewButton);
		setTitle("Sign Up");
		setBounds(300, 300, 650, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btn_createacc) {
			controller.registerCustomer(txt_createuser.getText(), passwordField.getText());
		}
		
		if (obj == btnNewButton) {
			setVisible(false);
			controller.displayLoginPage();
		}
	}

}
