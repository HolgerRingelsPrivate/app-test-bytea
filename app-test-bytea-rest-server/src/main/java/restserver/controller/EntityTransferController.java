package restserver.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import common.folder.director.CommonFolderDirector;
import common.model.pojo.PojoErrorResult;
import common.model.pojo.PojoSuccessResult;
import common.service.ServiceFactory;
import common.service.ServiceLocator;
import common.service.app.exception.AppXception;
import common.service.config.IConfigService;
import common.service.file.IAppFileUtils;
import common.service.rel.db.ISqlConnectionService;
import server.app.model.codetable.CT_ErrorCodes;
import server.app.model.codetable.CT_SoftDeletion;
import server.app.model.entity.ET_testb_EntityService;
import server.app.model.entity.ET_testb_Factory;
import server.app.model.entity.ET_testb_Pojo;

@RestController
public class EntityTransferController {

	private static final String MSG_COMMON_DATA_EXPORT_STRING = "Common data export";
	private static final String MSG_COMMON_DATA_IMPORT_STRING = "Common data import";

	private static final String FILE_EXTENSION_JSON = ".json";

	private static final String MSG_DONE = "done";

	@RequestMapping(method=RequestMethod.POST, value="Common/data/export")
	public ResponseEntity<Object>  commonDataExport(			
			@RequestHeader(value = "appHeaderMetaData", required = false) String metaData, 
			@RequestHeader(value = "appHeaderProcessId" , required = false) String processId 
			) 
	{

		ServiceLocator serviceLocator = ServiceFactory.getInstance().getServiceLocator();
		IConfigService configService = serviceLocator.getConfigService();
		IAppFileUtils appFileUtils = serviceLocator.getAppFileUtils();
		Path dataTransferDirectoryPath = CommonFolderDirector.getDataTransferDirectoryPath(configService);

		ResponseEntity<Object> responseEntity = null;

		try {

				{
					ET_testb_EntityService service = ET_testb_Factory.getService();
					ET_testb_Pojo[] list = service.list(metaData, processId, CT_SoftDeletion.DELETED_AND_NOT_DELETED, null);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String json = gson.toJson(list);
					Path path = Paths.get(dataTransferDirectoryPath.toString(), ET_testb_Pojo.class.getSimpleName() + FILE_EXTENSION_JSON);
					appFileUtils.writeStringToPath(json, path, metaData, processId);
				}

			PojoSuccessResult pojoSuccessResult = new PojoSuccessResult();
			pojoSuccessResult.setContext(MSG_COMMON_DATA_EXPORT_STRING);
			pojoSuccessResult.setMessage(MSG_DONE);
			responseEntity = new ResponseEntity<Object>(pojoSuccessResult, HttpStatus.OK);
			

		} catch (AppXception e) {

			String errorText = "";
			String errorCode = "";
			Exception eResultException = null;
			PojoErrorResult pojoErrorResult = new PojoErrorResult(errorText, errorCode, eResultException);
			responseEntity = new ResponseEntity<Object>(pojoErrorResult, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		
		
		return responseEntity;

	}

	@RequestMapping(method=RequestMethod.POST, value="Common/data/import")
	public ResponseEntity<Object>  commonDataImport(			
			@RequestHeader(value = "appHeaderMetaData", required = false) String metaData, 
			@RequestHeader(value = "appHeaderProcessId" , required = false) String processId 
			) 
	{

		ServiceLocator serviceLocator = ServiceFactory.getInstance().getServiceLocator();
		IConfigService configService = serviceLocator.getConfigService();
		IAppFileUtils appFileUtils = serviceLocator.getAppFileUtils();
		ISqlConnectionService sqlConnectionService = serviceLocator.getSqlConnectionService();
		Path dataTransferDirectoryPath = CommonFolderDirector.getDataTransferDirectoryPath(configService);
		Connection con = null;

		ResponseEntity<Object> responseEntity = null;

		try {
			con = sqlConnectionService.getConnection();
		} catch (Exception e) {
			String errorCode = CT_ErrorCodes.SQL_DATABASE_ERROR;
			String errorText = CT_ErrorCodes.textForCode(errorCode);
			Exception eResultException = null;
			PojoErrorResult pojoErrorResult = new PojoErrorResult(errorText, errorCode, eResultException);
			responseEntity = new ResponseEntity<Object>(pojoErrorResult, HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

		boolean databaseIsEmpty = true;
		try {
			
				{
					ET_testb_EntityService service = ET_testb_Factory.getService();
					String[] listPrimaryKeys = service.listPrimaryKeys(metaData, processId, CT_SoftDeletion.DELETED_AND_NOT_DELETED, con);
					if (listPrimaryKeys.length != 0) {
						databaseIsEmpty = false;
					}
				}
			
		} catch (AppXception e) {
			String errorText = "";
			String errorCode = "";
			Exception eResultException = null;
			PojoErrorResult pojoErrorResult = new PojoErrorResult(errorText, errorCode, eResultException);
			responseEntity = new ResponseEntity<Object>(pojoErrorResult, HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		} finally {
			sqlConnectionService.cleanUpSQLObjects(con, null, null);			
		}
		
		if (databaseIsEmpty == false) {
			String errorCode = CT_ErrorCodes.DATABASE_IMPORT_BLOCKED_BY_EXISTING_DATA;
			String errorText = CT_ErrorCodes.textForCode(errorCode);
			Exception eResultException = null;
			PojoErrorResult pojoErrorResult = new PojoErrorResult(errorText, errorCode, eResultException);
			responseEntity = new ResponseEntity<Object>(pojoErrorResult, HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
		

		try {
			con = sqlConnectionService.getConnection();
		} catch (Exception e) {
			String errorCode = CT_ErrorCodes.SQL_DATABASE_ERROR;
			String errorText = CT_ErrorCodes.textForCode(errorCode);
			Exception eResultException = null;
			PojoErrorResult pojoErrorResult = new PojoErrorResult(errorText, errorCode, eResultException);
			responseEntity = new ResponseEntity<Object>(pojoErrorResult, HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
		
		try {

			
				{
					Gson gson = new Gson();
					Path path = Paths.get(dataTransferDirectoryPath.toString(), ET_testb_Pojo.class.getSimpleName() + FILE_EXTENSION_JSON);
					if (path.toFile().exists()) {
  					   String json = appFileUtils.readStringFromPath(path, metaData, processId);
					   ET_testb_Pojo[] et_list = gson.fromJson(json, ET_testb_Pojo[].class);
					   ET_testb_EntityService service = ET_testb_Factory.getService();
					   for (int i = 0; i < et_list.length; i++) {
						   ET_testb_Pojo et_testb_Pojo = et_list[i];
						   service.create(metaData, processId, et_testb_Pojo, con);
					   }
					}
				}


			PojoSuccessResult pojoSuccessResult = new PojoSuccessResult();
			pojoSuccessResult.setContext(MSG_COMMON_DATA_IMPORT_STRING);
			pojoSuccessResult.setMessage(MSG_DONE);
			responseEntity = new ResponseEntity<Object>(pojoSuccessResult, HttpStatus.OK);

		} catch (AppXception e) {

			String errorText = "";
			String errorCode = "";
			Exception eResultException = null;
			PojoErrorResult pojoErrorResult = new PojoErrorResult(errorText, errorCode, eResultException);
			responseEntity = new ResponseEntity<Object>(pojoErrorResult, HttpStatus.INTERNAL_SERVER_ERROR);
			
		} finally {
			sqlConnectionService.cleanUpSQLObjects(con, null, null);
			
		}
		
		return responseEntity;

	}
	

}