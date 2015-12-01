package Instruction.RType;

import Instruction.Instruction;

public abstract class RType extends Instruction {

    protected int rd, rs, rt;

    public RType(String addr, int rd, int rs, int rt) {
        this.rd = rd;
        this.rs = rs;
        this.rt = rt;
    }

    /**
     * @return the rd
     */
    public int getRd() {
        return rd;
    }

    /**
     * @param rd the rd to set
     */
    public void setRd(int rd) {
        this.rd = rd;
    }

    /**
     * @return the rs
     */
    public int getRs() {
        return rs;
    }

    /**
     * @param rs the rs to set
     */
    public void setRs(int rs) {
        this.rs = rs;
    }

    /**
     * @return the rt
     */
    public int getRt() {
        return rt;
    }

    /**
     * @param rt the rt to set
     */
    public void setRt(int rt) {
        this.rt = rt;
    }

}
