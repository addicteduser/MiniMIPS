package PipelineStage;

import Table.CachedTables;
import Instruction.Instruction;
import Helper.NumberBuilder;
import java.awt.Point;
import java.math.BigInteger;
import javax.swing.table.DefaultTableModel;

public class IDEX {

    private String IR = "";
    private String A = "";
    private String B = "";
    private String IMM = "";
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
     * @return the A
     */
    public String getA() {
        return A;
    }

    /**
     * @param A the A to set
     */
    public void setA(String A) {
        this.A = A;
    }

    /**
     * @return the B
     */
    public String getB() {
        return B;
    }

    /**
     * @param B the B to set
     */
    public void setB(String B) {
        this.B = B;
    }

    /**
     * @return the IMM
     */
    public String getIMM() {
        return IMM;
    }

    /**
     * @param IMM the IMM to set
     */
    public void setIMM(String IMM) {
        this.IMM = IMM;
    }

    public void decode(Instruction ins, CachedTables ct) {
        this.ins=ins;
        System.out.println("testing [IDEX.decode]... ");
        
        //added this line to ensure that the data will always be fresh
        ct.getRtc().refreshRegisterCacheArray(); 
        
        this.IR = ct.getOtc().geOpcodeRow(ins.getInsNumber()).getOpcode();
        int a = Integer.parseInt(ct.getOtc().geOpcodeRow(ins.getInsNumber()).getA(), 2);
        this.A = ct.getRtc().getRegisterRow(a);
        int b = Integer.parseInt(ct.getOtc().geOpcodeRow(ins.getInsNumber()).getB(), 2);
        this.B = ct.getRtc().getRegisterRow(b);
        this.IMM = ct.getOtc().geOpcodeRow(ins.getInsNumber()).getImm();
        BigInteger binaryOp = new BigInteger(this.IMM, 2);
        this.IMM = binaryOp.toString(16);
        this.IMM = NumberBuilder.hexToNbit(this.IMM, 16);
    }

    public void reDecode( CachedTables ct) {       
        //added this line to ensure that the data will always be fresh
        ct.getRtc().refreshRegisterCacheArray(); 
        
        this.IR = ct.getOtc().geOpcodeRow(ins.getInsNumber()).getOpcode();
        int a = Integer.parseInt(ct.getOtc().geOpcodeRow(ins.getInsNumber()).getA(), 2);
        this.A = ct.getRtc().getRegisterRow(a);
        int b = Integer.parseInt(ct.getOtc().geOpcodeRow(ins.getInsNumber()).getB(), 2);
        this.B = ct.getRtc().getRegisterRow(b);
        this.IMM = ct.getOtc().geOpcodeRow(ins.getInsNumber()).getImm();
        BigInteger binaryOp = new BigInteger(this.IMM, 2);
        this.IMM = binaryOp.toString(16);
        this.IMM = NumberBuilder.hexToNbit(this.IMM, 16);
    }
    
    public void drawToMap(DefaultTableModel pipelinemapmodel) {
        pipelinemapmodel.setValueAt("ID", this.position.y, this.position.x);
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
