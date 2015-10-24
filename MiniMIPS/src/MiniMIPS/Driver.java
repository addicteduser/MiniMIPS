package MiniMIPS;

import java.io.File;

import Parser.FileParser;

public class Driver {

	public static void main(String[] args) {
		FileParser r = new FileParser(new File("_test\\sample.s"));
		r.getInput();
	}

}
