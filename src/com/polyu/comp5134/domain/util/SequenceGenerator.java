package com.polyu.comp5134.domain.util;

public class SequenceGenerator {
	
	public static SequenceGenerator instance;
	private int nextSeq = 0;
	
	public static SequenceGenerator getInstance() {
		if(instance==null) {
			instance= new SequenceGenerator();
		}
		return instance;
	}
	
	public int getNextSeq () {
		return nextSeq++;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public String getNextStaffId() {
		int seq=SequenceGenerator.getInstance().getNextSeq();
		
		return String.format("S%04d",seq);
	}
	
	public String getNextLeaveId() {
		int seq=SequenceGenerator.getInstance().getNextSeq();
		
		return String.format("L%04d",seq);
	}
}
