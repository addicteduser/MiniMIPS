package dataObjects;

import com.sun.org.apache.xerces.internal.util.ParserConfigurationSettings;

import constants.INSTRUCTIONS;
import constants.INSTRUCTIONTYPES;

public class Opcode {
	private static String opcodeHex;
	private static String opcodeBin;
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
		buildOpcode(i);
		
		return temp;
	}
	
	private static void buildOpcode(Instruction i) {
		int tempVal;
		String imm = "";
		switch(iType) {
		case R: // 0,RS,RT,RD,0,iCODE
			tempVal = Integer.parseInt(formatTokens[0]);
			IR0_5 = String.format("%6s", Integer.toBinaryString(tempVal & 0xFF)).replace(' ', '0');
			tempVal = Integer.parseInt(i.getV2().substring(1));
			IR6_10 = String.format("%5s", Integer.toBinaryString(tempVal & 0xFF)).replace(' ', '0');
			tempVal = Integer.parseInt(i.getV3().substring(1));
			IR11_15 = String.format("%5s", Integer.toBinaryString(tempVal & 0xFF)).replace(' ', '0');
			tempVal = Integer.parseInt(i.getV1().substring(1));
			imm = String.format("%5s", Integer.toBinaryString(tempVal & 0xFF)).replace(' ', '0');
			tempVal = Integer.parseInt(formatTokens[4]);
			imm = imm.concat(String.format("%5s", Integer.toBinaryString(tempVal & 0xFF)).replace(' ', '0'));
			tempVal = iCode;
			imm = imm.concat(String.format("%5s", Integer.toBinaryString(tempVal & 0xFF)).replace(' ', '0'));
			IR16_31 = imm;
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
		opcodeBin = IR0_5+IR6_10+IR11_15+IR16_31;
		opcodeHex = String.format("%08X", Long.parseLong(opcodeBin, 2));
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
