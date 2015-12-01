package Instruction.RType;

import Helper.Usable;
import Table.CachedTables;

public class MULS extends RType {

    private Usable usable = new Usable();

    public MULS(String addr, int rd, int rs, int rt) {
        super(addr, rd, rs, rt);
    }

    @Override
    public String ALU(CachedTables ct) {
        String sFD, sFS, sFT;
        int iFS, iFT;
        float fFD, fFS, fFT;

        sFS = ct.getRtc().getFRegisterRow(this.getRs()).substring(8);
        sFT = ct.getRtc().getFRegisterRow(this.getRt()).substring(8);
        System.out.println("FS1 = " + sFS);
        System.out.println("FT1 = " + sFT);

        iFS = Long.valueOf(sFS, 16).intValue();
        iFT = Long.valueOf(sFT, 16).intValue();
        System.out.println("FS2 = " + iFS);
        System.out.println("FT2 = " + iFT);

        fFS = Float.intBitsToFloat(iFS);
        fFT = Float.intBitsToFloat(iFT);
        System.out.println("FS3 = " + fFS);
        System.out.println("FT3 = " + fFT);

        fFD = fFS * fFT;
        System.out.println("FD = " + fFD);
        sFD = Integer.toHexString(Float.floatToIntBits(fFD)).toUpperCase();
        sFD = usable.hexToNbit(sFD, 16);
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
