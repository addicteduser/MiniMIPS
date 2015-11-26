package dataObjects;

import java.util.ArrayList;

import helper.NumberBuilder;

public class MemoryInstruction {
	private static ArrayList<Instruction> instructionList = new ArrayList<Instruction>();
	private static int iCtr = 0;

	public static void initializeInstructionList() {
		instructionList.clear();
		Instruction tempInstruction;
		for (int i = NumberBuilder.hexStringToIntBuilder("2000"); i < NumberBuilder.hexStringToIntBuilder("3FFF"); i+=4) {
			String address = NumberBuilder.paddedHexStringBuilder(4, i);
			tempInstruction = new Instruction(address);
			instructionList.add(tempInstruction);
		}
	}

	public static void resetCtr() {
		iCtr = 0;
	}

	public static void incrementCtr() {
		iCtr++;
	}

	public static String getAddress(String label) {
		for(int i = 0; i < iCtr; i++) {
			if (instructionList.get(i).getLabel().equalsIgnoreCase(label))
				return instructionList.get(i).getAddress();
		}
		return null;
	}

	/**
	 * @return the instructionList
	 */
	public static ArrayList<Instruction> getInstructionList() {
		return instructionList;
	}

	/**
	 * @param instructionList the instructionList to set
	 */
	public static void setInstructionList(ArrayList<Instruction> instructionList) {
		MemoryInstruction.instructionList = instructionList;
	}

	/**
	 * @return the iCtr
	 */
	public static int getiCtr() {
		return iCtr;
	}

	/**
	 * @param iCtr the iCtr to set
	 */
	public static void setiCtr(int iCtr) {
		MemoryInstruction.iCtr = iCtr;
	}
}
