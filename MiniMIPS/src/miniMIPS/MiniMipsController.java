package miniMIPS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import parser.FileParser;

public class MiniMipsController {
	private MiniMipsUI frame;
	private FileParser fileparser;
	
	public MiniMipsController() {
		frame = new MiniMipsUI();
		frame.addBtnLoadMipsCodeActionListener(new LoadMipsCodeActionLister());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private class LoadMipsCodeActionLister implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser(".");
			int result = fileChooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				fileparser = new FileParser(fileChooser.getSelectedFile());
				fileparser.parseFile();
			}
		}
		
	}
}
