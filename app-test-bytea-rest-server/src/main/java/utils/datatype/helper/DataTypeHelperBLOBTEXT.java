package utils.datatype.helper;

import java.util.StringTokenizer;
import java.util.Vector;

public class DataTypeHelperBLOBTEXT {

	private static final String BYTE_DELIMITER_STRING = ",";
	
	/**
	 * This method translates an origin-string into a string with bytes representing the origin-string 
	 * @param sIn origin-string - e.g. "ABC"
	 * @return e.g. ("65,66,67")
	 */
	public static final String convertStringToByteSequenceString(String sIn) {
		
		byte[] bytes = sIn.getBytes();
		String [] sBytes = new String[bytes.length]; 
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			int bInt = b;
			String sByte = Integer.toString(bInt);
			sBytes[i] = sByte;
		}
		String sOut = implode(sBytes, BYTE_DELIMITER_STRING);
		return sOut;
	}
	

	
	
	/**
	 * This method translates an origin-string into a string with bytes representing the origin-string, where
	 * all parts of the string with a character endcoding < 20hex (space) is replaced with 20hex (space)  
//	 * @param sIn origin-string - e.g. "\t\n\rABC"
	 * @return e.g. ("   ABC")
	 */
	public static final String convertStringToHealthyUnreadibleCharacters(String sIn) {
		
		byte[] bytes = sIn.getBytes();
		String [] sBytes = new String[bytes.length]; 
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			int bInt = b;
			if (bInt < 20) {
				bInt = 20;
			}
			String sByte = Integer.toString(bInt);
			sBytes[i] = sByte;
		}
		String sOut = implode(sBytes, BYTE_DELIMITER_STRING);
		String sResultString = convertByteSequenceStringToString(sOut);
		return sResultString;
	}
	

	
	
	/**
	 * This method translates a string with bytes representing the origin-string  into the origin-string  
	 * @param sIn origin-string - e.g. "65,66,67"
	 * @return e.g. ("ABC")
	 */
	public static final String convertByteSequenceStringToString(String sIn) {
		
		String [] sBytes = explode(sIn, BYTE_DELIMITER_STRING);
		byte[] bytes = new byte[sBytes.length];
		for (int i = 0; i < sBytes.length; i++) {
			String sByte = sBytes[i];
			Integer int1 = Integer.parseInt(sByte);
			byte byteValue = int1.byteValue();
			bytes[i] = byteValue;
		}
		
		String sOut = new String(bytes);
		return sOut;
	}

	
	
	
	
	/**
	 * This method explodes a string to an array of strings based on a delimiter
	 * @param str_Coded string to explode
	 * @param str_Delim delimiter
	 * @return Array of Strings (exploded)
	 */
	private static String[]  explode(String str_Coded, String str_Delim) {
		Vector<String> obj_Return = new Vector<String>();		
		StringTokenizer tokenizer = new StringTokenizer(str_Coded, str_Delim);
		while (tokenizer.hasMoreTokens()) {
			obj_Return.addElement((String) tokenizer.nextToken());
		}
		String[] aryReturn = obj_Return.toArray(new String[0]);
		return aryReturn;
		
	}
	
	/**
	 * This method is the counter-part to explode ... it implodes a String-Array using a delimiter
	 * @param exploded String-Array to implode
	 * @param str_Delim delimiter to use
	 * @return imploded string
	 */
	private static String implode(String[] exploded, String str_Delim) {
		if (exploded == null) {
			return null;
		}
		if (exploded.length == 0) {
			return "";
		}
		if (exploded.length == 1) {
			return exploded[0];
		}
		
		String sResult = "";
		for (int i = 0; i < exploded.length; i++) {
			String current = exploded[i];
			if (i == 0) {
				sResult = current; 
			} else {
				sResult = sResult + str_Delim + current;
			}
		}
		return sResult;
		
	}
}
