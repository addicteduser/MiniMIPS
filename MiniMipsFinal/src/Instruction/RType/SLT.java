package Instruction.RType;

import Table.CachedTables;
import java.math.BigInteger;
import javax.swing.table.DefaultTableModel;
import Helper.NumberBuilder;

public class SLT extends RType {

    private NumberBuilder usable = new NumberBuilder();

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
        //Long rs, rt;
        //rs = Long.parseLong(ct.getRtc().getRegisterRow(this.getRs()), 16);
        //rt = Long.parseLong(ct.getRtc().getRegisterRow(this.getRt()), 16);

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
        rd = usable.hexToNbit(rd, 16);

        /*    String rs, rt;
         rs = usable.hexStringToNBitBinary(ct.getRtc().getRegisterRow(this.getRs()), 64);
         rt = usable.hexStringToNBitBinary(ct.getRtc().getRegisterRow(this.getRt()), 64);
        
         BigInteger rsbin = new BigInteger(rs, 2);
         BigInteger rtbin = new BigInteger(rt, 2);
        
         System.out.println("RS - " + rs);
         System.out.println("RT - " + rt);
        
         if(rsbin.compareTo(rtbin)==-1){
         rd = "1";
         }
            
         rd = usable.hexToNbit(rd, 16);
         System.out.println("RD - "+rd);
         */
        return rd;
    }

    @Override
    public int specialFunction(CachedTables ct) {
        ct.getRtc().saveRegisterValueToCache(this.getRd(), this.ALU(ct));
        ct.getRtc().drawToRegisterTable();
        return -1;
    }

}
