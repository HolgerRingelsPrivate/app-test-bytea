import * as AppConstants from "./AppConstants";



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
  if (uiLanguage === "de")  {
    result = i18nLabel_de (code);  
    return result;
  }

  return result;
}

// **** en
export function i18nLabel_en (code) {
  if (code === "$LIST") { return "List"}
  if (code === "$PLEASE_SELECT_IN_LEFT_HANDED_NAVIGATION") { return " ... please select from the left-handed navigation"}
  if (code === "$MODAL_DIALOG_YES") { return "Yes"}
  if (code === "$MODAL_DIALOG_NO") { return "No"}
  if (code === "$MODAL_DIALOG_DELETE_ENTRY_HEADER") { return "Deletion"}
  if (code === "$MODAL_DIALOG_DELETE_ENTRY_QUESTION") { return "Shoud this entry be removed ?"}
  if (code === "$BUTTON_EDIT") { return "Edit"}
  if (code === "$BUTTON_EDIT") { return "Edit"}
  if (code === "$BUTTON_CREATE") { return "Create"}
  if (code === "$BUTTON_CLOSE") { return "Close"}
  if (code === "$BUTTON_SAVE_AND_CLOSE") { return "Save and Close"}
  return code;
}
// **** de
export function i18nLabel_de (code) {
  if (code === "$LIST") { return "Liste"}
  if (code === "$PLEASE_SELECT_IN_LEFT_HANDED_NAVIGATION") { return " ... bitte aus der links-seitigen Navigation wählen"}
  if (code === "$MODAL_DIALOG_YES") { return "Ja"}
  if (code === "$MODAL_DIALOG_NO") { return "Nein"}
  if (code === "$MODAL_DIALOG_DELETE_ENTRY_HEADER") { return "Löschen"}
  if (code === "$MODAL_DIALOG_DELETE_ENTRY_QUESTION") { return "Soll dieser Eintrag gelöscht werden ?"}
  if (code === "$BUTTON_EDIT") { return "Ändern"}
  if (code === "$BUTTON_CREATE") { return "Neuer Eintrag"}
  if (code === "$BUTTON_CLOSE") { return "Schliessen"}
  if (code === "$BUTTON_SAVE_AND_CLOSE") { return "Speichern und Schliessen"}
  return code;
}

