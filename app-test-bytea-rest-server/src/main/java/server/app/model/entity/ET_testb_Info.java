package server.app.model.entity;

import java.util.Hashtable;
import java.util.Vector;

import common.service.model.read.intf.PojoEntityAttribute;

public final class ET_testb_Info {

	public static final String TABLE_NAME = "testb";
 
 	// ****************************************************************************************
 	// * Model Information
 	// ****************************************************************************************
 
 
	/**
	 * This method delivers the data-columns
	 * @return
	 */
	public static String[] dataColumnNames() {
	
		Vector<String> vFields = new Vector<String>();

		vFields.add("field");
		vFields.add("field2");
		vFields.add("field3");

		String[] aryFields = vFields.toArray(new String[0]);
		return aryFields;

	}


	/**
	 * This method delivers the data-columns' details
	 * @return
	 */
	public static PojoEntityAttribute dataColumnDetails(String dataColumnName) {
	

        if ("field".equals(dataColumnName)) {
        
			PojoEntityAttribute pojoEntityAttribute = new PojoEntityAttribute();
			
			pojoEntityAttribute.setEntity                      ("testb");
			pojoEntityAttribute.setField                       ("field");
			pojoEntityAttribute.setDataTypeID		           (17);
			pojoEntityAttribute.setDataTypeName		           ("blobtext");
			pojoEntityAttribute.setMandatory		           (true);
			pojoEntityAttribute.setCardinality		           ("1");
			
            return pojoEntityAttribute;
		}
        if ("field2".equals(dataColumnName)) {
        
			PojoEntityAttribute pojoEntityAttribute = new PojoEntityAttribute();
			
			pojoEntityAttribute.setEntity                      ("testb");
			pojoEntityAttribute.setField                       ("field2");
			pojoEntityAttribute.setDataTypeID		           (1);
			pojoEntityAttribute.setDataTypeName		           ("string");
			pojoEntityAttribute.setMandatory		           (true);
			pojoEntityAttribute.setCardinality		           ("1");
            pojoEntityAttribute.setStringConstrainMinLength    (1);
            pojoEntityAttribute.setStringConstrainMaxLength    (-1);
			
            return pojoEntityAttribute;
		}
        if ("field3".equals(dataColumnName)) {
        
			PojoEntityAttribute pojoEntityAttribute = new PojoEntityAttribute();
			
			pojoEntityAttribute.setEntity                      ("testb");
			pojoEntityAttribute.setField                       ("field3");
			pojoEntityAttribute.setDataTypeID		           (1);
			pojoEntityAttribute.setDataTypeName		           ("string");
			pojoEntityAttribute.setMandatory		           (true);
			pojoEntityAttribute.setCardinality		           ("1");
            pojoEntityAttribute.setStringConstrainMinLength    (1);
            pojoEntityAttribute.setStringConstrainMaxLength    (-1);
			
            return pojoEntityAttribute;
		}
   		return null;

	}

 

}
