package Instruction.RType;

import Table.CachedTables;
import Helper.NumberBuilder;
import java.math.BigInteger;

public class DADDU extends RType {

    public DADDU(String addr, int rd, int rs, int rt) {
        super(addr, rd, rs, rt);
    }

    @Override
    public String ALU(CachedTables ct) {
        String rd = "";
        //long rs, rt, rd3;
        
        BigInteger rs, rt, rd3;
        
        rs = new BigInteger(ct.getRtc().getRegisterRow(this.getRs()),16);
        rt = new BigInteger(ct.getRtc().getRegisterRow(this.getRt()),16);
        
        rd3 = rs.add(rt);
        rd = rd3.toString(2);

        BigInteger binaryOp = new BigInteger(rd, 2);
        String result = "";
        result = binaryOp.toString(16);
        result = new NumberBuilder().hexToNbit(result, 16); //don't forget to instantiate the NumberBuilder class
        System.out.println(result + "ALU DADDU");
        return result;
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
