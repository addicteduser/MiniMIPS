package parser;

import constants.INSTRUCTIONS;
import dataObjects.Instruction;
import dataObjects.MemoryInstruction;
import helper.Validator;
import ui.ErrorMessage;

public class InstructionParser implements IParser {
	private static int ctr = 0;
	private String tokens[];

	private Instruction tempInstruction;
	private INSTRUCTIONS tempInstructionName;
	private String tempLabel;
	private String tempV1;
	private String tempV2;
	private String tempV3;

	@Override
	public void parse(String input) {
		clear();
		parseLabel(input);
		parseInstructionName();
		parseRegImm();
		addInstruction();
		ctr++;
	}

	public void resetCtr() {
		ctr = 0;
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
				ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Unknown instruction.");
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
		case BEQ: case ANDI: case DADDIU: // RS,RT,Label/IMM
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
						ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
					}
				}

				// 2nd Value
				tempVal = tokens[1].trim();
				if (Validator.isGeneralRegisterValid(tempVal))
					tempV2 = tempVal;
				else {
					ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
				}

				// 3rd Value
				tempVal = tokens[2].trim();
				if (tempInstructionName.toString().matches("DSLL|ANDI|DADDIU"))
					try {
						if(Validator.isImmediateValid(tempVal)) {
							if(tempVal.contains("0x")) {
								tempVal = tempVal.substring(2);
								tempV3 = String.valueOf((long) Integer.parseInt(tempVal, 16));
							} else {
								tempV3 = String.valueOf((long) Integer.parseInt(tempVal));
							}
						} else {
							ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid immediate/offset value.");
						}
					} catch(NumberFormatException e) {
						ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid immediate/offset value.");
					}
				else if (tempInstructionName.toString().matches("BEQ")) {
					tempV3 = tempVal;
				} else {
					if (Validator.isGeneralRegisterValid(tempVal))
						tempV3 = tempVal;
					else {
						ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
					}
				}
			} catch (NumberFormatException e) {
				ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
			} catch(NullPointerException e) {
				ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Label '"+tempVal+"' does not exist.");
			}

			break;

		case ADDS: case MULS: // FD,FS,FT
			tokens = tokens[0].trim().split(",");

			try {
				// 1st Value
				tempVal = tokens[0].trim();
				if (Validator.isFloatRegisterValid(tempVal))
					tempV1 = tempVal;
				else {
					ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
				}

				// 2nd Value
				tempVal = tokens[1].trim();
				if (Validator.isFloatRegisterValid(tempVal))
					tempV2 = tempVal;
				else {
					ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
				}

				// 3rd Value
				tempVal = tokens[2].trim();
				if (Validator.isFloatRegisterValid(tempVal))
					tempV3 = tempVal;
				else {
					ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
				}
			} catch (NumberFormatException e) {
				ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
			}
			break;

		case LW: case LWU: case SW: // RT, IMM(RS)
		case LS: case SS: // FT, IMM(RS)
			tokens = tokens[0].trim().split(",");

			try {
				// 1st Value
				tempVal = tokens[0].trim();
				if (tempInstructionName.toString().matches("LW|LWU|SW")) {
					if (Validator.isGeneralRegisterValid(tempVal))
						tempV1 = tempVal;
					else {
						ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
					}
				} else if (tempInstructionName.toString().matches("LS|SS")) {
					if (Validator.isFloatRegisterValid(tempVal))
						tempV1 = tempVal;
					else {
						ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
					}
				}

				tokens = tokens[1].trim().split("\\(");

				// 2nd Value
				tempVal = tokens[0].trim();
				tempV2 = tempVal;

				// 3rd Value
				tempVal = tokens[1].trim().substring(0, tokens[1].length()-1);
				if (Validator.isGeneralRegisterValid(tempVal))
					tempV3 = tempVal;
				else {
					ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
				}
			} catch (NumberFormatException e) {
				ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Invalid register: "+tempVal+".");
			} catch(NullPointerException e) {
				ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Label '"+tempVal+"' does not exist.");
			}
			break;

		case J: // Label
			tempVal = tokens[0].trim();
			tempV1 = tempVal;
		}
	}

	/**
	 * If input is valid, adds the parsed instruction to the instruction list.
	 */
	private void addInstruction() {
		try {
			if (!Validator.doesLabelExist(tempLabel)) {
				tempInstruction = MemoryInstruction.getInstructionList().get(ctr);
				tempInstruction.setInstructionName(tempInstructionName);
				tempInstruction.setLabel(tempLabel);
				tempInstruction.setV1(tempV1);
				tempInstruction.setV2(tempV2);
				tempInstruction.setV3(tempV3);
				MemoryInstruction.incrementCtr();
			} else {
				ErrorMessage.showErrorMsg("[ERROR at line:" + Parser.getLineCtr() + "] Label '"+tempLabel+"' already exists.");
			}
		} catch (NullPointerException e) {
			tempInstruction = MemoryInstruction.getInstructionList().get(ctr);
			tempInstruction.setInstructionName(tempInstructionName);
			tempInstruction.setLabel(tempLabel);
			tempInstruction.setV1(tempV1);
			tempInstruction.setV2(tempV2);
			tempInstruction.setV3(tempV3);
			MemoryInstruction.incrementCtr();
		}
	}
}
