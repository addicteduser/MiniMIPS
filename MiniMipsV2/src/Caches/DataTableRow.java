package Caches;

public class DataTableRow {
    private String address;

    public DataTableRow(String address, String beforeExec, String afterExec) {
        this.address = address;
        this.beforeExec = beforeExec;
        this.afterExec = afterExec;
    }
    private String beforeExec;
    private String afterExec;

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the beforeExec
     */
    public String getBeforeExec() {
        return beforeExec;
    }

    /**
     * @param beforeExec the beforeExec to set
     */
    public void setBeforeExec(String beforeExec) {
        this.beforeExec = beforeExec;
    }

    /**
     * @return the afterExec
     */
    public String getAfterExec() {
        return afterExec;
    }

    /**
     * @param afterExec the afterExec to set
     */
    public void setAfterExec(String afterExec) {
        this.afterExec = afterExec;
    }
}
