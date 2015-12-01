package Instruction.RType;

import Helper.NumberBuilder;
import Table.CachedTables;

public class ADDS extends RType {
   
    public ADDS(String addr, int rd, int rs, int rt) {
        super(addr, rd, rs, rt);
    }

    @Override
    public String ALU(CachedTables ct) {
        String sFD, sFS, sFT;
        int iFS, iFT;
        float fFD, fFS, fFT;
        
        sFS = ct.getRtc().getFRegisterRow(this.getRs()).substring(8);
        sFT = ct.getRtc().getFRegisterRow(this.getRt()).substring(8);
        
        iFS = Long.valueOf(sFS,16).intValue();
        iFT = Long.valueOf(sFT,16).intValue();

        fFS = Float.intBitsToFloat(iFS);
        fFT = Float.intBitsToFloat(iFT);

        fFD = fFS + fFT;
        sFD = Integer.toHexString(Float.floatToIntBits(fFD)).toUpperCase();
        sFD = NumberBuilder.hexToNbit(sFD, 16);
        return sFD;
    }

    @Override
    public int specialFunction(CachedTables ct) {
        ct.getRtc().saveFRegisterValueToCache(this.getRd(), this.ALU(ct).toUpperCase());
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
