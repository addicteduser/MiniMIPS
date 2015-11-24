package miniMIPS;

import dataObjects.Instruction;

public class GuiUpdater {
	// update code input panel
	// update memory
	public static void loadCodeTable() {
		for(Instruction i : Instruction.getInstructionList()) {
			String label = i.getLabel();
			String instruction = instructionBuilder(i);
			MiniMipsUI.getTblCode().addRow(new Object[]{label,instruction});
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
