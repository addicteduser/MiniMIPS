package Table;

import java.util.ArrayList;

public class RegisterTableRow {
     private String RegisterNumber;
    private String RegisterValue;

    public RegisterTableRow(String RegisterNumber, String RegisterValue) {
        this.RegisterNumber = RegisterNumber;
        this.RegisterValue = RegisterValue;
    }

    /**
     * @return the RegisterNumber
     */
    public String getRegisterNumber() {
        return RegisterNumber;
    }

    /**
     * @param RegisterNumber the RegisterNumber to set
     */
    public void setRegisterNumber(String RegisterNumber) {
        this.RegisterNumber = RegisterNumber;
    }

    /**
     * @return the RegisterValue
     */
    public String getRegisterValue() {
        return RegisterValue;
    }

    /**
     * @param RegisterValue the RegisterValue to set
     */
    public void setRegisterValue(String RegisterValue) {
        this.RegisterValue = RegisterValue;
    }
}
