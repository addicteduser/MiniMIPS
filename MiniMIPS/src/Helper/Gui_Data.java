/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import javax.swing.table.TableModel;

/**
 *
 * @author Octaviano
 */
public class Gui_Data {

    /*
     *String[0][x] = name of the table.
     *String[1][x] = value of the table.
     *String[x][32] = LO row in table.
     *String[x][33] = HI row in table.
     */
    private static String[][] genRegister = new String[2][34];
    private static String[][] floatPointRegister = new String[2][32];

    public static void setGenRegister(TableModel table) {
        for (int count = 0; count < 34; count++) {
            genRegister[0][count] = table.getValueAt(count, 0).toString();
            genRegister[1][count] = table.getValueAt(count, 1).toString();
        }
    }

    public static void setFPointRegister(TableModel table) {
        for (int count = 0; count < 32; count++) {
            floatPointRegister[0][count] = table.getValueAt(count, 0).toString();
            floatPointRegister[1][count] = table.getValueAt(count, 1).toString();
        }
    }

    public static String[][] getGeneralRegister() {
        return genRegister;
    }

    public static String[][] getFPointRegister() {
        return floatPointRegister;
    }
}
