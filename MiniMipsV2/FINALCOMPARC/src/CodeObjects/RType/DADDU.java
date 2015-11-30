/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeObjects.RType;

import Caches.CachedTables;
import Functions.Usable;
import java.math.BigInteger;

/**
 *
 * @author addicteduser
 */
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
        result = new Usable().hexToNbit(result, 16); //don't forget to instantiate the Usable class
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
