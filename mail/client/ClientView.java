package mail.client;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


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
		folderSelector(clientViewer);

		clientViewer.setVisible(true);
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
	/**
	 * This is the <code>JPanel</code> for selecting a folder, complete of appropriate <code>ListSelectionListeners</code>
	 * @param main, the main <code>JFrame</code> that the client is displayed on
	 */
	public static void folderSelector(JFrame main){
		//Create a panel prompting user for folder selection
		JPanel selectPanel = new JPanel();
		selectPanel.setLayout(new BorderLayout());
		main.add(selectPanel, BorderLayout.PAGE_START);	
		//Folder names to go in the selectPanel
		String folNames [] = {"Inbox", "Spam"};
		//Create a listbox contol for selectPanel
		JList<String> lb = new JList<String>(folNames);
		//Only one folder is selected at a time
		lb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectPanel.add(lb);
		//selectPanel.setVisible(true);		
		ListSelectionListener folderListener = new ListSelectionListener(){

			@SuppressWarnings("unchecked")
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				boolean adjusting = lse.getValueIsAdjusting();
				if (!adjusting) {
					JList<String> list = (JList<String>) lse.getSource();
					int selections[] = list.getSelectedIndices();
					//Gets the name of the selection E.G.: Inbox, Spam... no longer needed
					//Object selectionValues[] = list.getSelectedValues();
					for (int i = 0, n = selections.length; i < n; i++) {
						switch(selections[i]){
						case 0: System.out.println("Inbox");
						break;
						case 1: System.out.println("Spam");
						}
						//System.out.println(selections[i] + "/" + selectionValues[i] + " ");
					}

				}
			}
		};
		lb.addListSelectionListener(folderListener);

	}

}
