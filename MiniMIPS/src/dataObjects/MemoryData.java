package dataObjects;

import java.util.ArrayList;

public class MemoryData {
	private static ArrayList<Data> dataList = new ArrayList<Data>();
	
	public static void initializeDataList() {
		dataList.clear();
		Data tempData;
		for(int i = 0; i <= Short.toUnsignedInt(Short.parseShort("1FFF", 16)); i+=8) {
			String address = NumberBuilder.paddedHexBuilder(4, i);
			tempData = new Data(address);
			dataList.add(tempData);
		}
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
}
