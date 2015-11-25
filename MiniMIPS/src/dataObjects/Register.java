package dataObjects;

public class Register {
	private String regName;
	private long regValue;
	
	public Register(String regName, long regValue) {
		this.regName = regName;
		this.regValue = regValue;
	}
	
	/**
	 * @return the regName
	 */
	public String getRegName() {
		return regName;
	}
	
	/**
	 * @param regName the regName to set
	 */
	public void setRegName(String regName) {
		this.regName = regName;
	}
	
	/**
	 * @return the regValue
	 */
	public long getRegValue() {
		return regValue;
	}
	
	/**
	 * @param regValue the regValue to set
	 */
	public void setRegValue(long regValue) {
		this.regValue = regValue;
	}
}
