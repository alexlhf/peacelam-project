package com.polyu.comp5134.domain.data;

import java.util.ArrayList;
import java.util.List;
import com.polyu.comp5134.domain.util.StaffUtil;

public abstract class AbstractStaff {
	private String staffName;
	private String staffId;
	private String password;
	private String staffRole;
	private AbstractStaff supervisor;
	private List<AbstractStaff> subordinates = new ArrayList<AbstractStaff>();
	private List<Leave> leaveApplications = new ArrayList<Leave>();
	private boolean leaveNotifcation=false;
	private boolean pendingRequest=false;
	
	public enum StaffRole {
		Director,
		Admin,
		Staff
	}
	public AbstractStaff (String name, String password, AbstractStaff supervisor, String role) {
		this.staffName=name;
		this.password=password;
		this.supervisor=supervisor;
		this.staffRole=role;
	}
	
	public void setStaffName(String staffName) {
		this.staffName=staffName;
	}
	
	public String getStaffName() {
		return this.staffName;
	}
	
	public boolean getLeaveNotification() {
		return this.leaveNotifcation;
	}
	
	public void setLeaveNotification(boolean leaveNotifcation) {
		this.leaveNotifcation=leaveNotifcation;
	}

	public void setStaffId(String staffId) {
		this.staffId=staffId;
	}
	
	public String getStaffId() {
		return this.staffId;
	}

	public void setPassword(String password) {
		this.password=password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setRole(String staffRole) {
		this.staffRole=staffRole;
	}
	
	public String getStaffRole() {
		return this.staffRole;
	}
	
	public void setSupervisor(AbstractStaff supervisor) {
		this.supervisor=supervisor;
	}
	
	public AbstractStaff getSupervisor() {
		return this.supervisor;
	}

	public List<AbstractStaff> getSubordinates() {
		return this.subordinates;
	}
	
	public List<Leave> getLeaveApplication() {
		return this.leaveApplications;
	}
	
	public abstract void addSubordinate(AbstractStaff staff);
	public abstract void removeSubordinate(AbstractStaff staff);
	public abstract void processStaffLeave(Leave staffLeave, String leaveStatus);
	public abstract void ApplyStaffLeave(Leave staffLeave);
	public abstract void updateLeave(Leave eleave, String leaveStatus);
	public abstract int findLeaveIndex(Leave leave);
	public abstract void removeLeave(int index);
	
}
