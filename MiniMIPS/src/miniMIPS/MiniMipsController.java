package miniMIPS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import dataObjects.Instruction;
import dataObjects.MemoryData;
import helper.TableCellListener;
import helper.Validator;
import parser.Parser;
import ui.ErrorMessage;
import ui.GuiUpdater;
import ui.MiniMipsUI;

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
			GuiUpdater.resetUI(false);
			for(String s :frame.getTxtInput().split("\\n")) {
				parser.parseInput(s);
			}

			if (Validator.validateInput()) {
				GuiUpdater.loadDataTable();
				GuiUpdater.loadOpcodeTable();
				GuiUpdater.loadCodSegTable();
			} else {
				ErrorMessage.showErrorMsg("[Error] Please check your label(s) and/or variable name(s).");
			}
		}
	}

	private abstract class TableEditListener extends AbstractAction {
		TableCellListener tcl;
		int row;
		int col;
		long currentNumberVal;
		long newNumberVal;
		String newStringVal;
	}

	private class MemoryTableEditListener extends TableEditListener {
		public void actionPerformed(ActionEvent e) {
			tcl = (TableCellListener)e.getSource();
			row = tcl.getRow();
			col = tcl.getColumn();	
			
			if (col == 1) { // data
				currentNumberVal = Integer.parseInt((String)tcl.getOldValue(),16);
				try {
					newNumberVal = Integer.parseInt((String)tcl.getNewValue(),16);
				} catch (NumberFormatException ex) {
					ErrorMessage.showErrorMsg("[Error] Invalid data value.");
				} finally {
					GuiUpdater.updateDataTable(row, col, currentNumberVal, newNumberVal);
				}
			} else if (col == 2) { // variable
				MemoryData.updateVarName(row, newStringVal);
			}
		}		
	}

	private class GprTableEditListener extends TableEditListener {
		public void actionPerformed(ActionEvent e) {
			tcl = (TableCellListener)e.getSource();
			row = tcl.getRow();
			col = tcl.getColumn();
			currentNumberVal = Long.parseLong((String)tcl.getOldValue(),16);
			try {
				newNumberVal = Long.parseLong((String)tcl.getNewValue(),16);
			} catch (NumberFormatException ex) {
				ErrorMessage.showErrorMsg("[Error] Invalid register value.");
				newNumberVal = currentNumberVal;
			} finally {
				GuiUpdater.updateGprTable(row, col, currentNumberVal, newNumberVal);
			}
		}		
	}

	private class FprTableEditListener extends TableEditListener {
		public void actionPerformed(ActionEvent e) {
			tcl = (TableCellListener)e.getSource();
			row = tcl.getRow();
			col = tcl.getColumn();
			currentNumberVal = Long.parseLong((String)tcl.getOldValue(),16);
			try {
				newNumberVal = Long.parseLong((String)tcl.getNewValue(),16);
			} catch (NumberFormatException ex) {
				ErrorMessage.showErrorMsg("[Error] Invalid register value.");
				newNumberVal = currentNumberVal;
			} finally {
				GuiUpdater.updateFprTable(row, col, currentNumberVal, newNumberVal);
			}
		}		
	}
}
