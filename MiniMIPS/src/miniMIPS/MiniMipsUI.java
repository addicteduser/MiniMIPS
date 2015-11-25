package miniMIPS;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class MiniMipsUI extends JFrame {
	private JPanel contentPane;
	private JLabel lblTitle;
	private JPanel pnlCode;
	private JPanel pnlCodeInput;
	private JPanel pnlOpcode;
	private JPanel pnlPipeline;
	private JPanel pnlRegister;
	private JPanel pnlGPR;
	private JPanel pnlFPR;
	private JPanel pnlSimulation;
	private JPanel pnlAddress;
	private JPanel pnlCodeSeg;
	private JPanel pnlCycle;
	private JScrollPane scpCode;
	private JButton btnLoadMipsCode;
	private JComboBox cbRT;
	private JComboBox cbRS;
	private JComboBox cbRD;
	private JComboBox cbInstruction;
	private JButton btnAdd;
	private JScrollPane scpOpcode;
	private JButton btnStep;
	private JButton btnRun;
	private JButton btnPipelineSomething;
	private JScrollPane scpPipeline;
	private JScrollPane scpGPR;
	private JScrollPane scpFPR;
	private JScrollPane scpClock;
	private JScrollPane scpCodeSeg;
	private JScrollPane scpMemory;
	private JTextField txtLabel;
	private JTextField txtImmediate;

	private static JTable tblCode;
	private JTable tblCodeSeg;
	private JTable tblOpcode;
	private JTable tblPipeline;
	private JTable tblGPR;
	private JTable tblFPR;
	private JTable tblMemory;
	private JTable tblClock;

	private static UneditableTableModel tblmodCode;
	private static UneditableTableModel tblmodOpcode;
	private static UneditableTableModel tblmodPipeline;
	private static PartialEditableTableModel tblmodGPR;
	private static PartialEditableTableModel tblmodFPR;
	private static PartialEditableTableModel tblmodMemory;
	private static UneditableTableModel tblmodCodeSeg;
	private static UneditableTableModel tblmodClock;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MiniMipsUI frame = new MiniMipsUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MiniMipsUI() {
		createTables();
		createFrame();
		createMipsAssemblyCodePanel();
		createRegisterMonitorPanel();
		createMipsAssemblySimulationPanel();
	}

	private void createFrame() {
		setTitle("MIPS 2K: A mini MIPS Pipeline Simulator");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 725);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblTitle = new JLabel("MIPS 2K: A MINI MIPS PIPELINE SIMULATOR");
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		lblTitle.setBounds(16, 6, 549, 31);
		contentPane.add(lblTitle);
	}
	
	private void createMipsAssemblyCodePanel() {
		pnlCode = new JPanel();
		pnlCode.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "MIPS Assembly Code", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlCode.setBounds(16, 42, 499, 643);
		contentPane.add(pnlCode);
		pnlCode.setLayout(null);
		
		createCodeInputPanel();
		createCodeOpcodePanel();
		createPipelinePanel();
	}
	
	private void createCodeInputPanel() {
		pnlCodeInput = new JPanel();
		pnlCodeInput.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Code Input Panel", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		pnlCodeInput.setBounds(16, 23, 465, 206);
		pnlCode.add(pnlCodeInput);
		pnlCodeInput.setLayout(null);
		
		scpCode = new JScrollPane();
		scpCode.setBounds(18, 21, 430, 115);
		scpCode.setViewportView(tblCode);
		pnlCodeInput.add(scpCode);

		btnLoadMipsCode = new JButton("Load");
		btnLoadMipsCode.setBounds(373, 141, 82, 27);
		pnlCodeInput.add(btnLoadMipsCode);

		cbRT = new JComboBox();
		cbRT.setModel(new DefaultComboBoxModel(new String[]{"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15", "R16", "R17", "R18", "R19", "R20", "R21", "R22", "R23", "R24", "R25", "R26", "R27", "R28", "R29", "R30", "R31", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "F14", "F15", "F16", "F17", "F18", "F19", "F20", "F21", "F22", "F23", "F24", "F25", "F26", "F27", "F28", "F29", "F30", "F31"}));
		cbRT.setBounds(290, 169, 80, 27);
		pnlCodeInput.add(cbRT);

		cbRS = new JComboBox();
		cbRS.setModel(new DefaultComboBoxModel(new String[]{"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15", "R16", "R17", "R18", "R19", "R20", "R21", "R22", "R23", "R24", "R25", "R26", "R27", "R28", "R29", "R30", "R31", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "F14", "F15", "F16", "F17", "F18", "F19", "F20", "F21", "F22", "F23", "F24", "F25", "F26", "F27", "F28", "F29", "F30", "F31"}));
		cbRS.setBounds(208, 169, 80, 27);
		pnlCodeInput.add(cbRS);

		cbRD = new JComboBox();
		cbRD.setModel(new DefaultComboBoxModel(new String[]{"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15", "R16", "R17", "R18", "R19", "R20", "R21", "R22", "R23", "R24", "R25", "R26", "R27", "R28", "R29", "R30", "R31", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "F14", "F15", "F16", "F17", "F18", "F19", "F20", "F21", "F22", "F23", "F24", "F25", "F26", "F27", "F28", "F29", "F30", "F31"}));
		cbRD.setBounds(126, 169, 80, 27);
		pnlCodeInput.add(cbRD);

		cbInstruction = new JComboBox();
		cbInstruction.setModel(new DefaultComboBoxModel(new String[]{"DADDU", "DADDIU", "DSUBU", "DMULT", "DDIV", "LW", "SW"}));
		cbInstruction.setBounds(12, 169, 108, 27);
		pnlCodeInput.add(cbInstruction);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ADD INSTRUCTION CODE HERE
			}
		});
		btnAdd.setBounds(373, 169, 82, 27);
		pnlCodeInput.add(btnAdd);

		txtLabel = new JTextField();
		txtLabel.setText("-- LABEL --");
		txtLabel.setBounds(16, 141, 171, 26);
		pnlCodeInput.add(txtLabel);
		txtLabel.setColumns(10);

		txtImmediate = new JTextField();
		txtImmediate.setEnabled(false);
		txtImmediate.setText("IMM");
		txtImmediate.setColumns(10);
		txtImmediate.setBounds(290, 169, 80, 26);
		pnlCodeInput.add(txtImmediate);
	}
	
	private void createCodeOpcodePanel() {
		pnlOpcode = new JPanel();
		pnlOpcode.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Code Opcode Representation Panel", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlOpcode.setBounds(16, 241, 465, 175);
		pnlCode.add(pnlOpcode);
		pnlOpcode.setLayout(null);

		scpOpcode = new JScrollPane();
		scpOpcode.setBounds(18, 24, 429, 134);
		scpOpcode.setViewportView(tblOpcode);
		pnlOpcode.add(scpOpcode);
	}
	
	private void createPipelinePanel() {
		pnlPipeline = new JPanel();
		pnlPipeline.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Code Pipeline Map Panel", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlPipeline.setBounds(16, 428, 465, 198);
		pnlCode.add(pnlPipeline);
		pnlPipeline.setLayout(null);

		btnStep = new JButton("Step");
		btnStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// STEP EXECUTION CODE HERE
			}
		});
		btnStep.setBounds(86, 163, 82, 29);
		pnlPipeline.add(btnStep);

		btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// RUN EXECUTION CODE HERE
			}
		});
		btnRun.setBounds(165, 163, 82, 29);
		pnlPipeline.add(btnRun);

		btnPipelineSomething = new JButton("Pipeline Something");
		btnPipelineSomething.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// PIPELINE SOMETHING CODE HERE
			}
		});
		btnPipelineSomething.setBounds(244, 163, 127, 29);
		pnlPipeline.add(btnPipelineSomething);

		scpPipeline = new JScrollPane();
		scpPipeline.setBounds(19, 27, 426, 134);
		scpPipeline.setViewportView(tblPipeline);
		pnlPipeline.add(scpPipeline);
	}
	
	private void createRegisterMonitorPanel() {
		pnlRegister = new JPanel();
		pnlRegister.setLayout(null);
		pnlRegister.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Register Monitors", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlRegister.setBounds(527, 42, 233, 643);
		contentPane.add(pnlRegister);
		
		createGprPanel();
		createFprPanel();
	}
	
	private void createGprPanel() {
		pnlGPR = new JPanel();
		pnlGPR.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "General Purpose Registers", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlGPR.setBounds(16, 23, 200, 291);
		pnlRegister.add(pnlGPR);
		pnlGPR.setLayout(null);

		scpGPR = new JScrollPane();
		scpGPR.setBounds(17, 24, 165, 250);
		scpGPR.setViewportView(tblGPR);
		pnlGPR.add(scpGPR);
	}
	
	private void createFprPanel() {
		pnlFPR = new JPanel();
		pnlFPR.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Floating Point Registers", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlFPR.setBounds(16, 320, 200, 306);
		pnlRegister.add(pnlFPR);
		pnlFPR.setLayout(null);

		scpFPR = new JScrollPane();
		scpFPR.setBounds(18, 23, 165, 263);
		scpFPR.setViewportView(tblFPR);
		pnlFPR.add(scpFPR);
	}
	
	private void createMipsAssemblySimulationPanel() {
		pnlSimulation = new JPanel();
		pnlSimulation.setLayout(null);
		pnlSimulation.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "MIPS Assembly Simulation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlSimulation.setBounds(772, 42, 460, 643);
		contentPane.add(pnlSimulation);
		
		createMemoryAddressingPanel();
		createCodeSegmentPanel();
		createClockCyclePanel();
	}
	
	private void createMemoryAddressingPanel() {
		pnlAddress = new JPanel();
		pnlAddress.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Memory Addressing Simulation", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlAddress.setBounds(16, 23, 426, 183);
		pnlSimulation.add(pnlAddress);
		pnlAddress.setLayout(null);

		scpMemory = new JScrollPane();
		scpMemory.setBounds(17, 22, 389, 143);
		scpMemory.setViewportView(tblMemory);
		pnlAddress.add(scpMemory);
	}
	
	private void createCodeSegmentPanel() {
		pnlCodeSeg = new JPanel();
		pnlCodeSeg.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Code Segment Simulation", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlCodeSeg.setBounds(16, 218, 426, 183);
		pnlSimulation.add(pnlCodeSeg);
		pnlCodeSeg.setLayout(null);

		scpCodeSeg = new JScrollPane();
		scpCodeSeg.setBounds(17, 21, 389, 143);
		scpCodeSeg.setViewportView(tblCodeSeg);
		pnlCodeSeg.add(scpCodeSeg);
	}
	
	private void createClockCyclePanel() {
		pnlCycle = new JPanel();
		pnlCycle.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Clock Cycle Simulation", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlCycle.setBounds(16, 413, 426, 213);
		pnlSimulation.add(pnlCycle);
		pnlCycle.setLayout(null);

		scpClock = new JScrollPane();
		scpClock.setBounds(18, 23, 389, 172);
		scpClock.setViewportView(tblClock);
		pnlCycle.add(scpClock);
	}

	private void createTables() {
		tblmodCode = new UneditableTableModel();
		tblCode = new JTable(tblmodCode);
		resetTblModCode();
		
		tblmodOpcode = new UneditableTableModel();
		tblOpcode = new JTable(tblmodOpcode);
		resetTblModOpcode();
		
		tblmodPipeline = new UneditableTableModel();
		tblPipeline = new JTable(tblmodPipeline);
		resetTblModPipeline();
		
		tblmodGPR = new PartialEditableTableModel(new boolean[]{false,true});
		tblGPR = new JTable(tblmodGPR);
		tblmodFPR = new PartialEditableTableModel(new boolean[]{false,true});
		tblFPR = new JTable(tblmodFPR);
		resetRegisterMonitor();
		
		tblmodMemory = new PartialEditableTableModel(new boolean[]{false,true,true});
		tblMemory = new JTable(tblmodMemory);
		tblmodCodeSeg = new UneditableTableModel();
		tblCodeSeg = new JTable(tblmodCodeSeg);
		resetMemory();
		
		tblmodClock = new UneditableTableModel();
		tblClock = new JTable(tblmodClock);
		resetTblModClock();
	}

	public void resetTblModCode() {
		Object codeRow[][] = {};
		Object codeCol[] = {"LABEL", "INSTRUCTION"};
		tblmodCode.setDataVector(codeRow, codeCol);		
		tblCode.setModel(tblmodCode);
		tblCode.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblCode.getColumnModel().getColumn(1).setPreferredWidth(200);
	}
	
	public void resetTblModOpcode() {		
		Object opcodeRow[][] = {};
		/*{{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
				{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
				{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
				{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
				{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
				{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
				{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
				{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
				{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
				{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
				{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"}};*/
		Object opcodeCol[] = {"INSTRUCTION", "OPCODE", "IR(0..5)", "IR(6..10)", "IR(11..15)", "IR(16..31)"};
		tblmodOpcode.setDataVector(opcodeRow, opcodeCol);
		tblOpcode.setModel(tblmodOpcode);
		tblOpcode.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	public void resetTblModPipeline() {
		Object pipelineRow[][] = {};
		/*{{"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
				{"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
				{"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
				{"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
				{"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
				{"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
				{"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
				{"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"}};*/
		Object pipelineCol[] = {"INSTRUCTION", "Cycle 1", "Cycle 2", "Cycle 3", "Cycle 4", "Cycle 5"};
		tblmodPipeline.setDataVector(pipelineRow, pipelineCol);
		tblPipeline.setModel(tblmodPipeline);
		tblPipeline.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	}
	
	public void resetRegisterMonitor() {
		Object gprRow[][] = {};
		Object gprCol[] = {"REGISTER", "CONTENT"};
		tblmodGPR.setDataVector(gprRow, gprCol);
		tblGPR.setModel(tblmodGPR);
		
		Object fprRow[][] = {};
		Object fprCol[] = {"REGISTER", "CONTENTS"};
		tblmodFPR.setDataVector(fprRow, fprCol);
		tblFPR.setModel(tblmodFPR);
		
		GuiUpdater.createInitialRegisterMonitor();
	}

	public void resetMemory() {
		Object memoryRow[][] = {};
		Object memoryCol[] = {"ADDRESS", "DATA", "VARIABLE"};
		tblmodMemory.setDataVector(memoryRow, memoryCol);
		tblMemory.setModel(tblmodMemory);
		
		Object codesegRow[][] = {};
		Object codesegCol[] = {"ADDRESS", "REPRESENTATION", "LABEL", "INSTRUCTION"};
		tblmodCodeSeg.setDataVector(codesegRow, codesegCol);
		tblCodeSeg.setModel(tblmodCodeSeg);
		tblCodeSeg.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		GuiUpdater.createInitialMemory();
	}
	
	public void resetTblModClock() {
		Object clockRow[][] = {{"IF", "", "", "", ""},
				{"IF/ID.IR", "", "", "", ""},
				{"IF/ID.NPC", "", "", "", ""},
				{"PC", "", "", "", ""},
				{"ID/EX.IR", "", "", "", ""},
				{"ID/EX.A", "", "", "", ""},
				{"ID/EX.B", "", "", "", ""},
				{"ID/EX.Imm", "", "", "", ""}};
		Object clockCol[] = {"", "Cycle 1", "Cycle 2", "Cycle 3", "Cycle 4", "Cycle 5"};
		tblmodClock.setDataVector(clockRow, clockCol);
		tblClock.setModel(tblmodClock);
		tblClock.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	public String getCbInstructionSelected() {
		return cbInstruction.getSelectedItem().toString().toUpperCase();
	}

	public void inputRType() {
		txtImmediate.setEnabled(false);
		txtImmediate.setVisible(false);
		cbRT.setEnabled(true);
		cbRT.setVisible(true);
	}
	
	public void inputIType() {
		txtImmediate.setEnabled(true);
		txtImmediate.setVisible(true);
		cbRT.setEnabled(false);
		cbRT.setVisible(false);
	}
	
	public void addBtnLoadMipsCodeActionListener(ActionListener l) {
		btnLoadMipsCode.addActionListener(l);
	}

	public void addCbInstructionActionListener(ActionListener l) {
		cbInstruction.addActionListener(l);
	}

	public static UneditableTableModel getTblModCode() {
		return tblmodCode;
	}
	
	public static PartialEditableTableModel getTblModGPR() {
		return tblmodGPR;
	}
	
	public static PartialEditableTableModel getTblModFPR() {
		return tblmodFPR;
	}
	
	public static PartialEditableTableModel getTblModMemory() {
		return tblmodMemory;
	}
	
	public static UneditableTableModel getTblModCodeSeg() {
		return tblmodCodeSeg;
	}

	class UneditableTableModel extends DefaultTableModel {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
	
	class PartialEditableTableModel extends DefaultTableModel {
		boolean[] canEdit;
		
		public PartialEditableTableModel(boolean[] canEdit) {
			this.canEdit = canEdit;
		}
		@Override
		public boolean isCellEditable(int row, int col) {
			return canEdit[col];
		}
	}
}
