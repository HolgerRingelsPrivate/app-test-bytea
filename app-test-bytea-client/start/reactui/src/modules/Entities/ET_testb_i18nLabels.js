import * as AppConstants from "./../Common/AppConstants";



export function get(code) {

  let uiLanguage = AppConstants.getUiLanguage();
  let result = code;

  //undefined => return english
  if (uiLanguage === null)  { 
    result = i18nLabel_en (code);  
    return result;
  }

  if (uiLanguage === "en")  {
    result = i18nLabel_en (code);  
    return result;
  }

  return result;
}

// **** en
export function i18nLabel_en (code) {
  if (code === "$NV") { return "Test ByteA"}
  if (code === "$F1") { return "Test ByteA"}
  if (code === "$F1_field") { return "Field"}
  if (code === "$F1_field2") { return "Field 2"}
  if (code === "$F1_field3") { return "Field 3"}
  if (code === "$L1") { return "Test ByteA"}
  if (code === "$L1_field") { return "Field"}
  if (code === "$L1_field2") { return "Field 2"}
  if (code === "$L1_field3") { return "Field 3"}
  return code;
}

