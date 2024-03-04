package restserver.controller.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import common.model.pojo.PojoErrorResult;
import common.model.pojo.PojoSuccessResult;
import common.service.app.exception.AppXception;
import common.service.app.exception.AppXceptionUtil;
import server.app.model.codetable.CT_AppXceptionRelevance;
import server.app.model.entity.ET_testb_EntityService;
import server.app.model.entity.ET_testb_Factory;
import server.app.model.entity.ET_testb_Pojo;

@RestController
public class CRUDL_Controller_testb {
	
	private static final String LOG_INFO_CREATE 	= "Creating";
	private static final String LOG_INFO_READ 		= "Reading";
	private static final String LOG_INFO_UPDATE 	= "Updating";
	private static final String LOG_INFO_DELETE 	= "Deleting";
	private static final String LOG_INFO_LIST 		= "Listing";
	private static final String LOG_DEBUG_OK 		= "OK";


	/*****************************************
	 * CREATE
	 *****************************************/
	@CrossOrigin
	@RequestMapping(method=RequestMethod.POST, value="/testb")
	public ResponseEntity<Object> create(
			@RequestHeader(value = "appHeaderMetaData", required = false) String metaData, 
			@RequestHeader(value = "appHeaderProcessId" , required = false) String processId, 
			@RequestBody ET_testb_Pojo pojo
		) {

		Logger logger = LoggerFactory.getLogger(CRUDL_Controller_testb.class);
		logger.info(LOG_INFO_CREATE);

		ET_testb_EntityService service = ET_testb_Factory.getService();

		try {
			service.create(metaData, processId, pojo, null);
			logger.debug(LOG_DEBUG_OK);
			
		} catch (AppXception appXe) {
			
			//The REST-Controller can modify the Payload ... e.g. LogLevel, Relevance (System or Background), etc.
			// ... (for the first approach do ONLY modify the Relevance
			appXe.getPayload().setRelevance(CT_AppXceptionRelevance.BACKGROUND);
			
			// AND store the Application Exception:
			AppXceptionUtil.saveAppXceptionToDatabase(appXe, null);
			
			//we got an exception with details about errorCode and errorText
			Exception e       =  appXe.getException(); //logged but not yet stored
			String errorCode  =  appXe.getPayload().getErrorCode();
			String errorText  =  appXe.getPayload().getErrorText();

			PojoErrorResult pojoErrorResult = new PojoErrorResult(errorText, errorCode, e);
			
			//we send a Pojo with error information 
			ResponseEntity<Object> responseEntity 
			= new ResponseEntity<Object>(pojoErrorResult, HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
			
		}
	
		ResponseEntity<Object> responseEntity 
		= new ResponseEntity<Object>(pojo, HttpStatus.OK);
	
		return responseEntity;

	}

	/*****************************************
	 * READ
	 *****************************************/
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET, value="/testb/{id}")
	public ResponseEntity<Object> read(
			@RequestHeader(value = "appHeaderMetaData", required = false) String metaData, 
			@RequestHeader(value = "appHeaderProcessId" , required = false) String processId, 
			@PathVariable String id
		) {
		
		Logger logger = LoggerFactory.getLogger(CRUDL_Controller_testb.class);
		logger.info(LOG_INFO_READ);

		ET_testb_EntityService service = ET_testb_Factory.getService();
		ET_testb_Pojo pojo = null;
		try {
			pojo = service.read(metaData, processId, id, null);
			logger.debug(LOG_DEBUG_OK);
			
		} catch (AppXception appXe) {
			
			//The REST-Controller can modify the Payload ... e.g. LogLevel, Relevance (System or Background), etc.
			// ... (for the first approach do ONLY modify the Relevance
			appXe.getPayload().setRelevance(CT_AppXceptionRelevance.BACKGROUND);
			
			// AND store the Application Exception:
			AppXceptionUtil.saveAppXceptionToDatabase(appXe, null);
			
			//we got an exception with details about errorCode and errorText
			Exception e       =  appXe.getException(); //logged but not yet stored
			String errorCode  =  appXe.getPayload().getErrorCode();
			String errorText  =  appXe.getPayload().getErrorText();
			
			PojoErrorResult pojoErrorResult = new PojoErrorResult(errorText, errorCode, e);
			
			//we send a Pojo with error information 
			ResponseEntity<Object> responseEntity 
			= new ResponseEntity<Object>(pojoErrorResult, HttpStatus.BAD_REQUEST);
			return responseEntity;
			
		}

		ResponseEntity<Object> responseEntity 
		= new ResponseEntity<Object>(pojo, HttpStatus.OK);
	
		return responseEntity;


	}

	/*****************************************
	 * UPDATE
	 *****************************************/
	@CrossOrigin
	@RequestMapping(method=RequestMethod.PUT, value="/testb/{id}")
	public ResponseEntity<Object> update(
			@RequestHeader(value = "appHeaderMetaData", required = false) String metaData, 
			@RequestHeader(value = "appHeaderProcessId" , required = false) String processId, 
			@RequestBody ET_testb_Pojo pojo, 
			@PathVariable String id
		) {
		
		Logger logger = LoggerFactory.getLogger(CRUDL_Controller_testb.class);
		logger.info(LOG_INFO_UPDATE);

		ET_testb_EntityService service = ET_testb_Factory.getService();
		try {
			pojo.set_id(id);
			service.update(metaData, processId, pojo, null);
			
			logger.debug(LOG_DEBUG_OK);
			
		} catch (AppXception appXe) {
			
			//The REST-Controller can modify the Payload ... e.g. LogLevel, Relevance (System or Background), etc.
			// ... (for the first approach do ONLY modify the Relevance
			appXe.getPayload().setRelevance(CT_AppXceptionRelevance.BACKGROUND);
			
			// AND store the Application Exception:
			AppXceptionUtil.saveAppXceptionToDatabase(appXe, null);
			
			//we got an exception with details about errorCode and errorText
			Exception e       =  appXe.getException(); //logged but not yet stored
			String errorCode  =  appXe.getPayload().getErrorCode();
			String errorText  =  appXe.getPayload().getErrorText();
			
			PojoErrorResult pojoErrorResult = new PojoErrorResult(errorText, errorCode, e);
			
			//we send a Pojo with error information 
			ResponseEntity<Object> responseEntity 
			= new ResponseEntity<Object>(pojoErrorResult, HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
			
			
		}
	
		ResponseEntity<Object> responseEntity 
		= new ResponseEntity<Object>(pojo, HttpStatus.OK);
	
		return responseEntity;

	}


	
	private static final String MSG_DELETED 		= "deleted";
	/*****************************************
	 * DELETE
	 *****************************************/
	@CrossOrigin
	@RequestMapping(method=RequestMethod.DELETE, value="/testb/{id}")
	public ResponseEntity<Object> delete(
			@RequestHeader(value = "appHeaderMetaData", required = false) String metaData, 
			@RequestHeader(value = "appHeaderProcessId" , required = false) String processId, 
			@PathVariable String id
		) {
		
		Logger logger = LoggerFactory.getLogger(CRUDL_Controller_testb.class);
		logger.info(LOG_INFO_DELETE);

		ET_testb_EntityService service = ET_testb_Factory.getService();

 		try {
			service.delete(metaData, processId, id, null);
			
			logger.debug(LOG_DEBUG_OK);
			
		} catch (AppXception appXe) {
			
			//The REST-Controller can modify the Payload ... e.g. LogLevel, Relevance (System or Background), etc.
			// ... (for the first approach do ONLY modify the Relevance
			appXe.getPayload().setRelevance(CT_AppXceptionRelevance.BACKGROUND);
			
			// AND store the Application Exception:
			AppXceptionUtil.saveAppXceptionToDatabase(appXe, null);
			
			//we got an exception with details about errorCode and errorText
			Exception e       =  appXe.getException(); //logged but not yet stored
			String errorCode  =  appXe.getPayload().getErrorCode();
			String errorText  =  appXe.getPayload().getErrorText();
			
			PojoErrorResult pojoErrorResult = new PojoErrorResult(errorText, errorCode, e);
			
			//we send a Pojo with error information 
			ResponseEntity<Object> responseEntity 
			= new ResponseEntity<Object>(pojoErrorResult, HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
			
			

		}
	
 		PojoSuccessResult pojo = new PojoSuccessResult();
 		pojo.setContext(id);
 		pojo.setMessage(MSG_DELETED);
		ResponseEntity<Object> responseEntity 
		= new ResponseEntity<Object>(pojo, HttpStatus.OK);
	
		return responseEntity;

	}

	/*****************************************
	 * LIST
	 *****************************************/
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET, value="/testb")
	public ResponseEntity<Object> list(
			@RequestHeader(value = "appHeaderMetaData", required = false) String metaData, 
			@RequestHeader(value = "appHeaderProcessId" , required = false) String processId,
			@RequestHeader(value = "appHeaderInclSoftDeleted" , required = false) String softDelSelection
		) {
		
		if (softDelSelection == null) {
		  softDelSelection = "n";
		}
		Logger logger = LoggerFactory.getLogger(CRUDL_Controller_testb.class);
		logger.info(LOG_INFO_LIST);
		
		ET_testb_EntityService service = ET_testb_Factory.getService();

		ET_testb_Pojo[] list = new ET_testb_Pojo[0];
 		try {
			list = service.list(metaData, processId, softDelSelection, null);
			
			logger.debug(LOG_DEBUG_OK);
			
		} catch (AppXception appXe) {
			
			//The REST-Controller can modify the Payload ... e.g. LogLevel, Relevance (System or Background), etc.
			// ... (for the first approach do ONLY modify the Relevance
			appXe.getPayload().setRelevance(CT_AppXceptionRelevance.BACKGROUND);
			
			// AND store the Application Exception:
			AppXceptionUtil.saveAppXceptionToDatabase(appXe, null);
			
			//we got an exception with details about errorCode and errorText
			Exception e       =  appXe.getException(); //logged but not yet stored
			String errorCode  =  appXe.getPayload().getErrorCode();
			String errorText  =  appXe.getPayload().getErrorText();
			
			PojoErrorResult pojoErrorResult = new PojoErrorResult(errorText, errorCode, e);
			
			//we send a Pojo with error information 
			ResponseEntity<Object> responseEntity 
			= new ResponseEntity<Object>(pojoErrorResult, HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
			
			

		}

		ET_testb_List_Pojo listPojo = new ET_testb_List_Pojo();
		listPojo.setList(list);
		
		ResponseEntity<Object> responseEntity 
		= new ResponseEntity<Object>(list, HttpStatus.OK);
	
		return responseEntity;

	}

	class ET_testb_List_Pojo {
		ET_testb_Pojo[] list;

		public ET_testb_Pojo[] getList() {
			return list;
		}

		public void setList(ET_testb_Pojo[] list) {
			this.list = list;
		}
		
		
	  }
	
}