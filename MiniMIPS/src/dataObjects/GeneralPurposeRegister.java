package dataObjects;

import java.util.ArrayList;

public class GeneralPurposeRegister {
	private static ArrayList<Register> gpr = new ArrayList<Register>();
	
	public static void initializeGPR() {
		gpr.clear();
		Register tempReg;
		
		for(int i = 0; i <= 31; i++) {
			tempReg = new Register("R"+i,0);
			gpr.add(tempReg);
		}
		
		tempReg = new Register("HI",0);
		gpr.add(tempReg);
		tempReg = new Register("LO",0);
		gpr.add(tempReg);
	}

	/**
	 * @return the gpr
	 */
	public static ArrayList<Register> getGpr() {
		return gpr;
	}

	/**
	 * @param gpr the gpr to set
	 */
	public static void setGpr(ArrayList<Register> gpr) {
		GeneralPurposeRegister.gpr = gpr;
	}
}
