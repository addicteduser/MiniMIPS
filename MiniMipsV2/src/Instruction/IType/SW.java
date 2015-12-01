package Instruction.IType;

import Table.CachedTables;
import java.math.BigInteger;
import Helper.NumberBuilder;

public class SW extends IType {

    public SW(String addr, int rd, int rs, int rt, String immORoffset) {
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

    @Override
    public String ALU(CachedTables ct) {
        String sIMM, sALU;
        
        BigInteger rs, rt, imm, alu;
        
        rs = new BigInteger(ct.getRtc().getRegisterRow(this.getRs()),16);
        rt = new BigInteger(ct.getRtc().getRegisterRow(this.getRt()),16);
        sIMM = ct.getOtc().geOpcodeRow(this.insNumber).getImm();
        imm = new BigInteger(sIMM, 2);//to make binary to decimal
        alu = imm.add(rs);
        sALU = alu.toString(2);
        BigInteger binaryOp = new BigInteger(sALU, 2);
        sALU = binaryOp.toString(16);
        sALU = NumberBuilder.hexToNbit(sALU, 16);
        return sALU;
    }

    @Override
   public int specialFunction(CachedTables ct) {
        int start = 0;
        start = ct.getDtc().findAddrLocation(this.ALU(ct).substring(12));
        ct.getRtc().getRegisterRow(this.getRt());
        ct.getDtc().writeToMemoryCache(ct.getRtc().getRegisterRow(this.getRt()), start); //rt
        //ct.getDtc().drawToTable();
        return -1;
    }

}
