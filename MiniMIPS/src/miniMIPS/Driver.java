package miniMIPS;

import java.io.File;
import parser.FileParser;

public class Driver {
	public static void main(String[] args) {
            
		FileParser r = new FileParser(new File("C:\\Users\\Octaviano\\Documents\\NetBeansProjects\\MiniMipsRepo\\MiniMIPS\\src\\sample.s"));
		r.parseFile();
	}
}
