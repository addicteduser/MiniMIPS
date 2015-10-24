package dataObjects;

import java.util.ArrayList;

public class Data {
	private static ArrayList<Data> dataList = new ArrayList<Data>();
	private String varName;
	private String type;
	private long[] values;
	
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the values
	 */
	public long[] getValues() {
		return values;
	}
	/**
	 * @param values the values to set
	 */
	public void setValues(long[] values) {
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
