import * as AppConstants from "../AppConstants";

export const CODE = {
  UNDEFINED: "",
  JS_TRACE: "TRACE",
  JS_DEBUG: "DEBUG",
  JS_INFO: "INFO",
  JS_WARN: "WARN",
  JS_ERROR: "ERROR",
  JS_FATAL: "FATAL",
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
        code: 'TRACE',
        text: 'Trace'
      }, 
      {
        code: 'DEBUG',
        text: 'Debug'
      }, 
      {
        code: 'INFO',
        text: 'Info'
      }, 
      {
        code: 'WARN',
        text: 'Warn'
      }, 
      {
        code: 'ERROR',
        text: 'Error'
      }, 
      {
        code: 'FATAL',
        text: 'Fatal'
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
        code: 'TRACE',
        text: 'Trace'
      }, 
      {
        code: 'DEBUG',
        text: 'Debug'
      }, 
      {
        code: 'INFO',
        text: 'Info'
      }, 
      {
        code: 'WARN',
        text: 'Warn'
      }, 
      {
        code: 'ERROR',
        text: 'Error'
      }, 
      {
        code: 'FATAL',
        text: 'Fatal'
      }, 
      {
        code: '',
        text: ''
      }
    ];

	delete items[items.length-1];  
    return items

}




