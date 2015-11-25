package dataObjects;

import com.sun.org.apache.xerces.internal.util.ParserConfigurationSettings;

import constants.INSTRUCTIONS;
import constants.INSTRUCTIONTYPES;

public class Opcode {
	private String opcodeHex;
	private static String IR0_5; // opcode
	private static String IR6_10; // a
	private static String IR11_15; // b
	private static String IR16_31; // imm
	
	private static INSTRUCTIONS iName;
	private static INSTRUCTIONTYPES iType;
	private static int iCode;
	private static String[] formatTokens;
	
	public static Opcode getOpcode(Instruction i) {
		Opcode temp = null;
		iName = i.getInstructionName();
		//iType = i.getInstructionName();
		parseICode(Instruction.getInstructionCode().get(iName));
		parseIFormat(Instruction.getInstructionFormat().get(iType));
		buildOpcode();
		
		return temp;
	}
	
	private static void buildOpcode() {
		switch(iType) {
		case R:
			IR0_5 = String.format("%6s", Integer.toBinaryString(iCode & 0xFF)).replace(' ', '0');
			System.out.println(IR0_5);
			break;
		case ER:
			break;
		case I:
			break;
		case J:
			break;
		case RS:
			break;
		default:
			break;
		
		}
	}
	
	private static void parseICode(String code) {
		String tokens[] = code.split(",");
		iType = INSTRUCTIONTYPES.valueOf(tokens[0]);
		iCode = Integer.parseInt(tokens[1]);
	}
	
	private static void parseIFormat(String format) {
		formatTokens = format.split(",");
	}
}
