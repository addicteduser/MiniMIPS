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
	private static int lineCtr;

	public FileParser(File f) {
		lineCtr = 0;
		
		// Open the file
		try {
			fstream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			System.err.println("[ERROR] File Not Found.");
		}
		br = new BufferedReader(new InputStreamReader(fstream));
	}

	public void parseFile() {
		String strLine;
		Directive directive;

		// Read file per line
		try {
			while ((strLine = br.readLine()) != null) {
				lineCtr++;
				System.out.println("[STATUS] Now at line:"+lineCtr);
				
				// Clean input by replacing tabs with spaces and removing leading and trailing spaces
				strLine = strLine.replace('\t', ' ').trim();
				
				if (strLine.startsWith("#") || strLine.startsWith(";")) {
					// If strLine is a comment
					System.out.println("This is a comment.");
				} else if (strLine.isEmpty()) {
					// If strLine is a line break
					System.out.println("This is a line break.");
				} else if (strLine.startsWith(".")) {
					// If strLine is a directive
					System.out.println("This is a directive.");
					
					String tokens[] = strLine.split(" ", 2);			
					directive = Directive.valueOf(tokens[0].substring(1).toUpperCase());
					System.out.println("[DIRECTIVE] "+directive);
					
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
						parser.parse(strLine);
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

	/**
	 * @return the lineCtr
	 */
	public static int getLineCtr() {
		return lineCtr;
	}
}
