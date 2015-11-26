package miniMIPS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import dataObjects.Instruction;
import helper.TableCellListener;
import helper.Validator;
import parser.Parser;

public class MiniMipsController {
	private MiniMipsUI frame;
	private Parser parser;

	public MiniMipsController() {
		frame = new MiniMipsUI();
		parser = new Parser();
		Instruction.createMappings();
		addEventHandlers();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void addEventHandlers() {
		frame.addBtnLoadMipsCodeActionListener(new LoadMipsCodeActionLister());
		frame.addBtnStartActionListener(new StartActionListener());
		frame.addMemoryTblCellListener(new MemoryTableEditListener());
		frame.addGprTblCellListener(new GprTableEditListener());
		frame.addFprTblCellListener(new FprTableEditListener());
		frame.addTextAreaFocusListener(new TextAreaFocusListener());
	}

	private class TextAreaFocusListener extends WindowAdapter {
		public void windowGainedFocus(WindowEvent e) {
			frame.getTxtCode().requestFocusInWindow();
		}
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
				GuiUpdater.loadDataTable();
				GuiUpdater.loadOpcodeTable();
				GuiUpdater.loadCodSegTable();
			} else {
				System.err.println("[Error] Please check your label(s) and/or variable name(s).");
				System.exit(0);
			}
		}
	}

	private class MemoryTableEditListener extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			TableCellListener tcl = (TableCellListener)e.getSource();
			System.out.println("Row   : " + tcl.getRow());
			System.out.println("Column: " + tcl.getColumn());
			System.out.println("Old   : " + tcl.getOldValue());
			System.out.println("New   : " + tcl.getNewValue());	
		}		
	}

	private class GprTableEditListener extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			TableCellListener tcl = (TableCellListener)e.getSource();
			System.out.println("Row   : " + tcl.getRow());
			System.out.println("Column: " + tcl.getColumn());
			System.out.println("Old   : " + tcl.getOldValue());
			System.out.println("New   : " + tcl.getNewValue());	
		}		
	}

	private class FprTableEditListener extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			TableCellListener tcl = (TableCellListener)e.getSource();
			System.out.println("Row   : " + tcl.getRow());
			System.out.println("Column: " + tcl.getColumn());
			System.out.println("Old   : " + tcl.getOldValue());
			System.out.println("New   : " + tcl.getNewValue());	
		}		
	}
}
