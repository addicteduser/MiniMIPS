package Instruction.RType;

import Table.CachedTables;
import javax.swing.table.DefaultTableModel;
import Helper.NumberBuilder;
import java.math.BigInteger;

public class AND extends RType {

    private NumberBuilder usable = new NumberBuilder();

    public AND(String addr, int rd, int rs, int rt) {
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
        String rd = "";
        String sRS, sRT;
        sRS = usable.hexStringToNBitBinary(ct.getRtc().getRegisterRow(this.getRs()), 64);
        sRT = usable.hexStringToNBitBinary(ct.getRtc().getRegisterRow(this.getRt()), 64);
        for (int i = sRS.length() - 1; i >= 0; i--) {
            if (sRS.charAt(i) == '0' || sRT.charAt(i) == '0') {
                rd = "0" + rd;
            } else {
                rd = "1" + rd;
            }
        }
        BigInteger binaryOp = new BigInteger(rd, 2);
        rd = binaryOp.toString(16);
        rd = usable.hexToNbit(rd, 16);
        return rd;
    }

    @Override
    public int specialFunction(CachedTables ct) {
        ct.getRtc().saveRegisterValueToCache(this.getRd(), this.ALU(ct).toUpperCase());
        ct.getRtc().drawToRegisterTable();
        return -1;
    }

}
