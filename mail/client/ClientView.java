package mail.client;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


/**
 *Displays the client on a <code>JFrame</code> 
 */

public class ClientView {

	public static void main(String[] args) {
		Client c = new Client ();
		c.initializeClient(getCredentials());
		JFrame clientViewer = new JFrame();
		clientViewer.setSize(500,500);
		clientViewer.setTitle("TortugaMail");
		clientViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientViewer.setLayout(new BorderLayout());
		//Create a panel prompting user for folder selection
		JPanel selectPanel = new JPanel();
		selectPanel.setLayout(new BorderLayout());
		//Folder names to go in the selectPanel
		String folNames [] = {"Inbox", "Spam"};
		//Create a listbox contol for selectPanel
		JList<String> lb = new JList<String>(folNames);
		//Only one folder is selected at a time
		lb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectPanel.add(lb);
		//selectPanel.setVisible(true);
		clientViewer.add(selectPanel, BorderLayout.PAGE_START);	
		clientViewer.setVisible(true);
		//Open the right folder on selected option ..... works but too many inputs
		while(true){
			//System.out.println(lb.getSelectedIndex());
			if(lb.getSelectedIndex() == 1){
				System.out.println("Panel with spam");
			}
			else{
				System.out.println("Panel with inbox");
			}

		}

	}
	/**
	 * Prompt the user for username and password take them in
	 * @return combo, the username/password combination
	 */
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
