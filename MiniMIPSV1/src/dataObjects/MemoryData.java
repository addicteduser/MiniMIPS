package dataObjects;

import java.util.ArrayList;

import helper.NumberBuilder;
import helper.Validator;

public class MemoryData {
	private static ArrayList<Data> dataList = new ArrayList<Data>();
	private static int dCtr = 0;

	public static void initializeDataList() {
		dataList.clear();
		Data tempData;
		for(int i = 0; i <= NumberBuilder.hexStringToIntBuilder("1FFF"); i+=8) {
			String address = NumberBuilder.paddedHexStringBuilder(4, i);
			tempData = new Data(address);
			dataList.add(tempData);
		}
	}
	
	public static void updateVarName(int row, String newVal) {
		dataList.get(row).setVarName(newVal);
	}
	
	public static boolean updateValue(int row, long newVal) {
		if (Validator.isValueValid(Long.MIN_VALUE, Long.MAX_VALUE, newVal)) {
			dataList.get(row).setValue(newVal);
			return true;
		} else
			return false;
	}
	

	public static void resetCtr() {
		dCtr = 0;
	}

	public static void incrementCtr() {
		dCtr++;
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
		MemoryData.dataList = dataList;
	}

	/**
	 * @return the dCtr
	 */
	public static int getdCtr() {
		return dCtr;
	}

	/**
	 * @param dCtr the dCtr to set
	 */
	public static void setdCtr(int dCtr) {
		MemoryData.dCtr = dCtr;
	}

	public static String getAddress(String varName) {
		for(int i = 0; i < dCtr; i++) {
			if (dataList.get(i).getVarName().equalsIgnoreCase(varName))
				return dataList.get(i).getAddress();
		}
		return null;
	}
}
