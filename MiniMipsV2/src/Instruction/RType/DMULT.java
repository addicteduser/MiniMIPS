package Instruction.RType;

import Table.CachedTables;
import Helper.NumberBuilder;
import java.math.BigInteger;

public class DMULT extends RType {

    public DMULT(String addr, int rd, int rs, int rt) {
        super(addr, rd, rs, rt);
    }

    @Override
    public String ALU(CachedTables ct) {
        String rd = "0000000000000000", sLO, sHI;
        // Long rs, rt, lo, hi;
        //rs = Long.parseLong(ct.getRtc().getRegisterRow(this.getRs()));
        //rt = Long.parseLong(ct.getRtc().getRegisterRow(this.getRt()));
        BigInteger rd3, rs, rt, lo, hi;

        rs = new BigInteger(ct.getRtc().getRegisterRow(this.getRs()), 16);
        rt = new BigInteger(ct.getRtc().getRegisterRow(this.getRt()), 16);
        rd3 = rs.multiply(rt);
        
        String tempHiLo = NumberBuilder.hexToNbit(rd3.toString(16), 32);
        sHI = tempHiLo.substring(0, 16);
        sLO = tempHiLo.substring(16);
        rd = sHI+sLO;
        return rd;
    }

    @Override
    public int specialFunction(CachedTables ct) {
        ct.getRtc().saveRegisterValueToCache(32, this.ALU(ct).substring(16).toUpperCase());
        ct.getRtc().saveRegisterValueToCache(33, this.ALU(ct).substring(0, 16).toUpperCase());
        ct.getRtc().drawToRegisterTable();
        return -1;
    }

    @Override
    public boolean haveDataHazard(int rd) {
        if (this.getRs() == rd || this.getRt() == rd) {
            return true;
        } else {
            return false;
        }
    }

}
