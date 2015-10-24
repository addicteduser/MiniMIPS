package dataObjects;

import java.util.ArrayList;

public class Data {
	private static ArrayList<Data> dataList = new ArrayList<Data>();
	private String varName;
	private String type;
	private long[] values;
	
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
