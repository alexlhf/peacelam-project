package com.polyu.comp5134.domain.data;

import java.util.Date;

import com.polyu.comp5134.domain.util.SequenceGenerator;

public class Leave implements Cloneable{
	private String leaveId;
	private Date startDate;
	private Date endDate;
	private AbstractStaff approver;
	private String staffId;
	private AbstractStaff applicant;
	private String leaveStatus;
	private String leaveReason;
	public final static String accepted ="Accepted";
	public final static String processing ="In progress";
	public final static String declined ="Declined";
	public final static String Annual = "Annual";
	public final static String Sick = "Sick";			
	public final static String Other = "Other";
	
	public Leave () {
		
	}
	public Object clone()throws CloneNotSupportedException{  
		return super.clone(); 
	}
	
	public String getLeaveReason() {
		return this.leaveReason;
	}
	
	public void setLeaveReason(String leaveReason) {
		this.leaveReason=leaveReason;
	}
	
	public String getLeaveId() {
		return this.leaveId;
	}
	
	public void setLeaveId(String leaveId) {
		this.leaveId=leaveId;
	}
	
	public String getLeaveStatus() {
		return this.leaveStatus;
	}
	
	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus=leaveStatus;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate=startDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate=endDate;
	}
	
	public AbstractStaff getApprover() {
		return this.approver;
	}

	public void setApprover(AbstractStaff approver) {
		this.approver=approver;
	}
	
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId=staffId;
	}
	
	public AbstractStaff getApplicant() {
		return this.applicant;
	}

	public void setApplicant(AbstractStaff applicant) {
		this.applicant=applicant;
	}	
	
	public String getNextLeaveId() {
		return String.format("L%04d",SequenceGenerator.getInstance().getNextSeq());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
