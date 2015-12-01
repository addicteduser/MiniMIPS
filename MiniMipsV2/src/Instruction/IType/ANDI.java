package Instruction.IType;

import java.math.BigInteger;

import Helper.NumberBuilder;
import Table.CachedTables;

public class ANDI extends IType {
    
    public ANDI(String addr, int rd, int rs, int rt, String immORoffset) {
        super(addr, rd, rs, rt, immORoffset);
    }

    @Override
    public String ALU(CachedTables ct) {
        String rd = "";
        String sIMM;
        BigInteger rs, imm, ans;
        
        rs = new BigInteger(ct.getRtc().getRegisterRow(this.getRs()),16);
        System.out.println("RS = "+rs);
        sIMM = ct.getOtc().geOpcodeRow(this.insNumber).getImm();
        System.out.println("temp IMM 1 = "+sIMM);
        imm = new BigInteger(sIMM, 2);      
        
        ans = rs.and(imm);
        rd = NumberBuilder.hexToNbit(ans.toString(16), 16);
        
        return rd;
    }

    @Override
    public int specialFunction(CachedTables ct) {
        ct.getRtc().saveRegisterValueToCache(this.getRd(), this.ALU(ct).toUpperCase());
        ct.getRtc().drawToRegisterTable();
        return -1;
    }

    @Override
    public boolean haveDataHazard(int rd) {
        if (this.getRs() == rd) {
            return true;
        } else {
            return false;
        }
    }

}
