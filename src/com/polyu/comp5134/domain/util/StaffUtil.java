package com.polyu.comp5134.domain.util;

import java.util.ArrayList;
import java.util.List;
import com.polyu.comp5134.domain.data.*;

public class StaffUtil {
	public static Staff loggonUser;
	public static List<Staff> staffs = new ArrayList<Staff>();
	
	public static void createStaff(Staff staff){
		staffs.add(staff);
	}
	
	public static void updateStaff(Staff staff){
		for(Staff s: staffs){
			if(s.getStaffName().equalsIgnoreCase(staff.getStaffName())){
				s=staff;
			}
		}
	}
	
	public static void deleteStaff(String name){
		Staff staff = StaffUtil.findStaffByName(name);
		if(staff != null)
			staffs.remove(staff);
	}
	
	public static void deleteStaffByStaffId(String staffId){
		Staff staff = StaffUtil.findStaffByStaffId(staffId);
		if(staff != null) {
			staffs.remove(staff);
			System.out.println("deleted, size:"+staffs.size());
		}
	}
	
	public static Staff validate(String username, String password){
		boolean isValidated = false;
		for(Staff s: staffs){
			if(s.getStaffName().equals(username) && s.getPassword().equals(password)){
				isValidated = true;
				return s;
			}
		}
		return null;
	}
	
	public static Staff findStaffByName(String name){
		for(Staff s: staffs){
			if(s.getStaffName().equals(name)){
				return s;
			}
		}
		
		return null;
	}
	
	public static Staff findStaffByStaffId(String staffId){
		for(Staff s: staffs){
			if(s.getStaffId().equals(staffId)){
				return s;
			}
		}
		
		return null;
	}
	
	public static Staff findStaff(Staff staff){
		for(Staff s: staffs){
			if(s.equals(staff)){
				return s;
			}
		}
		
		return null;
	}
	
	public static List<Staff> getSubordinates(Staff staff){
		List<Staff> subordinates = new ArrayList<Staff>();
		for(Staff s: staffs){
			if(s.getSupervisor()!=null && s.getSupervisor().getStaffName().equals(staff.getStaffName())){
				subordinates.add(s);
			}
		}
		
		return subordinates;
	}
	
	public static List<Staff> getAllSTaff() {
		return staffs;
	}
}
