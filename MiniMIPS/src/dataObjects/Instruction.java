package dataObjects;

import java.util.ArrayList;
import java.util.EnumMap;

import constants.INSTRUCTIONTYPES;
import constants.INSTRUCTIONS;

public class Instruction {
	private static ArrayList<Instruction> instructionList = new ArrayList<Instruction>();
	private static EnumMap<INSTRUCTIONS, String> instructionCode;
	private static EnumMap<INSTRUCTIONTYPES, String> instructionFormat;
	private INSTRUCTIONTYPES instructionType;
	private INSTRUCTIONS instructionName;
	private String label;
	private String rd;
	private String rs;
	private String rt;
	private String imm;
	
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
		instructionFormat.put(INSTRUCTIONTYPES.RS, "0,0,RT,RD,SHF,iCODE");
		instructionCode.put(INSTRUCTIONS.DSLL, "RS,56"); // DSLL RD,RS,SHF
		
		// Extended R-type Instructions
		instructionFormat.put(INSTRUCTIONTYPES.ER, "17,16,RT,RS,RD,iCODE");
		instructionCode.put(INSTRUCTIONS.ADDS, "ER,0"); // ADD.S FD,FS,FT
		instructionCode.put(INSTRUCTIONS.MULS, "ER,2"); // MUL.S FD,FS,FT
		
		// I-type Instructions
		instructionFormat.put(INSTRUCTIONTYPES.I, "iCODE,RS,RT,IMM");
		instructionCode.put(INSTRUCTIONS.BEQ, "I,4"); // BEQ RS,RT,Offset
		instructionCode.put(INSTRUCTIONS.LW, "I,35"); // LW RD, Offset(RS)
		instructionCode.put(INSTRUCTIONS.LWU, "I,39"); // LWU RD, Offset(RS)
		instructionCode.put(INSTRUCTIONS.SW, "I,43"); // SW RT, Offset(RS)
		instructionCode.put(INSTRUCTIONS.ANDI, "I,12"); // ANDI RD,RS,IMM
		instructionCode.put(INSTRUCTIONS.DADDIU, "I,25"); // DADDIU RD,RS,IMM
		instructionCode.put(INSTRUCTIONS.LS, "I,49"); // L.S FD, Offset(RS)
		instructionCode.put(INSTRUCTIONS.SS, "I,57"); // S.S FT, Offset(RS)
		
		// J-type Instructions
		instructionFormat.put(INSTRUCTIONTYPES.J, "iCODE,IMM");
		instructionCode.put(INSTRUCTIONS.J, "J,2");
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
		Instruction.instructionList = instructionList;
	}

	/**
	 * @return the instructionCode
	 */
	public static EnumMap<INSTRUCTIONS, String> getInstructionCode() {
		return instructionCode;
	}

	/**
	 * @param instructionCode the instructionCode to set
	 */
	public static void setInstructionCode(EnumMap<INSTRUCTIONS, String> instructionCode) {
		Instruction.instructionCode = instructionCode;
	}

	/**
	 * @return the instructionFormat
	 */
	public static EnumMap<INSTRUCTIONTYPES, String> getInstructionFormat() {
		return instructionFormat;
	}

	/**
	 * @param instructionFormat the instructionFormat to set
	 */
	public static void setInstructionFormat(EnumMap<INSTRUCTIONTYPES, String> instructionFormat) {
		Instruction.instructionFormat = instructionFormat;
	}

	/**
	 * @return the instructionType
	 */
	public INSTRUCTIONTYPES getInstructionType() {
		return instructionType;
	}

	/**
	 * @param instructionType the instructionType to set
	 */
	public void setInstructionType(INSTRUCTIONTYPES instructionType) {
		this.instructionType = instructionType;
	}

	/**
	 * @return the instructionName
	 */
	public INSTRUCTIONS getInstructionName() {
		return instructionName;
	}

	/**
	 * @param instructionName the instructionName to set
	 */
	public void setInstructionName(INSTRUCTIONS instructionName) {
		this.instructionName = instructionName;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the rd
	 */
	public String getRd() {
		return rd;
	}

	/**
	 * @param rd the rd to set
	 */
	public void setRd(String rd) {
		this.rd = rd;
	}

	/**
	 * @return the rs
	 */
	public String getRs() {
		return rs;
	}

	/**
	 * @param rs the rs to set
	 */
	public void setRs(String rs) {
		this.rs = rs;
	}

	/**
	 * @return the rt
	 */
	public String getRt() {
		return rt;
	}

	/**
	 * @param rt the rt to set
	 */
	public void setRt(String rt) {
		this.rt = rt;
	}

	/**
	 * @return the imm
	 */
	public String getImm() {
		return imm;
	}

	/**
	 * @param imm the imm to set
	 */
	public void setImm(String imm) {
		this.imm = imm;
	}

	
	
}
