package Instruction.IType;

import Instruction.Instruction;
import Instruction.RType.RType;

public abstract class IType extends RType{
    private String immORoffset;

    public IType(String addr, int rd, int rs, int rt, String immORoffset) {
        super(addr, rd, rs, rt);
        this.immORoffset=immORoffset;
    }
    
    /**
     * @return the immORoffset
     */
    public String getImmORoffset() {
        return immORoffset;
    }

    /**
     * @param immORoffset the immORoffset to set
     */
    public void setImmORoffset(String immORoffset) {
        this.immORoffset = immORoffset;
    }

    
}
