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

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

	database d = new database();

	String global_pwd = "";

	public Login() {
				
		this.setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 200, 180);    
        
		JPanel container = new JPanel();
		setContentPane(container);
		container.setLayout(null);
		JLabel userLabel = new JLabel("Login");
		userLabel.setBounds(5, 5, 180, 20);
		JTextField username = new JTextField();
		username.setBounds(5, 25, 180, 20);
		JLabel pwdLabel = new JLabel("Password");
		pwdLabel.setBounds(5, 50, 180, 20);
		JPasswordField userpwd = new JPasswordField();
		userpwd.setBounds(5, 75, 180, 20);
		JButton okButton = new JButton("OK");
		okButton.setBounds(5, 120, 80, 25);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String login = username.getText();
				
				@SuppressWarnings("deprecation")
				String pwd = userpwd.getText();
								
				try {
					global_pwd = d.getMemberId(login)[1].toString();
					if (global_pwd.equals(pwd)) {
												
						MainWindow.GLOBAL_MEMBER_GROUP = d.getMemberId(login)[2].toString();
						if (MainWindow.GLOBAL_MEMBER_GROUP.equals("CLIENT")) {
							MainWindow.GLOBAL_MEMBER_ID = Integer.parseInt(d.getMemberId(login)[0].toString());												
							MainWindow.EDIT_MEMBER_ID = Integer.parseInt(d.getMemberId(login)[0].toString());												
						}
						else {
							MainWindow.EDIT_MEMBER_ID = Integer.parseInt(d.getMemberId(login)[0].toString());												
							MainWindow.GLOBAL_MEMBER_ID = 0;
						}
						
						javax.swing.SwingUtilities.invokeLater(new Runnable() {
				            public void run() {
				                JFrame.setDefaultLookAndFeelDecorated(true);
				                
				                MainWindow mainWindow = new MainWindow();
				                mainWindow.setVisible(true);
				                setVisible(false);
				            }
				        });					
					}
					else {
						JOptionPane.showMessageDialog(null,"Password mismach");
					}
				} catch(NullPointerException e) {
					JOptionPane.showMessageDialog(null,"No such user");					
				}				
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(105, 120, 80, 25);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		container.add(userLabel);
		container.add(username);
		container.add(pwdLabel);
		container.add(userpwd);
		container.add(okButton);
		container.add(cancelButton);
	}
	
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                
                Login login = new Login();
                login.setVisible(true);
            }
        });					
	}
}
