package PipelineStage;

import Table.CachedTables;
import Instruction.IType.BEQ;
import Instruction.IType.LS;
import Instruction.IType.SS;
import Instruction.IType.SW;
import Instruction.Instruction;
import Instruction.JType.J;
import Instruction.RType.ADDS;
import Instruction.RType.DMULT;
import Instruction.RType.MULS;
import Instruction.RType.RType;
import java.awt.Point;
import javax.swing.table.DefaultTableModel;

public class WB {
    private String affectedRegister="N/A";
    private Point position;
    private Instruction ins;
    /**
     * @return the affectedRegister
     */
    public String getAffectedRegister() {
        return affectedRegister;
    }

    /**
     * @param affectedRegister the affectedRegister to set
     */
    public void setAffectedRegister(String affectedRegister) {
        this.affectedRegister = affectedRegister;
    }
    public void writeback(Instruction ins, CachedTables ct){
        this.ins=ins;
        if(ins instanceof BEQ || ins instanceof SW || ins instanceof J){
            if(ins instanceof SW){
                ins.specialFunction(ct);
            }
            this.affectedRegister="N/A";  
        }
        else if(ins instanceof DMULT){
            this.affectedRegister="HI/LO"+"= "+ins.ALU(ct);
            ins.specialFunction(ct);
        }
        else{
            try{
                if(ins instanceof ADDS || ins instanceof MULS || ins instanceof LS)
                    this.affectedRegister="F"+((RType)ins).getRd()+"= "+ins.ALU(ct);
                else
                    this.affectedRegister="R"+((RType)ins).getRd()+"= "+ins.ALU(ct);
            }catch(Exception e){
                if(ins instanceof ADDS || ins instanceof MULS || ins instanceof LS)
                    this.affectedRegister="F"+((RType)ins).getRd()+"= "+ins.ALU(ct);
                else
                    this.affectedRegister="R"+((RType)ins).getRd()+"= "+ins.ALU(ct);
            }
            ins.specialFunction(ct);
        }
    }
    
        public void reWriteback( CachedTables ct){
        if(ins instanceof BEQ || ins instanceof SW || ins instanceof J || ins instanceof SS){
            if(ins instanceof SW || ins instanceof SS){
                ins.specialFunction(ct);
            }
            this.affectedRegister="N/A";  
        }
        else if(ins instanceof DMULT){
            this.affectedRegister="HI/LO"+"= "+ins.ALU(ct);
            ins.specialFunction(ct);
        }
        else{
            try{
                if(ins instanceof ADDS || ins instanceof MULS || ins instanceof LS)
                    this.affectedRegister="F"+((RType)ins).getRd()+"= "+ins.ALU(ct);
                else
                    this.affectedRegister="R"+((RType)ins).getRd()+"= "+ins.ALU(ct);
            }catch(Exception e){
                if(ins instanceof ADDS || ins instanceof MULS || ins instanceof LS)
                    this.affectedRegister="F"+((RType)ins).getRd()+"= "+ins.ALU(ct);
                else
                    this.affectedRegister="R"+((RType)ins).getRd()+"= "+ins.ALU(ct);
            }
            ins.specialFunction(ct);
        }
    }
    
 public void drawToMap(DefaultTableModel pipelinemapmodel){
        pipelinemapmodel.setValueAt("WB", this.position.y, this.position.x);
    }
    public void drawStall(DefaultTableModel pipelinemapmodel){
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
