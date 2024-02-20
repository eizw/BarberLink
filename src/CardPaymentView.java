import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class CardPaymentView extends JFrame implements ActionListener {

	private JButton btnPay;
	private JTextField textField;
	private JPasswordField passwordField;
	
	private Controller controller;
	/**
	 * Create the application.
	 */
	public CardPaymentView(Controller c) {
		controller = c;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Container pane = getContentPane();
		setTitle("Card Payment");
		setBounds(300, 300, 650, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		JLabel lblCardPayment = new JLabel("Card Payment");
		lblCardPayment.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblCardPayment.setBounds(166, 25, 307, 133);
		pane.add(lblCardPayment);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(235, 193, 160, 20);
		pane.add(textField);
		
		JLabel lblCardNumber = new JLabel("Card Number");
		lblCardNumber.setBounds(235, 169, 83, 14);
		pane.add(lblCardNumber);
		
		JLabel lblCardSecurityNumber = new JLabel("Card Security Number");
		lblCardSecurityNumber.setBounds(235, 234, 83, 14);
		pane.add(lblCardSecurityNumber);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(235, 259, 160, 20);
		pane.add(passwordField);
		
		btnPay = new JButton("Pay");
		btnPay.setBounds(266, 309, 89, 23);
		btnPay.addActionListener(this);
		pane.add(btnPay);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btnPay) {
			controller.payApp();
			setVisible(false);
		}
	}

}
