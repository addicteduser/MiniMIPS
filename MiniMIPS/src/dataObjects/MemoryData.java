package dataObjects;

import java.util.ArrayList;

public class MemoryData {
	private static ArrayList<Data> dataList = new ArrayList<Data>();
	private static int dCtr = 0;
	
	public static void initializeDataList() {
		dataList.clear();
		Data tempData;
		for(int i = 0; i <= Short.toUnsignedInt(Short.parseShort("1FFF", 16)); i+=8) {
			String address = NumberBuilder.paddedHexBuilder(4, i);
			tempData = new Data(address);
			dataList.add(tempData);
		}
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
}
