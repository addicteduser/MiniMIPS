package Instruction.IType;

import Table.CachedTables;
import Instruction.Instruction;
import Helper.NumberBuilder;
import java.math.BigInteger;

public class BEQ extends IType {

    private int cond;

    public BEQ(String addr, int rd, int rs, int rt, String immORoffset) {
        super(addr, rd, rs, rt, immORoffset);
    }

    @Override
    public boolean haveDataHazard(int rd) {
        if (this.getRs() == rd || this.getRt() == rd) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the cond
     */
    public int isCond() {
        return cond;
    }

    /**
     * @param cond the cond to set
     */
    public void setCond(int cond) {
        this.cond = cond;
    }

    @Override
    public String ALU(CachedTables ct) {
        String destination = this.getImmORoffset(); //this is not imm but this is label. siname lang yung name. hehez.
        int BEQindex;
        String result = "";
        BigInteger rs, rt;
        
        rs = new BigInteger(ct.getRtc().getRegisterRow(this.getRs()),16);
        rt = new BigInteger(ct.getRtc().getRegisterRow(this.getRt()),16);

        if (rs.equals(rt)) {
            setCond(1);
            this.cond = 1;
            BEQindex = this.specialFunction(ct);
        } else {
            setCond(0);
            this.cond = 0;
            BEQindex = this.insNumber + 1;
        }
        result = ct.getCtc().getCodeLine(BEQindex).getAddress();
        result = NumberBuilder.hexToNbit(result, 16);
        return result;
    }

    @Override
    public int specialFunction(CachedTables ct) {
        String destination;
        destination = this.getImmORoffset(); //this is the label..ginawa lang ImmORoffset yung name.
        if (isCond() == 1) {
            for (int i = 0; i <= 2047; i++) {
                if (ct.getCtc().getCodeLine(i).getLabel().equals(destination)) {
                    return i; //if cond 1 hanap label return index
                }
            }
        }
        return (this.getInsNumber() + 1);  //if con is false.  return this.getInstruction number + 1
    }

}
