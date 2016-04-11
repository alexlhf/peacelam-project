package com.polyu.comp5134.ui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.util.Observable;
import java.util.Observer;
import com.polyu.comp5134.domain.model.*;
import com.polyu.comp5134.domain.util.StaffUtil;
import com.polyu.comp5134.domain.data.*;

public class DeleteLeaveDialog extends JDialog implements Observer,ActionListener{
    private ELModel model;
    private JFrame frame;
    private ApplyLeaveView sv;
	private JPanel aPanel = new JPanel(new BorderLayout());
    private List<Staff> staffs;
    private JTextField staffIdText, superText, roleText;
    private DefaultComboBoxModel cdatamodel;
    private Vector comboBoxItems;
    private JPanel centerPanel;
    private JComboBox combox =new JComboBox();
    private JButton btnConfirmDelButton = new JButton("Ok");
    private JButton btnCancelButton = new JButton("CANCEL");
    
    public DeleteLeaveDialog(Frame aFrame, ApplyLeaveView parent, ELModel model) {
        super(aFrame, true);
        sv = parent;
		this.model=model;
		this.model.addObserver(this);
        setTitle("Delete Leave");
		initialize();
    }
    
	private void initialize() {
		buildUI();
        btnConfirmDelButton.addActionListener(this);
        btnCancelButton.addActionListener(this);
        combox.addActionListener(this);
	}
	
	private void buildUI() {
		JPanel topPanel = new JPanel();
	    topPanel.add(new JLabel("Delete Staff"));
	    aPanel.add(topPanel, BorderLayout.NORTH);
	    centerPanel = new JPanel(new GridLayout(7, 3, 5, 10));
	    for (int i = 0; i < 3; i++) {
	    	centerPanel.add(new JPanel());
	    }
	    centerPanel.add(new JLabel("Staff Name", SwingConstants.RIGHT));
	    comboBoxItems=new Vector();
	    staffs = StaffUtil.getAllSTaff();
	    for(Staff staff : staffs) {
	    	comboBoxItems.add(staff.getStaffName()+" "+staff.getStaffId());
	    }
	    cdatamodel = new DefaultComboBoxModel(comboBoxItems);
	    combox.setModel(cdatamodel);
	    //System.out.println("staff size:"+staffs.size());

	    combox.setSelectedIndex(0);
	    centerPanel.add(combox);
	    centerPanel.add(new JPanel());
	    centerPanel.add(new JLabel("Staff ID", SwingConstants.RIGHT));
	    staffIdText=new JTextField();
	    staffIdText.setEditable(false); // set textArea non-editable
	    centerPanel.add(staffIdText);
    	centerPanel.add(new JPanel());
    	centerPanel.add(new JLabel("Supervisor", SwingConstants.RIGHT));
    	superText=new JTextField();
    	superText.setEditable(false); // set textArea non-editable
    	centerPanel.add(superText);
    	centerPanel.add(new JPanel());
    	centerPanel.add(new JLabel("Role", SwingConstants.RIGHT));
    	roleText=new JTextField();
    	roleText.setEditable(false); // set textArea non-editable
    	centerPanel.add(roleText);
    	centerPanel.add(new JPanel());  	
	    for (int i = 0; i < 6; i++) {
	    	centerPanel.add(new JPanel());
	    }
	    aPanel.add(centerPanel, BorderLayout.CENTER);
	    
        JPanel bottomPanel = new JPanel(new GridLayout(2, 5, 5, 10));
        bottomPanel.add(new JPanel());
        bottomPanel.add(btnConfirmDelButton);
        bottomPanel.add(new JPanel());
        bottomPanel.add(btnCancelButton);
        bottomPanel.add(new JPanel());
        for (int i = 0; i < 5; i++) {
        	bottomPanel.add(new JPanel());
	    }
        aPanel.add(bottomPanel, BorderLayout.SOUTH);
        
	       //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(150, 80);
        centerPanel.setMinimumSize(minimumSize);
        bottomPanel.setMinimumSize(minimumSize);
        
        //Provide a preferred size for the split pane.
        aPanel.setPreferredSize(new Dimension(500, 300));
        btnConfirmDelButton.setActionCommand("delStaff");
        btnCancelButton.setActionCommand("exitDelView");
        combox.setActionCommand("combSelect");
        add(aPanel);		
	}

    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("delStaff")) {
			String staffId=staffIdText.getText();
			Staff staff = StaffUtil.findStaffByStaffId(staffId);
			if(staff!=null) {
				if(!staff.getStaffRole().equals(Staff.director)) {
					model.delStaff(staffId);
                    JOptionPane.showMessageDialog(null,
                            "Staff deleted",
                            "Message",
                            JOptionPane.PLAIN_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Cannot delete Director", "Delete error", JOptionPane.ERROR_MESSAGE);
				}
			}    		
    	}else if(e.getActionCommand().equals("exitDelView")) {
    		dispose();
    	}else if(e.getActionCommand().equals("combSelect")) {    		
            JComboBox cb = (JComboBox)e.getSource();
	        String staffName = (String)cb.getSelectedItem();
	        if(staffName!=null) {
	            String staffId = staffName.substring(staffName.indexOf(" S")+1).trim();
	            staffIdText.setText(staffId);
	            //System.out.println("Staff Id:["+staffId+"]");
	            Staff staff = StaffUtil.findStaffByStaffId(staffId);
	            if(staff==null)
	            	JOptionPane.showMessageDialog(null, "No staff found", "Search error", JOptionPane.ERROR_MESSAGE);
	            else {
	            	superText.setText(staff.getSupervisor()==null?"N/A":staff.getSupervisor().getStaffName());
	            	roleText.setText(staff.getStaffRole());
	            }
            }	            
    	}
    }
    
	public void update(Observable src, Object arg){
			//System.out.println("update view");
			staffIdText.setText("");
			superText.setText("");
			roleText.setText("");
			combox.removeAllItems();
		    staffs = StaffUtil.getAllSTaff();
		    for(Staff staff : staffs) {
		    	combox.addItem(new String(staff.getStaffName()+" "+staff.getStaffId()));
		    }		
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
