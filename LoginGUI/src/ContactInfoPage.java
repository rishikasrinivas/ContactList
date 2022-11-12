
import java.util.Hashtable;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ContactInfoPage {
	JLabel contactinfo;
	JLabel email;
	JLabel phone;
	JPanel panel;
	// displays the information for the desired contact
	ContactInfoPage(String nameOfContact, Hashtable<String[], String[]> usernameCurr){
		
		JFrame frame = new JFrame();
		panel = new JPanel();
		contactinfo = new JLabel();
		email = new JLabel();
		phone = new JLabel();
		frame.add(panel);
		frame.setSize(Main.len/2,Main.len/2);
		panel.setLayout(null);
		frame.setVisible(true);
		
		addContactInfo(nameOfContact,usernameCurr);
	}
	
	//goes through the hashtable containing the contact info and if the element we're on matches the target contact 
	//the info corresponsing to that contact is extracted from the hashtable and added to the panel
	public void addContactInfo(String nameOfContact, Hashtable<String[], String[]> usernameCurr) {
		Set<String[]> setOfKeys = usernameCurr.keySet();
		 
        // Iterating through the Hashtable
        // object using for-Each loop
        for (String[] key : setOfKeys) {
        	String[] contact = nameOfContact.split(" ");
            if (key[0].equals(contact[0]) && key[1].equals(contact[1])) {
            	contactinfo.setText("Contact information for: " + nameOfContact);
            	contactinfo.setBounds(50, 25, 300, 100);
            	email.setText("Email: " + usernameCurr.get(key)[0]);
            	email.setBounds(50,75,300,100);
            	phone.setText("Phone number: " + usernameCurr.get(key)[1]);
            	phone.setBounds(50,100,300,100);
            	panel.add(contactinfo);
            	panel.add(email);
            	panel.add(phone);
            }
        }
		
	}
}
