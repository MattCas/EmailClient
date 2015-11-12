package mail.client;


import javax.mail.*;
import javax.mail.internet.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *Sends smtp emails
 * @author Matthias Casula
 * @version 1.1
 */
public class Sender {

	public UsrPass combo;
	public String to;
	public String cc;
	public String body;
	public String subj;
	public File att;

	public Sender(UsrPass combo, String to, String cc,String subj, String body){
		this.combo = combo;
		this.to = to;
		this.cc = cc;
		this.body = body;
		this.subj = subj;
	}
	public Sender(UsrPass combo, String to, String cc,String subj, String body, File att){
		this.combo = combo;
		this.to = to;
		this.cc = cc;
		this.body = body;
		this.subj = subj;
		this.att = att;
	}

	/**
	 * assigns all fields ands sends the email
	 */
		
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
			Multipart multipart = new MimeMultipart();
			//message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subj);
			message.setText(body);
			message.saveChanges();
			
			MimeBodyPart attachPart = new MimeBodyPart();
			
			//String attachFile = "D:/Documents/MyFile.mp4";
			try {
				attachPart.attachFile(att);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			multipart.addBodyPart(attachPart);

			// Step 4: Send the message by javax.mail.Transport .			
			Transport tr = session.getTransport("smtp");	// Get Transport object from session		
			tr.connect(smtphost, username, password); // We need to connect
			tr.sendMessage(message, message.getAllRecipients()); // Send message

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * sets the attachment file to <code>att</code>
	 * @param f, the attachment file
	 */
	public void setAttachment(File f){
		att = f;
	}
}
