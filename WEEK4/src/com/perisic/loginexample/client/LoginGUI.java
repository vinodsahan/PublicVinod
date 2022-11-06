package com.perisic.loginexample.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.net.MalformedURLException;
import java.rmi.Naming;
//import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;

import com.perisic.loginexample.rmiinterface.LoginInterface;


/**
 * A Simple Graphical User Interface to demonstrate passwords and cookies
 *  
 * @author Marc Conrad
 *
 */
public class LoginGUI extends JFrame implements ActionListener  {

	private static final long serialVersionUID = -1077856539035386635L;

	private LoginInterface myService; 
	public void actionPerformed(ActionEvent e) {
		if( e.getSource().equals(btnHello)) { 
			String result;
			try {
				result = myService.sayHello();	
				System.out.println("Result: "+result); 
			} catch (RemoteException ex) {
				System.out.println("A problem occured: "+ex.toString());
				ex.printStackTrace();
			} 

		} else if(e.getSource().equals(btnLogin)) { 
			String str = JOptionPane.showInputDialog("Please enter the password.");
			try {
				String result = myService.login(str); 
				if( result.equals("wrong")) { 
					System.out.println("Wonrg Password. Try again!"); 
				} else { 
					mySessionCookie = result; 
					System.out.println("Your login was successful.");
				}
			} catch (Exception ex) {
				System.out.println("A problem occured: "+ex.toString());
				ex.printStackTrace();
			} 

		}else if(e.getSource().equals(btnSecret)) { 
			try {
				String result = myService.getSecretMessage(mySessionCookie); 
				System.out.println("Result: "+result); 
			} catch (Exception ex) {
				System.out.println("A problem occured: "+ex.toString());
				ex.printStackTrace();
			} 

		}else if(e.getSource().equals(btnLogout)) { 
			try {
				String result = myService.logout(mySessionCookie); 
				System.out.println("Logout: "+result); 
			} catch (Exception ex) {
				System.out.println("A problem occured: "+ex.toString());
				ex.printStackTrace();
			} 

		}
	}

	JButton btnHello = new JButton("Hello"); 
	JButton btnLogin = new JButton("Login"); 
	JButton btnSecret = new JButton("Secret"); 
	JButton btnLogout = new JButton("Logout"); 

	String mySessionCookie = "not set"; 


	public LoginGUI() {
		super();
		setSize(400, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		JPanel panel = new JPanel(); 

		try {
			myService =  (LoginInterface) Naming.lookup("rmi://localhost/LoginService");
		} catch (Exception e) {
			System.out.println("A problem occured: "+e.toString());
			e.printStackTrace();
		} 
		panel.add(btnHello); 
		btnHello.addActionListener(this);

		panel.add(btnLogin); 
		btnLogin.addActionListener(this);

		panel.add(btnSecret); 
		btnSecret.addActionListener(this);

		panel.add(btnLogout); 
		btnLogout.addActionListener(this);

		getContentPane().add(panel);
		panel.repaint();



	}
	/**
	 * Starts the Client.
	 * @param args not used.
	 */
	public static void main(String [] args ) { 
		// Seeting up the GUI
		LoginGUI myGUI = new LoginGUI(); 
		myGUI.setVisible(true); 
		// Establish remote object


	}

}