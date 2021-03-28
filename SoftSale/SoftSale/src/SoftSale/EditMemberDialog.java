package SoftSale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class EditMemberDialog extends JFrame {

	private static final long serialVersionUID = 1L;

	database d = new database();
	
	int MEMBER_ID = 0;
	String USERNAME = "";
	String PASSWORD = "";
	String EMAIL = "";
	String FULLNAME = "";
	String ADDRESS = "";
	String PHONE = "";
	
	
	public EditMemberDialog() {

		this.setTitle("Edit user");
		setResizable(false);
		setBounds(450, 200, 200, 360);    
        String[] userinfo = new String[6]; 
        
        userinfo = d.getUserInfo(MainWindow.EDIT_MEMBER_ID);
		JPanel container = new JPanel();
		setContentPane(container);
		container.setLayout(null);
		
		JLabel usernameLabel = new JLabel("Login");
		usernameLabel.setBounds(5, 5, 180, 20);
		JTextField usernamefield = new JTextField();
		usernamefield.setBounds(5, 25, 180, 20);
		usernamefield.setText(userinfo[0]);
		
		JLabel pwdLabel = new JLabel("Password");
		pwdLabel.setBounds(5, 50, 180, 20);
		JPasswordField jpfield = new JPasswordField();
		jpfield.setBounds(5, 75, 180, 20);
		jpfield.setText(userinfo[1]);
		
		JLabel emailLabel = new JLabel("e-mail");
		emailLabel.setBounds(5, 100, 180, 20);
		JTextField emailfield = new JTextField();
		emailfield.setBounds(5, 125, 180, 20);
		emailfield.setText(userinfo[2]);
		
		JLabel fullnameLabel = new JLabel("Full name");
		fullnameLabel.setBounds(5, 150, 180, 20);
		JTextField fullnamefield = new JTextField();
		fullnamefield.setBounds(5, 175, 180, 20);
		fullnamefield.setText(userinfo[3]);
		
		JLabel addressLabel = new JLabel("Address");
		addressLabel.setBounds(5, 200, 180, 20);
		JTextField addressfield = new JTextField();
		addressfield.setBounds(5, 225, 180, 20);
		addressfield.setText(userinfo[4]);
		
		JLabel phoneLabel = new JLabel("Phone");
		phoneLabel.setBounds(5, 250, 180, 20);
		JTextField phonefield = new JTextField();
		phonefield.setBounds(5, 275, 180, 20);
		phonefield.setText(userinfo[5]);
		
		JButton okButton = new JButton("OK");
		okButton.setBounds(5, 300, 80, 25);
		okButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				MEMBER_ID = MainWindow.EDIT_MEMBER_ID;
				USERNAME = usernamefield.getText();
				PASSWORD = jpfield.getText();
				EMAIL = emailfield.getText();
				FULLNAME = fullnamefield.getText();
				ADDRESS = addressfield.getText();
				PHONE = phonefield.getText();
				
				try {
					if (d.setUserInfo(MEMBER_ID, USERNAME, PASSWORD, EMAIL, FULLNAME, ADDRESS, PHONE)) {
						JOptionPane.showMessageDialog(null,"User information updated");
						setVisible(false);
					}
				}catch (Exception e) {
					
				}
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(105, 300, 80, 25);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		 
		
		container.add(usernameLabel);
		container.add(usernamefield);
		container.add(pwdLabel);
		container.add(jpfield);
		container.add(emailLabel);
		container.add(emailfield);
		container.add(fullnameLabel);
		container.add(fullnamefield);
		container.add(addressLabel);
		container.add(addressfield);
		container.add(phoneLabel);
		container.add(phonefield);
		container.add(okButton);
		container.add(cancelButton);

	}
}
