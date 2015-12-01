package Table;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class DataTableCache {

    private ArrayList<DataTableRow> dataTableRows = new ArrayList();
    private DefaultTableModel model;

    public DataTableCache(DefaultTableModel dataTableModel) {
        this.model = dataTableModel;
        String addr = "";
        String before = "";
        for (int x = 0; x < 8192; x++) {
            try {
                addr = (this.model.getValueAt(x, 0).toString());
                before = (this.model.getValueAt(x, 1).toString());
            } catch (Exception e) {

            }
            this.dataTableRows.add(new DataTableRow(addr, before, ""));
        }

    }

    public String getMemoryCacheContents(int row) {
        String value = "";
        Object temp;
        for (int i = 0; i < 4; i++) {
            temp = this.model.getValueAt(row, 1);
            value = value + temp;
            row++;
        }

        return value;
    }

    public int findAddrLocation(String addr) {
        for (int x = 0; x < 8192; x++) {
            if (this.dataTableRows.get(x).getAddress().equals(addr)) {
                return x;
            }
        }
        return -1;
    }

    public void drawToTable() {
        for (int x = 0; x < 8192; x++) { //before and after execution cannot be determined yet
            this.model.setValueAt(this.dataTableRows.get(x).getBeforeExec(), x, 1);
            this.model.setValueAt(this.dataTableRows.get(x).getBeforeExec(), x, 2);
        }
    }

    public void writeToMemoryCache(String value, int addresslocation) {
        int j = 8;
        for (int i = 0; i < 4; i++) {
            this.model.setValueAt(value.substring(j, (j + 2)), addresslocation + i, 2);
            j = j + 2;
        }
    }
}
