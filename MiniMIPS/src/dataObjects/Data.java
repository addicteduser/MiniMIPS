package dataObjects;

import java.util.ArrayList;

import constants.Directive;

public class Data {
	private static ArrayList<Data> dataList = new ArrayList<Data>();
	private String varName;
	private Directive directive;
	private ArrayList<Long> values;
	
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
	public Directive getDirective() {
		return directive;
	}
	/**
	 * @param directive the directive to set
	 */
	public void setDirective(Directive directive) {
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
