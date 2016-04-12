package com.polyu.comp5134.domain.data;

import com.polyu.comp5134.domain.util.SequenceGenerator;
import java.util.List;

public class Staff extends AbstractStaff{
	public final static String director="Director";
	public final static String staff="Staff";
	public final static String admin="Admin";
	
	public Staff (String name, String password, AbstractStaff supervisor, String role) {
		super(name, password, supervisor, role);
	}

	public void addSubordinate(AbstractStaff staff){
		System.out.println("1 addSubordinate: Current user:"+this.getStaffName()+" adding subordinate:"+staff.getStaffName()+" subordinates size"+getSubordinates().size());
		getSubordinates().add(staff);
		System.out.println("2 addSubordinate: Current user:"+this.getStaffName()+" added subordinate:"+staff.getStaffName()+" subordinates size"+getSubordinates().size());
	}
	
	public AbstractStaff findSubordiate(AbstractStaff staff) {
		for(AbstractStaff stafffromList: getSubordinates()){
			if(staff.getStaffId().equals(stafffromList.getStaffId())){
				return stafffromList;
			}
		}
		return null;
	}
	
	public void removeSubordinate(AbstractStaff staff){
		AbstractStaff subordinate =null;
		
		System.out.println("1 removeSubordinate: Current user:"+this.getStaffName()+" removing subordinate:"+staff.getStaffName()+" subordinates size"+getSubordinates().size());
		subordinate=findSubordiate(staff);
		if(subordinate!=null) {
			getSubordinates().remove(subordinate);
		}
		System.out.println("2 removeSubordinate: Current user:"+this.getStaffName()+" removed subordinate:"+subordinate.getStaffName()+" subordinates size"+getSubordinates().size());
	}
	
 	public void updateLeave(Leave eleave, String leaveStatus){
		int index=0;
		index=eleave.getApplicant().findLeaveIndex(eleave);
		eleave.setLeaveStatus(leaveStatus);
		System.out.println("1 updateLeave: array index="+index+" leaveStatus:"+leaveStatus+" applicant:"+eleave.getApplicant().getStaffName()+" size:"+eleave.getApplicant().getLeaveApplication().size()+ " current user:"+this.getStaffName());
		if(index < 0) {
			eleave.getApplicant().getLeaveApplication().add(eleave);
			System.out.println("2 updateLeave: array index="+index+" leaveStatus:"+leaveStatus+" applicant:"+eleave.getApplicant().getStaffName()+" size:"+eleave.getApplicant().getLeaveApplication().size()+ " current user:"+this.getStaffName());
		}else {
			eleave.getApplicant().getLeaveApplication().set(index, eleave);
		}
	}
 	
 	public void removeLeave(int index) {
 		getLeaveApplication().remove(index);
 	}
 	
 	public int findLeaveIndex(Leave leave) {
 		int index=0;
 		
 		for(Leave le : getLeaveApplication()) {
 			System.out.println("1. findLeaveIndex: index:"+index+" leaveID:"+le.getLeaveId());
 			if(le.getLeaveId().equals(leave.getLeaveId())) { 				
 				System.out.println("2. findLeaveIndex: found index:"+index+" leaveID:"+le.getLeaveId());
 				return index;
 			}
 			index++;
 		}
 		return -1;
 	
 	}
 	
	public Leave findLeaveByLeaveId(String leaveId){
		
		for(Leave leave: getLeaveApplication()){
			if(leave.getLeaveId().equals(leaveId)){
				return leave;
			}
		}
		
		return null;
	}
	
	public void processStaffLeave(Leave staffLeave, String leaveStatus) {	
		int index=0;
		System.out.println("1 current staff:"+this.getStaffName()+" no. of leaves:"+getLeaveApplication().size());
		//If I am the approver of the leave request
		if(staffLeave.getLeaveStatus().equals(Leave.processing) && staffLeave.getApprover().getStaffId().equals(this.getStaffId())) {
			if(leaveStatus.equals(Leave.accepted)) {
				// I accepted the leave request, then I pass it to my supervisor for further endorsement
				if(this.getSupervisor()!=null) {
				  staffLeave.setApprover(this.getSupervisor());
				  ApplyStaffLeave(staffLeave);
				}else { //I am the director, notice the staff of the successful approval directly.
					staffLeave.getApplicant().updateLeave(staffLeave, Leave.accepted);
					staffLeave.getApplicant().setLeaveNotification(true);
				}
				index = findLeaveIndex(staffLeave);
				this.getLeaveApplication().remove(index);
				System.out.println("2 current staff:"+this.getStaffName()+" no. of leaves:"+this.getLeaveApplication().size()+" index removed:"+index+" leaveID removed:"+staffLeave.getLeaveId());
			}else { //If I decline the leave request, notice the staff of the unsuccessful approval directly. No need to further pass up.
				staffLeave.getApplicant().updateLeave(staffLeave, Leave.declined);
				staffLeave.getApplicant().setLeaveNotification(true);
				index = findLeaveIndex(staffLeave);
				this.getLeaveApplication().remove(index);
				System.out.println("3 current staff:"+this.getStaffName()+" no. of leaves:"+this.getLeaveApplication().size()+" index removed:"+index+" leaveID removed:"+staffLeave.getLeaveId());
			}
			//System.out.println("4 current staff:"+this.getStaffName()+" no. of leaves:"+getLeaveApplication().size());
		}
	}
	
	public void ApplyStaffLeave(Leave staffLeave) {
					
		if(staffLeave.getApprover().getStaffId().equals(this.getStaffId())) {
			System.out.println("5 ApplyStaffLeave Approver Id:"+staffLeave.getApprover().getStaffId()+" Current staff id="+this.getStaffId());
			this.getLeaveApplication().add(staffLeave);
			System.out.println("6  ApplyStaffLeave current staff:"+this.getStaffName()+" no. of leaves:"+this.getLeaveApplication().size());
		}else { 
			System.out.println("7 ApplyStaffLeave Approver Id:"+staffLeave.getApprover().getStaffId()+" Current staff id="+this.getStaffId());
			this.getSupervisor().ApplyStaffLeave(staffLeave); //pass the leave application to supervisor.
		}
	}
	
	public void MoveLeavestoSupervisor() {
		int index=0;
		for(Leave leave: this.getLeaveApplication()) {
			if(leave.getLeaveStatus().equals(Leave.processing) && leave.getApprover().getStaffId().equals(this.getStaffId())) {
				leave.setApprover(this.getSupervisor());
				System.out.println("1  MoveLeavestoSupervisor current staff:"+this.getStaffName()+" Supervisor:"+this.getSupervisor().getStaffName()+" no. of current staff's leaves:"+this.getLeaveApplication().size()+" no. of supervisor's leaves:"+this.getSupervisor().getLeaveApplication().size());
				this.getSupervisor().getLeaveApplication().add(leave);
				System.out.println("2  MoveLeavestoSupervisor current staff:"+this.getStaffName()+" Supervisor:"+this.getSupervisor().getStaffName()+" no. of current staff's leaves:"+this.getLeaveApplication().size()+" no. of supervisor's leaves:"+this.getSupervisor().getLeaveApplication().size());
			}
			index++;
		}
	}
	
	public void AssignChildstoSupervisor() {
		List<AbstractStaff> subordinates = this.getSubordinates();
		for(AbstractStaff child : subordinates) {
			System.out.println("1  AssignChildstoSupervisor: current staff:"+this.getStaffName()+" Child name:"+child.getStaffName()+" Child Supervisor:"+child.getSupervisor().getStaffName()+" supervisor subordinates size:"+this.getSupervisor().getSubordinates().size());
			child.setSupervisor(this.getSupervisor());
			this.getSupervisor().addSubordinate(child);
			System.out.println("2  AssignChildstoSupervisor: current staff:"+this.getStaffName()+" Child name:"+child.getStaffName()+" Child Supervisor:"+child.getSupervisor().getStaffName()+" supervisor subordinates size:"+this.getSupervisor().getSubordinates().size());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
}
