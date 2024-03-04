package server.app.model.codetable;

public final class CT_AppXceptionRelevance {

	/** CODETABLE: AppXceptionRelevance **/
	public static final String CODETABLE = "AppXceptionRelevance";
	
	/** DEFAULTCODE=null **/
	public static final String DEFAULTCODE = null;

//
// Access to Codes (via Java)
//
	/**
	 * Code 'SYSTEM' = Value 'System relevant'
	 */
	public static final String SYSTEM = "SYSTEM";

	/**
	 * Code 'PROCESS' = Value 'Process relevant'
	 */
	public static final String PROCESS = "PROCESS";

	/**
	 * Code 'BACKGROUND' = Value 'Background relevant'
	 */
	public static final String BACKGROUND = "BACKGROUND";

//
// Access to English Text (via Java) ... e.g. for REST-Service Implementation
//


	/**
	 * Get detals with english text for a code
	 * @param sDetails
	 * @param code
	 * @return
	 */
	public static final String detailsForCode(String sDetails, String code) {
		String sResult = sDetails + ":" + textForCode(code);
		return sResult;
	}

	/**
	 * Get english text for a code
	 * @param code
	 * @return
	 */
	public static final String textForCode(String code) {
		if (code == null) {
			return "";
		}
		if (code.equals("SYSTEM")) { return "System relevant";}
		if (code.equals("PROCESS")) { return "Process relevant";}
		if (code.equals("BACKGROUND")) { return "Background relevant";}
		return "";
	}


	/**
	 * Get english text for a code
	 * @param code
	 * @return
	 */
	public static final String hintForCode(String code) {
		if (code == null) {
			return "";
		}
		if (code.equals("SYSTEM")) { return "Display: Sanity Check";}
		if (code.equals("PROCESS")) { return "Display: Processing";}
		if (code.equals("BACKGROUND")) { return "Display: Others";}
		return "";
	}


	/**
	 * Check if code is a valid code
	 * @param code
	 * @return
	 */
	public static final boolean isKnownCode(String code) {
		if (code == null) {
			return false;
		}
		if (code.equals("SYSTEM")) { return true; }
		if (code.equals("PROCESS")) { return true; }
		if (code.equals("BACKGROUND")) { return true; }
		return false;
	}



	/**
	 * Delivers a list of codes, delimited by delimiter
	 * @param delimiter if null, then commata is used, otherwise the selected delimter is used;
	 * @return
	 */
	public static final String deliverListOfCodes(String delimiter) {
		String sResult = "";
		if (delimiter == null) {
			delimiter = ",";
		}
		sResult = sResult + "SYSTEM" + delimiter;
		sResult = sResult + "PROCESS" + delimiter;
		sResult = sResult + "BACKGROUND" + delimiter;
		sResult = sResult.substring(0, sResult.length() - delimiter.length());
		return sResult;
		
	}



}
