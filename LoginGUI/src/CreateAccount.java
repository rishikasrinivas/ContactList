import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccount implements ActionListener{
	
	JFrame frame = new JFrame();
	JLabel firstnamelabel = new JLabel("First Name");
	JLabel lastnamelabel = new JLabel("Last Name");
	JLabel usernamelabel = new JLabel("Username");
	JLabel passwordlabel = new JLabel("Password");
	JButton createaccountbutton = new JButton("Create My Account");
	JPanel panel = new JPanel();
	JTextField firstname = new JTextField();
	JTextField lastname = new JTextField();
	JTextField usernameTxt = new JTextField();
	JPasswordField passwordTxt = new JPasswordField();
	JLabel error = new JLabel();
	static Hashtable<String[],String[]> hashtableHoldingcontact = new Hashtable<String[],String[]>();
	static int userNum = 0;
	CreateAccount() {
		//create the frame and add the panel onto the frame
		frame.setSize(Main.len,Main.len);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create the first name text without the textbook so the user knows that in the textbook that
		//follows, they enter their name
		firstnamelabel.setBounds(10,20,80,25);
		panel.add(firstnamelabel);
		
		//create the last name text without the textbook so the user knows that in the textbook that
		//follows, they enter their last name
		lastnamelabel.setBounds(10,70,80,25);
		panel.add(lastnamelabel);
		
		//create the username text without the textbook so the user knows that in the textbook that
		//follows, they enter their username
		usernamelabel.setBounds(10,120,80,25);
		panel.add(usernamelabel);
		
		//create the password text without the textbook so the user knows that in the textbook that
		//follows, they enter their password
		passwordlabel.setBounds(10,170,80,25);
		panel.add(passwordlabel);
		
		
		panel.setLayout(null);
		
		//add the text box for the user to enter their name
		firstname.setBounds(90,20,80,25);
		panel.add(firstname);
		
		//add the text box for the user to enter their last  name
		lastname.setBounds(90,70,80,25);
		panel.add(lastname);
		
		//add the text box for the user to enter their user name
		usernameTxt.setBounds(90,120,80,25);
		panel.add(usernameTxt);
		
		//add the text box for the user to enter their password
		passwordTxt.setBounds(90,170,80,25);
		panel.add(passwordTxt);
		
		//add the create account button
		createaccountbutton.setBounds(10,220,160,25);
		createaccountbutton.addActionListener(this);
		panel.add(createaccountbutton);
		
		frame.setVisible(true);
	}
	

		
	//add all the user name passwords to a file	
	public void writeToFile(File filename) {
		try (FileWriter f = new FileWriter(filename,true);
				BufferedWriter b = new BufferedWriter(f);
				PrintWriter p = new PrintWriter(b);) {
			p.println(firstname.getText() + "," + lastname.getText()); 
			p.println(usernameTxt.getText() + "," + passwordTxt.getText()); 
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//if the user presses createaccountbutton
		//the usernmae password is written to a file
		//and the user is taken back to the login 
		//page to login again
		String username = usernameTxt.getText();
		String password = passwordTxt.getText();
		String first = firstname.getText();
		String last = lastname.getText();
		String msg = "";
		if (e.getSource() == createaccountbutton) {
			if (username.length() > 5 && password.length() >= 6 && first.length() > 0 && last.length() > 0) {
				userNum += 1;
				File f = new File(username+password+".txt");
				writeToFile(f);
				hashtableHoldingcontact = new Hashtable<String[],String[]>(); 
				
				Login l = new Login("Re-login",Main.len,Main.len,hashtableHoldingcontact);
				userNum = 0;
			} else {
				if (password.length() < 6) {
					msg = "Enter a longer password";
				} else if (username.length() <= 5) {
					msg = "Enter a longer username";
				} else if (first.length() == 0) {
					msg = "Enter your first name";
				} else {
					msg = "Enter your last name";
				}
				frame.add(warning(msg));
			}
		} 
	}
	
	public JPanel warning(String msg) {
		error.setText(msg);
		error.setBounds(10,220,10*firstname.getText().length(),25);
		frame.add(error);
		panel.setLayout(null);
		frame.setVisible(true);
		panel.revalidate();
		return panel;
	}
	//creates a hashtable fr each account



	public String getFirstname() {
		return firstname.getText();
	}

	public String getLastname() {
		return lastname.getText();
	}



	

	
}
