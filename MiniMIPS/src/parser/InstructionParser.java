package parser;

import constants.INSTRUCTIONS;
import dataObjects.Instruction;

public class InstructionParser implements IParser {

	private String tokens[];
	//private String input;

	private Instruction tempInstruction;
	//private INSTRUCTIONTYPES tempInstructionType;
	private INSTRUCTIONS tempInstructionName;
	private String tempLabel;
	private String tempV1;
	private String tempV2;
	private String tempV3;

	public InstructionParser() {
		Instruction.createMappings();
	}

	@Override
	public void parse(String input) {
		//this.input = input;

		clear();
		parseLabel(input);
		parseInstructionName();
		parseRegImm();
		addInstruction();
		System.out.println(Instruction.getInstructionList().toString());
		for (Instruction x : Instruction.getInstructionList()) {
		}
	}

	private void clear() {
		tempLabel = "";
		tempV1 = "";
		tempV2 = "";
		tempV3 = "";
	}

	/**
	 * Gets the label from the input. tokens[0]
	 *
	 * @param input
	 */
	private void parseLabel(String input) {
		tokens = input.split(":", 2);
		tempLabel = tokens[0];
	}

	/**
	 * Gets the instructionName. If there is a label, tokens will get the split
	 * tokens[1] Else, tokens will get the split tokens[0] - tokens[0] holds the
	 * instruction name - tokens[1] holds the registers and/or immediate
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
			try {
				tempInstructionName = INSTRUCTIONS.valueOf(tokens[0].toUpperCase());
			} catch (IllegalArgumentException e) {
				System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Unknown instruction.");
				System.exit(0);
			}
		}
	}

	/**
	 * Gets the registers and/or immediate.
	 */
	private void parseRegImm() {
		String tempVal = "";

		tokens = tokens[1].trim().split("[;#]"); // removes comments

		switch (tempInstructionName) {
		case DADDU: case OR: case SLT: // RD,RS,RT
		case DMULT: // RS,RT
		case DSLL: // RD,RS,SHF/IMM
		case BEQ: case ANDI: case DADDIU: // RS,RT,IMM
			tokens = tokens[0].trim().split(",");

			try {
				// 1st Value
				tempVal = tokens[0].trim();
				if (tempInstructionName.toString().matches("DMULT")) {
					tempV1 = "0";
				} else {
					if (Validator.isGeneralRegisterValid(tempVal))
						tempV1 = tempVal;
					else {
						System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Invalid register: "+tempVal+".");
						System.exit(0);
					}
				}

				// 2nd Value
				tempVal = tokens[1].trim();
				if (Validator.isGeneralRegisterValid(tempVal))
					tempV2 = tempVal;
				else {
					System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Invalid register: "+tempVal+".");
					System.exit(0);
				}

				// 3rd Value
				tempVal = tokens[2].trim();
				if (tempInstructionName.toString().matches("DSLL|BEQ|ANDI|DADDIU"))
					tempV3 = tempVal;
				else {
					if (Validator.isGeneralRegisterValid(tempVal))
						tempV3 = tempVal;
					else {
						System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Invalid register: "+tempVal+".");
						System.exit(0);
					}
				}
			} catch (NumberFormatException e) {
				System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Invalid register: "+tempVal+".");
				System.exit(0);
			}

			break;

		case ADDS: case MULS: // FD,FS,FT
			tokens = tokens[0].trim().split(",");

			// 1st Value
			tempVal = tokens[0].trim();
			if (Validator.isFloatRegisterValid(tempVal))
				tempV1 = tempVal;
			else {
				System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Invalid register: "+tempVal+".");
				System.exit(0);
			}

			// 2nd Value
			tempVal = tokens[1].trim();
			if (Validator.isFloatRegisterValid(tempVal))
				tempV2 = tempVal;
			else {
				System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Invalid register: "+tempVal+".");
				System.exit(0);
			}

			// 3rd Value
			tempVal = tokens[2].trim();
			if (Validator.isFloatRegisterValid(tempVal))
				tempV3 = tempVal;
			else {
				System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Invalid register: "+tempVal+".");
				System.exit(0);
			}
			break;

		case LW: case LWU: case SW: // RT, IMM(RS)
		case LS: case SS: // FT, IMM(RS)
			tokens = tokens[0].trim().split(",");

			// 1st Value
			tempVal = tokens[0].trim();
			if (tempInstructionName.toString().matches("LW|LWU|SW")) {
				if (Validator.isGeneralRegisterValid(tempVal))
					tempV1 = tempVal;
				else {
					System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Invalid register: "+tempVal+".");
					System.exit(0);
				}
			} else if (tempInstructionName.toString().matches("LS|SS")) {
				if (Validator.isFloatRegisterValid(tempVal))
					tempV1 = tempVal;
				else {
					System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Invalid register: "+tempVal+".");
					System.exit(0);
				}
			}

			tokens = tokens[1].trim().split("\\(");

			// 2nd Value
			tempV2 = tokens[0].trim();

			// 3rd Value
			tempVal = tokens[1].trim();
			if (Validator.isGeneralRegisterValid(tempVal))
				tempV3 = tempVal;
			else {
				System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Invalid register: "+tempVal+".");
				System.exit(0);
			}
			break;

		case J: // Label
			tempV1 = tokens[0].trim();
		}
	}

	/**
	 * If input is valid, adds the parsed instruction to the instruction list.
	 */
	private void addInstruction() {
		if (Validator.isLabelValid(tempLabel)) {
			tempInstruction = new Instruction();
			tempInstruction.setInstructionName(tempInstructionName);
			tempInstruction.setLabel(tempLabel);
			tempInstruction.setV1(tempV1);
			tempInstruction.setV2(tempV2);
			tempInstruction.setV3(tempV3);
			Instruction.getInstructionList().add(tempInstruction);
		} else {
			System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Label already exits.");
			System.exit(0);
		}
	}



	private static class Validator {
		/**
		 * Checks whether the label is already existing.
		 * @param label
		 * @return TRUE if label is valid, FALSE if label is already in use.    	 
		 */
		private static boolean isLabelValid(String label) {
			for (int i = 0; i < Instruction.getInstructionList().size(); i++) {
				if (!label.isEmpty()) {
					if (Instruction.getInstructionList().get(i).getLabel().matches(label)) {
						return false;
					}
				}
			}

			return true;
		}

		/**
		 * Checks whether the input register is a valid general register or not.
		 * @param register
		 * @return TRUE if register input is valid, FALSE if not.
		 */
		private static boolean isGeneralRegisterValid(String register) throws NumberFormatException {
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
		private static boolean isFloatRegisterValid(String register) throws NumberFormatException {
			int regNumber = Integer.parseInt(register.replace("F", "").replace("f", ""));
			if (regNumber >= 0 && regNumber <= 31)
				return true;
			else
				return false;
		}
	}
}
