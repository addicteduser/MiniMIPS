package miniMIPS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import dataObjects.Instruction;
import helper.Validator;
import parser.Parser;

public class MiniMipsController {
	private MiniMipsUI frame;
	private Parser parser;

	public MiniMipsController() {
		frame = new MiniMipsUI();
		parser = new Parser();
		Instruction.createMappings();
		frame.addBtnLoadMipsCodeActionListener(new LoadMipsCodeActionLister());
		frame.addBtnStartActionListener(new StartActionListener());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private class LoadMipsCodeActionLister implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser(".\\_test");
			int result = fileChooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				GuiUpdater.resetUI(true);
				GuiUpdater.loadCode(parser.parseFile(fileChooser.getSelectedFile()));
			}
		}
	}
	
	private class StartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// save register content
			// save memory content
			GuiUpdater.resetUI(false);
			for(String s :frame.getTxtInput().split("\\n")) {
				parser.parseInput(s);
			}
			
			if (Validator.validateInput()) {
				//GuiUpdater.loadDataTable();
				//GuiUpdater.loadOpcodeTable();
				//GuiUpdater.loadCodSegTable();
			} else {
				System.err.println("[Error] Please check your label(s) and/or variable name(s).");
				System.exit(0);
			}
		}
	}
}
