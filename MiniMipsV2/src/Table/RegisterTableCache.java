package Table;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class RegisterTableCache {

    private ArrayList<RegisterTableRow> registerTableRows = new ArrayList();
    private DefaultTableModel model;

    public RegisterTableCache(DefaultTableModel registerTableModel) {
        this.model = registerTableModel;
        for (int x = 0; x < 46; x++) {
            this.registerTableRows.add(new RegisterTableRow((this.model.getValueAt(x, 0).toString()), (this.model.getValueAt(x, 1).toString())));
        }
        System.out.println("testing [registertablecache] init...");
    }

    public String getRegisterRow(int registerNum) {
        System.out.println("testing [registertablecache] getregister row... row num: "+registerNum);
        if (registerNum == 0) {
            return "0000000000000000";
        } else {
            System.out.println("testing [registertablecache] getregister row... row num: "+this.registerTableRows.get(registerNum).getRegisterValue());
            return this.registerTableRows.get(registerNum).getRegisterValue();
        }
    }
    
    public String getFRegisterRow(int registerNum) {
        System.out.println("testing [registertablecache] getregister row... row num: "+registerNum);
        if (registerNum == 0) {
            return "0000000000000000";
        } else {
            System.out.println("testing [registertablecache] getregister row... row num: "+this.registerTableRows.get(registerNum+34).getRegisterValue());
            return this.registerTableRows.get(registerNum+34).getRegisterValue();
        }
    }

    public void saveRegisterValueToCache(int registerNum, String value) {
        this.registerTableRows.get(registerNum).setRegisterValue(value);
    }
    
    public void saveFRegisterValueToCache(int registerNum, String value) {
        this.registerTableRows.get(registerNum+34).setRegisterValue(value);
    }

    public void drawToRegisterTable() {
        for (int x = 0; x < 46; x++) {
            this.model.setValueAt(this.registerTableRows.get(x).getRegisterValue(), x, 1);
        }
    }
    //added by codefinisher
    public void refreshRegisterCacheArray(){
        for (int x = 0; x < 46; x++) {
            this.registerTableRows.get(x).setRegisterValue((this.model.getValueAt(x, 1).toString()));
//            this.registerTableRows.add(new RegisterTableRow((this.model.getValueAt(x, 0).toString()), (this.model.getValueAt(x, 1).toString())));
        }
        System.out.println("testing [registertablecache] refreshing values...");
    }
}
