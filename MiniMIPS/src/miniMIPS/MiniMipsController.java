package miniMIPS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import constants.INSTRUCTIONS;
import dataObjects.Data;
import dataObjects.Instruction;
import parser.FileParser;

public class MiniMipsController {
	private MiniMipsUI frame;
	private FileParser fileparser;

	public MiniMipsController() {
		frame = new MiniMipsUI();
		frame.addBtnLoadMipsCodeActionListener(new LoadMipsCodeActionLister());
		frame.addCbInstructionActionListener(new CbInstructionActionListener());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private class LoadMipsCodeActionLister implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.resetTblModCode();
			Instruction.resetInstructionList();
			Data.resetDataList();
			JFileChooser fileChooser = new JFileChooser(".\\_test");
			int result = fileChooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				fileparser = new FileParser(fileChooser.getSelectedFile());
				fileparser.parseFile();
				GuiUpdater.loadCodeTable();
			}
		}

	}

	private class CbInstructionActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			INSTRUCTIONS instruction = INSTRUCTIONS.valueOf(frame.getCbInstructionSelected());

			switch(instruction) {
			case DADDU: case OR: case SLT: // RD,RS,RT
			case ADDS: case MULS: // FD,FS,FT
				frame.inputRType();
			case DSLL: // RD,RS,SHF/IMM
			case ANDI: case DADDIU: // RS,RT,IMM
			case LW: case LWU: case SW: // RT, IMM(RS)
			case LS: case SS: // FT, IMM(RS)
				frame.inputIType();
				break;
			case DMULT: // RS,RT
				break;
			case BEQ: case J: // Label
				break;
			}
		}
	}
}
