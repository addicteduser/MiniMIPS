package Instruction.IType;

import Table.CachedTables;
import java.math.BigInteger;
import Helper.NumberBuilder;

public class DADDIU extends IType {

    public DADDIU(String addr, int rd, int rs, int rt, String immORoffset) {
        super(addr, rd, rs, rt, immORoffset);
    }

    @Override
    public boolean haveDataHazard(int rd) {
        if (this.getRs() == rd) {
            return true;
        } else {
            return false;
        }
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

    @Override
    public String ALU(CachedTables ct) {
        BigInteger rs, ans, imm;

        String ansRT = null;
        String ansALU = null;
        String immTemp = null;

        immTemp = ct.getOtc().geOpcodeRow(this.insNumber).getImm();
        imm = new BigInteger(immTemp, 2);

        rs = new BigInteger(ct.getRtc().getRegisterRow(this.getRs()), 16);

        ans = rs.add(imm);

        ansALU = ans.toString(16);

        if (ansALU.length() > 16) {
            ansALU = ansALU.substring(ansALU.length() - 16);
        } 
        else {
            ansALU = NumberBuilder.hexToNbit(ansALU, 16);
        }

        return ansALU.toUpperCase();
    }

    @Override
    public int specialFunction(CachedTables ct) {
        ct.getRtc().saveRegisterValueToCache(this.getRd(), this.ALU(ct).toUpperCase());
        ct.getRtc().drawToRegisterTable();
        return -1;
    }

}
