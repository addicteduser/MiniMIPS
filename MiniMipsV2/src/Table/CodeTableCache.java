package Table;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class CodeTableCache {

    private ArrayList<CodeTableRow> codeTableRows = new ArrayList();
    private DefaultTableModel model;

    public CodeTableCache( int insSize, DefaultTableModel codeTableModel) {
        this.model = codeTableModel;
        for (int x = 0; x < insSize; x++) {
            this.codeTableRows.add(new CodeTableRow((this.model.getValueAt(x, 0).toString()), (this.model.getValueAt(x, 1).toString()), (this.model.getValueAt(x, 2).toString()), (this.model.getValueAt(x, 3).toString())));
        }

    }
    public CodeTableRow getCodeLine(int insnumber){
        return this.codeTableRows.get(insnumber);
    }

}
