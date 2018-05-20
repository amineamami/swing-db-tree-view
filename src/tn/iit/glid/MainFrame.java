package tn.iit.glid;

import javax.swing.JFrame;

import tn.iit.glid.auth.LoginPanel;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Panel mainPanel;
	private LoginPanel loginPanel;

	public MainFrame(LoginPanel loginPanel) {
		this.loginPanel = loginPanel;
		mainPanel = new Panel(this);
		setSize(800, 600);
		setContentPane(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setVisible(true);
	}

	public LoginPanel getLoginPanel() {
		return loginPanel;
	}
	
	
}
