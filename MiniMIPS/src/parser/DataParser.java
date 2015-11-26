package parser;

import constants.DIRECTIVES;
import dataObjects.Data;
import dataObjects.MemoryData;
import helper.Validator;

// TODO values are double
// TODO if values are in hex
// TODO if varName already exists

public class DataParser implements IParser {
	private static int ctr = 0;
	private String[] tokens;

	private Data tempData;
	private String tempVarName;
	private DIRECTIVES tempDirective;
	private long tempValue;

	@Override
	public void parse(String input) {
		clear();
		parseVarName(input);
		parseDirective();
		parseValue();
		addData();
		ctr++;
	}

	public void resetCtr() {
		ctr = 0;
	}


	/**
	 * Resets the values to null.
	 */
	private void clear() {
		tempVarName = "";
	}

	/**
	 * Gets the varName from the input.
	 * tokens[0] holds the varName
	 * tokens[1] holds the rest of the input (directive + values)
	 * @param input
	 */
	private void parseVarName(String input) {
		tokens = input.split(":", 2);
		try {
			if (!Validator.doesVarNameExist(tokens[0]))
				tempVarName = tokens[0];
			else {
				System.err.println("[ERROR at line:" + Parser.getLineCtr() + "] Variable name '"+tokens[0]+"' already exists.");
				System.exit(0);
			}
		} catch(NullPointerException e) {
			tempVarName = tokens[0];
		}
	}

	/**
	 * Gets the directive.
	 * If there is a varName, tokens will get the split tokens[1]
	 * Else, tokens will get the split tokens[0]
	 *    - tokens[0] holds the directive
	 *    - tokens[1] holds the values
	 */
	private void parseDirective() {
		try {
			tokens[1] = tokens[1].trim();
			tokens = tokens[1].split(" ", 2);
		} catch (Exception e) {
			tempVarName = "";
			tokens[0] = tokens[0].trim();
			tokens = tokens[0].split(" ", 2);
		} finally {
			if (tokens[0].startsWith("."))
				tempDirective = DIRECTIVES.valueOf(tokens[0].substring(1).toUpperCase());
			else {
				System.err.println("[ERROR at line:" + Parser.getLineCtr() + "] Invalid data segment.");
				System.exit(0);
			}
		}
	}

	/**
	 * Gets the values from tokens[1]. tokens will get the split tokens[1].
	 */
	private void parseValue() {
		tokens = tokens[1].trim().split("[;#]"); // removes comments
		tempValue = Long.parseLong(tokens[0].trim());
	}

	/**
	 * If the input is valid, adds the parsed input to the data list, else the program terminates.
	 */
	private void addData() {
		if (areValuesValid()) {
			addToList();
		} else {
			System.err.println("[ERROR at line:"+Parser.getLineCtr()+"] Value(s) out of bounds for given directive.");
			System.exit(0);
		}
	}

	/**
	 * Checks if the values match the directive.
	 * @return TRUE if all values are within bounds of given directive
	 */
	private boolean areValuesValid() {
		boolean validValues = true;

		switch(tempDirective) {
		case BYTE: // 8 bits
			if (!Validator.isValueValid(Byte.MIN_VALUE, Byte.MAX_VALUE, tempValue))
				validValues = false;
			break;
		case WORD16: // 16 bits
			if (!Validator.isValueValid(Short.MIN_VALUE, Short.MAX_VALUE, tempValue))
				validValues = false;
			break;
		case WORD32: // 32 bits
			if (!Validator.isValueValid(Integer.MIN_VALUE, Integer.MAX_VALUE, tempValue))
				validValues = false;
			break;
		case WORD: // 64 bits
			if (!Validator.isValueValid(Long.MIN_VALUE, Long.MAX_VALUE, tempValue))
				validValues = false;
			break;
			//		case DOUBLE:
			//			System.out.println("DOUBLE");
			//			for (long value : tempValues) {				
			//				System.out.println("[COMPARING] "+Double.MIN_VALUE+" <= "+value+" <= "+Double.MAX_VALUE);
			//				if (!isValueValid(Double.MIN_VALUE, Double.MAX_VALUE, value))
			//					validValues = false;
			//			}
			//			break;
		default:
			System.err.println("[Error at line:"+Parser.getLineCtr()+"] Invalid directive.");
			System.exit(0);
		}

		return validValues;
	}

	/**
	 * Adds the data who correctly follows the format 'varName: .directive v1,v2,v3,v4'.
	 */
	private void addToList() {
		tempData = MemoryData.getDataList().get(ctr);
		tempData.setVarName(tempVarName);
		tempData.setDirective(tempDirective);
		tempData.setValue(tempValue);
		MemoryData.incrementCtr();
	}
}
