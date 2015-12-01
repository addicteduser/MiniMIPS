package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import constants.DIRECTIVES;
import ui.ErrorMessage;

public class Parser {

	private FileInputStream fstream;
	private BufferedReader br;
	private IParser parser;
	private static int lineCtr;

	public Parser() {
		resetLineCtr();
	}

	public ArrayList<String> parseFile(File f) {
		String strLine = "";
		ArrayList<String> code = new ArrayList<String>();

		// Open the file
		try {
			fstream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			ErrorMessage.showErrorMsg("[ERROR] File Not Found.");
		}

		br = new BufferedReader(new InputStreamReader(fstream));

		try {
			while ((strLine = br.readLine()) != null) {
				code.add(strLine+"\r\n");
			}
		} catch (IOException e) {
			//e.printStackTrace();
			ErrorMessage.showErrorMsg("[ERROR at line:" + getLineCtr() + "]");
		} finally {
			// Close the input stream
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return code;
	}

	public void parseInput(String strLine) {
		DIRECTIVES directive;

		lineCtr++;
		//System.out.println("[STATUS] Now at line:"+lineCtr);

		// Clean input by replacing tabs with spaces and removing leading and trailing spaces
		strLine = strLine.replace('\t', ' ').trim();

		System.out.println("[INPUT] " + strLine);

		try {
			if (strLine.startsWith("#") || strLine.startsWith(";")) {
				// If strLine is a comment
			} else if (strLine.isEmpty()) {
				// If strLine is a line break
			} else if (strLine.startsWith(".")) {
				// If strLine is a directive
				String tokens[] = strLine.split(" ", 2);

				directive = DIRECTIVES.valueOf(tokens[0].substring(1).toUpperCase());
				switch (directive) {
				case DATA:
					// If strLine is the start of .data directive
					parser = new DataParser();
					parser.resetCtr();
					break;
				case CODE:
				case TEXT:
					// If strLine is the start of the .code/.text directive
					parser = new InstructionParser();
					parser.resetCtr();
					break;
				default:
					parser.parse(strLine);
				}
			} else {
				parser.parse(strLine);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			ErrorMessage.showErrorMsg("[ERROR at line:" + getLineCtr() + "]");
		}
	}

	/**
	 * @return the lineCtr
	 */
	public static int getLineCtr() {
		return lineCtr;
	}

	public static void resetLineCtr() {
		lineCtr = 0;
	}
}
