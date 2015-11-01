package mail.client;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


/**
Displays the client on a panel 
 */

public class ClientView {

	public static void main(String[] args) {
		Client c = new Client ();
		c.initializeClient(getCredentials());
		JFrame clientViewer = new JFrame();
		clientViewer.setSize(500,500);
		clientViewer.setTitle("SnailMail");
		clientViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

	}
	public static UsrPass getCredentials(){
		String username = "" ;
		String password = "";
		//Retrieve username from User using JTextField
		JTextField u = new JTextField(40);
		int uAction = JOptionPane.showConfirmDialog(null, u, "Enter your Username", JOptionPane.OK_CANCEL_OPTION);
		if (uAction > 0){
			//Error if user cancelled (YES!)
			JOptionPane.showMessageDialog(null, "User cancelled the operation 'Enter Username'", "Quitting", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		else{
			username = new String (u.getText());
		}
		//Retrieve password from user using JPasswordField 
		JPasswordField p = new JPasswordField(15);
		int pAction = JOptionPane.showConfirmDialog(null, p, "Enter your Password", JOptionPane.OK_CANCEL_OPTION);
		if (pAction > 0){
			//Error if user cancelled
			JOptionPane.showMessageDialog(null, "User cancelled the operation 'Enter Password'", "Quitting", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		else{
			password = new String (p.getPassword());
		}
		UsrPass combo = new UsrPass(username, password);
		return combo;
	}
	

}
