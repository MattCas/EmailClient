/**
 *An IMAP/SMPT based Email client for a university assignment
 * @author Matthias Casula
 * @version 1.1
 */
package mail.client;
import java.util.Properties;
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

		//Set mail properties to Properties object
		props.setProperty("mail.store.protocol", "imaps");
		//Set the acquired username and password as properties 
		props.setProperty("mail.user", combo.getUsername());
		props.setProperty("mail.password", combo.getPassword());
		//Establish mail session
		Session ssn = Session.getInstance(props);
		try {
			//Get store object from Session
			store = ssn.getStore("imaps");
			//Store new connects to the imap server (Gmail in this case) 
			store.connect("imap.googlemail.com", combo.getUsername(), combo.getPassword());
			//Store a folder in the variable (Hardcoded Inbox in this case)
			inbox = (IMAPFolder) store.getFolder("Inbox");
			spam = (IMAPFolder) store.getFolder("[Gmail]/Spam");
		}catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public IMAPFolder getInbox(){
		return inbox;
	}
	public IMAPFolder getSpam(){
		return spam;
	}
}
