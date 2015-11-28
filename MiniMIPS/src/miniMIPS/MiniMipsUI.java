package miniMIPS;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Element;

import helper.TableCellListener;

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

	//private static JTable tblCode;
	private static JTable tblCodeSeg;
	private static JTable tblOpcode;
	private static JTable tblPipeline;
	private static JTable tblGPR;
	private static JTable tblFPR;
	private static JTable tblMemory;
	private static JTable tblClock;

	//private static UneditableTableModel tblmodCode;
	private static UneditableTableModel tblmodOpcode;
	private static UneditableTableModel tblmodPipeline;
	private static PartialEditableTableModel tblmodGPR;
	private static PartialEditableTableModel tblmodFPR;
	private static PartialEditableTableModel tblmodMemory;
	private static UneditableTableModel tblmodCodeSeg;
	private static UneditableTableModel tblmodClock;
	private JButton btnStart;
	private static JTextArea txtCode;
	private static JTextArea lines;

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

		lines = new JTextArea("1");
		lines.setBackground(Color.LIGHT_GRAY);
		lines.setFont(new Font("Monospaced", Font.PLAIN, 12));
		lines.setEditable(false);
		
		txtCode = new JTextArea();
		txtCode.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtCode.setBounds(0, 0, 4, 22);
		txtCode.getDocument().addDocumentListener(new DocumentListener(){
			public String getText(){
				int caretPosition = txtCode.getDocument().getLength();
				Element root = txtCode.getDocument().getDefaultRootElement();
				String text = "1" + System.getProperty("line.separator");
				for(int i = 2; i < root.getElementIndex( caretPosition ) + 2; i++){
					text += i + System.getProperty("line.separator");
				}
				return text;
			}
			@Override
			public void changedUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
			@Override
			public void insertUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
			@Override
			public void removeUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
		});
		
		scpCode = new JScrollPane();
		scpCode.setBounds(18, 21, 430, 137);
		scpCode.setViewportView(txtCode);
		scpCode.setRowHeaderView(lines);
		pnlCodeInput.add(scpCode);		

		btnLoadMipsCode = new JButton("Load");
		btnLoadMipsCode.setBounds(378, 169, 70, 27);
		pnlCodeInput.add(btnLoadMipsCode);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(18, 169, 70, 27);
		pnlCodeInput.add(btnStart);
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
		//tblmodCode = new UneditableTableModel();
		//tblCode = new JTable(tblmodCode);
		//resetTblModCode();

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

//	public static void resetTblModCode() {
//		Object codeRow[][] = {};
//		Object codeCol[] = {"LABEL", "INSTRUCTION"};
//		tblmodCode.setDataVector(codeRow, codeCol);		
//		tblCode.setModel(tblmodCode);
//		tblCode.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		tblCode.getColumnModel().getColumn(1).setPreferredWidth(200);
//	}

	public static void resetTblModOpcode() {		
		Object opcodeRow[][] = {};
		Object opcodeCol[] = {"INSTRUCTION", "OPCODE", "IR(0..5)", "IR(6..10)", "IR(11..15)", "IR(16..31)"};
		tblmodOpcode.setDataVector(opcodeRow, opcodeCol);
		tblOpcode.setModel(tblmodOpcode);
		tblOpcode.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	public static void resetTblModPipeline() {
		Object pipelineRow[][] = {};
		Object pipelineCol[] = {"INSTRUCTION", "Cycle 1", "Cycle 2", "Cycle 3", "Cycle 4", "Cycle 5"};
		tblmodPipeline.setDataVector(pipelineRow, pipelineCol);
		tblPipeline.setModel(tblmodPipeline);
		tblPipeline.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	}

	public static void resetRegisterMonitor() {
		Object gprRow[][] = {};
		Object gprCol[] = {"GPR", "CONTENT"};
		tblmodGPR.setDataVector(gprRow, gprCol);
		tblGPR.setModel(tblmodGPR);

		Object fprRow[][] = {};
		Object fprCol[] = {"FPR", "CONTENT"};
		tblmodFPR.setDataVector(fprRow, fprCol);
		tblFPR.setModel(tblmodFPR);

		GuiUpdater.createInitialRegisterMonitor();
	}

	public static void resetMemory() {
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

	public static void resetTblModClock() {
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

	public void addBtnLoadMipsCodeActionListener(ActionListener l) {
		btnLoadMipsCode.addActionListener(l);
	}
	
	public void addBtnStartActionListener(ActionListener l) {
		btnStart.addActionListener(l);
	}
	
	public void addMemoryTblCellListener(Action l) {
		new TableCellListener(tblMemory, l);
	}
	
	public void addGprTblCellListener(Action l) {
		new TableCellListener(tblGPR, l);
	}
	
	public void addFprTblCellListener(Action l) {
		new TableCellListener(tblFPR, l);
	}
	
	public void addTextAreaFocusListener(WindowAdapter l) {
		this.addWindowFocusListener(l);		
	}

//	public static UneditableTableModel getTblModCode() {
//		return tblmodCode;
//	}

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

	public static UneditableTableModel getTblModOpcode() {
		return tblmodOpcode;
	}

	public class UneditableTableModel extends DefaultTableModel {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}

	public class PartialEditableTableModel extends DefaultTableModel {
		boolean[] canEdit;

		public PartialEditableTableModel(boolean[] canEdit) {
			this.canEdit = canEdit;
		}
		@Override
		public boolean isCellEditable(int row, int col) {
			return canEdit[col];
		}
	}

	public String getTxtInput() {
		return txtCode.getText();
	}
	
	public static void appendTxtInput(String str) {
		txtCode.append(str);
	}

	public static void resetTxtInput() {
		txtCode.setText("");
	}

	/**
	 * @return the txtCode
	 */
	public JTextArea getTxtCode() {
		return txtCode;
	}

	/**
	 * @param txtCode the txtCode to set
	 */
	public static void setTxtCode(JTextArea txtCode) {
		MiniMipsUI.txtCode = txtCode;
	}
}
