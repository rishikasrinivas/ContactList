import java.awt.Font;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class NewWindow {
	JFrame frame;
	JPanel panel;
	JLabel label;
	NewWindow(String message) {
		label = new JLabel(message);
		frame = new JFrame();
		panel = new JPanel();
		frame.add(panel);
		label.setBounds(message.length(),message.length(),message.length()*100,100);
		label.setFont(new Font(null,Font.PLAIN,30));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Main.len,Main.len);
		panel.setLayout(null);
		frame.setVisible(true);
		
		
		panel.add(label);
		
		
		
	}
}
