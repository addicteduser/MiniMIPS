package CodeObjects.IType;

import Caches.CachedTables;
import Functions.Usable;
import java.math.BigInteger;

public class DSLL extends IType {
    
    private Usable usable = new Usable();

    public DSLL(String addr, int rd, int rs, int rt, String immORoffset) {
        super(addr, rd, rs, rt, immORoffset);
    }

    @Override
    public String ALU(CachedTables ct) {
        BigInteger rs, ans;
        int imm;
        
        String rd = "";
        String sRS, sIMM;
        //Long rs;

        rs = new BigInteger(ct.getRtc().getRegisterRow(this.getRs()),16);
        System.out.println("RS = "+rs);
        sIMM = ct.getOtc().geOpcodeRow(this.insNumber).getImm();
        System.out.println("temp IMM 1 = "+sIMM);
        sIMM = sIMM.substring(5, 10);
        System.out.println("temp IMM 2 = "+sIMM);
        imm = Integer.parseInt(sIMM,2);
        System.out.println("IMM = "+imm);
        ans = rs.shiftLeft(imm);
        
        rd = usable.hexToNbit(ans.toString(16), 16);
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
