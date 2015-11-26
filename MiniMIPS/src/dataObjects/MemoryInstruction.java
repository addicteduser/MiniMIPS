package dataObjects;

import java.util.ArrayList;

public class MemoryInstruction {
	private static ArrayList<Instruction> instructionList = new ArrayList<Instruction>();
	private static int iCtr = 0;
	
	public static void initializeInstructionList() {
		instructionList.clear();
		Instruction tempInstruction;
		for (int i = Short.toUnsignedInt(Short.parseShort("2000", 16)); i < Short.toUnsignedInt(Short.parseShort("3FFF", 16)); i+=4) {
			String address = NumberBuilder.paddedHexBuilder(4, i);
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
