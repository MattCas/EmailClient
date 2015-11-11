package mail.client;

import java.awt.EventQueue;

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
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import javax.swing.JButton;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ClientWindow extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup folSelectButtonGroup = new ButtonGroup();
	public static Client c = new Client();
	public static ClientModel m = new ClientModel(c);
	public JScrollPane scrollInbox = new JScrollPane();
	public JScrollPane scrollSpam = new JScrollPane();
	public JTextArea emailBodyView = new JTextArea();

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
		//Implememnt the list selection interface
		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			@SuppressWarnings({ "static-access" })
			public void valueChanged(ListSelectionEvent lse) {
				boolean adjust = lse.getValueIsAdjusting();
				if (!adjust) {
					JList list = (JList) lse.getSource();
					int selections[] = list.getSelectedIndices();
					for (int i = 0, n = selections.length; i < n; i++) {
						//emailBodyView.setText(m.displayInboxMessage(selections[i]);
						emailBodyView.setBounds(301, 33, 293, 159);
						 try {
							 emailBodyView.setText(m.displayInboxMessage(selections[i]));
						} catch (javax.mail.MessagingException e) {
							e.printStackTrace();
						} 
						
						contentPane.add(emailBodyView);
						contentPane.revalidate();
						contentPane.repaint();
					}
					//System.out.println();
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
				listIn.addListSelectionListener(listSelectionListener);
				scrollSpam.setVisible(false);;
				contentPane.add(scrollInbox);
				contentPane.revalidate();
				contentPane.repaint();

				//scrollSpam.setVisible(false);
				//listIn.addListSelectionListener(objectListener);
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
				JList<String> list = new JList<String>();
				list = m.folderToList(c.getSpam());
				//Stick all subjects in a scrollPane
				scrollSpam = new JScrollPane(list);
				scrollSpam.setBounds(6, 33, 292, 159);
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

		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(99, 278, 401, 173);
		contentPane.add(editorPane);

		JButton btnSendEmail = new JButton("Send Email");
		btnSendEmail.setBounds(99, 463, 117, 29);
		contentPane.add(btnSendEmail);

		JButton btnEraseAllFields = new JButton("Erase all fields");
		btnEraseAllFields.setBounds(384, 463, 117, 29);
		contentPane.add(btnEraseAllFields);



	}



}
