import java.awt.EventQueue;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;

public class PaymentView extends JFrame implements ActionListener {

	private JPanel paymentPanel;
	private JLabel lblPayment;
	private JRadioButton rdbtnOnlineBanking;
	private JRadioButton rdbtnEWallet;
	private JRadioButton rdbtnCard;
	private JButton btnPay;
	private ButtonGroup paymentOptions;
	private JFrame onlineBankingFrame;
	
	private Controller controller;
	
	public PaymentView(Controller c) {
		controller = c;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Container pane = getContentPane();
		setBackground(new Color(240, 240, 240));
		setTitle("Payment");
		setBounds(300, 300, 650, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		 paymentOptions = new ButtonGroup();
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 140, 461);
		pane.add(panel);
		
		JLabel lblNewLabel = new JLabel("Payment");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(315, 33, 140, 64);
		pane.add(lblNewLabel);
		
		rdbtnOnlineBanking = new JRadioButton("Online Banking");
		rdbtnOnlineBanking.setBounds(322, 168, 109, 23);
		pane.add(rdbtnOnlineBanking);
		paymentOptions.add(rdbtnOnlineBanking);
		
		rdbtnEWallet = new JRadioButton("E Wallet");
		rdbtnEWallet.setBounds(322, 204, 109, 23);
		pane.add(rdbtnEWallet);
		paymentOptions.add(rdbtnEWallet);
		
		rdbtnCard  = new JRadioButton("Card");
		rdbtnCard .setBounds(322, 240, 109, 23);
		pane.add(rdbtnCard );
		paymentOptions.add(rdbtnCard);
		
		btnPay = new JButton("Pay");
		btnPay.addActionListener(this);
		btnPay.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPay.setBounds(322, 285, 89, 37);
		rdbtnOnlineBanking.setSelected(true);
		pane.add(btnPay);
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btnPay) {
	        // Check which radio button is selected
	        if (rdbtnOnlineBanking.isSelected()) {
	            controller.showOnlineBankingView();
	        } else if (rdbtnEWallet.isSelected()) {
	            controller.showEWalletView();
	        } else if (rdbtnCard.isSelected()) {
	            controller.showCardPaymentView();
	        }
            setVisible(false);
			
		}
    }
	
}
