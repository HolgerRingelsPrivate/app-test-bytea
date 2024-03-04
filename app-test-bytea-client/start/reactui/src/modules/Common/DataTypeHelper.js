/********************************************************************************
/* Support for Variables of type codetable with cardinality > 1 
/* which are soemthing like a CeckBox Group
/********************************************************************************


/**
 * A Variable of type codetable with cardinality > 1 is something like a CeckBox Group
 * The correspnding state variable is a stateString holding the entries as 
 * comma separated list: [Code1],[Code2],[Code3]
 * 
 * This method toggles the occurance of a code by adding
 * or removing the code to/from the stateString
 * @param {*} stateString 
 * @param {*} code 
 * @returns 
 */
export function toggleCheckBox(stateString, code) {
    //if stateString is null or undefined, then use empty string as default
    let workString = stateString;
    if (stateString === null) {
        workString = '';
    }
    if (stateString === undefined) {
        workString = '';
    }

    let codeArray = [];
    if (workString !== '') {
        codeArray = workString.split(",");
    }

    let codeIndex = codeArray.indexOf(code);
    if (codeIndex === -1) { //toggle means: if code is not available, then add it
        const newValue = [code]; 
        codeArray = codeArray.concat(newValue);
    } else {  //toggle means: if code is available, then remove it
        let arrayBeforeCodeIndex = codeArray.slice(0, codeIndex);
        let arrayBeyondCodeIndex = codeArray.slice(codeIndex+1);
        codeArray = arrayBeforeCodeIndex.concat(arrayBeyondCodeIndex);
    }
    let codeArrayString = codeArray.toString();
    return codeArrayString;
}

/**
 * A Variable of type codetable with cardinality > 1 is something like a CeckBox Group
 * The correspnding state variable is a stateString holding the entries as 
 * comma separated list: [Code1],[Code2],[Code3]
 * 
 * This method delivers the default for a CheckBox as boolean value
 * 
 * If code is in stateString then the result is true
 * If code is NOT in stateString then the result is false
 * @param {*} stateString 
 * @param {*} code 
 * @returns 
 */
export function checkBoxSelected(stateString, code) {
    if (stateString === null) {
        return false;
    }
    if (stateString === undefined) {
        return false;
    }
    let codeArray = [];
    if (stateString !== '') {
        codeArray = stateString.split(",");
    }

    var arrayLength = codeArray.length;
    for (var i = 0; i < arrayLength; i++) {
        let entry = codeArray[i]; 
        if (entry === code) {
            return true;
        }
    }
    return false;

}


// #######################################################

//export function explodeStringToArray(stringWithDelimiter, delimiter) {
//    let codeArray = [];
//    if (stringWithDelimiter === null) {
//        return codeArray;
//    }
//    if (stringWithDelimiter === undefined) {
//        return codeArray;
//    }
//    if (stringWithDelimiter === '') {
//        return codeArray;
//    }
//    codeArray = stringWithDelimiter.split(delimiter);
//    return codeArray;
//}

