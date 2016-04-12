package com.polyu.comp5134.ui.view;

import com.polyu.comp5134.domain.model.*;
import com.polyu.comp5134.domain.util.SequenceGenerator;
import com.polyu.comp5134.domain.util.StaffUtil;
import com.polyu.comp5134.domain.data.*;

public class MVCDriver {
	
	private StaffView staffView;
	private ELModel model = new ELModel();
	
	public MVCDriver() {
		Staff staff = new Staff("director","123456",null,Staff.director);
		staff.setStaffId(SequenceGenerator.getInstance().getNextStaffId());
		StaffUtil.createStaff(staff);
		staff = new Staff("Mary","123456",staff,Staff.admin);
		staff.setStaffId(SequenceGenerator.getInstance().getNextStaffId());
		staff.getSupervisor().addSubordinate(staff);
		StaffUtil.createStaff(staff);
		staff = new Staff("Peter","123456",staff,Staff.staff);
		staff.setStaffId(SequenceGenerator.getInstance().getNextStaffId());
		staff.getSupervisor().addSubordinate(staff);
		StaffUtil.createStaff(staff);
		
	}
	
	public ELModel getModel() {
		return this.model;
	}
	
	public  StaffView getStaffView() {
		return this.staffView;
	}
	
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MVCDriver driver = new MVCDriver();
            	MainView window = new MainView(driver.getModel());
                window.setOpaque(true); //content panes must be opaque
                window.getFrame().setContentPane(window);
         
                //Display the window.
                window.getFrame().pack();
                window.getFrame().setVisible(true);
            }
        });	
	}
	
}
