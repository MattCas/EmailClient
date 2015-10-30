import java.util.Properties;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.sun.mail.imap.IMAPFolder;

public class Client {

		Properties props = new Properties();
		Store store = null;	
		IMAPFolder folder = null;
		
	public static void main(String[] args) {
		Client c = new Client();
		c.initializeClient();
			

	}

	public void initializeClient(){
		//Create initial variables
		String username = "matthiascasula@gmail.com";
		String password = "";
		//Set mail properties to Properties obj
		props.setProperty("mail.store.protocol", "imaps");
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
		System.out.println(username + password);
		
		//Establish mail session
		Session ssn = Session.getInstance(props);
		
		
	}
}
