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
	private Instructions instructionName;
	private String label;
	private String rd;
	private String rs;
	private String rt;
	private String imm;
	
	/**
	 * Creates the mappings of the instructions to their Opcodes as well as the instruction type to its format.
	 */
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
	public static EnumMap<Instructions, String> getInstructionCode() {
		return instructionCode;
	}

	/**
	 * @param instructionCode the instructionCode to set
	 */
	public static void setInstructionCode(EnumMap<Instructions, String> instructionCode) {
		Instruction.instructionCode = instructionCode;
	}

	/**
	 * @return the instructionFormat
	 */
	public static EnumMap<InstructionTypes, String> getInstructionFormat() {
		return instructionFormat;
	}

	/**
	 * @param instructionFormat the instructionFormat to set
	 */
	public static void setInstructionFormat(EnumMap<InstructionTypes, String> instructionFormat) {
		Instruction.instructionFormat = instructionFormat;
	}

	/**
	 * @return the instructionType
	 */
	public InstructionTypes getInstructionType() {
		return instructionType;
	}

	/**
	 * @param instructionType the instructionType to set
	 */
	public void setInstructionType(InstructionTypes instructionType) {
		this.instructionType = instructionType;
	}

	/**
	 * @return the instructionName
	 */
	public Instructions getInstructionName() {
		return instructionName;
	}

	/**
	 * @param instructionName the instructionName to set
	 */
	public void setInstructionName(Instructions instructionName) {
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
