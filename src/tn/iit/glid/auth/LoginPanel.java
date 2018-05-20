package tn.iit.glid.auth;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import tn.iit.glid.MainFrame;
import tn.iit.glid.database.DBConnection;

public class LoginPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginFrame loginFrame;
	private JLabel userLabel;
	private JLabel passLabel;
	private JTextField userField;
	private JPasswordField passField;
	private JLabel sidLabel;
	private JTextField sidField;
	private JButton loginBtn;
	private JButton cancelBtn;
	DBConnection connection = new DBConnection();

	public LoginPanel(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		userLabel = new JLabel(" Username :");
		c.gridwidth = 1;
		add(userLabel, c);
		c.gridx++;
		c.gridwidth = 2;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		userField = new JTextField();
		add(userField, c);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		passLabel = new JLabel(" Password :");
		add(passLabel, c);
		c.gridx++;
		c.gridwidth = 2;
		c.weightx = 1;
		passField = new JPasswordField();
		add(passField, c);
		c.gridx = 0;
		c.gridy++;
		c.weightx = 0;
		c.gridwidth = 2;
		loginBtn = new JButton("Login");
		loginBtn.addActionListener(e -> {
			if (!connection.openConnection(userField.getText(), passField.getText(),sidField.getText())) {
				JOptionPane.showMessageDialog(loginFrame, "Invalid username or password !.", "error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(loginFrame, "Logged successfully :)");
				loginFrame.setVisible(false);
				new MainFrame(this);
				// loginFrame.dispatchEvent(new WindowEvent(loginFrame,
				// WindowEvent.WINDOW_CLOSING));
			}
		});
		sidLabel = new JLabel(" SID :");
		add(sidLabel, c);
		c.gridx++;
		c.gridwidth = 2;
		cancelBtn = new JButton("Close");
		cancelBtn.addActionListener(e -> {
			System.exit(0);
		});
		sidField = new JTextField("orcl");
		add(sidField, c);
		c.gridy++;
		c.gridx = 0;
		add(cancelBtn, c);
		c.gridx++;
		add(loginBtn, c);

	}

	public LoginFrame getLoginFrame() {
		return loginFrame;
	}

	public JTextField getUserField() {
		return userField;
	}

	public JTextField getPassField() {
		return passField;
	}

	public DBConnection getConnection() {
		return connection;
	}
}
