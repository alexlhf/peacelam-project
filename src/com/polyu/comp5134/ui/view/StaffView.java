package com.polyu.comp5134.ui.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.List;

import com.polyu.comp5134.domain.data.Staff;
import com.polyu.comp5134.domain.model.*;
import com.polyu.comp5134.domain.util.StaffUtil;

public class StaffView extends ELView implements Observer, ActionListener{
    private ELModel model;
    private JSplitPane splitPane;
    private JTextArea staffInfoArea;
    private JPanel aPanel = new JPanel();
    private JFrame frame;
    private AddStaffDialog addStaffDialog;
    private DeleteStaffDialog delStaffDialog;
	private List<Staff> staffs;
	private String staffInfo;
	private String staffName, staffId, supervisor, staffRole;
    private JButton btnAddStaffButton = new JButton("Add");
	private JButton btnDelStaffButton = new JButton("Delete");
	
    public StaffView(ELModel model) {

		this.model=model;
		this.model.addObserver(this);
		intialize();

    }
    
    public void intialize() {
		buildUI();
		btnAddStaffButton.addActionListener(this);
		btnDelStaffButton.addActionListener(this); 
    }
    
    private void buildUI() {
        frame = new JFrame("COMP5134 Leave Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500, 200);
        
        addStaffDialog = new AddStaffDialog(frame, this, model);
        addStaffDialog.pack();
        delStaffDialog = new DeleteStaffDialog(frame, this, model);
        delStaffDialog.pack();
        
		JPanel topPanel = new JPanel();
	    topPanel.add(new JLabel("Staff Management"));
	    aPanel.add(topPanel, BorderLayout.NORTH);
	    
	    staffInfoArea=new JTextArea(16, 58);
	    staffInfoArea.setEditable(false); // set textArea non-editable
 
	    refreshStaffInfo();

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(staffInfoArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        JPanel bottomPanel = new JPanel(new GridLayout(2, 5, 5, 10));
        for(int i=0;i<5;i++)
        	bottomPanel.add(new JPanel());
        
        bottomPanel.add(new JPanel());
		
        bottomPanel.add(btnAddStaffButton);
        bottomPanel.add(new JPanel());
        bottomPanel.add(btnDelStaffButton);
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
        splitPane.setPreferredSize(new Dimension(500, 200));
        //Provide a preferred size for the split pane.
        aPanel.setPreferredSize(new Dimension(500, 300));
        aPanel.add(splitPane, BorderLayout.SOUTH);
        btnAddStaffButton.setActionCommand("AddStaff");
        btnDelStaffButton.setActionCommand("DelStaff");
        if(StaffUtil.loggonUser.getStaffRole().equals(Staff.staff)) {
        	btnAddStaffButton.setEnabled(false);
        	btnDelStaffButton.setEnabled(false);
        }else {
        	btnAddStaffButton.setEnabled(true);
        	btnDelStaffButton.setEnabled(true);	
        }
        add(aPanel);
		   	
    }

    public void actionPerformed(ActionEvent e) {
       	if (e.getActionCommand().equals("AddStaff")) {
			addStaffDialog.setLocationRelativeTo(frame);
			addStaffDialog.setLocation(600, 400);
			addStaffDialog.setVisible(true);       		
       	}else if (e.getActionCommand().equals("DelStaff")) {
			delStaffDialog.setLocationRelativeTo(frame);
			delStaffDialog.setLocation(600, 400);
			delStaffDialog.setVisible(true);       		
       	}
    }
    
    public void refreshStaffInfo() {
    	String staffInfo="";
    	
    	staffs = StaffUtil.getAllSTaff();
    	staffInfo+=String.format("%1$25s %2$30s %3$30s %4$25s\n","Name", "Staff ID", "Supervisor", "Role");
	    for(Staff staff : staffs) {
	    	staffName=staff.getStaffName();
	    	staffId=staff.getStaffId();
	    	supervisor=staff.getSupervisor()==null?"N/A":staff.getSupervisor().getStaffName();
	    	staffRole=staff.getStaffRole();
	    	staffInfo+=String.format("%1$25s %2$30s %3$30s %4$30s\n",staffName, staffId, supervisor, staffRole);
	    }
	    staffInfoArea.setText(staffInfo);
    }
    
	public void update(Observable src, Object arg){
		System.out.println("Update StaffView");
		if(StaffUtil.loggonUser!=null) {
			refreshStaffInfo();
	        if(StaffUtil.loggonUser.getStaffRole().equals(Staff.staff)) {
	        	btnAddStaffButton.setEnabled(false);
	        	btnDelStaffButton.setEnabled(false);
	        }else {
	        	btnAddStaffButton.setEnabled(true);
	        	btnDelStaffButton.setEnabled(true);	
	        }
		}
	}
 
    public JSplitPane getSplitPane() {
        return splitPane;
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
            	StaffView window = new StaffView(driver.getModel());
                window.setOpaque(true); //content panes must be opaque
                window.frame.setContentPane(window);
         
                //Display the window.
                window.frame.pack();
                window.frame.setVisible(true);
            }
        });
    }
 
       
}
