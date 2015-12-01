package helper;

public class NumberBuilder {
	public static String paddedBinaryStringBuilder(int pad, long value) {
		if (pad == 5)
			return String.format("%"+pad+"s", Long.toBinaryString(value & 0x1F)).replace(' ', '0');
		else if (pad == 6)
			return String.format("%"+pad+"s", Long.toBinaryString(value & 0x3F)).replace(' ', '0');
		else if (pad == 16)
			return String.format("%"+pad+"s", Long.toBinaryString(value & 0xFFFF)).replace(' ', '0');
		else
			return String.format("%"+pad+"s", Long.toBinaryString(value & 0x3FFFFFF)).replace(' ', '0');
	}

	public static String paddedHexStringBuilder(int pad, long value) {
		return String.format("%0"+pad+"X", value);
	}

	public static int hexStringToIntBuilder(String number) {
		return Short.toUnsignedInt(Short.parseShort(number, 16));
	}
}
