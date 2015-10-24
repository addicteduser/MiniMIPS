package parser;

import dataObjects.Data;

public class DataParser implements IParser {
	private Data tempData;

	@Override
	public void parse(String input) {
		System.out.println("INPUT: "+input);
	}

	
	//TODO code to handle no var name if first variable
	//TODO type checking and error handling
	//TODO handle succeeding lines if there is existing variable
}
