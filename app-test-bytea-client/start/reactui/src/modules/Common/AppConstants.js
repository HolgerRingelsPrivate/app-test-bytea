//if value are not yet set, then result is null
export const CONSTANTS = {

  //****************************************
  //Link to REST-Server
  //****************************************


  REST_SERVER_BASE_URL : 'http://localhost:8089/', 

  //****************************************
  //Standard Errors
  //****************************************

  ERROR_UNKNOWN : '{"errorText": "","e": null,"errorCode": "99999"}',
  ERROR_FETCH_FAIL : '{"errorText": "","e": null,"errorCode": "99998"}', //not even a http-status could be detected

  
  //****************************************
  //SessionStorageKEYS 
  //****************************************

  //LoggedIn User
  SESSION_STORAGE_KEY_LOGGED_IN_USER_NAME :"loggedInAppUser",

  //Ui-Parameters
  SESSION_STORAGE_KEY_UI_PARAMETERS :"uiParams",


}

/**
 * This method derives the ui language from uiParams (sessionStorage)
 * @returns "de" or "en" or null (if nout found)
 */
export function getUiLanguage() {
  let uiParams = sessionStorage.getItem(CONSTANTS.SESSION_STORAGE_KEY_UI_PARAMETERS);
  if (uiParams === null) {
    return null;
  }
  if (uiParams === undefined) {
    return null;
  }

  try {
    let objUiParams = JSON.parse(uiParams);
    let language = objUiParams.uiLanguage;
    return language;
  } catch (error) {
    return null;    
  }
}

export function resetSessionVariablesToBaseSet(removeLoggedInUser) {

  if (removeLoggedInUser === true) {
    sessionStorage.removeItem(CONSTANTS.SESSION_STORAGE_KEY_LOGGED_IN_USER_NAME);
  }

  //UI-paramenters SESSION_STORAGE_KEY_UI_PARAMETERS are NEVER removed

}

