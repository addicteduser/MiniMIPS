package Instruction.IType;

import Helper.NumberBuilder;
import Table.CachedTables;
import UI.MipsUI;
import java.math.BigInteger;
import javax.swing.JOptionPane;

public class LS extends IType {
    
    public LS(String addr, int rd, int rs, int rt, String immORoffset) {
        super(addr, rd, rs, rt, immORoffset);
    }

    @Override
    public String ALU(CachedTables ct) {
        String lALU = null;
        String offsetTemp = null;
        long rs, rt, offset;
        int value;

        offsetTemp = ct.getOtc().geOpcodeRow(this.insNumber).getImm();
        offset = Long.parseLong(offsetTemp, 2);
        rs = Long.parseLong(ct.getRtc().getRegisterRow(this.rs), 16);
        offset = offset + rs;

        offsetTemp = Long.toBinaryString(offset);
        BigInteger binaryOp = new BigInteger(offsetTemp, 2);
        offsetTemp = binaryOp.toString(16).toUpperCase();

        if (new MipsUI().isINVALIDinLW(offsetTemp)) {
            JOptionPane.showMessageDialog(new MipsUI(), "LOADING ADDRESS IS NOT AVAILABLE!", "Error", JOptionPane.ERROR_MESSAGE);

        } else {

            value = ct.getDtc().findAddrLocation(offsetTemp);
            lALU = ct.getDtc().getMemoryCacheContents(value);

            lALU = lALU.toUpperCase();
            lALU = NumberBuilder.hexToNbit(lALU, 16);

        }

        return lALU;
    }

    @Override
    public int specialFunction(CachedTables ct) {
        ct.getRtc().saveFRegisterValueToCache(this.getRd(), this.ALU(ct).toUpperCase());
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
