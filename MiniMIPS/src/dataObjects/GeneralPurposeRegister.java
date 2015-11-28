package dataObjects;

import java.util.ArrayList;

import helper.Validator;

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
	 * Updates the gpr list if the newValue is a valid register value.
	 * @param row
	 * @param newVal
	 * @return TRUE if the newValue is valid, FALSE if not.
	 */
	public static boolean updateRegister(int row, long newVal) {
		if (Validator.isValueValid(Long.MIN_VALUE, Long.MAX_VALUE, newVal)) {
			gpr.get(row).setRegValue(newVal);
			return true;
		} else
			return false;
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
