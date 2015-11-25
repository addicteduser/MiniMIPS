package dataObjects;

import java.util.ArrayList;

public class MemoryInstruction {
	private static ArrayList<Instruction> instructionList = new ArrayList<Instruction>();
	
	public static void initializeInstructionList() {
		instructionList.clear();
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
}
