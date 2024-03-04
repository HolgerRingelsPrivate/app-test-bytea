package server.app.model.codetable;

public final class CT_LogLevel {

	/** CODETABLE: LogLevel **/
	public static final String CODETABLE = "LogLevel";
	
	/** DEFAULTCODE=null **/
	public static final String DEFAULTCODE = null;

//
// Access to Codes (via Java)
//
	/**
	 * Code 'TRACE' = Value 'Trace'
	 */
	public static final String TRACE = "TRACE";

	/**
	 * Code 'DEBUG' = Value 'Debug'
	 */
	public static final String DEBUG = "DEBUG";

	/**
	 * Code 'INFO' = Value 'Info'
	 */
	public static final String INFO = "INFO";

	/**
	 * Code 'WARN' = Value 'Warn'
	 */
	public static final String WARN = "WARN";

	/**
	 * Code 'ERROR' = Value 'Error'
	 */
	public static final String ERROR = "ERROR";

	/**
	 * Code 'FATAL' = Value 'Fatal'
	 */
	public static final String FATAL = "FATAL";

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
		if (code.equals("TRACE")) { return "Trace";}
		if (code.equals("DEBUG")) { return "Debug";}
		if (code.equals("INFO")) { return "Info";}
		if (code.equals("WARN")) { return "Warn";}
		if (code.equals("ERROR")) { return "Error";}
		if (code.equals("FATAL")) { return "Fatal";}
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
		if (code.equals("TRACE")) { return "";}
		if (code.equals("DEBUG")) { return "";}
		if (code.equals("INFO")) { return "";}
		if (code.equals("WARN")) { return "";}
		if (code.equals("ERROR")) { return "";}
		if (code.equals("FATAL")) { return "";}
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
		if (code.equals("TRACE")) { return true; }
		if (code.equals("DEBUG")) { return true; }
		if (code.equals("INFO")) { return true; }
		if (code.equals("WARN")) { return true; }
		if (code.equals("ERROR")) { return true; }
		if (code.equals("FATAL")) { return true; }
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
		sResult = sResult + "TRACE" + delimiter;
		sResult = sResult + "DEBUG" + delimiter;
		sResult = sResult + "INFO" + delimiter;
		sResult = sResult + "WARN" + delimiter;
		sResult = sResult + "ERROR" + delimiter;
		sResult = sResult + "FATAL" + delimiter;
		sResult = sResult.substring(0, sResult.length() - delimiter.length());
		return sResult;
		
	}



}
