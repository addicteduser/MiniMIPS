package parser;

import constants.Directives;
import constants.InstructionTypes;
import constants.Instructions;
import dataObjects.Instruction;

//TODO if label already exists

public class InstructionParser implements IParser{
	private String tokens[];
	//private String input;

	private Instruction tempInstruction;
	private InstructionTypes tempInstructionType;
	private Instructions tempInstructionName;
	private String tempLabel;
	private String tempRD;
	private String tempRS;
	private String tempRT;
	private String tempIMM;


	public InstructionParser() {
		Instruction.createMappings();
	}

	@Override
	public void parse(String input) {
		//this.input = input;

		clear();
		parseLabel(input);
		parseInstructionName();
		parseInstructionType();
	}

	private void clear() {
		tempLabel = "";
		tempRD = "";
		tempRS = "";
		tempRT = "";
		tempIMM = "";
	}

	/**
	 * Gets the label from the input.
	 * tokens[0]
	 * @param input
	 */
	private void parseLabel(String input) {
		tokens = input.split(":",2);
		tempLabel = tokens[0];
	}

	/**
	 * Gets the instructionName.
	 * If there is a label, tokens will get the split tokens[1]
	 * Else, tokens will get the split tokens[0]
	 *    - tokens[0] holds the instruction name
	 *    - tokens[1] holds the registers and/or immediate
	 */
	private void parseInstructionName() {
		try {
			tokens[1] = tokens[1].trim();
			tokens = tokens[1].split(" ", 2);
		} catch (Exception e) {
			tempLabel = "";
			tokens[0] = tokens[0].trim();
			tokens = tokens[0].split(" ", 2);
		} finally {
			tempInstructionName = Instructions.valueOf(tokens[0].toUpperCase());
		}
	}

	/**
	 * Gets the instructionType based on the instructionName.
	 */
	private void parseInstructionType() {
		switch(tempInstructionName) {
			case DADDU: case DMULT: case OR: case SLT:
				tempInstructionType = InstructionTypes.R;
				break;
			case DSLL:
				tempInstructionType = InstructionTypes.RS;
				break;
			case ADDS: case MULS:
				tempInstructionType = InstructionTypes.ER;
				break;
			case BEQ: case LW: case LWU: case SW: case ANDI: case DADDIU: case LS: case SS:
				tempInstructionType = InstructionTypes.I;
				break;
			case J:
				tempInstructionType = InstructionTypes.J;
				break;
		}
	}
	
	private void parseRegImm() {
		
	}
}
