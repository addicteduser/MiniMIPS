package parser;

import java.util.ArrayList;

import constants.DIRECTIVES;
import dataObjects.Data;

// TODO values are double
// TODO if values are in hex
// TODO if varName already exists

public class DataParser implements IParser {
	private String[] tokens;
	
	private Data tempData;
	private String tempVarName;
	private DIRECTIVES tempDirective;
	private long[] tempValues;
	
	@Override
	public void parse(String input) {
		clear();
		parseVarName(input);
		parseDirective();
		parseValues();
		addData();
	}

	
	/**
	 * Resets the values to null.
	 */
	private void clear() {
		tempVarName = "";
		tempValues = new long[4];
	}
	
	/**
	 * Gets the varName from the input.
	 * tokens[0] holds the varName
	 * tokens[1] holds the rest of the input (directive + values)
	 * @param input
	 */
	private void parseVarName(String input) {
		tokens = input.split(":", 2);
		tempVarName = tokens[0];
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
				System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Invalid data segment.");
                System.exit(0);
			}
		}
	}
	
	/**
	 * Gets the values from tokens[1]. tokens will get the split tokens[1].
	 */
	private void parseValues() {
		tokens = tokens[1].trim().split("[;#]"); // removes comments
		tokens = tokens[0].trim().split(",");
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].trim();
			tempValues[i] = Long.parseLong(tokens[i]);
		}
	}
	
	/**
	 * If the input is valid, adds the parsed input to the data list, else the program terminates.
	 */
	private void addData() {
		if (isValuesValid()) {
			if (tempVarName.isEmpty()) {
				appendToPrevious();
			} else {
				addToList();
			}
		} else {
			System.err.println("[ERROR at line:"+FileParser.getLineCtr()+"] Value(s) out of bounds for given directive.");
			System.exit(0);
		}
	}
	
	/**
	 * Checks if the values match the directive.
	 * @return TRUE if all values are within bounds of given directive
	 */
	private boolean isValuesValid() {
		boolean validValues = true;
		
		switch(tempDirective) {
		case BYTE: // 8 bits
			System.out.println("[BYTE]");			
			for (long value : tempValues) {				
				//System.out.println("[COMPARING] "+Byte.MIN_VALUE+" <= "+value+" <= "+Byte.MAX_VALUE);
				if (!isValueValid(Byte.MIN_VALUE, Byte.MAX_VALUE, value))
					validValues = false;
			}
			break;
		case WORD16: // 16 bits
			System.out.println("[WORD16]");
			for (long value : tempValues) {				
				//System.out.println("[COMPARING] "+Short.MIN_VALUE+" <= "+value+" <= "+Short.MAX_VALUE);
				if (!isValueValid(Short.MIN_VALUE, Short.MAX_VALUE, value))
					validValues = false;
			}
			break;
		case WORD32: // 32 bits
			System.out.println("[WORD32]");
			for (long value : tempValues) {				
				//System.out.println("[COMPARING] "+Integer.MIN_VALUE+" <= "+value+" <= "+Integer.MAX_VALUE);
				if (!isValueValid(Integer.MIN_VALUE, Integer.MAX_VALUE, value))
					validValues = false;
			}
			break;
		case WORD: // 64 bits
			System.out.println("[WORD]");
			for (long value : tempValues) {				
				//System.out.println("[COMPARING] "+Long.MIN_VALUE+" <= "+value+" <= "+Long.MAX_VALUE);
				if (!isValueValid(Long.MIN_VALUE, Long.MAX_VALUE, value))
					validValues = false;
			}
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
			System.err.println("[Error at line:"+FileParser.getLineCtr()+"] Invalid directive.");
			System.exit(0);
		}
		
		return validValues;
	}
	
	/**
	 * Checks if value is within the min and max bound of the directive.
	 * @param min minimum value of the directive
	 * @param max maximum value of the directive
	 * @param value value to compare to min and max
	 * @return TRUE if the value is within the min and max bound of the directive
	 */
	private boolean isValueValid(long min, long max, long value) {
		if (min <= value && value <= max)
			return true;
		else
			return false;
	}
	
	/**
	 * Converts the array[] to an ArrayList.
	 * @return ArrayList of values add to the data list
	 */
	private ArrayList<Long> convertArrayToList() {
		ArrayList<Long> list = new ArrayList<Long>();
		for (long value : tempValues) {
			list.add(value);
		}
		
		return list;
	}
	
	/**
	 * Adds the data who correctly follows the format 'varName: .directive v1,v2,v3,v4'.
	 */
	private void addToList() {
		tempData = new Data();
		tempData.setVarName(tempVarName);
		tempData.setDirective(tempDirective);
		tempData.setValues(convertArrayToList());
		Data.getDataList().add(tempData);
	}
	
	/**
	 * Appends the data to the previous entry that followed the format 'varName: .directive v1,v2,v3,v4'.
	 */
	private void appendToPrevious() {
		try {
			Data.getDataList().get(Data.getDataList().size()-1).getValues().addAll(convertArrayToList());
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println("[ERROR at line:"+FileParser.getLineCtr()+"]");
			System.exit(0);
		}
	}
}
