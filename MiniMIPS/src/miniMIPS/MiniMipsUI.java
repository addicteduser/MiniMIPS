package miniMIPS;

import Helper.Gui_Data;
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
    private JComboBox cdRT;
    private JComboBox cdRS;
    private JComboBox cdRD;
    private JComboBox comboBox;
    private JButton btnAdd;
    private JScrollPane scpOpcode;
    private JButton btnStep;
    private JButton btnRun;
    private JButton btnPipelineSomething;
    private JScrollPane scpPipeline;
    private JScrollPane scpGRP;
    private JScrollPane scpFPR;
    private JScrollPane scpClock;
    private JScrollPane scpCodeSeg;
    private JScrollPane scpMemory;

    private static JTable tblCode;
    private JTable tblCodeSeg;
    private JTable tblOpcode;
    private JTable tblPipeline;
    private JTable tblGRP;
    private JTable tblFPR;
    private JTable tblMemory;
    private JTable tblClock;

    private boolean hasRegisterSet = false;
    private static UneditableTableModel tblmodCode;
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
        initGUI();
    }

    private void initGUI() {
        // ******************* TABLES FOR GUI - START *******************
        tblmodCode = new UneditableTableModel();
        Object codeRow[][] = {};
        	/*{{"TEST", "LH", "R1", "R5(1000)", "--"},
        {"--", "DADDU", "R1", "R5", "R3"},
        {"--", "DADDU", "R1", "R5", "R3"},
        {"--", "DADDU", "R1", "R5", "R3"},
        {"--", "DADDU", "R1", "R5", "R3"},
        {"--", "DADDU", "R1", "R5", "R3"},
        {"--", "DADDU", "R1", "R5", "R3"},
        {"--", "DADDU", "R1", "R5", "R3"},
        {"--", "DADDU", "R1", "R5", "R3"},
        {"--", "DADDU", "R1", "R5", "R3"},
        {"--", "DADDU", "R1", "R5", "R3"}};*/
        Object codeCol[] = {"LABEL", "INSTRUCTION"};
        tblmodCode.setDataVector(codeRow, codeCol);

        UneditableTableModel tblmodOpcode = new UneditableTableModel();
        Object opcodeRow[][] = {{"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
        {"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
        {"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
        {"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
        {"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
        {"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
        {"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
        {"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
        {"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
        {"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"},
        {"DADDU R5,R6,R7", "DC081111", "00001", "10110", "010100", "1111111"}};
        Object opcodeCol[] = {"INSTRUCTION", "OPCODE", "IR(0..5)", "IR(6..10)", "IR(11..15)", "IR(16..31)"};
        tblmodOpcode.setDataVector(opcodeRow, opcodeCol);

        UneditableTableModel tblmodPipeline = new UneditableTableModel();
        Object pipelineRow[][] = {{"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
        {"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
        {"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
        {"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
        {"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
        {"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
        {"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"},
        {"DADDU R5,R6,R7", "IF", "ID", "EX", "MEM", "WB"}};
        Object pipelineCol[] = {"INSTRUCTION", "Cycle 1", "Cycle 2", "Cycle 3", "Cycle 4", "Cycle 5"};
        tblmodPipeline.setDataVector(pipelineRow, pipelineCol);

        DefaultTableModel tblmodGRP = new DefaultTableModel();
        Object grpRow[][] = {{"R0", "00000000"},
        {"R1", "00000000"}, {"R2", "00000000"}, {"R3", "00000000"}, {"R4", "00000000"},
        {"R5", "00000000"}, {"R6", "00000000"}, {"R7", "00000000"}, {"R8", "00000000"},
        {"R9", "00000000"}, {"R10", "00000000"}, {"R11", "00000000"}, {"R12", "00000000"},
        {"R13", "00000000"}, {"R14", "00000000"}, {"R15", "00000000"}, {"R16", "00000000"},
        {"R17", "00000000"}, {"R18", "00000000"}, {"R19", "00000000"}, {"R20", "00000000"},
        {"R21", "00000000"}, {"R22", "00000000"}, {"R23", "00000000"}, {"R24", "00000000"},
        {"R25", "00000000"}, {"R26", "00000000"}, {"R27", "00000000"}, {"R28", "00000000"},
        {"R29", "00000000"}, {"R30", "00000000"}, {"R31", "00000000"},
        {"LO", "00000000"}, {"HI", "00000000"}
        };
        Object grpCol[] = {"REGISTER", "CONTENTS"};
        tblmodGRP.setDataVector(grpRow, grpCol);

        DefaultTableModel tblmodFPR = new DefaultTableModel();
        Object fprRow[][] = {{"F0", "00000000"},
        {"F1", "00000000"}, {"F2", "00000000"}, {"F3", "00000000"}, {"F4", "00000000"},
        {"F5", "00000000"}, {"F6", "00000000"}, {"F7", "00000000"}, {"F8", "00000000"},
        {"F9", "00000000"}, {"F10", "00000000"}, {"F11", "00000000"}, {"F12", "00000000"},
        {"F13", "00000000"}, {"F14", "00000000"}, {"F15", "00000000"}, {"F16", "00000000"},
        {"F17", "00000000"}, {"F18", "00000000"}, {"F19", "00000000"}, {"F20", "00000000"},
        {"F21", "00000000"}, {"F22", "00000000"}, {"F23", "00000000"}, {"F24", "00000000"},
        {"F25", "00000000"}, {"F26", "00000000"}, {"F27", "00000000"}, {"F28", "00000000"},
        {"F29", "00000000"}, {"F30", "00000000"}, {"F31", "00000000"}};
        Object fprCol[] = {"REGISTER", "CONTENTS"};
        tblmodFPR.setDataVector(fprRow, fprCol);

        UneditableTableModel tblmodMemory = new UneditableTableModel();
        Object memoryRow[][] = {{"00000000", "00000000", "00000004"},
        {"00000000", "00000000", "00000004"},
        {"00000000", "00000000", "00000004"},
        {"00000000", "00000000", "00000004"},
        {"00000000", "00000000", "00000004"},
        {"00000000", "00000000", "00000004"},
        {"00000000", "00000000", "00000004"},
        {"00000000", "00000000", "00000004"},
        {"00000000", "00000000", "00000004"},
        {"00000000", "00000000", "00000004"}};
        Object memoryCol[] = {"ADDRESS", "BEFORE EXECUTION", "AFTER EXECUTION"};
        tblmodMemory.setDataVector(memoryRow, memoryCol);

        UneditableTableModel tblmodCodeSeg = new UneditableTableModel();
        Object codesegRow[][] = {{"00000000", "DC091100", "--", "DADDU R1,R2,R3"},
        {"00000000", "DC091100", "--", "DADDU R1,R2,R3"},
        {"00000000", "DC091100", "--", "DADDU R1,R2,R3"},
        {"00000000", "DC091100", "--", "DADDU R1,R2,R3"},
        {"00000000", "DC091100", "--", "DADDU R1,R2,R3"},
        {"00000000", "DC091100", "--", "DADDU R1,R2,R3"},
        {"00000000", "DC091100", "--", "DADDU R1,R2,R3"},
        {"00000000", "DC091100", "--", "DADDU R1,R2,R3"},
        {"00000000", "DC091100", "--", "DADDU R1,R2,R3"},
        {"00000000", "DC091100", "--", "DADDU R1,R2,R3"},
        {"00000000", "DC091100", "--", "DADDU R1,R2,R3"}};
        Object codesegCol[] = {"ADDRESS", "REPRESENTATION", "LABEL", "INSTRUCTION"};
        tblmodCodeSeg.setDataVector(codesegRow, codesegCol);

        UneditableTableModel tblmodClock = new UneditableTableModel();
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

        // ******************* TABLES FOR GUI - START *******************
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

        pnlCode = new JPanel();
        pnlCode.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "MIPS Assembly Code", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnlCode.setBounds(16, 42, 499, 643);
        contentPane.add(pnlCode);
        pnlCode.setLayout(null);

        pnlCodeInput = new JPanel();
        pnlCodeInput.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Code Input Panel", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        pnlCodeInput.setBounds(16, 23, 465, 206);
        pnlCode.add(pnlCodeInput);
        pnlCodeInput.setLayout(null);

        scpCode = new JScrollPane();
        scpCode.setBounds(16, 21, 310, 145);
        tblCode = new JTable(tblmodCode);
        tblCode.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblCode.getColumnModel().getColumn(1).setPreferredWidth(200);
        scpCode.setViewportView(tblCode);
        pnlCodeInput.add(scpCode);

        btnLoadMipsCode = new JButton("Load");
        btnLoadMipsCode.setBounds(331, 75, 121, 38);
        pnlCodeInput.add(btnLoadMipsCode);

        cdRT = new JComboBox();
        cdRT.setModel(new DefaultComboBoxModel(new String[]{"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15", "R16", "R17", "R18", "R19", "R20", "R21", "R22", "R23", "R24", "R25", "R26", "R27", "R28", "R29", "R30", "R31", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "F14", "F15", "F16", "F17", "F18", "F19", "F20", "F21", "F22", "F23", "F24", "F25", "F26", "F27", "F28", "F29", "F30", "F31"}));
        cdRT.setBounds(290, 169, 80, 27);
        pnlCodeInput.add(cdRT);

        cdRS = new JComboBox();
        cdRS.setModel(new DefaultComboBoxModel(new String[]{"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15", "R16", "R17", "R18", "R19", "R20", "R21", "R22", "R23", "R24", "R25", "R26", "R27", "R28", "R29", "R30", "R31", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "F14", "F15", "F16", "F17", "F18", "F19", "F20", "F21", "F22", "F23", "F24", "F25", "F26", "F27", "F28", "F29", "F30", "F31"}));
        cdRS.setBounds(208, 169, 80, 27);
        pnlCodeInput.add(cdRS);

        cdRD = new JComboBox();
        cdRD.setModel(new DefaultComboBoxModel(new String[]{"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15", "R16", "R17", "R18", "R19", "R20", "R21", "R22", "R23", "R24", "R25", "R26", "R27", "R28", "R29", "R30", "R31", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "F14", "F15", "F16", "F17", "F18", "F19", "F20", "F21", "F22", "F23", "F24", "F25", "F26", "F27", "F28", "F29", "F30", "F31"}));
        cdRD.setBounds(126, 169, 80, 27);
        pnlCodeInput.add(cdRD);

        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"DADDU", "DADDIU", "DSUBU", "DMULT", "DDIV", "LW", "SW"}));
        comboBox.setBounds(12, 169, 108, 27);
        pnlCodeInput.add(comboBox);

        btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ADD INSTRUCTION CODE HERE
            }
        });
        btnAdd.setBounds(372, 168, 82, 29);
        pnlCodeInput.add(btnAdd);

        pnlOpcode = new JPanel();
        pnlOpcode.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Code Opcode Representation Panel", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnlOpcode.setBounds(16, 241, 465, 175);
        pnlCode.add(pnlOpcode);
        pnlOpcode.setLayout(null);

        scpOpcode = new JScrollPane();
        scpOpcode.setBounds(18, 24, 429, 134);
        tblOpcode = new JTable(tblmodOpcode);
        tblOpcode.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scpOpcode.setViewportView(tblOpcode);
        pnlOpcode.add(scpOpcode);

        pnlPipeline = new JPanel();
        pnlPipeline.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Code Pipeline Map Panel", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnlPipeline.setBounds(16, 428, 465, 198);
        pnlCode.add(pnlPipeline);
        pnlPipeline.setLayout(null);

        btnStep = new JButton("Step");
        btnStep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (hasRegisterSet == false) {
                    hasRegisterSet = true;
                    Gui_Data.setFPointRegister(tblFPR.getModel());
                    Gui_Data.setGenRegister(tblGRP.getModel());
                }

                // STEP EXECUTION CODE HERE
            }
        });
        btnStep.setBounds(86, 163, 82, 29);
        pnlPipeline.add(btnStep);

        btnRun = new JButton("Run");
        btnRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                /**
                 * Make sure that register is set once.
                 */
                if (hasRegisterSet == false) {
                    hasRegisterSet = true;
                    Gui_Data.setFPointRegister(tblFPR.getModel());
                    Gui_Data.setGenRegister(tblGRP.getModel());
                }
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
        tblPipeline = new JTable(tblmodPipeline);
        tblPipeline.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scpPipeline.setViewportView(tblPipeline);
        pnlPipeline.add(scpPipeline);

        pnlRegister = new JPanel();
        pnlRegister.setLayout(null);
        pnlRegister.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Register Monitors", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnlRegister.setBounds(527, 42, 233, 643);
        contentPane.add(pnlRegister);

        pnlGPR = new JPanel();
        pnlGPR.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "General Purpose Registers", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnlGPR.setBounds(16, 23, 200, 291);
        pnlRegister.add(pnlGPR);
        pnlGPR.setLayout(null);

        scpGRP = new JScrollPane();
        scpGRP.setBounds(17, 24, 165, 250);
        tblGRP = new JTable(tblmodGRP);
        scpGRP.setViewportView(tblGRP);
        pnlGPR.add(scpGRP);

        pnlFPR = new JPanel();
        pnlFPR.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Floating Point Registers", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnlFPR.setBounds(16, 320, 200, 306);
        pnlRegister.add(pnlFPR);
        pnlFPR.setLayout(null);

        scpFPR = new JScrollPane();
        scpFPR.setBounds(18, 23, 165, 263);
        tblFPR = new JTable(tblmodFPR);
        scpFPR.setViewportView(tblFPR);
        pnlFPR.add(scpFPR);

        pnlSimulation = new JPanel();
        pnlSimulation.setLayout(null);
        pnlSimulation.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "MIPS Assembly Simulation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnlSimulation.setBounds(772, 42, 460, 643);
        contentPane.add(pnlSimulation);

        pnlAddress = new JPanel();
        pnlAddress.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Memory Addressing Simulation", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnlAddress.setBounds(16, 23, 426, 183);
        pnlSimulation.add(pnlAddress);
        pnlAddress.setLayout(null);

        scpMemory = new JScrollPane();
        scpMemory.setBounds(17, 22, 389, 143);
        tblMemory = new JTable(tblmodMemory);
        scpMemory.setViewportView(tblMemory);
        pnlAddress.add(scpMemory);

        pnlCodeSeg = new JPanel();
        pnlCodeSeg.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Code Segment Simulation", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnlCodeSeg.setBounds(16, 218, 426, 183);
        pnlSimulation.add(pnlCodeSeg);
        pnlCodeSeg.setLayout(null);

        scpCodeSeg = new JScrollPane();
        scpCodeSeg.setBounds(17, 21, 389, 143);
        tblCodeSeg = new JTable(tblmodCodeSeg);
        tblCodeSeg.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scpCodeSeg.setViewportView(tblCodeSeg);
        pnlCodeSeg.add(scpCodeSeg);

        pnlCycle = new JPanel();
        pnlCycle.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Clock Cycle Simulation", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnlCycle.setBounds(16, 413, 426, 213);
        pnlSimulation.add(pnlCycle);
        pnlCycle.setLayout(null);

        scpClock = new JScrollPane();
        scpClock.setBounds(18, 23, 389, 172);
        tblClock = new JTable(tblmodClock);
        tblClock.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scpClock.setViewportView(tblClock);

        pnlCycle.add(scpClock);
    }

	public void addBtnLoadMipsCodeActionListener(ActionListener l) {
		btnLoadMipsCode.addActionListener(l);
	}
	
	class UneditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

	/**
	 * @return the tblCode
	 */
	public static UneditableTableModel getTblModCode() {
		return tblmodCode;
	}
	
	public static void resetTblModCode() {
		Object codeRow[][] = {};
		Object codeCol[] = {"LABEL", "INSTRUCTION"};
		tblmodCode.setDataVector(codeRow, codeCol);
		tblCode.setModel(tblmodCode);
		tblCode.getColumnModel().getColumn(1).setPreferredWidth(200);
	}
}
