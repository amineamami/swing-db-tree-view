package tn.iit.glid.auth;

import javax.swing.JFrame;

public class LoginFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginPanel loginPanel;

	public LoginFrame() {
		loginPanel = new LoginPanel(this);
		setSize(400, 200);
		setLocationRelativeTo(null);
		setContentPane(loginPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new LoginFrame();
	}

}
