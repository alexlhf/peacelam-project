package com.polyu.comp5134.ui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import com.polyu.comp5134.domain.data.AbstractStaff.StaffRole;
import com.polyu.comp5134.domain.data.Leave;
import com.polyu.comp5134.domain.data.AbstractStaff;
import com.polyu.comp5134.domain.data.Staff;
import com.polyu.comp5134.domain.model.*;
import com.polyu.comp5134.domain.util.SequenceGenerator;
import com.polyu.comp5134.domain.util.StaffUtil;

public class AddLeaveDialog extends JDialog implements Observer,ActionListener{
    
    private ELModel model;
    private JFrame frame;
    private ApplyLeaveView sv;
    private JPanel aPanel = new JPanel(new BorderLayout());
    private List<Staff> staffs;
    private JComboBox comboxReason =new JComboBox();
    private Vector comboBoxItems;
    private DefaultComboBoxModel cdatamodel;
    private JTextField txtTo=new JTextField();
    private JTextField txtFrom=new JTextField();
    private JButton btnConfirmAddButton = new JButton("Submit");
    private JButton btnCancelButton = new JButton("CANCEL");
    
    public AddLeaveDialog(Frame aFrame, ApplyLeaveView parent, ELModel model) {
        super(aFrame, true);
        sv = parent;
		this.model=model;
		this.model.addObserver(this);
		setTitle("Create Leave");
		initialize();
    }
    
    private void initialize() {
		buildUI();
        btnConfirmAddButton.addActionListener(this);
        btnCancelButton.addActionListener(this);
	}
    
    private void buildUI() {
		JPanel topPanel = new JPanel();
	    topPanel.add(new JLabel("Create Leave"));
	    aPanel.add(topPanel, BorderLayout.NORTH);
	    JPanel centerPanel = new JPanel(new GridLayout(5, 3, 5, 10));
	    for (int i = 0; i < 3; i++) {
	    	centerPanel.add(new JPanel());
	    }
		txtFrom.setColumns(10);
		txtFrom.setToolTipText("Date Format:25-12-2016");
		
		txtTo.setColumns(10);
		txtTo.setToolTipText("Date Format:31-12-2016");
		
	    centerPanel.add(new JLabel("From (DD-MM-YYYY)", SwingConstants.RIGHT));
	    centerPanel.add(txtFrom);
	    centerPanel.add(new JPanel());
	    centerPanel.add(new JLabel("To (DD-MM-YYYY)", SwingConstants.RIGHT));
	    centerPanel.add(txtTo);
	    centerPanel.add(new JPanel());
	    centerPanel.add(new JLabel("Reason", SwingConstants.RIGHT));
	    comboBoxItems=new Vector();
	    comboBoxItems.add(Leave.Annual);
	    comboBoxItems.add(Leave.Sick);
	    comboBoxItems.add(Leave.Other);
	    cdatamodel = new DefaultComboBoxModel(comboBoxItems);
	    comboxReason.setModel(cdatamodel);	    
	    centerPanel.add(comboxReason);
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
        btnConfirmAddButton.setActionCommand("AddSLeaveCmd");
        btnCancelButton.setActionCommand("exitViewCmd");
	    add(aPanel);
	}
    
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("AddSLeaveCmd")) {
			if(txtFrom.getText().trim().isEmpty() || txtFrom.getText().contains(" ")){
				System.out.println("From date can't be empty");
				JOptionPane.showMessageDialog(null, "From date must be filled", "Add error", JOptionPane.ERROR_MESSAGE); 
			}
			else if(txtTo.getText().trim().isEmpty() || txtTo.getText().contains(" "))
				JOptionPane.showMessageDialog(null, "To date must be filled", "Add error", JOptionPane.ERROR_MESSAGE); 
			else{
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date from=null,to=null;
				try {
					System.out.println("From:"+txtFrom.getText()+" To:"+txtTo.getText());
					from = formatter.parse(txtFrom.getText().trim());
					to = formatter.parse(txtTo.getText().trim());

					String reason = (String)comboxReason.getSelectedItem();
					Leave newLeave = new Leave();
					newLeave.setApplicant(StaffUtil.loggonUser);
					newLeave.setStartDate(from);
					newLeave.setEndDate(to);
					newLeave.setApprover(StaffUtil.loggonUser.getSupervisor());
					newLeave.setLeaveReason(reason);
					newLeave.setLeaveId(SequenceGenerator.getInstance().getNextLeaveId());
					newLeave.setLeaveStatus(Leave.processing);
					model.addLeave(StaffUtil.loggonUser, newLeave);
	                JOptionPane.showMessageDialog(null,
	                        "Your leave application is submitted to your supervisor",
	                        "Message",
	                        JOptionPane.PLAIN_MESSAGE);
				}catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
    	}else if(e.getActionCommand().equals("exitViewCmd")) {
    		dispose();
    	}	
    }
    
	public void update(Observable src, Object arg){
		txtFrom.setText("");
		txtTo.setText("");
		comboxReason.setSelectedIndex(0);
	}
	
	public JFrame getWindow(){
		return this.frame;
	}
	
	public JPanel getPane() {
		return this.aPanel;
	}
 
    public static void main(String[] args) {
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date from=null,to=null;
		try {
			from = formatter.parse("21-06-2016");
			to = formatter.parse("22-06-2016");
			String fromTxt = formatter.format(from);
			String toTxt = formatter.format(to);
			System.out.println("From:"+fromTxt+" To:"+toTxt);
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}	
    }
}
