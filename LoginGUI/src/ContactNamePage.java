

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLDocument.Iterator;

public class ContactNamePage implements ActionListener{
	JFrame frame = new JFrame();
	JLabel userInitials = new JLabel();
	JLabel greeting = new JLabel();
	JLabel nameheaders = new JLabel("Contact Name");
	JLabel emailheaders = new JLabel("Email");
	JLabel phoneheaders = new JLabel("Phone");
	JButton addbutton = new JButton("+");
	JButton contactNameFirst;
	JTextField txt = new JTextField();
	JPanel panel;
	int contactCardWidth = 100;
	int contactCardX = 10;
	int contactCardY = 10;
	int plusbuttonwidth = 50;
	String uiText = "";
	//positioning of the titles and contacts
	int contactNameX;;
	int contactNameY;
	int contactLastNameX;
	int contactEmailX;
	int contactPhoneX;
	
	//format font
	AffineTransform affinetransform = new AffineTransform();     
    FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
    
    //counts the number of contacts so we can plcae them at different y values
	int count = 1;
	File file;
	static Hashtable<String[],String[]> usernameCurr;
	
	
	ContactNamePage(Hashtable<String[],String[]> userName,File files){
		//formatting the contact name page which has the names of all the contacts
		file = files;
		usernameCurr = userName;
		panel = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				//make the circle
				g.setColor(Color.RED);
				g.drawOval(contactCardX/2, contactCardY/2, contactCardWidth, contactCardWidth);
				g.setColor(Color.BLACK);
				
				g.setColor(Color.BLACK);
				g.draw3DRect(0, 7+contactCardWidth, Main.len, 1, getFocusTraversalKeysEnabled());
				
				
			}
			
		};
		
		//make a search bar
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Main.len,Main.len);
		
		
		Font init  = createFont("College", Font.PLAIN, contactCardWidth/2);
		//place the initials text
		String[] ui= findInitials();
		userInitials.setText(ui[0]);
		int initHeight = getTextHeight(init,userInitials.getText());
		int initWidth = getTextWidth(init,userInitials.getText());
		userInitials.setFont(init);
        userInitials.setBounds((contactCardWidth/2) - (initWidth/2) ,contactCardY+initHeight/4,initWidth*2,initHeight);
        panel.add(userInitials);
		
        //+ button
		addbutton.setBounds(Main.len-plusbuttonwidth, contactCardY, plusbuttonwidth, plusbuttonwidth);
		panel.add(addbutton);
		addbutton.addActionListener(this);
		
		//font obj for "contact name" header
		Font fnh = createFont("College", Font.PLAIN, 15);
		int nhWidth = getTextWidth(fnh,nameheaders.getText());
		int nhHeight= getTextWidth(fnh,nameheaders.getText());
		nameheaders.setFont(fnh);
		nameheaders.setBounds((Main.len/2)-(nhWidth/2),105,nhWidth,nhHeight);
		panel.add(nameheaders);
	
		//create text to welcome the user
		Font welcome  = createFont("College", Font.PLAIN, contactCardWidth/2);
		//place the initials text
		String[] greet = findInitials();
		greeting.setText("Welcome " + greet[1]);
		int greetHeight = getTextHeight(welcome,greeting.getText());
		int greetWidth = getTextWidth(welcome,greeting.getText());
		greeting.setFont(welcome);
		greeting.setForeground(Color.RED);
		greeting.setBounds((Main.len/2)-(greetWidth/2),greetHeight/4,greetWidth*2,greetHeight);
        panel.add(greeting);
		
		panel.setLayout(null);
		frame.setVisible(true);
		
	}
	
	//Looks through the file to find the first character in the first and second elem of each line
	//these are the intials of the account holder
	public String[] findInitials() {
		int line = 0;
		String[] arr = new String[2];
		try {
			Scanner read = new Scanner(file);
			while(read.hasNextLine()) {
				line++;
				if(line == 1) {
					String data = read.nextLine();
					String[] splitted = data.split(",");
					String first = splitted[0].substring(0,1);
					String last =  splitted[1].substring(0,1);
					arr[0]=first.toUpperCase() + last.toUpperCase();
					arr[1] = splitted[0]+" "+splitted[1];
					return arr;
				}
				break;
			}
			read.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}
	
	//gets the width of the text
	public int getTextWidth(Font f,String str) {
		int twidth = (int)(f.getStringBounds(str,frc).getWidth());
        return twidth;
	}
	
	//gets the height of the text
	public int getTextHeight(Font f,String str) {
		int theight = (int)(f.getStringBounds(str,frc).getHeight());
		return theight;
	}
	
	//creates the font object so we can use it to design the page
	public Font createFont(String fontname, int plain, int fontsize) {
		Font f = new Font(fontname,plain,fontsize);
        
        return f;
	}
	
	// reads the first 2 items in each line of the file (The contacts first and last name) and adds it to the panel
	public void updateContactList(Hashtable<String[],String[]> usernameCurr) {
		int line = 0;
		 File user = file;
		try {
	      Scanner myReader = new Scanner(user);
	      while (myReader.hasNextLine()) {
	    	String data = myReader.nextLine();
	    	line++;
	    	if(line > 2) {
		        String[] splitdata = data.split(",");
		        Font f = createFont("Arial",Font.PLAIN,15);
		        contactNameFirst = new JButton(splitdata[0]+" " + splitdata[1]);
		        
		        int twidth = (int)(f.getStringBounds(contactNameFirst.getText(),frc).getWidth());
		        int theight = (int)(f.getStringBounds(contactNameFirst.getText(),frc).getHeight());
		        contactNameFirst.setBounds(Main.len/2 - twidth,180+5*count,2*twidth,theight);
	    		contactNameFirst.addActionListener(this);
	    		panel.add(contactNameFirst);
	    		String[] nameLastname = {splitdata[0],splitdata[1]};
	    		String[] emailphone = {splitdata[2],splitdata[3]};
	    		usernameCurr.put(nameLastname, emailphone); 
	   
	 
	    		count += 10;
	    	}
	      }
	      
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		  
		//print the hashmap contents
		 /*for(Map.Entry<String[], String[]> entry:usernameCurr.entrySet()){    
		        String[] key=entry.getKey();  
		        String[] b=entry.getValue();  
		        System.out.println("names:"+""+key[0]+""+key[1]);  
		        System.out.println("email phone"+""+b[0]+""+b[1]);   
		    }   
		    */
	}

	

	
	@Override
	//If the user clicks the add button, it will go to create new contact page
	//otherwise if the user clicks on a contact it'll open to a page containing info about the contact
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addbutton) {
			CreateNewContact cnc = new CreateNewContact(usernameCurr);
		} else if (e.getSource() instanceof JButton) {
			ContactInfoPage cpi = new ContactInfoPage(e.getActionCommand(),usernameCurr);
		}
	}
	
	

}
