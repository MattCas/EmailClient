import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.sun.mail.imap.IMAPFolder;

public class Client {

	public static void main(String[] args) {
		Client c = new Client();
		c.initializeClient();


	}

	public void initializeClient(){
		//Create initial variables
		String username = "matthiascasula@gmail.com";
		String password = "";
		Properties props = new Properties();
		Store store = null;	
		IMAPFolder inbox = null;
		IMAPFolder spam = null;
		
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
		//Set the acquired username and password as properties 
		props.setProperty("mail.user", username);
		props.setProperty("mail.password", password);
		//Establish mail session
		Session ssn = Session.getInstance(props);
		try {
			//Get store object from Session
			//Store new connects to the imap server (Gmail in this case) 
			store = ssn.getStore("imaps");
			store.connect("imap.googlemail.com", username, password);
			//Store a folder in the variable (Hardcoded Inbox in this case)
			inbox = (IMAPFolder) store.getFolder("Inbox");
			spam = (IMAPFolder) store.getFolder("[Gmail]/Spam");
			//
			if (!inbox.isOpen()){
				inbox.open(Folder.READ_WRITE);
			}
			//Display number of messages
			System.out.println("You have " + inbox.getNewMessageCount() + " new messages and " + inbox.getUnreadMessageCount() + "unread messages.");
			Message inboxes [] = inbox.getMessages();
			int counter = 0;
			//Iterate through each message from folder inboxes, calling it inboxMessage within this loop
			for (Message inboxMessage:inboxes){
				counter++;
				//Print subject for each message
				System.out.println("The subject of message number " + counter + " is: " + inboxMessage.getSubject());
				//Check the content type of the email, if PLAIN/TEXT then we display it normally, if Multipart then deal with it
				if (inboxMessage.getContentType().contains("TEXT/PLAIN")){
					System.out.println(inboxMessage.getContent());
				}
				else{
					//Decompose and print multipart message body (MIME)
					Multipart body = (Multipart)inboxMessage.getContent();
					System.out.println("==================" + body.getCount() + "==================");
					for(int i = 0; i < body.getCount(); i++){
						BodyPart bodyPart = body.getBodyPart(i);
						System.out.println(bodyPart.getContentType());
						System.out.println(bodyPart.getContent().toString());
					}
					
				}
			}		
		
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
