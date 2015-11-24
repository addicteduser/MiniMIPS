package dataObjects;

import java.util.ArrayList;

import constants.DIRECTIVES;

public class Data {
	private static ArrayList<Data> dataList = new ArrayList<Data>();
	private String varName;
	private DIRECTIVES directive;
	private ArrayList<Long> values;
	
	public static void resetDataList() {
		dataList.clear();
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
	public ArrayList<Long> getValues() {
		return values;
	}
	/**
	 * @param values the values to set
	 */
	public void setValues(ArrayList<Long> values) {
		this.values = values;
	}
	/**
	 * @return the dataList
	 */
	public static ArrayList<Data> getDataList() {
		return dataList;
	}
	/**
	 * @param dataList the dataList to set
	 */
	public static void setDataList(ArrayList<Data> dataList) {
		Data.dataList = dataList;
	}
}
