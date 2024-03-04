import * as AppConstants from "../AppConstants";

export const CODE = {
  UNDEFINED: "",
  JS_NO_ERROR: "00000",
  JS_NO_ERROR_CODE: "00001",
  JS_DIRECTORY_NOT_AVAILABLE: "00002",
  JS_UNALLOWED_EMPTY_STRING: "00003",
  JS_FILE_UPLOAD_FOLFER_NOT_FOUND: "00004",
  JS_USER_UNKNOWN: "00005",
  JS_FILE_NOT_EXISTING: "00006",
  JS_MODULE_NOT_REACHIBLE: "00007",
  JS_SQL_DATABASE_ERROR: "00008",
  JS_SQL_ENTITY_UNKNOWN: "00009",
  JS_FILE_HANDLING_ISSUE: "00010",
  JS_DATABASE_IMPORT_BLOCKED_BY_EXISTING_DATA: "00011",
}


export function getTextForCode(code) {

  if (code === null)      		  {return CODE.UNDEFINED;}
  if (code === undefined) 		  {return CODE.UNDEFINED;}
  if (code === CODE.UNDEFINED) 	{return CODE.UNDEFINED;}

  let searchCode = code;
  let indexCommata = code.indexOf(','); //-1 means: single code
  if (indexCommata !== -1) {
    searchCode = searchCode.substring(0, indexCommata)
  } 

  let items =  getUiItems();
  let i = 0;
  while (i < items.length) {
    let item = items[i];
    if (item.code === searchCode) {
      if (indexCommata === -1) {
        return item.text;
      } else {
        return item.text + ", ...";
      } 
    } 
    i++;
  }  
  return CODE.UNDEFINED;
}  

 



export function getItemForCode(code) {

  if (code === null)      		{return null;}
  if (code === undefined) 		{return null;}

  let items =  getUiItems();
  let i = 0;
  while (i < items.length) {
    let item = items[i];
    if (item.code === code) {
    	return item;
    } 
    i++;
  }  
  return null;
}  


export function getCodeForText(text) {

  let items =  getUiItems();
  let i = 0;
  while (i < items.length) {
    let item = items[i];
    if (item.text === text) {
    	return item.code;
    } 
    i++;
  }  
  return "";
}  



export function getUiItems() {

  let uiLanguage = AppConstants.getUiLanguage();

  let result = null;

  //undefined => return english
  if (uiLanguage === null)  { 
    result = getUiItems_en();  
    return result;
  }

  //deliver language not-"en"-language: de
  if (uiLanguage === "de")  { 
    result = getUiItems_de (); 
    return result;
  }

  //no other language then "en"
  result = getUiItems_en(); 
  return result;
}  




// en
export function getUiItems_en () {
  const items = [
      {
        code: '00000',
        text: 'OK, No Error'
      }, 
      {
        code: '00001',
        text: 'No Error Details'
      }, 
      {
        code: '00002',
        text: 'Directory does not exist'
      }, 
      {
        code: '00003',
        text: 'Entry is not allowed to be an empty string'
      }, 
      {
        code: '00004',
        text: 'File Upload Folder not found'
      }, 
      {
        code: '00005',
        text: 'Unknown User'
      }, 
      {
        code: '00006',
        text: 'File does not exist'
      }, 
      {
        code: '00007',
        text: 'Module not reachible'
      }, 
      {
        code: '00008',
        text: 'SQL/Database Error'
      }, 
      {
        code: '00009',
        text: 'Entity does not exist (Unknown ID)'
      }, 
      {
        code: '00010',
        text: 'File Handling does not work properly'
      }, 
      {
        code: '00011',
        text: 'Data Import not possible (database is not empty)'
      }, 
      {
        code: '',
        text: ''
      }
    ];

	delete items[items.length-1];  
    return items

}



// de
export function getUiItems_de () {
  const items = [
      {
        code: '00000',
        text: 'OK kein Fehler aufgetreten'
      }, 
      {
        code: '00001',
        text: 'Keine Fehler Details'
      }, 
      {
        code: '00002',
        text: 'Verzeichnis existiert nicht'
      }, 
      {
        code: '00003',
        text: 'Die Eingabe darf kein Leerstring sein'
      }, 
      {
        code: '00004',
        text: 'Das Upload Verzeichnis ist nicht vorhanden'
      }, 
      {
        code: '00005',
        text: 'Benutzer unbekannt'
      }, 
      {
        code: '00006',
        text: 'Datei ist nicht vorhanden'
      }, 
      {
        code: '00007',
        text: 'Module ist nicht erreichbar'
      }, 
      {
        code: '00008',
        text: 'SQL/Datenbank-Fehler'
      }, 
      {
        code: '00009',
        text: 'Das Objekt existiert nicht (Unbekannte ID)'
      }, 
      {
        code: '00010',
        text: 'Datei Modifikation arbeitet nicht korrekt'
      }, 
      {
        code: '00011',
        text: 'Daten Import nicht möglich (Datenbank ist nicht leer)'
      }, 
      {
        code: '',
        text: ''
      }
    ];

	delete items[items.length-1];  
    return items

}




