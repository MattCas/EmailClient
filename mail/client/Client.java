/**
 *An IMAP/SMPT based Email client for a university assignment
 */
package mail.client;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import com.sun.mail.imap.IMAPFolder;
import mail.client.UsrPass;

public class Client {
	IMAPFolder inbox = null;
	IMAPFolder spam = null;



	/**
	 *Initializes the Client by setting properties, IMAP settings, sets up the session gets folders from the <code>store</code> object
	 *@param combo, the Username/Password combination
	 * @return 
	 */
	public void initializeClient(UsrPass combo){
		//Create initial variables
		Properties props = new Properties();
		Store store = null;	

		//Set mail properties to Properties obj
		props.setProperty("mail.store.protocol", "imaps");
		//Retrieve login credentials 

		//UsrPass combo = setCredentials();
		//Set the acquired username and password as properties 
		props.setProperty("mail.user", combo.getUsername());
		props.setProperty("mail.password", combo.getPassword());
		//Establish mail session
		Session ssn = Session.getInstance(props);
		try {
			//Get store object from Session
			//Store new connects to the imap server (Gmail in this case) 
			store = ssn.getStore("imaps");
			store.connect("imap.googlemail.com", combo.getUsername(), combo.getPassword());
			//Store a folder in the variable (Hardcoded Inbox in this case)
			inbox = (IMAPFolder) store.getFolder("Inbox");
			spam = (IMAPFolder) store.getFolder("[Gmail]/Spam");
			//System.out.println(combo.getUsername() + " " + combo.getPassword());
		}catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /* only need this if getting multipart body catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
	}
	public IMAPFolder getInbox(){
		return inbox;
	}
	public IMAPFolder getSpam(){
		return spam;
	}
	/**
	 * Takes a folder and displays all the objects of the email in it.
	 * @param f, the <code> IMAPFolder </code>, for instance inbox, spam, etc.
	 */
	public static void displayMail(IMAPFolder f){
		
		try{
			if (!f.isOpen()){
				f.open(Folder.READ_WRITE);
			}
			//Display number of messages
			System.out.println("You have " + f.getNewMessageCount() + " new messages and " + f.getUnreadMessageCount() + " unread messages.");
			Message inboxes [] = f.getMessages();
			int counter = 0;
			//Iterate through each message from folder inboxes, calling it inboxMessage within this loop
			for (Message inboxMessage:inboxes){
				counter++;
				//Print subject for each message
				System.out.println("The subject of message number " + counter + " is: " + inboxMessage.getSubject());
				/*
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
				 */	
			}		
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 *Retrieves user selection for which folder to display
	 */
	public void folderSelection(){
		//prompt user for folder selection
		Scanner s = new Scanner(System.in);
		System.out.println("1 for for inbox 2 for spam");
		int folderSelection = s.nextInt(); 
		switch(folderSelection){
		case 1: displayMail(inbox); 
		break;
		case 2: displayMail(spam);
		}
		s.close();
	}
}
