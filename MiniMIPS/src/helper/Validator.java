package helper;

import java.lang.management.MemoryType;
import java.util.ArrayList;

import constants.INSTRUCTIONS;
import dataObjects.MemoryData;
import dataObjects.MemoryInstruction;

public class Validator {
	/**
	 * Checks whether the label is already existing.
	 * @param label
	 * @return TRUE if label exists, FALSE if label does NOT exists.    	 
	 */
	public static boolean doesLabelExist(String label) throws NullPointerException {
		for (int i = 0; i < MemoryInstruction.getInstructionList().size(); i++)
			if (!label.isEmpty())
				if (MemoryInstruction.getInstructionList().get(i).getLabel().matches(label))
					return true;

		return false;
	}

	/**
	 * Checks whether the input register is a valid general register or not.
	 * @param register
	 * @return TRUE if register input is valid, FALSE if not.
	 */
	public static boolean isGeneralRegisterValid(String register) throws NumberFormatException {
		int regNumber = Integer.parseInt(register.replace("R", "").replace("r", ""));
		if (regNumber >= 0 && regNumber <= 31)
			return true;
		else
			return false;
	}

	/**
	 * Checks whether the input register is valid floating point register or not.
	 * @param register
	 * @return TRUE if register input is valid, FALSE if not.
	 */
	public static boolean isFloatRegisterValid(String register) throws NumberFormatException {
		int regNumber = Integer.parseInt(register.replace("F", "").replace("f", ""));
		if (regNumber >= 0 && regNumber <= 31)
			return true;
		else
			return false;
	}

	/**
	 * Checks if the input immediate/offest is valid.
	 * @param imm
	 * @return TRUE if immediate/offset is valid, FALSE if not.
	 */
	public static boolean isImmediateValid(String imm) {
		try {
			int x = Long.compareUnsigned(Long.decode(imm), Long.decode("0xFFFF"));
			if (x > 0)
				return false;
		} catch(NumberFormatException e) {
			return false;
		}

		return true;
	}
	
	/**
	 * Checks whether the data varName already exists.
	 * @param varName
	 * @return TRUE if varName exists, FALSE if not.
	 */
	public static boolean doesVarNameExist(String varName) {
		for (int i = 0; i < MemoryData.getDataList().size(); i++)
			if (!varName.isEmpty())
				if (MemoryData.getDataList().get(i).getVarName().matches(varName))
					return true;

		return false;
	}

	/**
	 * Checks if value is within the min and max bound of the directive.
	 * @param min minimum value of the directive
	 * @param max maximum value of the directive
	 * @param value value to compare to min and max
	 * @return TRUE if the value is within the min and max bound of the directive
	 */
	public static boolean isValueValid(long min, long max, long value) {
		if (min <= value && value <= max)
			return true;
		else
			return false;
	}
	
	public static boolean validateInput() {
		ArrayList<String> labels = new ArrayList<String>();
		ArrayList<String> varNames = new ArrayList<String>();
		
		// get all varname
		for(int i = 0; i < MemoryData.getdCtr(); i++) {
			String tempVarName = MemoryData.getDataList().get(i).getVarName();
			if(!tempVarName.isEmpty())
				varNames.add(tempVarName);
		}
		
		// get all label
		for(int i = 0; i < MemoryInstruction.getiCtr(); i++) {
			String tempLabel = MemoryInstruction.getInstructionList().get(i).getLabel();
			if(!tempLabel.isEmpty())
				labels.add(tempLabel);
		}
		
		for(int i = 0; i < MemoryInstruction.getiCtr(); i++) {
			INSTRUCTIONS iName = MemoryInstruction.getInstructionList().get(i).getInstructionName();
			String temp = "";

			if(iName.toString().matches("J")) { // v1
				temp = MemoryInstruction.getInstructionList().get(i).getV1();
				return checkValAgainstList(temp, labels);
			} else if(iName.toString().matches("LW|LWU|SW|LS|SS")) { // v2
				temp = MemoryInstruction.getInstructionList().get(i).getV2();
				return checkValAgainstList(temp, varNames);
			} else if(iName.toString().matches("BEQ")) { // v3
				temp = MemoryInstruction.getInstructionList().get(i).getV3();
				return checkValAgainstList(temp, labels);
			}
		}
		
		return true;
	}
	
	private static boolean checkValAgainstList(String val, ArrayList<String> list) {
		if(list.isEmpty())
			return false;
		
		for(String l : list)
			if(!l.contains(val))
				return false;
		
		return true;
	}
}
