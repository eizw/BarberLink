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

public class OnlineBankingView extends JFrame implements ActionListener {

	private JTextField txtBankUsername;
	private JPasswordField passwordfld;
	private JButton btnPay;
	
	private Controller controller;
	/**
	 * Create the application.
	 */
	public OnlineBankingView(Controller c) {
		controller = c;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Container pane = getContentPane();
		setTitle("Online Banking");
		setBounds(300, 300, 650, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Online Banking");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setBounds(168, 31, 307, 133);
		pane.add(lblNewLabel);
		
		txtBankUsername = new JTextField();
		txtBankUsername.setBounds(237, 199, 160, 20);
		pane.add(txtBankUsername);
		txtBankUsername.setColumns(10);
		
		JLabel lnlUsername = new JLabel("Username");
		lnlUsername.setBounds(237, 175, 83, 14);
		pane.add(lnlUsername);
		
		JLabel txtPassword = new JLabel("Password");
		txtPassword.setBounds(237, 240, 83, 14);
		pane.add(txtPassword);
		
		passwordfld = new JPasswordField();
		passwordfld.setBounds(237, 265, 160, 20);
		pane.add(passwordfld);
		
		btnPay = new JButton("Pay");
		btnPay.setBounds(268, 315, 89, 23);
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
