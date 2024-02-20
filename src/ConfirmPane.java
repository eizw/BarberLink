import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ConfirmPane extends JFrame implements ActionListener {
	private final int WIDTH = 500;
    private final int HEIGHT = 350;
    private final Color BACKGROUND_COLOR = new Color(20, 20, 20);
    private final Color MENU_COLOR = new Color(250, 250, 250);
    
    private JLabel msgLbl;
    private JButton cancelBtn, confirmBtn;
	
	private Controller controller;
	
	public ConfirmPane() {
		
		Container pane = getContentPane();
        pane.setBackground(BACKGROUND_COLOR);
        pane.setLayout(new BorderLayout(10, 10));
        ((JComponent)pane).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
    	setSize(WIDTH, HEIGHT);
        setTitle("Barber Link - Confirm");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        
        msgLbl = new JLabel("", JLabel.CENTER);
        cancelBtn = new JButton("CANCEL");
        confirmBtn = new JButton("CONFIRM");
        
        JPanel btnPanel = new JPanel(new GridLayout(1, 3));
        
        btnPanel.add(cancelBtn);
        btnPanel.add(Box.createRigidArea(new Dimension(0, 0)));
        btnPanel.add(confirmBtn);
	}
	
	public void setMsg(String m) {
		msgLbl.setText(m);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == cancelBtn) {
			setVisible(false);
		}
		if (obj == confirmBtn) {
			setVisible(false);
			
		}
	}
}
