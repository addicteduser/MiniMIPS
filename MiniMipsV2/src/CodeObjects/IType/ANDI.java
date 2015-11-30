/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeObjects.IType;

import Caches.CachedTables;
import Functions.Usable;
import java.math.BigInteger;

/**
 *
 * @author addicteduser
 */
public class ANDI extends IType {
    private Usable usable = new Usable();
    
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
