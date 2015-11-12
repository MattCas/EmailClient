package mail.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;

/**
 * This class creates a <code>JFrame</code> on the <code>ClientModel</code> will be displayed.
 * @author Matthias Casula
 * @version 1.1
 */
@SuppressWarnings("serial")
public class ClientWindow extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup folSelectButtonGroup = new ButtonGroup();
	public static Client c = new Client();
	public static ClientModel m = new ClientModel(c);
	public JScrollPane scrollInbox = new JScrollPane();
	public JScrollPane scrollSpam = new JScrollPane();
	public JTextArea emailBody = new JTextArea();

	/**
	 * Launch the application.
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		c.initializeClient(m.getCredentials());
		ClientWindow frame = new ClientWindow();
		frame.setVisible(true);
	}
	/**
	 * Create the frame.
	 */
	public ClientWindow() {
		//Implememnt the list selection interface for inbox
		ListSelectionListener inboxSelectionListener = new ListSelectionListener() {
			@SuppressWarnings({ "static-access" })
			public void valueChanged(ListSelectionEvent lse) {
				boolean adjust = lse.getValueIsAdjusting();
				if (!adjust) {
					JList list = (JList) lse.getSource();
					int selections[] = list.getSelectedIndices();
					for (int i = 0, n = selections.length; i < n; i++) {
						//emailBodyView.setText(m.displayInboxMessage(selections[i]);
						emailBody.setBounds(301, 33, 293, 159);
						 try {
							 emailBody.setText(m.displayMessage(selections[i], c.getInbox()));
						} catch (javax.mail.MessagingException e) {
							e.printStackTrace();
						} 
						emailBody.setEditable(false);
						contentPane.add(emailBody);
						contentPane.revalidate();
						contentPane.repaint();
					}
				}
			}
		};
		
		//Implememnt the list selection interface
		ListSelectionListener spamSelectionListener = new ListSelectionListener() {
			@SuppressWarnings({ "static-access" })
			public void valueChanged(ListSelectionEvent lse) {
				boolean adjust = lse.getValueIsAdjusting();
				if (!adjust) {
					JList list = (JList) lse.getSource();
					int selections[] = list.getSelectedIndices();
					for (int i = 0, n = selections.length; i < n; i++) {
						emailBody.setBounds(301, 33, 293, 159);
						 try {
							 emailBody.setText(m.displayMessage(selections[i], c.getSpam()));
						} catch (javax.mail.MessagingException e) {
							e.printStackTrace();
						} 
						emailBody.setEditable(false);
						contentPane.add(emailBody);
						contentPane.revalidate();
						contentPane.repaint();
					}
				}
			}
		};

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JRadioButton rdbtnInbox = new JRadioButton("Inbox");
		rdbtnInbox.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				JList<String> listIn = new JList<String>();
				listIn = m.folderToList(c.getInbox());
				//Stick all subjects in a scrollPane
				scrollInbox = new JScrollPane(listIn);
				scrollInbox.setBounds(6, 33, 292, 159);
				listIn.addListSelectionListener(inboxSelectionListener);
				scrollSpam.setVisible(false);;
				contentPane.add(scrollInbox);
				contentPane.revalidate();
				contentPane.repaint();

				scrollInbox.setVisible(true);
			}
		});
		folSelectButtonGroup.add(rdbtnInbox);
		rdbtnInbox.setBounds(219, 6, 79, 23);
		contentPane.add(rdbtnInbox);

		JRadioButton rdbtnSpam = new JRadioButton("Spam");
		rdbtnSpam.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				JList<String> listSp = new JList<String>();
				listSp = m.folderToList(c.getSpam());
				//Stick all subjects in a scrollPane
				scrollSpam = new JScrollPane(listSp);
				scrollSpam.setBounds(6, 33, 292, 159);
				listSp.addListSelectionListener(spamSelectionListener);
				scrollInbox.setVisible(false);
				contentPane.add(scrollSpam);
				contentPane.revalidate();
				contentPane.repaint();
				scrollSpam.setVisible(true);
			}
		});
		folSelectButtonGroup.add(rdbtnSpam);
		rdbtnSpam.setBounds(301, 6, 71, 23);
		contentPane.add(rdbtnSpam);




		JEditorPane toEdit = new JEditorPane();
		toEdit.setBounds(99, 204, 402, 16);
		contentPane.add(toEdit);

		JLabel lblTo = DefaultComponentFactory.getInstance().createLabel("To:");
		lblTo.setBounds(43, 204, 29, 23);
		contentPane.add(lblTo);

		JLabel lblCc = DefaultComponentFactory.getInstance().createLabel("Cc:");
		lblCc.setBounds(43, 229, 29, 16);
		contentPane.add(lblCc);

		JEditorPane ccEdit = new JEditorPane();
		ccEdit.setBounds(99, 229, 402, 16);
		contentPane.add(ccEdit);

		JLabel lblSubject = DefaultComponentFactory.getInstance().createLabel("Subject:");
		lblSubject.setBounds(21, 253, 50, 16);
		contentPane.add(lblSubject);

		JEditorPane subjectEdit = new JEditorPane();
		subjectEdit.setBounds(99, 253, 402, 16);
		contentPane.add(subjectEdit);

		JLabel lblBody = DefaultComponentFactory.getInstance().createLabel("Body:");
		lblBody.setBounds(31, 273, 41, 16);
		contentPane.add(lblBody);

		JEditorPane bodyEdit = new JEditorPane();
		bodyEdit.setBounds(99, 278, 401, 173);
		contentPane.add(bodyEdit);

		JButton btnSendEmail = new JButton("Send Email");
		btnSendEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String to = toEdit.getText();
				String cc = ccEdit.getText();
				String subj = subjectEdit.getText();
				String body = bodyEdit.getText();
				Sender s = new Sender(m.getCredentials(), to, cc, subj, body);
				s.send();
			}
		});
		btnSendEmail.setBounds(99, 463, 117, 29);
		contentPane.add(btnSendEmail);

		JButton btnEraseAllFields = new JButton("Erase all fields");
		btnEraseAllFields.setBounds(384, 463, 117, 29);
		contentPane.add(btnEraseAllFields);



	}



}
