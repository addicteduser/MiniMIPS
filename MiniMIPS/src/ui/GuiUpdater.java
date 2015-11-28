package ui;

import java.util.ArrayList;

import dataObjects.Data;
import dataObjects.FloatingPointRegister;
import dataObjects.GeneralPurposeRegister;
import dataObjects.Instruction;
import dataObjects.MemoryData;
import dataObjects.MemoryInstruction;
import dataObjects.Register;
import helper.NumberBuilder;
import parser.Parser;
import pipeline.Pipeline;
import pipeline.PipelineMapGenerator;
import pipeline.PipelineStage;

public class GuiUpdater {
	public static void resetUI(boolean isTotalReset) {
		if(isTotalReset) {
			MiniMipsUI.resetTxtInput();
		}

		Parser.resetLineCtr();
		MemoryInstruction.resetCtr();
		MemoryData.resetCtr();
		MiniMipsUI.resetMemoryData();
		MiniMipsUI.resetCodeSegment();
		MiniMipsUI.resetTblModOpcode();
		MiniMipsUI.resetRegisterMonitor();
		MiniMipsUI.resetTblModClock();
		MiniMipsUI.resetTblModPipeline();

	}

	public static void loadCode(ArrayList<String> code) {
		for (String s : code)
			MiniMipsUI.appendTxtInput(s);
	}

	public static void loadOpcodeTable() {
		MemoryInstruction.generateAllOpcode();
		for (int i = 0; i < MemoryInstruction.getiCtr(); i++) {
			Instruction instruction = MemoryInstruction.getInstructionList().get(i);
			String instructionStr;
			if (instruction.getLabel().isEmpty())
				instructionStr = instructionBuilder(instruction);
			else			
				instructionStr = instruction.getLabel()+": "+instructionBuilder(instruction);
			String opcodeHex = instruction.getOpcode().getOpcodeHex();
			String ir0_5 = instruction.getOpcode().getIR0_5();
			String ir6_10 = instruction.getOpcode().getIR6_10();
			String ir11_15 = instruction.getOpcode().getIR11_15();
			String ir16_31 = instruction.getOpcode().getIR16_31();
			MiniMipsUI.getTblModOpcode().addRow(new Object[]{instructionStr,opcodeHex,ir0_5,ir6_10,ir11_15,ir16_31});
		}
	}

	public static void loadDataTable() {
		int i = 0;
		for(Data d : MemoryData.getDataList()) {
			String varName = d.getVarName();
			long value = d.getValue();

			MiniMipsUI.getTblModMemory().setValueAt(NumberBuilder.paddedHexStringBuilder(16,value), i, 1);
			MiniMipsUI.getTblModMemory().setValueAt(varName, i, 2);
			varName = "";
			i++;
		}
		
//		for (int i = 0; i < MemoryData.getdCtr(); i++) {
//			Data d = MemoryData.getDataList().get(i);
//			
//		}
	}

	public static void loadCodSegTable() {
		for(int i = 0; i < MemoryInstruction.getiCtr(); i++) {
			Instruction instruction = MemoryInstruction.getInstructionList().get(i);
			String opcodeHex = instruction.getOpcode().getOpcodeHex();
			String label = instruction.getLabel();
			String instructionStr = instructionBuilder(instruction);
			MiniMipsUI.getTblModCodeSeg().setValueAt(opcodeHex, i, 1);
			MiniMipsUI.getTblModCodeSeg().setValueAt(label, i, 2);
			MiniMipsUI.getTblModCodeSeg().setValueAt(instructionStr, i, 3);
		}
	}
	
	public static void generatePipelineMapTable() {
		new PipelineMapGenerator().generateMap();
		for (PipelineStage p : Pipeline.getPipeline()) {
			MiniMipsUI.getTblModPipeline().addRow(p.getStages().toArray());
		}
	}
	
	public static void updateDataTable(int row, int col, long currentVal, long newVal) {
		String value = NumberBuilder.paddedHexStringBuilder(16, currentVal);
		if (MemoryData.updateValue(row, newVal))
			value = NumberBuilder.paddedHexStringBuilder(16, newVal);		
		MiniMipsUI.getTblModMemory().setValueAt(value, row, col);
	}

	public static void updateGprTable(int row, int col, long currentVal, long newVal) {
		String regValue = NumberBuilder.paddedHexStringBuilder(16, currentVal);
		if (GeneralPurposeRegister.updateRegister(row, newVal))
			regValue = NumberBuilder.paddedHexStringBuilder(16, newVal);		
		MiniMipsUI.getTblModGPR().setValueAt(regValue, row, col);
	}
	
	public static void updateFprTable(int row, int col, long currentVal, long newVal) {
		String regValue = NumberBuilder.paddedHexStringBuilder(16, currentVal);
		if (FloatingPointRegister.updateRegister(row, newVal))
			regValue = NumberBuilder.paddedHexStringBuilder(16, newVal);		
		MiniMipsUI.getTblModGPR().setValueAt(regValue, row, col);
	}

	public static void createInitialRegisterMonitor() {
		GeneralPurposeRegister.initializeGPR();
		for(Register r : GeneralPurposeRegister.getGpr()) {
			String regName = r.getRegName();
			String regValue = NumberBuilder.paddedHexStringBuilder(16, r.getRegValue());
			if (!regName.matches("R0"))
				MiniMipsUI.getTblModGPR().addRow(new Object[]{regName,regValue});
		}

		FloatingPointRegister.initializeFPR();
		for(Register r : FloatingPointRegister.getFpr()) {
			String regName = r.getRegName();
			String regValue = NumberBuilder.paddedHexStringBuilder(16, r.getRegValue());
			if (!regName.matches("F0"))
				MiniMipsUI.getTblModFPR().addRow(new Object[]{regName,regValue});
		}
	}
	
	public static void createInitialData() {
		MemoryData.initializeDataList();
		for(Data d : MemoryData.getDataList()) {
			String address = d.getAddress();
			MiniMipsUI.getTblModMemory().addRow(new Object[]{address,NumberBuilder.paddedHexStringBuilder(16, 0),""});
		}
	}

	public static void createInitialInstruction() {
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
