package server.app.model.codetable;

public final class CT_ErrorCodes {

	/** CODETABLE: ErrorCodes **/
	public static final String CODETABLE = "ErrorCodes";
	
	/** DEFAULTCODE=null **/
	public static final String DEFAULTCODE = null;

//
// Access to Codes (via Java)
//
	/**
	 * Code '00000' = Value 'OK, No Error'
	 */
	public static final String NO_ERROR = "00000";

	/**
	 * Code '00001' = Value 'No Error Details'
	 */
	public static final String NO_ERROR_CODE = "00001";

	/**
	 * Code '00002' = Value 'Directory does not exist'
	 */
	public static final String DIRECTORY_NOT_AVAILABLE = "00002";

	/**
	 * Code '00003' = Value 'Entry is not allowed to be an empty string'
	 */
	public static final String UNALLOWED_EMPTY_STRING = "00003";

	/**
	 * Code '00004' = Value 'File Upload Folder not found'
	 */
	public static final String FILE_UPLOAD_FOLFER_NOT_FOUND = "00004";

	/**
	 * Code '00005' = Value 'Unknown User'
	 */
	public static final String USER_UNKNOWN = "00005";

	/**
	 * Code '00006' = Value 'File does not exist'
	 */
	public static final String FILE_NOT_EXISTING = "00006";

	/**
	 * Code '00007' = Value 'Module not reachible'
	 */
	public static final String MODULE_NOT_REACHIBLE = "00007";

	/**
	 * Code '00008' = Value 'SQL/Database Error'
	 */
	public static final String SQL_DATABASE_ERROR = "00008";

	/**
	 * Code '00009' = Value 'Entity does not exist (Unknown ID)'
	 */
	public static final String SQL_ENTITY_UNKNOWN = "00009";

	/**
	 * Code '00010' = Value 'File Handling does not work properly'
	 */
	public static final String FILE_HANDLING_ISSUE = "00010";

	/**
	 * Code '00011' = Value 'Data Import not possible (database is not empty)'
	 */
	public static final String DATABASE_IMPORT_BLOCKED_BY_EXISTING_DATA = "00011";

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
		if (code.equals("00000")) { return "OK, No Error";}
		if (code.equals("00001")) { return "No Error Details";}
		if (code.equals("00002")) { return "Directory does not exist";}
		if (code.equals("00003")) { return "Entry is not allowed to be an empty string";}
		if (code.equals("00004")) { return "File Upload Folder not found";}
		if (code.equals("00005")) { return "Unknown User";}
		if (code.equals("00006")) { return "File does not exist";}
		if (code.equals("00007")) { return "Module not reachible";}
		if (code.equals("00008")) { return "SQL/Database Error";}
		if (code.equals("00009")) { return "Entity does not exist (Unknown ID)";}
		if (code.equals("00010")) { return "File Handling does not work properly";}
		if (code.equals("00011")) { return "Data Import not possible (database is not empty)";}
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
		if (code.equals("00000")) { return "";}
		if (code.equals("00001")) { return "";}
		if (code.equals("00002")) { return "";}
		if (code.equals("00003")) { return "";}
		if (code.equals("00004")) { return "";}
		if (code.equals("00005")) { return "";}
		if (code.equals("00006")) { return "";}
		if (code.equals("00007")) { return "Please revisit page, after ALL Modules have been restarted";}
		if (code.equals("00008")) { return "Please revisit page, after ALL Modules have been restarted";}
		if (code.equals("00009")) { return "";}
		if (code.equals("00010")) { return "";}
		if (code.equals("00011")) { return "";}
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
		if (code.equals("00000")) { return true; }
		if (code.equals("00001")) { return true; }
		if (code.equals("00002")) { return true; }
		if (code.equals("00003")) { return true; }
		if (code.equals("00004")) { return true; }
		if (code.equals("00005")) { return true; }
		if (code.equals("00006")) { return true; }
		if (code.equals("00007")) { return true; }
		if (code.equals("00008")) { return true; }
		if (code.equals("00009")) { return true; }
		if (code.equals("00010")) { return true; }
		if (code.equals("00011")) { return true; }
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
		sResult = sResult + "00000" + delimiter;
		sResult = sResult + "00001" + delimiter;
		sResult = sResult + "00002" + delimiter;
		sResult = sResult + "00003" + delimiter;
		sResult = sResult + "00004" + delimiter;
		sResult = sResult + "00005" + delimiter;
		sResult = sResult + "00006" + delimiter;
		sResult = sResult + "00007" + delimiter;
		sResult = sResult + "00008" + delimiter;
		sResult = sResult + "00009" + delimiter;
		sResult = sResult + "00010" + delimiter;
		sResult = sResult + "00011" + delimiter;
		sResult = sResult.substring(0, sResult.length() - delimiter.length());
		return sResult;
		
	}



}
