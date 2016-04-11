package com.polyu.comp5134.ui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import com.polyu.comp5134.domain.data.AbstractStaff.StaffRole;
import com.polyu.comp5134.domain.data.AbstractStaff;
import com.polyu.comp5134.domain.data.Staff;
import com.polyu.comp5134.domain.model.*;
import com.polyu.comp5134.domain.util.SequenceGenerator;
import com.polyu.comp5134.domain.util.StaffUtil;

public class AddStaffDialog extends JDialog implements Observer,ActionListener{
    
    private ELModel model;
    private JFrame frame;
    private StaffView sv;
    private JPanel aPanel = new JPanel(new BorderLayout());
    private List<Staff> staffs;
    private JComboBox comboxSuper =new JComboBox();
    private JComboBox comboxRole =new JComboBox();
    private Vector comboBoxItems;
    private DefaultComboBoxModel cdatamodel;
    private JTextField staffNameText=new JTextField();
    private JTextField staffPasswordText=new JTextField();
    private JButton btnConfirmAddButton = new JButton("Ok");
    private JButton btnCancelButton = new JButton("CANCEL");
    
    public AddStaffDialog(Frame aFrame, StaffView parent, ELModel model) {
        super(aFrame, true);
        sv = parent;
		this.model=model;
		this.model.addObserver(this);
		setTitle("Add Staff");
		initialize();
    }
    
    private void initialize() {
		buildUI();
        btnConfirmAddButton.addActionListener(this);
        btnCancelButton.addActionListener(this);
	}
    
    private void buildUI() {
		JPanel topPanel = new JPanel();
	    topPanel.add(new JLabel("Add Staff"));
	    aPanel.add(topPanel, BorderLayout.NORTH);
	    JPanel centerPanel = new JPanel(new GridLayout(6, 3, 5, 10));
	    for (int i = 0; i < 3; i++) {
	    	centerPanel.add(new JPanel());
	    }
	    centerPanel.add(new JLabel("Name", SwingConstants.RIGHT));
	    staffNameText = new JTextField(20);
	    centerPanel.add(staffNameText);
	    centerPanel.add(new JPanel());
	    centerPanel.add(new JLabel("Password", SwingConstants.RIGHT));
	    centerPanel.add(staffPasswordText);
	    centerPanel.add(new JPanel());
	    centerPanel.add(new JLabel("Supervisor", SwingConstants.RIGHT));
	    comboBoxItems=new Vector();
	    staffs = StaffUtil.getAllSTaff();
	    for(Staff staff : staffs) {
	    	comboBoxItems.add(staff.getStaffName());
	    }
	    cdatamodel = new DefaultComboBoxModel(comboBoxItems);
	    comboxSuper.setModel(cdatamodel);	    
	    centerPanel.add(comboxSuper);
	    centerPanel.add(new JPanel());
	    centerPanel.add(new JLabel("Role", SwingConstants.RIGHT));
	    comboxRole.addItem(Staff.admin);
	    comboxRole.addItem(Staff.staff);
	    centerPanel.add(comboxRole);
	    centerPanel.add(new JPanel());
	    for (int i = 0; i < 3; i++) {
	    	centerPanel.add(new JPanel());
	    }
	    aPanel.add(centerPanel, BorderLayout.CENTER);
	    
        JPanel bottomPanel = new JPanel(new GridLayout(2, 5, 5, 10));
        bottomPanel.add(new JPanel());
        
        bottomPanel.add(btnConfirmAddButton);
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
        btnConfirmAddButton.setActionCommand("AddStaff");
        btnCancelButton.setActionCommand("exitDelView");
	    add(aPanel);
	}
    
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("AddStaff")) {
			String staffName=staffNameText.getText().trim();
			String password = staffPasswordText.getText().trim();
			System.out.println("paassword:"+password);
			String superName = (String)comboxSuper.getSelectedItem();
			String role = (String)comboxRole.getSelectedItem();
			if (staffName.length()==0) {
				JOptionPane.showMessageDialog(null, "Name cannnot be empty", "Add error", JOptionPane.ERROR_MESSAGE);
			}else if(password.length()==0) {
				JOptionPane.showMessageDialog(null, "Password cannot be empty", "Add error", JOptionPane.ERROR_MESSAGE);
			}else if(staffName.equalsIgnoreCase(superName)) {
				JOptionPane.showMessageDialog(null, "Staff and supervisor cannot be same person", "Add error", JOptionPane.ERROR_MESSAGE);
			} else {	
				Staff staff = StaffUtil.findStaffByName(staffName);
				if(staff==null) {
					Staff supervisor = StaffUtil.findStaffByName(superName);
					staff = new Staff(staffName, password,supervisor,role);
					staff.setStaffId(SequenceGenerator.getInstance().getNextStaffId());
					supervisor.addSubordinate(staff);
					model.addStaff(staff);
	                JOptionPane.showMessageDialog(null,
	                        "Staff Added",
	                        "Message",
	                        JOptionPane.PLAIN_MESSAGE);
					
				}else {
					JOptionPane.showMessageDialog(null, "The name is existed", "Add error", JOptionPane.ERROR_MESSAGE);
				}
			}
    	}else if(e.getActionCommand().equals("exitDelView")) {
    		dispose();
    	}	
    }
    
	public void update(Observable src, Object arg){
		staffNameText.setText("");
		staffPasswordText.setText("");
		comboxSuper.removeAllItems();
	    staffs = StaffUtil.getAllSTaff();
	    for(Staff staff : staffs) {
	    	comboxSuper.addItem(new String(staff.getStaffName()));
	    }
	    //System.out.println("staff size:"+staffs.size());
	}
	
	public JFrame getWindow(){
		return this.frame;
	}
	
	public JPanel getPane() {
		return this.aPanel;
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
