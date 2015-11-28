package dataObjects;

import java.util.ArrayList;

import helper.Validator;

public class FloatingPointRegister {
	private static ArrayList<Register> fpr = new ArrayList<Register>();

	public static void initializeFPR() {
		fpr.clear();
		Register tempReg;
		for(int i = 0; i <= 31; i++) {
			tempReg = new Register("F"+i,0);
			fpr.add(tempReg);
		}
	}
	
	/**
	 * Updates the fpr list if the newValue is a valid register value.
	 * @param row
	 * @param newVal
	 * @return TRUE if the newValue is valid, FALSE if not.
	 */
	public static boolean updateRegister(int row, int newVal) {
		if (Validator.isValueValid(Long.MIN_VALUE, Long.MAX_VALUE, newVal)) {
			fpr.get(row).setRegValue(newVal);
			return true;
		} else
			return false;
	}

	/**
	 * @return the fpr
	 */
	public static ArrayList<Register> getFpr() {
		return fpr;
	}

	/**
	 * @param fpr the fpr to set
	 */
	public static void setFpr(ArrayList<Register> fpr) {
		FloatingPointRegister.fpr = fpr;
	}
}
