package Instruction.RType;

import Table.CachedTables;
import java.math.BigInteger;
import Helper.NumberBuilder;

public class SLT extends RType {

    public SLT(String addr, int rd, int rs, int rt) {
        super(addr, rd, rs, rt);
    }

    @Override
    public boolean haveDataHazard(int rd) {
        if (this.getRs() == rd || this.getRt() == rd) {
            return true; //
        } else {
            return false;
        }
    }

    @Override
    public String ALU(CachedTables ct) {
        String rd = "0";
        String sRS, sRT;

        BigInteger rs, rt;

        rs = new BigInteger(ct.getRtc().getRegisterRow(this.getRs()), 16);
        rt = new BigInteger(ct.getRtc().getRegisterRow(this.getRt()), 16);
        sRS = ct.getRtc().getRegisterRow(this.getRs());
        sRT = ct.getRtc().getRegisterRow(this.getRt());
  
        if (sRS.substring(0,1).equals("F") && sRT.substring(0,1).equals("0")) {
            rd = "1";
        } 
        else if (sRS.substring(0,1).equals("0") && sRT.substring(0,1).equals("F")) {
            rd = "0";
        } 
        else if (sRS.substring(0,1).equals("F") && sRT.substring(0,1).equals("F")) {
            if (rs.compareTo(rt) > 0) {
                rd = "1";
            }
        }
        else {
            
            if (rs.compareTo(rt) < 0) {
                rd = "1";
            }
        }
        rd = NumberBuilder.hexToNbit(rd, 16);
        
        return rd;
    }

    @Override
    public int specialFunction(CachedTables ct) {
        ct.getRtc().saveRegisterValueToCache(this.getRd(), this.ALU(ct));
        ct.getRtc().drawToRegisterTable();
        return -1;
    }

}
