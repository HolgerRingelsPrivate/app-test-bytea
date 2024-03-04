import * as AppConstants from "./../Common/AppConstants";
import * as Linking from "./../Common/Linking"
import { StatusCodes } from 'http-status-codes';


const CONSTANTS = {
	ENDPOINT : 'testb'
}
  


// ********************************************************************************
// * C R E A T E
// ********************************************************************************
export async function create(data, functionOnOK, functionOnError) {

	let restURL = await Linking.getRestServiceUrl() + CONSTANTS.ENDPOINT
	let response_FromServer = null;
	try {
		response_FromServer = await fetch(restURL, {
			method: 'POST',
			headers: { 
				'Content-Type': 'application/json',
				'Accept': 'application/json'
			},
			body: JSON.stringify(data)
		   });
	} catch (error) {
		let errorJson = AppConstants.CONSTANTS.ERROR_FETCH_FAIL;
		let errorInfo = JSON.parse(errorJson); 
		errorInfo.errorText = error.message;
		functionOnError(errorInfo);
		return;
	}
	   
	let httpStatus = retrieveHTTPStatus(response_FromServer)

	//handle HTTP Status
	if (httpStatus === StatusCodes.OK) {
		let data = await response_FromServer.json();
		functionOnOK(data);
		return;
	}  
	if (httpStatus === StatusCodes.BAD_REQUEST) {
		let errorInfo = await response_FromServer.json();
		functionOnError(errorInfo);
		return;
	}
	if (httpStatus === StatusCodes.INTERNAL_SERVER_ERROR) {
	 	let errorInfo = await response_FromServer.json();
		 functionOnError(errorInfo);
		 return;
    }

	let errorJson = AppConstants.CONSTANTS.ERROR_UNKNOWN;
	let errorInfo = JSON.parse(errorJson); 
	errorInfo.errorText = 'Caused by HTTP-Status ' + httpStatus;
	functionOnError(errorInfo)


}


// ********************************************************************************
// * R E A D
// ********************************************************************************
export async function read(id, functionOnOK, functionOnError) {

	let restURL = await Linking.getRestServiceUrl() + CONSTANTS.ENDPOINT + "/" + id
	
	let response_FromServer = null;
	try {
		response_FromServer = await fetch(restURL, {
			method: 'GET',
			headers: { 'Accept': 'application/json, text/plain' }
		   });
	} catch (error) {
		let errorJson = AppConstants.CONSTANTS.ERROR_FETCH_FAIL;
		let errorInfo = JSON.parse(errorJson); 
		errorInfo.errorText = error.message;
		functionOnError(errorInfo);
		return;
	}
	   
	let httpStatus = retrieveHTTPStatus(response_FromServer)

	//handle HTTP Status
	if (httpStatus === StatusCodes.OK) {
		let data = await response_FromServer.json();
		functionOnOK(data);
		return;
	}  
	if (httpStatus === StatusCodes.BAD_REQUEST) {
		let errorInfo = await response_FromServer.json();
		functionOnError(errorInfo);
		return;
	}
	if (httpStatus === StatusCodes.INTERNAL_SERVER_ERROR) {
	 	let errorInfo = await response_FromServer.json();
		 functionOnError(errorInfo);
		 return;
    }

	let errorJson = AppConstants.CONSTANTS.ERROR_UNKNOWN;
	let errorInfo = JSON.parse(errorJson); 
	errorInfo.errorText = 'Caused by HTTP-Status ' + httpStatus;
	functionOnError(errorInfo)


}

// ********************************************************************************
// * U P D A T E
// ********************************************************************************
export async function update(id, data, functionOnOK, functionOnError) {

	let restURL = await Linking.getRestServiceUrl() + CONSTANTS.ENDPOINT + "/" + id
	let response_FromServer = null;
	try {
		response_FromServer = await fetch(restURL, {
			method: 'PUT',
			headers: { 
				'Content-Type': 'application/json',
				'Accept': 'application/json'
			},
			body: JSON.stringify(data)
		   });
	} catch (error) {
		let errorJson = AppConstants.CONSTANTS.ERROR_FETCH_FAIL;
		let errorInfo = JSON.parse(errorJson); 
		errorInfo.errorText = error.message;
		functionOnError(errorInfo);
		return;
	}
	   
	let httpStatus = retrieveHTTPStatus(response_FromServer)

	//handle HTTP Status
	if (httpStatus === StatusCodes.OK) {
		let data = await response_FromServer.json();
		functionOnOK(data);
		return;
	}  
	if (httpStatus === StatusCodes.BAD_REQUEST) {
		let errorInfo = await response_FromServer.json();
		functionOnError(errorInfo);
		return;
	}
	if (httpStatus === StatusCodes.INTERNAL_SERVER_ERROR) {
	 	let errorInfo = await response_FromServer.json();
		 functionOnError(errorInfo);
		 return;
    }

	let errorJson = AppConstants.CONSTANTS.ERROR_UNKNOWN;
	let errorInfo = JSON.parse(errorJson); 
	errorInfo.errorText = 'Caused by HTTP-Status ' + httpStatus;
	functionOnError(errorInfo)


}


// ********************************************************************************
// * D E L E T E
// ********************************************************************************
export async function deleteEntity(id, functionOnOK, functionOnError) {

	let restURL = await Linking.getRestServiceUrl() + CONSTANTS.ENDPOINT +  "/" + id
	let response_FromServer = null;
	try {
		response_FromServer = await fetch(restURL, {
			method: 'DELETE',
			headers: { 
				'Content-Type': 'application/json',
				'Accept': 'application/json'
			}
		   });
	} catch (error) {
		let errorJson = AppConstants.CONSTANTS.ERROR_FETCH_FAIL;
		let errorInfo = JSON.parse(errorJson); 
		errorInfo.errorText = error.message;
		functionOnError(errorInfo);
		return;
	}
	   
	let httpStatus = retrieveHTTPStatus(response_FromServer)

	//handle HTTP Status
	if (httpStatus === StatusCodes.OK) {
		let data = await response_FromServer.json();
		functionOnOK(data);
		return;
	}  
	if (httpStatus === StatusCodes.BAD_REQUEST) {
		let errorInfo = await response_FromServer.json();
		functionOnError(errorInfo);
		return;
	}
	if (httpStatus === StatusCodes.INTERNAL_SERVER_ERROR) {
	 	let errorInfo = await response_FromServer.json();
		 functionOnError(errorInfo);
		 return;
    }

	let errorJson = AppConstants.CONSTANTS.ERROR_UNKNOWN;
	let errorInfo = JSON.parse(errorJson); 
	errorInfo.errorText = 'Caused by HTTP-Status ' + httpStatus;
	functionOnError(errorInfo)


}



// ********************************************************************************
// * L I S T
// ********************************************************************************
export async function list(functionOnOK, functionOnError) {

	let restURL = await Linking.getRestServiceUrl() + CONSTANTS.ENDPOINT;
	
	let response_FromServer = null;
	try {
		response_FromServer = await fetch(restURL, {
			method: 'GET',
			headers: { 'Accept': 'application/json, text/plain' }
		   });
	} catch (error) {
		let errorJson = AppConstants.CONSTANTS.ERROR_FETCH_FAIL;
		let errorInfo = JSON.parse(errorJson); 
		errorInfo.errorText = error.message;
		functionOnError(errorInfo);
		return;
	}
	   
	let httpStatus = retrieveHTTPStatus(response_FromServer)

	//handle HTTP Status
	if (httpStatus === StatusCodes.OK) {
		let data = await response_FromServer.json();
		functionOnOK(data);
		return;
	}  
	if (httpStatus === StatusCodes.BAD_REQUEST) {
		let errorInfo = await response_FromServer.json();
		functionOnError(errorInfo);
		return;
	}
	if (httpStatus === StatusCodes.INTERNAL_SERVER_ERROR) {
	 	let errorInfo = await response_FromServer.json();
		 functionOnError(errorInfo);
		 return;
    }

	let errorJson = AppConstants.CONSTANTS.ERROR_UNKNOWN;
	let errorInfo = JSON.parse(errorJson); 
	errorInfo.errorText = 'Caused by HTTP-Status ' + httpStatus;
	functionOnError(errorInfo)

}

function retrieveHTTPStatus(responseFromServer) {
	let httpStatus = StatusCodes.OK;
	if (!responseFromServer.ok) {
		httpStatus = responseFromServer.status;
	}
	return httpStatus;
}

