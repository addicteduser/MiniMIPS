package parser;

import dataObjects.Data;

public class DataParser implements IParser {
	private Data tempData;

	@Override
	public void parse(String input) {
		String[] tokens;
		String tempVarName;
		
		System.out.println("[INPUT] "+input);
		tokens = input.split(":");
		
		tempVarName = tokens[0];
		
		tokens = tokens[1].split(",");
		for(String token : tokens) {
			System.out.println(token.trim());
		}
	}

	
	//TODO code to handle no var name if first variable
	//TODO type checking and error handling
	//TODO handle succeeding lines if there is existing variable
}
