package miniMIPS;

import java.util.ArrayList;

import dataObjects.Data;
import dataObjects.FloatingPointRegister;
import dataObjects.GeneralPurposeRegister;
import dataObjects.Instruction;
import dataObjects.MemoryData;
import dataObjects.MemoryInstruction;
import dataObjects.NumberBuilder;
import dataObjects.Register;

public class GuiUpdater {
	// update code input panel
	// update memory
	public static void loadCodeTable() {
		for(Instruction i : MemoryInstruction.getInstructionList()) {
			String label = i.getLabel();
			String instruction = instructionBuilder(i);
			MiniMipsUI.getTblModCode().addRow(new Object[]{label,instruction});
		}

	}

	public static void loadDataTable() {
		int i = 0;
		for(Data d : MemoryData.getDataList()) {
			String varName = d.getVarName();
			long value = d.getValue();

			MiniMipsUI.getTblModMemory().setValueAt(NumberBuilder.paddedHexBuilder(16,value), i, 1);
			MiniMipsUI.getTblModMemory().setValueAt(varName, i, 2);
			varName = "";
			i++;
		}
	}

	public static void loadOpcodeTable() {

	}

	public static void createInitialRegisterMonitor() {
		GeneralPurposeRegister.initializeGPR();
		for(Register r : GeneralPurposeRegister.getGpr()) {
			String regName = r.getRegName();
			String regValue = NumberBuilder.paddedHexBuilder(16, r.getRegValue());
			MiniMipsUI.getTblModGPR().addRow(new Object[]{regName,regValue});
		}

		FloatingPointRegister.initializeFPR();
		for(Register r : FloatingPointRegister.getFpr()) {
			String regName = r.getRegName();
			String regValue = NumberBuilder.paddedHexBuilder(16, r.getRegValue());
			MiniMipsUI.getTblModFPR().addRow(new Object[]{regName,regValue});
		}
	}

	public static void createInitialMemory() {
		MemoryData.initializeDataList();
		for(Data d : MemoryData.getDataList()) {
			String address = d.getAddress();
			MiniMipsUI.getTblModMemory().addRow(new Object[]{address,NumberBuilder.paddedHexBuilder(16, 0),""});
		}

		for (int i = Short.toUnsignedInt(Short.parseShort("2000", 16)); i < Short.toUnsignedInt(Short.parseShort("3FFF", 16)); i+=4) {
			String address = NumberBuilder.paddedHexBuilder(4, i);
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
