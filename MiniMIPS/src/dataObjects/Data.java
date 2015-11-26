package dataObjects;

import constants.DIRECTIVES;

public class Data {
	private String address;
	private String varName;
	private DIRECTIVES directive;
	private long value;

	public Data(String address) {
		this.address = address;
	}

	/**
	 * @return the varName
	 */
	public String getVarName() {
		return varName;
	}
	/**
	 * @param varName the varName to set
	 */
	public void setVarName(String varName) {
		this.varName = varName;
	}
	/**
	 * @return the directive
	 */
	public DIRECTIVES getDirective() {
		return directive;
	}
	/**
	 * @param directive the directive to set
	 */
	public void setDirective(DIRECTIVES directive) {
		this.directive = directive;
	}
	/**
	 * @return the values
	 */
	public long getValue() {
		return value;
	}
	/**
	 * @param values the values to set
	 */
	public void setValue(long value) {
		this.value = value;
	}

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
}
