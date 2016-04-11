package com.polyu.comp5134.ui.view;

import javax.swing.*;

import com.polyu.comp5134.domain.data.Staff;
import com.polyu.comp5134.domain.model.ELModel;
import com.polyu.comp5134.domain.util.StaffUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class LoginDialog extends JDialog implements Observer,ActionListener{
    
	private JPanel aPanel;
    private JFrame frame;
    private ELModel model;
    private MainView sv;
    private JButton btnLoginButton = new JButton("Ok");
    private JTextField passwordField = new JTextField();
    private JTextField userNameField = new JTextField();
    
    public LoginDialog(Frame aFrame, MainView parent, ELModel model) {
        super(aFrame, true);
        sv = parent;
		this.model=model;
		this.model.addObserver(this);
		setTitle("Login");
		initialize();
    }
    
    private void initialize() {
		buildUI();
		btnLoginButton.addActionListener(this);
	}
    
	private void buildUI() {
        //Create and set up the window.
		aPanel = new JPanel(new BorderLayout());
        userNameField.setText("");
        passwordField.setText("");
        System.out.println("loginview");
		JPanel topPanel = new JPanel();
	    topPanel.add(new JLabel("Login"));
	    aPanel.add(topPanel, BorderLayout.NORTH);
	    JPanel centerPanel = new JPanel(new GridLayout(8, 3, 5, 10));
	    for (int i = 0; i < 6; i++) {
	    	centerPanel.add(new JPanel());
	    }
	    centerPanel.add(new JLabel("Username", SwingConstants.RIGHT));
	    centerPanel.add(userNameField);
	    centerPanel.add(new JPanel());
	    centerPanel.add(new JLabel("Password", SwingConstants.RIGHT));
	    centerPanel.add(passwordField);
	    centerPanel.add(new JPanel());
	    centerPanel.add(new JPanel());
	    centerPanel.add(btnLoginButton);
	    centerPanel.add(new JPanel());
	    for (int i = 0; i < 9; i++) {
	    	centerPanel.add(new JPanel());
	    }
	    aPanel.add(centerPanel, BorderLayout.CENTER);
	    
	       //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(150, 80);
        centerPanel.setMinimumSize(minimumSize);
 
        //Provide a preferred size for the split pane.
        aPanel.setPreferredSize(new Dimension(500, 300));
        btnLoginButton.setActionCommand("LoginCmd");
	    add(aPanel);
	}

    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getActionCommand().equals("LoginCmd")) {
    		System.out.println("loginclicked");
    		System.out.println("userName:"+userNameField.getText()+" password:"+passwordField.getText());
			Staff s = StaffUtil.validate(userNameField.getText(), passwordField.getText());
			if(s != null){
				StaffUtil.loggonUser = s;
				dispose();
				model.notifyObservers();
			} else {
				StaffUtil.loggonUser = null;
				JOptionPane.showMessageDialog(null, "The user name and password are not matched");
			}
				
    	}
    }
    
	public void update(Observable src, Object arg){

	}
	
	public JFrame getWindow(){
		return this.frame;
	}
 
	public JPanel getPane() {
		return this.aPanel;
	}
}
