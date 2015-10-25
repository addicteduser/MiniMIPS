package parser;

import java.util.ArrayList;

import constants.Directive;
import dataObjects.Data;

public class DataParser implements IParser {
	private Data tempData;
	private String[] tokens;
	private String tempVarName;
	private Directive tempDirective;
	private long[] tempValues;
	
	@Override
	public void parse(String input) {
		System.out.println("[INPUT] "+input);
		parseVarName(input);
		parseDirective();
		parseValues();
		if (isValuesValid()) {
			if (tempVarName.isEmpty()) {
				appendDataToPrevious();
			} else {
				addData();
				System.out.println("Added data!");
			}
		} else {
			System.err.println("[ERROR at line:"+FileParser.getLineCtr()+"] Value(s) out of bounds for given directive.\nProgram will now terminate.");
			System.exit(0);
		}
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
	 * Gets the directive from tokens[1]. tokens will get the split tokens[1].
	 * tokens[0] holds the directive
	 * tokens[1] holds the values
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
			tempDirective = Directive.valueOf(tokens[0].substring(1).toUpperCase());
		}
	}
	
	/**
	 * Gets the values from tokens[1]. tokens will get the split tokens[1].
	 */
	private void parseValues() {
		tempValues = new long[4];
		tokens = tokens[1].trim().split(",");
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].trim();
			tempValues[i] = Long.parseLong(tokens[i]);
		}
	}
	
	/**
	 * Checks if the values matches the directive.
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
		}
		
		return validValues;
	}
	
	/**
	 * Checks if value is within the min and max bound of the directive
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
	
	private ArrayList<Long> convertArrayToList() {
		ArrayList<Long> list = new ArrayList<Long>();
		for (long value : tempValues) {
			list.add(value);
		}
		
		return list;
	}
	
	private void addData() {
		tempData = new Data();
		tempData.setVarName(tempVarName);
		tempData.setDirective(tempDirective);
		tempData.setValues(convertArrayToList());
		Data.getDataList().add(tempData);
	}
	
	private void appendDataToPrevious() {
		Data.getDataList().get(Data.getDataList().size()-1).getValues().addAll(convertArrayToList());
	}
	
	//TODO code to handle no var name if first variable
	//TODO type checking and error handling
	//TODO handle succeeding lines if there is existing variable
	//TODO values are double
}
