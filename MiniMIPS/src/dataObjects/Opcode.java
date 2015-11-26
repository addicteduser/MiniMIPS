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
	
	public static Opcode fetchOpcode(Instruction i) {
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
			// 0
			tempVal = Integer.parseInt(formatTokens[0]);
			IR0_5 = NumberBuilder.paddedBinaryBuilder(6, tempVal);
			// RS
			tempVal = Integer.parseInt(i.getV2().substring(1));
			IR6_10 = NumberBuilder.paddedBinaryBuilder(5, tempVal);
			// RT
			tempVal = Integer.parseInt(i.getV3().substring(1));
			IR11_15 = NumberBuilder.paddedBinaryBuilder(5, tempVal);
			// RD
			tempVal = Integer.parseInt(i.getV1().substring(1));
			imm = NumberBuilder.paddedBinaryBuilder(5, tempVal);
			// 0
			tempVal = Integer.parseInt(formatTokens[4]);
			imm = imm.concat(NumberBuilder.paddedBinaryBuilder(5, tempVal));
			// iCode
			tempVal = iCode;
			imm = imm.concat(NumberBuilder.paddedBinaryBuilder(6, tempVal));
			IR16_31 = imm;
			break;
		case RS: // 0,0,RS,RD,SHF,iCODE
			// 0
			tempVal = Integer.parseInt(formatTokens[0]);
			IR0_5 = NumberBuilder.paddedBinaryBuilder(6, tempVal);
			// 0
			tempVal = Integer.parseInt(formatTokens[1]);
			IR6_10 = NumberBuilder.paddedBinaryBuilder(5, tempVal);
			// RS
			tempVal = Integer.parseInt(i.getV2().substring(1));
			IR11_15 = NumberBuilder.paddedBinaryBuilder(5, tempVal);
			// RD
			tempVal = Integer.parseInt(i.getV1().substring(1));
			imm = NumberBuilder.paddedBinaryBuilder(5, tempVal);
			// SHF
			tempVal = Integer.parseInt(i.getV3());
			imm = imm.concat(NumberBuilder.paddedBinaryBuilder(5, tempVal));
			//iCode
			tempVal = iCode;
			imm = imm.concat(NumberBuilder.paddedBinaryBuilder(6, tempVal));
			IR16_31 = imm;
			break;
		case ER: // 17,16,RT,RS,RD,iCODE
			// 17
			tempVal = Integer.parseInt(formatTokens[0]);
			IR0_5 = NumberBuilder.paddedBinaryBuilder(6, tempVal);
			// 16
			tempVal = Integer.parseInt(formatTokens[1]);
			IR6_10 = NumberBuilder.paddedBinaryBuilder(5, tempVal);
			// RT
			tempVal = Integer.parseInt(i.getV3().substring(1));
			IR11_15 = NumberBuilder.paddedBinaryBuilder(5, tempVal);
			// RS
			tempVal = Integer.parseInt(i.getV2().substring(1));
			imm = NumberBuilder.paddedBinaryBuilder(5, tempVal);
			// RD
			tempVal = Integer.parseInt(i.getV1().substring(1));
			imm = imm.concat(NumberBuilder.paddedBinaryBuilder(5, tempVal));
			// iCode
			tempVal = iCode;
			imm = imm.concat(NumberBuilder.paddedBinaryBuilder(6, tempVal));
			IR16_31 = imm;
			break;
		case I: // iCODE,RS,RT,IMM 3 1
			// iCode
			tempVal = iCode;
			IR0_5 = NumberBuilder.paddedBinaryBuilder(6, tempVal);
			// RS
			if (iName.toString().matches("BEQ"))
				tempVal = Integer.parseInt(i.getV1().substring(1));
			else if (iName.toString().matches("ANDI|DADDIU"))
				tempVal = Integer.parseInt(i.getV2().substring(1));
			else if (iName.toString().matches("LW|LWU|SW|LS|SS"))
				tempVal = Integer.parseInt(i.getV3().substring(1));
			IR6_10 = NumberBuilder.paddedBinaryBuilder(5, tempVal);
			// RT
			if (iName.toString().matches("BEQ"))
				tempVal = Integer.parseInt(i.getV2().substring(1));
			else if (iName.toString().matches("ANDI|DADDIU"))
				tempVal = Integer.parseInt(i.getV1().substring(1));
			else if (iName.toString().matches("LW|LWU|SW|LS|SS"))
				tempVal = Integer.parseInt(i.getV1().substring(1));
			IR11_15 = NumberBuilder.paddedBinaryBuilder(5, tempVal);
			// IMM
			if (iName.toString().matches("BEQ")){}
				// tempVal = Integer.parseInt(i.getV3());
			else if (iName.toString().matches("ANDI|DADDIU"))
				tempVal = Integer.parseInt(i.getV3());
			else if (iName.toString().matches("LW|LWU|SW|LS|SS"))
				tempVal = Integer.parseInt(i.getV2());
			IR16_31 = NumberBuilder.paddedBinaryBuilder(16, tempVal);
			break;
		case J: // iCODE,IMM
			break;
		
		default:
			break;
		
		}
		opcodeBin = IR0_5+IR6_10+IR11_15+IR16_31;
		opcodeHex = String.format("%08X", Long.parseLong(opcodeBin, 2));
		System.out.println(IR0_5);
		System.out.println(IR6_10);
		System.out.println(IR11_15);
		System.out.println(IR16_31);
		System.out.println(opcodeBin);
		System.out.println(opcodeHex);
	}
	
	private static void parseICode(String code) {
		String tokens[] = code.split(",");
		iType = INSTRUCTIONTYPES.valueOf(tokens[0]);
		iCode = Integer.parseInt(tokens[1]);
	}
	
	private static void parseIFormat(String format) {
		formatTokens = format.split(",");
	}

	/**
	 * @return the opcodeHex
	 */
	public static String getOpcodeHex() {
		return opcodeHex;
	}

	/**
	 * @param opcodeHex the opcodeHex to set
	 */
	public static void setOpcodeHex(String opcodeHex) {
		Opcode.opcodeHex = opcodeHex;
	}

	/**
	 * @return the iR0_5
	 */
	public static String getIR0_5() {
		return IR0_5;
	}

	/**
	 * @param iR0_5 the iR0_5 to set
	 */
	public static void setIR0_5(String iR0_5) {
		IR0_5 = iR0_5;
	}

	/**
	 * @return the iR6_10
	 */
	public static String getIR6_10() {
		return IR6_10;
	}

	/**
	 * @param iR6_10 the iR6_10 to set
	 */
	public static void setIR6_10(String iR6_10) {
		IR6_10 = iR6_10;
	}

	/**
	 * @return the iR11_15
	 */
	public static String getIR11_15() {
		return IR11_15;
	}

	/**
	 * @param iR11_15 the iR11_15 to set
	 */
	public static void setIR11_15(String iR11_15) {
		IR11_15 = iR11_15;
	}

	/**
	 * @return the iR16_31
	 */
	public static String getIR16_31() {
		return IR16_31;
	}

	/**
	 * @param iR16_31 the iR16_31 to set
	 */
	public static void setIR16_31(String iR16_31) {
		IR16_31 = iR16_31;
	}
}
