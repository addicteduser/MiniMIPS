package miniMIPS;

import java.util.ArrayList;

import dataObjects.Data;
import dataObjects.Instruction;

public class GuiUpdater {
	// update code input panel
	// update memory
	public static void loadCodeTable() {
		for(Instruction i : Instruction.getInstructionList()) {
			String label = i.getLabel();
			String instruction = instructionBuilder(i);
			MiniMipsUI.getTblModCode().addRow(new Object[]{label,instruction});
		}

	}
	
	public static void loadDataTable() {
		int i = 0;
		for(Data d : Data.getDataList()) {
			String varName = d.getVarName();
			ArrayList<Long> values = d.getValues();
			for(Long v : values) {
				MiniMipsUI.getTblModMemory().setValueAt(v, i, 1);
				MiniMipsUI.getTblModMemory().setValueAt(varName, i, 2);
				varName = "";
				i++;
			}
		}
	}
	
	public static void loadOpcodeTable() {
		
	}

	public static void createInitialRegisterMonitor() {
		for(int i = 0; i <= 31; i++) {
			String gpr = "R"+i;
			String fpr = "F"+i;
			MiniMipsUI.getTblModGPR().addRow(new Object[]{gpr,"0000000000000000"});
			MiniMipsUI.getTblModFPR().addRow(new Object[]{fpr,"0000000000000000"});
		}
		MiniMipsUI.getTblModGPR().addRow(new Object[]{"HI","0000000000000000"});
		MiniMipsUI.getTblModGPR().addRow(new Object[]{"LO","0000000000000000"});
	}
	
	public static void createInitialMemory() {
		for(int i = 0; i <= Short.toUnsignedInt(Short.parseShort("1FFF", 16)); i+=8) {
			String address = String.format("%04X", i);
			MiniMipsUI.getTblModMemory().addRow(new Object[]{address,"0000000000000000",""});
		}
		
		for (int i = Short.toUnsignedInt(Short.parseShort("2000", 16)); i < Short.toUnsignedInt(Short.parseShort("3FFF", 16)); i+=4) {
			String address = String.format("%04X", i);
			MiniMipsUI.getTblModCodeSeg().addRow(new Object[]{address,"","",""});
		}
	}

	private static String instructionBuilder(Instruction i) {
		String instruction = i.getInstructionName().toString()+" ";

		switch(i.getInstructionName()) {
		case DADDU: case OR: case SLT: // RD,RS,RT
		case ADDS: case MULS: // FD,FS,FT
		case DSLL: // RD,RS,SHF/IMM
		case BEQ: case ANDI: case DADDIU: // RS,RT,IMM
			instruction = instruction.concat(i.getV1()+","+i.getV2()+","+i.getV3());
			break;
		case DMULT: // RS,RT
			instruction = instruction.concat(i.getV2()+","+i.getV3());
			break;
		case LW: case LWU: case SW: // RT, IMM(RS)
		case LS: case SS: // FT, IMM(RS)
			instruction = instruction.concat(i.getV1()+","+i.getV2()+"("+i.getV3()+")");
			break;
		case J: // Label
			instruction = instruction.concat(i.getV1());
			break;
		}

		return instruction.toUpperCase();
	}
}
