import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login implements ActionListener {
	private JFrame frame;
	private JPanel panel;
	private JLabel userlabel;
	private JLabel loginlabel;
	private static JTextField userTxt;
	private JLabel passwordLbl;
	private static JPasswordField passTxt;
	private JButton loginbutton;
	private JButton createaccountbutton;
	private JLabel success;
	private JLabel emptyField = new JLabel();
	private JLabel wrongInfo = new JLabel();
	private JLabel error = new JLabel();
	private int errormsgY = 100;
	//data to keep track of accounts
	//public static Hashtable<String,String> usernamepass = new Hashtable<String,String>();
	private int len = 500;
	static Hashtable<String[], String[]> holdcurcontact;
	
	
	
	Login(String loginString, int len, int width, Hashtable<String[], String[]> hashtableHoldingcontact){
		// creates the layout for the login page
		if(CreateAccount.userNum == 1) {
			holdcurcontact = hashtableHoldingcontact;
		}else {
			holdcurcontact = new Hashtable<String[], String[]>();
		}
		//frame is the window
		frame = new JFrame();
		// panel is the layout but first you
		//have to put the panel on the frame

		panel = new JPanel(); 
		
		loginlabel = new JLabel(loginString);
		loginlabel.setBounds(Main.len/(14/3),0,loginString.length()*100, loginString.length()*2);
		panel.add(loginlabel);
		//configure the frame
		frame.setSize(len,width);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(panel); // to put the panel on the frame
		
		//configure the panel
		panel.setLayout(null);
		
		userlabel = new JLabel("User"); 
		userlabel.setBounds(Main.len/35,Main.len/35,80,25);
		panel.add(userlabel);
		
		userTxt = new JTextField();
		userTxt.setBounds(Main.len/7,Main.len/35,165,25);
		panel.add(userTxt);
		
		passwordLbl = new JLabel("Password"); 
		passwordLbl.setBounds(Main.len/70,Main.len/14,80,25);
		panel.add(passwordLbl);
		
		passTxt = new JPasswordField();
		passTxt.setBounds(Main.len/7,Main.len/14,165,25);
		panel.add(passTxt);
		
		
		loginbutton = new JButton("Login");
		loginbutton.setBounds(Main.len/70,4*Main.len/35,80,25);
		loginbutton.addActionListener(this);
		panel.add(loginbutton);
		
		createaccountbutton = new JButton("Create an account");
		createaccountbutton.setBounds(Main.len/(70/9),4*Main.len/35,160,25);
		createaccountbutton.addActionListener(this);
		panel.add(createaccountbutton);
		
		/*success = new JLabel("");
		success.setBounds(Main.len/70,11*Main.len/70,300,25);
		panel.add(success);*/
		
		frame.setVisible(true);
	}

	public int usernamePassMatch(File user) {
		//check if the file already exists 
		if (user.exists()) {
			return 1;
		}
		return 2;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String user = userTxt.getText();
		String password = passTxt.getText();
		String mes= " ";
		
		//if the login button is clicked 
		if(e.getSource() == loginbutton  && (user.length() > 0 && password.length() > 0)) {
			//creates a file for the user
			File usernamepassfile = new File(user+password+".txt");
			if (usernamePassMatch(usernamepassfile) == 1) {
				frame.dispose();
				//passes in a hashtable corresponding to the current user and the file
				ContactNamePage cp = new ContactNamePage(holdcurcontact,usernamepassfile);	
				cp.updateContactList(holdcurcontact);
			} else {
				frame.add(missing("The account you're trying to enter doesn't exist. Please re-enter or create an account"));
			} 
		} else if (e.getSource() == loginbutton && (user.length() == 0 || password.length() == 0)) {
			if (user.length() == 0 && password.length() > 0) {
				mes = "Enter a username";
			} else if (password.length() == 0 && user.length() > 0) {
				mes = "Enter a password";
				
			} else {
				mes = "Enter a username and password";
				
			}
			frame.add(missing(mes));
		//if the create account was clicked and if so, it takes you to create an account page
		} else if (e.getSource() == createaccountbutton) {
			frame.dispose();
			String username = user;
			String pass = password;
			CreateAccount create = new CreateAccount();
		}

	}
	
	public JPanel missing(String message) {
		//prints an error msg
		error.setText(message);
		error.setBounds(10,11*Main.len/70,100*userlabel.getText().length(),15);
		panel.add(error);
		panel.setLayout(null);
		frame.setVisible(true);
		frame.repaint();
		return panel;
	}
	
	
	//gets the username and password so we can use it to create our file
	public static String getUserTxt() {
		return userTxt.getText();
	}


	public static String getPassTxt() {
		return passTxt.getText();
	}

	
}
