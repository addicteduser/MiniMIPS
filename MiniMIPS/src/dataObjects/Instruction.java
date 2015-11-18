package dataObjects;

import java.util.ArrayList;
import java.util.EnumMap;

import constants.INSTRUCTIONTYPES;
import constants.INSTRUCTIONS;

public class Instruction {
	private static ArrayList<Instruction> instructionList = new ArrayList<Instruction>();
	private static EnumMap<INSTRUCTIONS, String> instructionCode;
	private static EnumMap<INSTRUCTIONTYPES, String> instructionFormat;
	//private INSTRUCTIONTYPES instructionType;
	private INSTRUCTIONS instructionName;
	private String label;
	private String v1;
	private String v2;
	private String v3;
	
	/**
	 * Creates the mappings of the instructions to their Opcodes as well as the instruction type to its format.
	 */
	public static void createMappings() {
		instructionCode = new EnumMap<INSTRUCTIONS, String>(INSTRUCTIONS.class);
		instructionFormat = new EnumMap<INSTRUCTIONTYPES, String>(INSTRUCTIONTYPES.class);
		
		// R-type Instructions
		instructionFormat.put(INSTRUCTIONTYPES.R, "0,RS,RT,RD,0,iCODE");
		instructionCode.put(INSTRUCTIONS.DADDU, "R,45"); // DADDU RD,RS,RT
		instructionCode.put(INSTRUCTIONS.DMULT, "R,28"); // DMULT RS,RT
		instructionCode.put(INSTRUCTIONS.OR, "R,37"); // OR RD,RS,RT
		instructionCode.put(INSTRUCTIONS.SLT, "R,42"); // SLT RD,RS,RT
		
		// R-type (Shift)
		instructionFormat.put(INSTRUCTIONTYPES.RS, "0,0,RS,RD,SHF,iCODE");
		instructionCode.put(INSTRUCTIONS.DSLL, "RS,56"); // DSLL RD,RS,SHF(IMM)
		
		// Extended R-type Instructions
		instructionFormat.put(INSTRUCTIONTYPES.ER, "17,16,RT,RS,RD,iCODE");
		instructionCode.put(INSTRUCTIONS.ADDS, "ER,0"); // ADD.S RD,RS,RT
		instructionCode.put(INSTRUCTIONS.MULS, "ER,2"); // MUL.S RD,RS,RT
		
		// I-type Instructions
		instructionFormat.put(INSTRUCTIONTYPES.I, "iCODE,RS,RT,IMM");
		instructionCode.put(INSTRUCTIONS.BEQ, "I,4"); // BEQ RS,RT,IMM
		instructionCode.put(INSTRUCTIONS.LW, "I,35"); // LW RT,IMM(RS)
		instructionCode.put(INSTRUCTIONS.LWU, "I,39"); // LWU RT,IMM(RS)
		instructionCode.put(INSTRUCTIONS.SW, "I,43"); // SW RT,IMM(RS)
		instructionCode.put(INSTRUCTIONS.ANDI, "I,12"); // ANDI RT,RS,IMM
		instructionCode.put(INSTRUCTIONS.DADDIU, "I,25"); // DADDIU RT,RS,IMM
		instructionCode.put(INSTRUCTIONS.LS, "I,49"); // L.S RT,IMM(RS)
		instructionCode.put(INSTRUCTIONS.SS, "I,57"); // S.S RT,IMM(RS)
		
		// J-type Instructions
		instructionFormat.put(INSTRUCTIONTYPES.J, "iCODE,IMM");
		instructionCode.put(INSTRUCTIONS.J, "J,2"); // J Label
	}
}