import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateNewContact implements ActionListener {
	String firstNameHead = "Enter the contact's first name"; 
	String lastNameHead = "Enter the contact's last name";
	String emailHead = "Enter the contact's email";
	String numHead = "Enter the contact's phone number";
	
	private JFrame frame= new JFrame();
	private JPanel panel = new JPanel();
	private JLabel contactfirstname = new JLabel(firstNameHead);
	private JLabel contactlastname = new JLabel(lastNameHead);
	private JTextField firsttxt = new JTextField();
	private JTextField lasttxt =  new JTextField();
	private JLabel email=new JLabel(emailHead);
	private JLabel phone=new JLabel(numHead);
	private JTextField emailtxt = new JTextField();
	private JTextField phonetxt = new JTextField();
	private JButton add = new JButton();
	
	private boolean addError =false;
	
	
	File file = new File(Login.getUserTxt()+Login.getPassTxt()+".txt");
	int widthTxt = 200;
	
	//counts how many users have created an account
	int userNum = 0;
	static Hashtable<String[],String[]> usernameCurrent;
	
	CreateNewContact(Hashtable<String[],String[]> username) {
		usernameCurrent = username;
		frame.setSize(Main.len,Main.len);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//sets the coordinates for the titles
		contactfirstname.setBounds(10,20,10*firstNameHead.length(),25);
		panel.add(contactfirstname);
		
		
		contactlastname.setBounds(10,70,lastNameHead.length() * 10,25);
		panel.add(contactlastname);
		
		email.setBounds(10,120,10*emailHead.length(),25);
		panel.add(email);
		
		phone.setBounds(10,170,10*numHead.length(),25);
		panel.add(phone);
		
		//sets the coordinates for the text boxes
		firsttxt.setBounds(7*firstNameHead.length(),20,100,25);
		panel.add(firsttxt);
		

		lasttxt.setBounds(7*lastNameHead.length(),70,100,25);
		panel.add(lasttxt);
		

		emailtxt.setBounds(7*emailHead.length(),120,100,25);
		panel.add(emailtxt);
		

		phonetxt.setBounds(7*numHead.length(),170,100,25);
		panel.add(phonetxt);
		
		//add the button so the user can add the contact
		//the the contact list
		add = new JButton("Add Contact to my Contact List");
		add.setBounds(10,220,270,25);
		add.addActionListener(this);
		panel.add(add);
		
		
		panel.setLayout(null);
		frame.setVisible(true);
	}

	public boolean nameExist(File file, String firstname, String lastname) {
		//checks to see if the name exists in the file
		//if it does, it returns True otherwise false
		file = new File(Login.getUserTxt()+Login.getPassTxt()+".txt");
		try {
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
		    	String data = myReader.nextLine();
		        String[] fullname = data.split(",");
		        if (firstname.equals(fullname[0]) && lastname.equals(fullname[1])) {
		        	return true;
		        }
		    }
		      myReader.close();
		} catch (FileNotFoundException e) {
			return false;
		}
		return false;
	}
	
	public ArrayList<String> writeToArrayLisr(String name,String lastName,String email,String phone) {
		ArrayList<String> l = new ArrayList<String>();
		l.add(name +", "+ lastName + ", " + email + ", " + phone);
		return sort(l);
		
	}
	
	public ArrayList<String> sort(ArrayList<String> s) {
		int counter = 0;
		for (int currName = 0; currName < s.size(); currName++) {
			for (int nextName = currName+1; nextName < s.size(); nextName++) {
				if (s.get(currName).length() < s.get(nextName).length()) {
					counter = s.get(currName).length();
				} else {
					counter = s.get(nextName).length();
				}
				
				int i = 0;
				while(i < counter) {
					if ((int)(s.get(currName).charAt(i)) > (int)(s.get(nextName).charAt(i))) {
						String temp = s.get(currName);
						s.set(currName, s.get(nextName));
						s.set(nextName, temp);
						i=0;
					}else {
						i++;
					}
					break;
				}
			}
		}
		return s;
		/*for (int i = 0; i < s.size(); i++) {
			System.out.println(s.get(i)+ " ");
		}*/
	}
	public void writeToFile(File file, String name,String lastName,String email,String phone){
		//writes the contacts name,lastname, email, and number
		//to the file
		try {
			 FileWriter fw = new FileWriter(file, true);
			 BufferedWriter bw = new BufferedWriter(fw);
			    bw.write(name + "," + lastName + "," + email + "," + phone);
			    bw.newLine();
			    bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String name = firsttxt.getText();
		String lastName = lasttxt.getText();
		String email = emailtxt.getText();
		String phone = phonetxt.getText();
		
		//checks if the info is written properly
		//if it is then it checks if the name already exists,
		//if it does, a message is printed
		//otherwise the info is checked to make sure it's written porperly
		//if it is name is written to the file 
		//and the user is taken back to the main
		//contact page
		//if the info isn't written properly, a message
		//is printed
		if (name.length() > 0 && lastName.length() > 0 && email.length() > 0 && phone.length()>9) {
			if (nameExist(file,name,lastName)) {
				frame.add(addMes("Please fill in all the fields properly"));
				System.out.println("This contact already exists");
			} else if (email.contains(".com") && email.contains("@")) {
				writeToFile(file, name,lastName,email,phone);
				ContactNamePage cp1 = new ContactNamePage(usernameCurrent,file);
				cp1.updateContactList(usernameCurrent);
			} else {
				frame.add(addMes("This contact already exists"));
			}
		} else {
			frame.add(addMes("This contact already exists"));

		}
		
	}
	
	//Adds error messages as needed
	public JPanel addMes(String mes) {
		JLabel error = new JLabel(mes);
		error.setBounds(10,220,10*firstNameHead.length(),25);
		addPanels(error);
		return panel;
	}
	
	public void addPanels(JLabel labels) {
		frame.add(labels);
		panel.setLayout(null);
		frame.setVisible(true);
		panel.revalidate();
	}
}
