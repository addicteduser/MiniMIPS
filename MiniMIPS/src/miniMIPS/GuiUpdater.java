package miniMIPS;

import dataObjects.Data;
import dataObjects.FloatingPointRegister;
import dataObjects.GeneralPurposeRegister;
import dataObjects.Instruction;
import dataObjects.MemoryData;
import dataObjects.MemoryInstruction;
import dataObjects.NumberBuilder;
import dataObjects.Opcode;
import dataObjects.Register;

public class GuiUpdater {
	public static void resetUI() {
		MemoryData.resetCtr();
		MemoryInstruction.resetCtr();
		MiniMipsUI.resetMemory();
		MiniMipsUI.resetRegisterMonitor();
		MiniMipsUI.resetTblModClock();
		MiniMipsUI.resetTblModCode();
		MiniMipsUI.resetTblModOpcode();
		MiniMipsUI.resetTblModPipeline();
	}
	
	public static void loadCodeTable() {
		for (int i = 0; i < MemoryInstruction.getiCtr(); i++) {
			Instruction instruction = MemoryInstruction.getInstructionList().get(i);
			String label = instruction.getLabel();
			String instructionStr = instructionBuilder(instruction);
			MiniMipsUI.getTblModCode().addRow(new Object[]{label,instructionStr});
		}
	}

	public static void loadDataTable() {
		for (int i = 0; i < MemoryData.getdCtr(); i++) {
			Data d = MemoryData.getDataList().get(i);
			String varName = d.getVarName();
			long value = d.getValue();

			MiniMipsUI.getTblModMemory().setValueAt(NumberBuilder.paddedHexBuilder(16,value), i, 1);
			MiniMipsUI.getTblModMemory().setValueAt(varName, i, 2);
			varName = "";
		}
	}

	public static void loadOpcodeTable() {
		for (int i = 0; i < MemoryInstruction.getiCtr(); i++) {
			Instruction instruction = MemoryInstruction.getInstructionList().get(i);
			String instructionStr = instruction.getLabel()+": "+instructionBuilder(instruction);
			String opcodeHex = instruction.getOpcode().getOpcodeHex();
			String ir0_5 = instruction.getOpcode().getIR0_5();
			String ir6_10 = instruction.getOpcode().getIR6_10();
			String ir11_15 = instruction.getOpcode().getIR11_15();
			String ir16_31 = instruction.getOpcode().getIR16_31();
			MiniMipsUI.getTblModOpcode().addRow(new Object[]{instructionStr,opcodeHex,ir0_5,ir6_10,ir11_15,ir16_31});
		}
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

		MemoryInstruction.initializeInstructionList();
		for (Instruction i : MemoryInstruction.getInstructionList()) {
			String address = i.getAddress();
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
