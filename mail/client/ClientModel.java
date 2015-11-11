package mail.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.mail.imap.IMAPFolder;


/**
 *Models the client later displayed by <code>ClientWindow</code> 
 */

public class ClientModel {

	private static Client c;
	public ClientModel(Client c){
		this.c = c;
	}
	/**
	public static void main(String[] args) {
		Client c = new Client ();
		c.initializeClient(getCredentials());
		JFrame clientViewer = new JFrame();
		clientViewer.setSize(800,600);
		clientViewer.setResizable(false);
		clientViewer.setTitle("TortugaMail");
		clientViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientViewer.setLayout(new GridBagLayout());
		folderSelector(c, clientViewer);

		clientViewer.setVisible(true);
	}
	*/
	
	/**
	 * Prompt the user for username and password take them in
	 * @return combo, the username/password combination
	 */
	public static UsrPass getCredentials(){
		String username = "" ;
		String password = "";
		//Retrieve username from User using JTextField
		JTextField u = new JTextField(40);
		u.setText("matthiascasula@gmail.com");
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
		p.setText("mattias92");
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
		JButton button;

		//Create a panel prompting user for folder selection
		JPanel selectPanel = new JPanel();
		main.add(selectPanel);	

		/**
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
						case 0: folderToList(c.getInbox());
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
		 */
	}

	public static JList<String> folderToList(IMAPFolder f){
		/**if (main.getComponentCount() > 2){
		main.remove(main.getComponent(1));
		} */
		DefaultListModel<String> objects = new DefaultListModel<String>();
		try{
			if (!f.isOpen()){
				f.open(Folder.READ_WRITE);
			}
			Message thisFolder [] = f.getMessages();	
			//Iterate through each message from folder inboxes, calling it inboxMessage within this loop
			for (Message currentMessage:thisFolder){
				//Add all message objects to the objects ListModel
				if (currentMessage.isSet(Flags.Flag.SEEN)){
					objects.addElement("READ  " + currentMessage.getSubject());					
				}
				else{
					objects.addElement("UNREAD  " + currentMessage.getSubject());
				}
				
			}		
		} catch (Exception e){
			e.printStackTrace();
		}

		JList<String> emailObjs = new JList<String> (objects);
		emailObjs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return emailObjs;
		
		
		/**
		//add listener	
		ListSelectionListener objectListener = new ListSelectionListener(){

			@SuppressWarnings("unchecked")
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				boolean adjusting = lse.getValueIsAdjusting();
				if (!adjusting) {
					JList<String> list = (JList<String>) lse.getSource();
					int selections[] = list.getSelectedIndices();

					//Display the selected folder
					for (int i = 0, n = selections.length; i < n; i++) {
						while (!(i < 0)){
							try {
								System.out.println(i);
								displayMessage(f.getMessage(i));
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							//case 1: folderToPanel(c.getSpam(), main);
						}
						//System.out.println(selections[i] + "/" + selectionValues[i] + " ");
						//main.remove(lay.getLayoutComponent(BorderLayout.LINE_START));
					}

				}
			}
		};
		//Close the folder after getting all the messages
		/** try {
			f.close(true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//emailObjs.addListSelectionListener(objectListener);
		

	}
	public static String displayInboxMessage(int i) throws MessagingException{
		IMAPFolder f = c.getInbox();
		String s = "";
		try{
			if (!f.isOpen()){
				f.open(Folder.READ_WRITE);
			}
			
			Message inboxes [] = f.getMessages();
			Message inboxMessage = inboxes [i];
			inboxes [i].setFlag(Flags.Flag.SEEN, true);
			
		//Check the content type of the email, if PLAIN/TEXT then we display it normally, if Multipart then deal with it
		if (inboxMessage.getContentType().contains("TEXT/PLAIN")){
			s= ((String) inboxMessage.getContent());
		}
		else{
			s= "Cannot display this eMail as it's content is not plain text" ;
			/**
			//Decompose and print multipart message body (MIME)
			Multipart body = (Multipart)inboxMessage.getContent();
			System.out.println("==================" + body.getCount() + "==================");
			for(int i = 0; i < body.getCount(); i++){
				BodyPart bodyPart = body.getBodyPart(i);
				System.out.println(bodyPart.getContentType());
				System.out.println(bodyPart.getContent().toString());
			}
		}
			 */	
		}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return s;
	}

}
