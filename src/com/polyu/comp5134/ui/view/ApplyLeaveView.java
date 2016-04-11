package com.polyu.comp5134.ui.view;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.event.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Date;
import com.polyu.comp5134.domain.data.Staff;
import com.polyu.comp5134.domain.model.*;
import com.polyu.comp5134.domain.util.StaffUtil;

import com.polyu.comp5134.domain.data.*;

public class ApplyLeaveView extends ELView implements Observer, ActionListener{

    private JSplitPane splitPane;
    private JTable leaveTable;
    private JPanel aPanel = new JPanel();
    private ELModel model;
    private JFrame frame;
    private AddLeaveDialog addLeaveDialog;
    private DeleteLeaveDialog delLeaveDialog;
    private JTextArea leaveInfoArea;
    private JButton btnAddLeaveButton = new JButton("New Leave");
    private JButton btnDeleteLeaveButton = new JButton("Delete Leave");
    
    public ApplyLeaveView(ELModel model) {

		this.model=model;
		this.model.addObserver(this);
		intialize();

    }
    
    private void intialize() {
		buildUI();
		btnAddLeaveButton.addActionListener(this);
		btnDeleteLeaveButton.addActionListener(this); 
    }
    
    private void buildUI() {
        frame = new JFrame("COMP5134 Leave Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500, 200);
        
		JPanel topPanel = new JPanel();
	    topPanel.add(new JLabel("Staff Leaves"));
	    aPanel.add(topPanel, BorderLayout.NORTH);
 
	    leaveInfoArea=new JTextArea(16, 250);
	    leaveInfoArea.setEditable(false); // set textArea non-editable
 
	    refreshLeaveInfo();

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(leaveInfoArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        JPanel bottomPanel = new JPanel(new GridLayout(2, 5, 5, 10));
        for(int i=0;i<5;i++)
        	bottomPanel.add(new JPanel());
        bottomPanel.add(new JPanel());
        bottomPanel.add(new JPanel());
        bottomPanel.add(btnAddLeaveButton);
        bottomPanel.add(new JPanel());
        bottomPanel.add(new JPanel());
        
        //Create a split pane with the two scroll panes in it.
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
        		scrollPane, bottomPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
        
        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(150, 50);
        scrollPane.setMinimumSize(minimumSize);
        bottomPanel.setMinimumSize(minimumSize);
        splitPane.setPreferredSize(new Dimension(700, 200));
        //Provide a preferred size for the split pane.
        aPanel.setPreferredSize(new Dimension(700, 250));
        aPanel.add(splitPane, BorderLayout.SOUTH);
        btnAddLeaveButton.setActionCommand("AddLeaveCmd");
        btnDeleteLeaveButton.setActionCommand("DeleteLeaveCmd");
        //Add the scroll pane to this panel.
        add(aPanel);

    }
     
    public void actionPerformed(ActionEvent e) {
       	if (e.getActionCommand().equals("AddLeaveCmd")) {
       		addLeaveDialog = new AddLeaveDialog(frame, this, model);
       		addLeaveDialog.pack();
       		addLeaveDialog.setLocationRelativeTo(frame);
       		addLeaveDialog.setLocation(600, 400);
       		addLeaveDialog.setVisible(true);       		
       	}/*else if (e.getActionCommand().equals("DeleteLeaveCmd")) {
            delLeaveDialog = new DeleteLeaveDialog(frame, this, model);
            delLeaveDialog.pack();
       		delLeaveDialog.setLocationRelativeTo(frame);
       		delLeaveDialog.setLocation(600, 400);
       		delLeaveDialog.setVisible(true);       		
       	}*/
    }
    
    public void refreshLeaveInfo() {
    	String leaveInfo="";
    	Staff currentStaff = StaffUtil.loggonUser;
    	if(currentStaff!=null) {
	    	List<Leave> leaveApplications=currentStaff.getLeaveApplication();
	    	String leaveId="";
	    	String from="";
	    	String to="";
	    	String reason="";
	    	String status="";
	    	String approver="";
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	
	    	leaveInfo+=String.format("%1$10s%2$20s%3$20s%4$20s%5$20s%6$20s\n","Leave ID", "From", "To", "Reason", "Approver", "Status");
		    for(Leave leave : leaveApplications) {
		    	if(leave.getApplicant().getStaffId().equals(currentStaff.getStaffId())) {
		    		leaveId = leave.getLeaveId();
		    		from = formatter.format(leave.getStartDate());
		    		to = formatter.format(leave.getEndDate());
		    		reason = leave.getLeaveReason();
		    		status = leave.getLeaveStatus();
		    		approver = leave.getApprover().getStaffName();
		    		leaveInfo+=String.format("%1$10s%2$20s%3$20s%4$20s%5$20s%6$20s\n",leaveId, from, to, reason, approver, status);
		    	}
		    }
		    //System.out.println(leaveInfo);
		    leaveInfoArea.setText(leaveInfo);
    	}
    }
    
	public void update(Observable src, Object arg){
		System.out.println("Update ApplyLeave View");
		refreshLeaveInfo();
	
	}
 
    public JSplitPane getSplitPane() {
        return splitPane;
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //createAndShowGUI();
            }
        });
    }
 
       
}
