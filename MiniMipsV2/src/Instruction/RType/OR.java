package Instruction.RType;

import Table.CachedTables;
import java.math.BigInteger;
import Helper.Usable;

public class OR extends RType {
    
    private Usable usable = new Usable();

    public OR(String addr, int rd, int rs, int rt) {
        super(addr, rd, rs, rt);
    }

    @Override
    public String ALU(CachedTables ct) {
        String rd = "";
        BigInteger rs, rt, ans;
        
        rs = new BigInteger(ct.getRtc().getRegisterRow(this.getRs()),16);
        rt = new BigInteger(ct.getRtc().getRegisterRow(this.getRt()),16);
        ans = rs.or(rt);
        rd = usable.hexToNbit(ans.toString(16), 16);
        
//        String rd = "";
//        String sRS, sRT;
//        sRS = usable.hexStringToNBitBinary(ct.getRtc().getRegisterRow(this.getRs()), 64);
//        sRT = usable.hexStringToNBitBinary(ct.getRtc().getRegisterRow(this.getRt()), 64);
//        for (int i = sRS.length() - 1; i >= 0; i--) {
//            if (sRS.charAt(i) == '0' && sRT.charAt(i) == '0') {
//                rd = "0" + rd;
//            } else {
//                rd = "1" + rd;
//            }
//        }
//        BigInteger binaryOp = new BigInteger(rd, 2);
//        rd = binaryOp.toString(16);
//        rd = usable.hexToNbit(rd, 16);
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
        if (this.getRs() == rd || this.getRt() == rd) {
            return true; //
        } else {
            return false;
        }
    }

}
