package server.app.model.entity;

public final class ET_testb_Pojo {

/********************************************************************************
 * Pojo for Entity [testb]
 *
 * Description:
 *
 * One fields as blob text
 *
 * -------------------- Fields: ----------------------------------------
 * Field                            BusinessType
 * ---------------------------------------------------------------------
 *
 * _id                            : BusinessType String (primary key)
 * field                          : BusinessType blobtext
 * field2                         : BusinessType string
 * field3                         : BusinessType string
 *********************************************************************************/


	private String _id; //primary key
	private String _softdeleted; 
	private Long _lastmodified;
	
	
	private String field; 
	private String field2; 
	private String field3; 


// * ---------------------------------------------------------------------
// * Getter and Setter:
// * ---------------------------------------------------------------------

	public String get_id() {          // primary key
		return _id;
	}
	public void set_id(String _id) {  // primary key
		this._id = _id;
	}
	public String get_softdeleted() { // softdeleted 'y' or 'n'
		return _softdeleted;
	}
	public void set_softdeleted(String _softdeleted) { // softdeleted 'y' or 'n'
		this._softdeleted = _softdeleted;
	}
	public Long get_lastmodified() {
		return _lastmodified;
	}
	public void set_lastmodified(Long _lastmodified) {
		this._lastmodified = _lastmodified;
	}
	



	public String getField() {
		return field;
	}
	public void setField (String field) {
		this.field = field;
	}


	public String getField2() {
		return field2;
	}
	public void setField2 (String field2) {
		this.field2 = field2;
	}


	public String getField3() {
		return field3;
	}
	public void setField3 (String field3) {
		this.field3 = field3;
	}



 
}
