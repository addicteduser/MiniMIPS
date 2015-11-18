package miniMIPS;

import java.io.File;
import java.util.ArrayList;

import dataObjects.Data;
import parser.FileParser;

public class Driver {
	public static void main(String[] args) {
		FileParser r = new FileParser(new File("_test\\sample.s"));
		r.parseFile();
	}
}
