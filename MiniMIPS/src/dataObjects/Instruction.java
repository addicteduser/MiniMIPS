package dataObjects;

import java.util.ArrayList;
import java.util.EnumMap;

import constants.InstructionTypes;
import constants.Instructions;

public class Instruction {
	private static ArrayList<Instruction> instructionList = new ArrayList<Instruction>();
	private static EnumMap<Instructions, String> instructionCode;
	private static EnumMap<InstructionTypes, String> instructionFormat;
	private InstructionTypes instructionType;
	private Instructions instruction;
	private String rd;
	private String rs;
	private String rt;
	private String imm;
	
	public static void createMappings() {
		instructionCode = new EnumMap<Instructions, String>(Instructions.class);
		instructionFormat = new EnumMap<InstructionTypes, String>(InstructionTypes.class);
		
		// R-type Instructions
		instructionFormat.put(InstructionTypes.R, "0,RS,RT,RD,0,iCODE");
		instructionCode.put(Instructions.DADDU, "R,45");
		instructionCode.put(Instructions.DMULT, "R,28");
		instructionCode.put(Instructions.OR, "R,37");
		instructionCode.put(Instructions.SLT, "R,42");
		
		// R-type (Shift)
		instructionFormat.put(InstructionTypes.RS, "0,0,RT,RD,SHF,iCODE");
		instructionCode.put(Instructions.DSLL, "RS,56");
		
		// Extended R-type Instructions
		instructionFormat.put(InstructionTypes.ER, "17,16,RT,RS,RD,iCODE");
		instructionCode.put(Instructions.ADDS, "ER,0");
		instructionCode.put(Instructions.MULS, "ER,2");
		
		// I-type Instructions
		instructionFormat.put(InstructionTypes.I, "iCODE,RS,RT,IMM");
		instructionCode.put(Instructions.BEQ, "I,4");
		instructionCode.put(Instructions.LW, "I,35");
		instructionCode.put(Instructions.LWU, "I,39");
		instructionCode.put(Instructions.SW, "I,43");
		instructionCode.put(Instructions.ANDI, "I,12");
		instructionCode.put(Instructions.DADDIU, "I,25");
		instructionCode.put(Instructions.LS, "I,49");
		instructionCode.put(Instructions.SS, "I,57");
		
		// J-type Instructions
		instructionFormat.put(InstructionTypes.J, "iCODE,IMM");
		instructionCode.put(Instructions.J, "J,2");
	}
}
