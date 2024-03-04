

import * as Common_i18nLabels from "../Common/Common_i18nLabels";
import * as ET_testb$Local_i18nLabels from "../Entities/ET_testb_i18nLabels";


/**
 * This Hook provides methods provided by development as addon to generated
 * code for Entity testb$ . The Hook is generated once and will never be
 * deleted again by Code Generation. The only way to get a fresh one is
 * to delete (backup) the existing one and to let a new one be generated
 * Might be you will transfer logic from backup version back to the new
 * generate one
 */

/**
 * This method delivers a String array with data, which is displayed in Deletion-Confirm-Dialog
 * @param data testb$ data       object(as it is available in Object/Form's state)
 * @return Array of Strings displayed in Deletion-Confirm-Dialog
 */
export function questionInDeleteConfirm(data) {

    let question = 
    [Common_i18nLabels.get('$MODAL_DIALOG_DELETE_ENTRY_QUESTION')
      ,""
    ];

    return question; 
}

/**
 * This method validates data entered in an Obejct/Form
 * @param data mediaentry data       object(as it is available in Object/Form's state)
 * @param data mediaentry validation object (same base structure as data object)
 * @return validate data structured the same way a Object/Form's state (holding error-information)
 */
export function validateFormData(data, validationResult) {

//    validationResult.mediatype = validDataMandatoryCodeField(data.mediatype);
//    validationResult.name = validDataMandatoryStringField(data.name);
//    validationResult.location = validDataMandatoryStringField(data.name);
//    validationResult.position = validDataMandatoryStringField(data.name);
    
    return validationResult; 

}



/************************************************************************************
 *  Internal Helper functions
 ************************************************************************************/

//Datatype 1: String  (cardinality 1 and N?) 
function validDataMandatoryStringField(field) { 
    if (field === null)         { return false;}
    if (field === undefined)    { return false;}
    if (field === '')           { return false;}
    return true;
}

//Datatype 2: code (cardinality 1 and N)
function validDataMandatoryCodeField(field) {   
    if (field === null)         { return false;}
    if (field === undefined)    { return false;}
    if (field === '')           { return false;}
    return true;
}

