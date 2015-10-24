package Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

	public void getInput() {
		String strLine;

		// Read file per line
		try {
			while ((strLine = br.readLine()) != null) {
				// Remove leading and trailing spaces
				strLine = strLine.trim();

				// Print the content on the console

				switch (strLine) {
				case "": // Do nothing
					break;
				case ".data":
					System.out.println("Data Parser");
					parser = new DataParser();
					break;
				case ".code":
				case ".text":
					System.out.println("Instruction Parser");
					parser = new InstructionParser();
					break;
				default:
					if (strLine.startsWith("#") || strLine.startsWith(";")) {
						// Do nothing
					} else {
						parser.parse();
					}
					System.out.println(strLine);
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
