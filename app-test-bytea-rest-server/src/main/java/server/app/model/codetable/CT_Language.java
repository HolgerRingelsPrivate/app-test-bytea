package server.app.model.codetable;

public final class CT_Language {

	/** CODETABLE: Language **/
	public static final String CODETABLE = "Language";
	
	/** DEFAULTCODE="en" **/
	public static final String DEFAULTCODE = "en";
		
//
// Access to Codes (via Java)
//
	/**
	 * Code 'de' = Value 'German'
	 */
	public static final String DE = "de";

	/**
	 * Code 'en' = Value 'English'
	 */
	public static final String EN = "en";

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
		if (code.equals("de")) { return "German";}
		if (code.equals("en")) { return "English";}
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
		if (code.equals("de")) { return "";}
		if (code.equals("en")) { return "";}
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
		if (code.equals("de")) { return true; }
		if (code.equals("en")) { return true; }
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
		sResult = sResult + "de" + delimiter;
		sResult = sResult + "en" + delimiter;
		sResult = sResult.substring(0, sResult.length() - delimiter.length());
		return sResult;
		
	}



}
