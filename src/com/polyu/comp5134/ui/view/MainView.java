package com.polyu.comp5134.ui.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import com.polyu.comp5134.domain.model.*;
import com.polyu.comp5134.domain.util.StaffUtil;
import com.polyu.comp5134.domain.data.*;
import com.polyu.comp5134.ui.view.MVCDriver;

public class MainView extends ELView implements Observer, ActionListener{

    private JSplitPane splitPane;
	private ELModel model;
    private JTable leaveTable;
    private JPanel aPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JFrame frame;
    private StaffView staffView;
    private MVCDriver driver;
    private LoginDialog loginDialog;
    private JButton btnStaffButton = new JButton("Staff");
    private JButton btnApplyLeaveButton = new JButton("My Leaves");
    private JButton btnApproveLeaveButton = new JButton("Approve Leave");
    private JButton btnLoginButton = new JButton("Login");
    private JButton btnLogoutButton = new JButton("Logout");
	private String loginText;
	private JLabel lblLoginText=new JLabel();
	
    public MainView(ELModel model) {
		this.model=model;
		this.model.addObserver(this);
		intialize();
    }
    
    private void intialize() {
		buildUI();
		btnStaffButton.addActionListener(this);
		btnApplyLeaveButton.addActionListener(this);
		btnApproveLeaveButton.addActionListener(this);
		btnLogoutButton.addActionListener(this);
		btnLoginButton.addActionListener(this);
    }
    
	private void buildUI() { 
        frame = new JFrame("COMP5134 e-Leave Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300, 200);
        if(StaffUtil.loggonUser!=null) {
        	String staffText=StaffUtil.loggonUser.getStaffName();
        	loginText="Main View (Staff:"+staffText+")";
        }else {
        	loginText="Main View";
        }
       
	    topPanel.add(lblLoginText);
	    lblLoginText.setText(loginText);
	    aPanel.add(topPanel, BorderLayout.NORTH);
        //Create the scroll pane and add the table to it.
        JScrollPane rightScrollPane = new JScrollPane();
        
        JPanel leftPanel = new JPanel(new GridLayout(10, 1, 5, 2));
		
        leftPanel.add(btnStaffButton);
        leftPanel.add(new JPanel());
        leftPanel.add(btnApplyLeaveButton);
        leftPanel.add(new JPanel());
        leftPanel.add(btnApproveLeaveButton);
        leftPanel.add(new JPanel());
        leftPanel.add(btnLogoutButton);
        leftPanel.add(new JPanel());
        leftPanel.add(btnLoginButton);
        leftPanel.add(new JPanel());
		btnLogoutButton.setEnabled(false);
		btnStaffButton.setEnabled(false);
		btnApplyLeaveButton.setEnabled(false);
		btnApproveLeaveButton.setEnabled(false);
		btnLoginButton.setEnabled(true);
        //Create a split pane with the two scroll panes in it.
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        		leftPanel, rightScrollPane);
        //splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(120);
        
        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(120, 50);
        leftPanel.setMinimumSize(minimumSize);
        rightScrollPane.setMinimumSize(minimumSize);
        splitPane.setPreferredSize(new Dimension(700, 250));
        //Provide a preferred size for the split pane.
        aPanel.setPreferredSize(new Dimension(700, 300));
        aPanel.add(splitPane, BorderLayout.CENTER);
        //Add the scroll pane to this panel.
        btnStaffButton.setActionCommand("StaffView");
        btnApplyLeaveButton.setActionCommand("ApplyLeaveView");
        btnApproveLeaveButton.setActionCommand("ApproveLeaveView");
        btnLogoutButton.setActionCommand("LogoutCmd");
        btnLoginButton.setActionCommand("LoginCmd");
        add(aPanel);
    }

    public void actionPerformed(ActionEvent e) {
       	if (e.getActionCommand().equals("StaffView")) {
			StaffView staffView = new StaffView(model);
			JSplitPane rightPanel = staffView.getSplitPane();
			splitPane.setRightComponent(rightPanel);
			btnLogoutButton.setEnabled(true);
       	}else if (e.getActionCommand().equals("ApplyLeaveView")) {
			ApplyLeaveView applyLeave = new ApplyLeaveView(model);
			JSplitPane rightPanel = applyLeave.getSplitPane();
			splitPane.setRightComponent(rightPanel);
			btnLogoutButton.setEnabled(true);	
       	}else if (e.getActionCommand().equals("ApproveLeaveView")) {
			ApproveLeaveView approveLeave = new ApproveLeaveView(model);
			JSplitPane rightPanel = approveLeave.getSplitPane();
			splitPane.setRightComponent(rightPanel);
			btnLogoutButton.setEnabled(true);	
    	}else if (e.getActionCommand().equals("LogoutCmd")) {
			btnLogoutButton.setEnabled(false);
			btnStaffButton.setEnabled(false);
			btnApplyLeaveButton.setEnabled(false);
			btnApproveLeaveButton.setEnabled(false);
			btnLoginButton.setEnabled(true);
			StaffUtil.loggonUser=null;
			model.refreshModel();
       	}else if (e.getActionCommand().equals("LoginCmd")) {
       		loginDialog = new LoginDialog(frame, this, model);
       		loginDialog.pack();
			loginDialog.setLocationRelativeTo(frame);
			loginDialog.setLocation(600, 400);
			loginDialog.setVisible(true); 
			if(StaffUtil.loggonUser!=null) {
				btnLogoutButton.setEnabled(true);
				btnStaffButton.setEnabled(true);
				btnApplyLeaveButton.setEnabled(true);
				btnApproveLeaveButton.setEnabled(true);
				btnStaffButton.setEnabled(true);
				btnLoginButton.setEnabled(false);
				Staff staff=StaffUtil.findStaffByName(StaffUtil.loggonUser.getStaffName());
				if(staff.getStaffRole().equals(staff.director)) {
					btnApplyLeaveButton.setEnabled(false);
				}
			}
       	}
    }

	public void update(Observable src, Object arg){
		System.out.println("Update MainView");
        if(StaffUtil.loggonUser!=null) {
        	String staffText=StaffUtil.loggonUser.getStaffName();
        	loginText="Main View (Staff:"+staffText+")";
        }else {
        	loginText="Main View";
        }
        
        lblLoginText.setText(loginText);
	
	}
	
    public JSplitPane getSplitPane() {
        return this.splitPane;
    }
    
    public JFrame getFrame() {
        return this.frame;
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MVCDriver driver = new MVCDriver();
            	MainView window = new MainView(driver.getModel());
                window.setOpaque(true); //content panes must be opaque
                window.frame.setContentPane(window);
         
                //Display the window.
                window.frame.pack();
                window.frame.setVisible(true);
            }
        });
    }
 
       
}
