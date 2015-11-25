package dataObjects;

public class NumberBuilder {
	public static String paddedBinaryBuilder(int pad, int value) {
		if (pad == 5)
			return String.format("%"+pad+"s", Integer.toBinaryString(value & 0x1F)).replace(' ', '0');
		else if (pad == 6)
			return String.format("%"+pad+"s", Integer.toBinaryString(value & 0x3F)).replace(' ', '0');
		else // pad == 16
			return String.format("%"+pad+"s", Integer.toBinaryString(value & 0xFFFFFFFF)).replace(' ', '0');
	}
	
	public static String paddedHexBuilder(int pad, int value) {
		return String.format("%0"+pad+"X", value);
	}
}
