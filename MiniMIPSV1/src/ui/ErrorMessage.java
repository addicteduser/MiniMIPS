package ui;

import javax.swing.JOptionPane;

public class ErrorMessage {
	public static void showErrorMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
}
