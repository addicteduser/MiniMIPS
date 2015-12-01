package PipelineStage;

import Table.CachedTables;
import Instruction.Instruction;
import Helper.NumberBuilder;
import java.awt.Point;
import java.math.BigInteger;
import javax.swing.table.DefaultTableModel;

public class IFID {

    private String IR;
    private String NPC;
    private String PC;
    private Point position;
    private Instruction ins;
    /**
     * @return the IR
     */
    public String getIR() {
        return IR;
    }

    /**
     * @param IR the IR to set
     */
    public void setIR(String IR) {
        this.IR = IR;
    }

    /**
     * @return the NPC
     */
    public String getNPC() {
        return NPC;
    }

    /**
     * @param NPC the NPC to set
     */
    public void setNPC(String NPC) {
        this.NPC = NPC;
    }

    /**
     * @return the PC
     */
    public String getPC() {
        return PC;
    }

    /**
     * @param PC the PC to set
     */
    public void setPC(String PC) {
        this.PC = PC;
    }

    public void fetch(Instruction ins, CachedTables ct) {
        this.ins=ins;
        this.IR = ct.getOtc().geOpcodeRow(ins.getInsNumber()).getOpcode();
        try {
            this.PC = NumberBuilder.hexToNbit(ct.getCtc().getCodeLine(ins.getInsNumber() + 1).getAddress(), 16);
            this.NPC = NumberBuilder.hexToNbit(ct.getCtc().getCodeLine(ins.getInsNumber() + 1).getAddress(), 16);
        } catch (Exception e) {
            long templong = Long.parseLong(ct.getCtc().getCodeLine(ins.getInsNumber()).getAddress(), 16) + 4;
            String tempaddress = Long.toBinaryString(templong);
            BigInteger binaryOpcode = new BigInteger(tempaddress, 2);
            String opcode = binaryOpcode.toString(16);
            String hex = NumberBuilder.hexToNbit(opcode, 16);
            this.PC = hex;
            this.NPC = hex;
        }
    }

        public void reFetch(CachedTables ct) {
        this.IR = ct.getOtc().geOpcodeRow(this.ins.getInsNumber()).getOpcode();
        try {
            this.PC = NumberBuilder.hexToNbit(ct.getCtc().getCodeLine(this.ins.getInsNumber() + 1).getAddress(), 16);
            this.NPC = NumberBuilder.hexToNbit(ct.getCtc().getCodeLine(this.ins.getInsNumber() + 1).getAddress(), 16);
        } catch (Exception e) {
            long templong = Long.parseLong(ct.getCtc().getCodeLine(this.ins.getInsNumber()).getAddress(), 16) + 4;
            String tempaddress = Long.toBinaryString(templong);
            BigInteger binaryOpcode = new BigInteger(tempaddress, 2);
            String opcode = binaryOpcode.toString(16);
            String hex = NumberBuilder.hexToNbit(opcode, 16);
            this.PC = hex;
            this.NPC = hex;
        }
    }
        
    public void drawToMap(DefaultTableModel pipelinemapmodel) {
        pipelinemapmodel.setValueAt("IF", this.position.y, this.position.x);
    }

    public void drawStall(DefaultTableModel pipelinemapmodel) {
        pipelinemapmodel.setValueAt("*", this.position.y, this.position.x);
    }

    /**
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Point position) {
        this.position = position;
    }
}
