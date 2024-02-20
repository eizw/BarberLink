import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JSlider;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

public class NewReviewMenu extends JFrame implements ActionListener {
	private JTextField txt_feedback;
	private JButton btnNewButton;
	private JSlider ratingSldr;
	
	private Appointment currApp;
	private Controller controller;

	/**
	 * Create the application.
	 */
	public NewReviewMenu(Controller c) {
		controller = c;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Container pane = getContentPane();
		pane.setBackground(Color.BLACK);
		setIconImage(Toolkit.getDefaultToolkit().getImage(NewReviewMenu.class.getResource("/img/logo.jpg")));
		setTitle("Customer Feedback");
		setBounds(300, 300, 650, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane.setLayout(null);
		
		ratingSldr = new JSlider();
		ratingSldr.setMajorTickSpacing(1);
		ratingSldr.setMaximum(5);
		ratingSldr.setToolTipText("");
		ratingSldr.setBackground(Color.DARK_GRAY);
		ratingSldr.setForeground(Color.WHITE);
		ratingSldr.setOrientation(SwingConstants.VERTICAL);
		ratingSldr.setSnapToTicks(true);
		ratingSldr.setPaintTicks(true);
		ratingSldr.setPaintLabels(true);
		ratingSldr.setBounds(0, 85, 72, 320);
		pane.add(ratingSldr);
		
		txt_feedback = new JTextField();
		txt_feedback.setToolTipText("");
		txt_feedback.setHorizontalAlignment(SwingConstants.CENTER);
		txt_feedback.setBounds(143, 128, 406, 228);
		pane.add(txt_feedback);
		txt_feedback.setColumns(10);
		
		btnNewButton = new JButton("Submit");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setForeground(Color.ORANGE);
		btnNewButton.setBounds(512, 406, 89, 23);
		btnNewButton.addActionListener(this);
		pane.add(btnNewButton);
		
		JLabel lbl_instruction = new JLabel("Dear customer do leave us some feedbacks to help us improve our \r\n\r\nservices .");
		lbl_instruction.setVerticalAlignment(SwingConstants.TOP);
		lbl_instruction.setHorizontalAlignment(SwingConstants.TRAILING);
		lbl_instruction.setForeground(Color.ORANGE);
		lbl_instruction.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_instruction.setBounds(119, 55, 443, 23);
		pane.add(lbl_instruction);
		
		JLabel lblins = new JLabel(" Use the slider to express your satisfaction of our services .");
		lblins.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblins.setForeground(Color.ORANGE);
		lblins.setBounds(129, 87, 433, 14);
		pane.add(lblins);
		
		JLabel lbl_backgorund = new JLabel("New label");
		lbl_backgorund.setIcon(new ImageIcon(NewReviewMenu.class.getResource("/img/feedback bg.jpg")));
		lbl_backgorund.setBounds(0, 0, 634, 461);
		pane.add(lbl_backgorund);
	}
	
	public void setAppointment(Appointment a) {
		currApp = a;
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btnNewButton) {
			controller.newReview(currApp, txt_feedback.getText(), ratingSldr.getValue());
		}
	}

}
