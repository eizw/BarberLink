import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class EWalletView extends JFrame implements ActionListener {

	private JButton btnPay;
	
	private Controller controller;
	/**
	 * Create the application.
	 */
	public EWalletView(Controller c) {
		controller = c;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Container pane = getContentPane();
		setTitle("E Wallet");
		setBounds(300, 300, 650, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		JLabel lblQR = new JLabel("");
		lblQR.setIcon(new ImageIcon(EWalletView.class.getResource("/img/QR.jpg")));
		lblQR.setBounds(255, 166, 135, 135);
		pane.add(lblQR);
		
		JLabel lblTitle = new JLabel("QR Payment");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblTitle.setBounds(193, 59, 261, 43);
		pane.add(lblTitle);
		
		btnPay = new JButton("Pay");
		btnPay.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPay.setBounds(278, 327, 89, 23);
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
