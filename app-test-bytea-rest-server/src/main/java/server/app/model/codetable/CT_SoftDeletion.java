package server.app.model.codetable;

public final class CT_SoftDeletion {

	/** CODETABLE: SoftDeletion **/
	public static final String CODETABLE = "SoftDeletion";
	
	/** DEFAULTCODE=null **/
	public static final String DEFAULTCODE = null;

//
// Access to Codes (via Java)
//
	/**
	 * Code 'y' = Value 'deleted'
	 */
	public static final String DELETED = "y";

	/**
	 * Code 'n' = Value 'not deleted'
	 */
	public static final String NOT_DELETED = "n";

	/**
	 * Code 'yn' = Value 'deleted and not deleted'
	 */
	public static final String DELETED_AND_NOT_DELETED = "yn";

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
		if (code.equals("y")) { return "deleted";}
		if (code.equals("n")) { return "not deleted";}
		if (code.equals("yn")) { return "deleted and not deleted";}
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
		if (code.equals("y")) { return "";}
		if (code.equals("n")) { return "";}
		if (code.equals("yn")) { return "";}
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
		if (code.equals("y")) { return true; }
		if (code.equals("n")) { return true; }
		if (code.equals("yn")) { return true; }
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
		sResult = sResult + "y" + delimiter;
		sResult = sResult + "n" + delimiter;
		sResult = sResult + "yn" + delimiter;
		sResult = sResult.substring(0, sResult.length() - delimiter.length());
		return sResult;
		
	}



}
