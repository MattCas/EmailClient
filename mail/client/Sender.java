package mail.client;


import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class Sender {

	public UsrPass combo;
	public String to;
	public String cc;
	public String body;
	public String subj;
	
	//public static Client c;

	public Sender(UsrPass combo, String to, String cc,String subj, String body){
		this.combo = combo;
		this.to = to;
		this.cc = cc;
		this.body = body;
		this.subj = subj;
	}


	public void send() {

		String username = combo.getUsername();
		String password = combo.getPassword();
		String smtphost = "smtp.gmail.com";

		// Step 1: Set all Properties
		// Get system properties
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtphost);
		props.put("mail.smtp.port", "587");

		
		// Set Property with username and password for authentication  
		props.setProperty("mail.user", username);
		props.setProperty("mail.password", password);

		//Step 2: Establish a mail session (java.mail.Session)
		Session session = Session.getDefaultInstance(props);

		try {

			// Step 3: Create a message
			MimeMessage message = new MimeMessage(session);
			//message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subj);
			message.setText(body);
			message.saveChanges();

			// Step 4: Send the message by javax.mail.Transport .			
			Transport tr = session.getTransport("smtp");	// Get Transport object from session		
			tr.connect(smtphost, username, password); // We need to connect
			tr.sendMessage(message, message.getAllRecipients()); // Send message

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
