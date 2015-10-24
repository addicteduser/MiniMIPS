package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import constants.Directive;

public class FileParser {
	private FileInputStream fstream;
	private BufferedReader br;
	private IParser parser;

	public FileParser(File f) {
		// Open the file
		try {
			fstream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			System.err.println("File Not Found.");
		}
		br = new BufferedReader(new InputStreamReader(fstream));
	}

	public void parseFile() {
		String strLine;
		Directive directive;

		// Read file per line
		try {
			while ((strLine = br.readLine()) != null) {
				// Remove leading and trailing spaces
				strLine = strLine.trim();
				
				
				
				if (strLine.startsWith("#") || strLine.startsWith(";")) {
					// If strLine is a comment
				} else if (strLine.isEmpty()) {
					// If strLine is a line break
				} else if (strLine.startsWith(".")) {
					directive = Directive.valueOf(strLine.substring(1).toUpperCase());
					switch (directive) {
					case DATA:
						// If strLine is the start of .data directive
						parser = new DataParser();
						break;
					case CODE: case TEXT:
						// If strLine is the start of the .code/.text directive
						parser = new InstructionParser();
						break;
					default:
						System.out.println("default");
					}
				}  else {
					parser.parse(strLine);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Close the input stream
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
