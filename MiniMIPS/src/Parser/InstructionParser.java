package parser;

import constants.InstructionTypes;
import constants.Instructions;
import dataObjects.Instruction;

//TODO if label already exists

public class InstructionParser implements IParser{
	private String tokens[];
	private String input;
	
	private boolean isLabelOnly = false;
	
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
		this.input = input;
		
		clear();
		parseLabel();
		if (!isLabelOnly) {
			parseInstructionName();
			parseInstructionType();
		}
	}
	
	private void clear() {
		tempLabel = "";
		tempRD = "";
		tempRS = "";
		tempRT = "";
		tempIMM = "";
	}
	
	private void parseLabel() {
		tokens = input.split(":",2);
		tempLabel = tokens[0];

		if (tokens[1].trim().startsWith("#") || tokens[1].trim().startsWith(";")) {
			isLabelOnly = false;
		} else if (tokens[1].isEmpty()) {
			isLabelOnly = false;
		}
	}
	
	private void parseInstructionName() {
		
	}
	
	private void parseInstructionType() {
		
	}
}
