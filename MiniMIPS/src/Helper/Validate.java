/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

/**
 *
 * @author Octaviano
 */
public class Validate {

    

    public static boolean isFloatRegister(String register) {
        try {
            int regNumber = Integer.parseInt(register.replace("F", "").replace("f", ""));

            if (regNumber >= 0 && regNumber <= 11) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("MUST BE FLOAT INPUT! "+register);
            System.exit(0);
            return false;
        }
    }
}
