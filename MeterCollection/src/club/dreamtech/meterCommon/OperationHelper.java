package club.dreamtech.meterCommon;

public class OperationHelper {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	/**
	 * byte数组转16进制string
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	/**
	 * byte数组转值
	 * @param bytes 
	 * @param decimalNum 数值小数位数
	 * @return 
	 */
	public static String bytesToValue(byte[] bytes,int decimalNum) {
		String resultString=bytesToHex(bytes);
		resultString=resultString.substring(0, resultString.length()-1-decimalNum)+"."+resultString.substring(resultString.length()-decimalNum);
		return resultString;
	}
}
