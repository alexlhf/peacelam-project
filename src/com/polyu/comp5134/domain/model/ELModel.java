package com.polyu.comp5134.domain.model;

import com.polyu.comp5134.domain.data.*;
import com.polyu.comp5134.domain.util.StaffUtil;

import java.util.Observer;
import java.util.Observable;
import java.util.ArrayList;
import java.util.Iterator;

public class ELModel{

	private ArrayList listeners;
	
	public ELModel (){ 
		listeners = new ArrayList();
	}
	
	public void addObserver (Observer o) {
		listeners.add(o);
	}
	
	public void removeObserver (Observer o) {
		listeners.remove(o);
	}
	
	public void notifyObservers() {
		Iterator i = listeners.iterator();
		while (i.hasNext()) {
			Observer o = (Observer) i.next();
			o.update(null, null);
		}
	}
	
	public void addStaff(Staff staff) {
		StaffUtil.createStaff(staff);
		notifyObservers();
	}
	
	public void delStaff(String staffId) {
		StaffUtil.deleteStaffByStaffId(staffId);
		notifyObservers();
	}
	
	public void addLeave(AbstractStaff staff, Leave leave) {
		staff.ApplyStaffLeave(leave);
		notifyObservers();
	}
	
	public void updateLeave(AbstractStaff staff, Leave leave, String leaveStatus) {
		staff.processStaffLeave(leave, leaveStatus);
		notifyObservers();
	}
	
	public void delLeave(String leaveId) {
		notifyObservers();
	}
	
	public void refreshModel() {
		notifyObservers();
	}
	
	
}
