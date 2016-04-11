package com.polyu.comp5134.ui.view;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.Date;

import com.polyu.comp5134.domain.model.*;
import com.polyu.comp5134.domain.util.SequenceGenerator;
import com.polyu.comp5134.domain.util.StaffUtil;

import com.polyu.comp5134.domain.data.*;

public class ApproveLeaveView extends ELView implements Observer, ActionListener{

    private JSplitPane splitPane;
    private JTable leaveTable;
    private JPanel aPanel = new JPanel();
    private ELModel model;
    private JFrame frame;
    private AddLeaveDialog addLeaveDialog;
    private DeleteLeaveDialog delLeaveDialog;
    private JTextArea leaveInfoArea;
    private JComboBox comboxLeaveId = new JComboBox();
    private JButton btnEndorseLeaveButton = new JButton("Endorse");
    private JButton btnDeclineLeaveButton = new JButton("Decline");
    private Vector comboBoxItems;
    private DefaultComboBoxModel cdatamodel;
    private JLabel pendingRequest;
    
    public ApproveLeaveView(ELModel model) {

		this.model=model;
		this.model.addObserver(this);
		intialize();

    }
    
    private void intialize() {
		buildUI();
		btnEndorseLeaveButton.addActionListener(this);
		btnDeclineLeaveButton.addActionListener(this); 

    }
    
    private void buildUI() {
        frame = new JFrame("COMP5134 Leave Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500, 200);
        
		JPanel topPanel = new JPanel(new GridLayout(3, 1, 5, 10));
 
	    leaveInfoArea=new JTextArea(16, 150);
	    leaveInfoArea.setEditable(false); // set textArea non-editable
 
	    refreshLeaveInfo();

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(leaveInfoArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
 
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 5, 10));
        centerPanel.setPreferredSize(new Dimension(100, 100));
        centerPanel.add(new JLabel("No. of pending requests to be processed:", SwingConstants.RIGHT));
        pendingRequest= new JLabel();
        centerPanel.add(pendingRequest);
        int totalLeaves=refreshLeaveInfo();
	    pendingRequest.setText(totalLeaves+"");
	    topPanel.add(centerPanel);
	    centerPanel = new JPanel(new GridLayout(1, 5, 5, 10));
        centerPanel.add(new JPanel());
        centerPanel.add(new JLabel("Leave ID", SwingConstants.RIGHT));
	    comboBoxItems=new Vector();
	    List<Leave> requests=getPendingRequests();
	    for(Leave req : requests) {
	    	comboBoxItems.add(req.getLeaveId());
	    }
		//System.out.println("5 current staff:"+StaffUtil.loggonUser.getStaffName()+" no. of leaves:"+StaffUtil.loggonUser.getLeaveApplication().size());
	    cdatamodel = new DefaultComboBoxModel(comboBoxItems);
	    comboxLeaveId.setModel(cdatamodel);	
	    centerPanel.add(comboxLeaveId);
	    centerPanel.add(new JPanel());
	    centerPanel.add(new JPanel());
	    topPanel.add(centerPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 5, 5, 10));
        bottomPanel.add(new JPanel());
        bottomPanel.add(btnEndorseLeaveButton);
        bottomPanel.add(new JPanel());
        bottomPanel.add(btnDeclineLeaveButton);
        bottomPanel.add(new JPanel());
        topPanel.add(bottomPanel);
        	    
        //Create a split pane with the two scroll panes in it.
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
        		scrollPane, topPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
        
        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(150, 50);
        scrollPane.setMinimumSize(minimumSize);
        minimumSize = new Dimension(150, 200);
        //topPanel.setMinimumSize(minimumSize);
        splitPane.setPreferredSize(new Dimension(700, 240));
        //topPanel.setPreferredSize(new Dimension(700, 200));
        //Provide a preferred size for the split pane.
        aPanel.setPreferredSize(new Dimension(700, 250));
        aPanel.add(splitPane, BorderLayout.SOUTH);
        btnEndorseLeaveButton.setActionCommand("EndorseLeaveCmd");
        btnDeclineLeaveButton.setActionCommand("DeclineLeaveCmd");
        if(totalLeaves > 0) {
        	btnEndorseLeaveButton.setEnabled(true);
        	btnDeclineLeaveButton.setEnabled(true);
        }else {
        	btnEndorseLeaveButton.setEnabled(false);
        	btnDeclineLeaveButton.setEnabled(false);
        }
        //Add the scroll pane to this panel.
        add(aPanel);

    }
     
    public void actionPerformed(ActionEvent e) {
    	Staff currentStaff = StaffUtil.loggonUser;
    	String leaveID=(String) comboxLeaveId.getSelectedItem();
		Leave leave=currentStaff.findLeaveByLeaveId(leaveID);
		System.out.println("ApproveLeave: selected leaveID:"+leaveID+" found leaveID:"+leave.getLeaveId()+ " current user:"+StaffUtil.loggonUser.getStaffName() +" action:"+e.getActionCommand());
		
       	if (e.getActionCommand().equals("EndorseLeaveCmd")) {
       		model.updateLeave(currentStaff, leave, Leave.accepted);
    		
       	}else if (e.getActionCommand().equals("DeclineLeaveCmd")) {
       		model.updateLeave(currentStaff, leave, Leave.declined);
       	}
    }
    
    public int refreshLeaveInfo() {
    	String leaveInfo="";
    	int totalLeave=0;
    	
    	Staff currentStaff = StaffUtil.loggonUser;
    	if(currentStaff!=null) {
	    	List<Leave> leaveApplications=currentStaff.getLeaveApplication();
	    	String leaveId="";
	    	String from="";
	    	String to="";
	    	String reason="";
	    	String status="";
	    	String applicant="";
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	
	    	leaveInfo+=String.format("%1$10s%2$20s%3$20s%4$20s%5$20s%6$20s\n","Leave ID", "From", "To", "Reason", "Applicant", "Status");
		    for(Leave leave : leaveApplications) {
		    	if(leave.getApprover().getStaffId().equals(currentStaff.getStaffId())) {
		    		leaveId = leave.getLeaveId();
		    		from = formatter.format(leave.getStartDate());
		    		to = formatter.format(leave.getEndDate());
		    		reason = leave.getLeaveReason();
		    		status = leave.getLeaveStatus();
		    		applicant = leave.getApplicant().getStaffName();
		    		leaveInfo+=String.format("%1$10s%2$20s%3$20s%4$20s%5$20s%6$20s\n",leaveId, from, to, reason, applicant, status);
		    		totalLeave++;
		    	}
		    }
		    leaveInfoArea.setText(leaveInfo);
    	}
    	return totalLeave;
    }
    
    public List<Leave> getPendingRequests() {
    	Staff currentStaff = StaffUtil.loggonUser;
    	List<Leave> leaveApplications=currentStaff.getLeaveApplication();
    	List<Leave> pendingRequests=new ArrayList<Leave>();
    	
    	for(Leave leave : leaveApplications) {
    		if(leave.getApprover().getStaffId().equals(currentStaff.getStaffId())) {
    			pendingRequests.add(leave);
    			System.out.println("1 getPendingRequests:"+pendingRequests.size()+" leaveID:"+leave.getLeaveId());
    		}
    	}
    	System.out.println("2 getPendingRequests:"+pendingRequests.size());
    	return pendingRequests;
    }
    
	public void update(Observable src, Object arg){
		System.out.println("Update ApproveLeave View");
		
		if(StaffUtil.loggonUser!=null) {
			int totalLeaves=refreshLeaveInfo();
			pendingRequest.setText(totalLeaves+"");
	        if(totalLeaves > 0) {
	        	btnEndorseLeaveButton.setEnabled(true);
	        	btnDeclineLeaveButton.setEnabled(true);
	        }else {
	        	btnEndorseLeaveButton.setEnabled(false);
	        	btnDeclineLeaveButton.setEnabled(false);
	        }
	    	
			comboBoxItems.removeAllElements();
		    List<Leave> requests=getPendingRequests();
		    for(Leave req : requests) {
		    	comboBoxItems.add(req.getLeaveId());
		    }
		    System.out.println("2 comboxItems size:"+comboBoxItems.size());
    	}
	}
 
    public JSplitPane getSplitPane() {
        return splitPane;
    }
 
    public JFrame getFrame() {
        return this.frame;
    }    
    
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MVCDriver driver = new MVCDriver();
            	ApproveLeaveView window = new ApproveLeaveView(driver.getModel());
                window.setOpaque(true); //content panes must be opaque
                window.getFrame().setContentPane(window);
         
                //Display the window.
                window.getFrame().pack();
                window.getFrame().setVisible(true);
            }
        });
    }
 
       
}
