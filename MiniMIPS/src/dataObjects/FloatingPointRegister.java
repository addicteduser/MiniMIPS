package dataObjects;

import java.util.ArrayList;

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
