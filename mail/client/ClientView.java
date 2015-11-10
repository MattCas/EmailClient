package mail.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.mail.Folder;
import javax.mail.Message;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.mail.imap.IMAPFolder;


/**
 *Displays the client on a <code>JFrame</code> 
 */

public class ClientView {

	private static final Graphics Graphics = null;

	public static void main(String[] args) {
		Client c = new Client ();
		c.initializeClient(getCredentials());
		JFrame clientViewer = new JFrame();
		clientViewer.setSize(800,600);
		clientViewer.setResizable(false);
		clientViewer.setTitle("TortugaMail");
		clientViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientViewer.setLayout(new BorderLayout());
		folderSelector(c, clientViewer);
		//folderToPanel(c.getInbox(), clientViewer);
		
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
	public static void folderSelector(Client c, JFrame main){
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
		//add listener	
		ListSelectionListener folderListener = new ListSelectionListener(){

			@SuppressWarnings("unchecked")
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				boolean adjusting = lse.getValueIsAdjusting();
				if (!adjusting) {
					JList<String> list = (JList<String>) lse.getSource();
					int selections[] = list.getSelectedIndices();
					//BorderLayout lay = (BorderLayout) main.getLayout();
					//Display the selected folder
					for (int i = 0, n = selections.length; i < n; i++) {
						switch(selections[i]){
						case 0: folderToPanel(c.getInbox(), main);
						break;
						case 1: folderToPanel(c.getSpam(), main);
						}
						//System.out.println(selections[i] + "/" + selectionValues[i] + " ");
						//main.remove(lay.getLayoutComponent(BorderLayout.LINE_START));
					}

				}
			}
		};
		lb.addListSelectionListener(folderListener);

	}

	public static void folderToPanel(IMAPFolder f, JFrame main){
		/**if (main.getComponentCount() > 1){
		main.remove(main.getComponent(1));
		} */
		Dimension dim = new Dimension(300, 200);
		JPanel folPanel = new JPanel();
		//folPanel.setSize(300, 200);
		folPanel.setMaximumSize(dim);
		//folPanel.setSize((int)(main.getWidth()/3),(int) (main.getHeight()/3));
		folPanel.setLayout(new BoxLayout(folPanel, 0));
		String subjects [] = new String[5000];
		try{
			if (!f.isOpen()){
				f.open(Folder.READ_WRITE);
			}
			Message thisFolder [] = f.getMessages();
			int counter = 0;	
			//Iterate through each message from folder inboxes, calling it inboxMessage within this loop
			for (Message currentMessage:thisFolder){
				//Add all message objects to the subjects array
				subjects[counter] = (currentMessage.getSubject()); 
				counter++;
			}		
		} catch (Exception e){
			e.printStackTrace();
		}
		//String objects [] = new String [subjects.length];
		//objects = subjects;
		JList<String> emailObjs = new JList<String> (subjects);
		emailObjs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		folPanel.add(emailObjs);
		//JScrollPane scroll = new JScrollPane(folPanel);
		main.add(folPanel, BorderLayout.LINE_START);	
		//Border border;
		folPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		System.out.println(main.getComponentCount());
		System.out.println(folPanel.getComponentCount());
	
	}

}
