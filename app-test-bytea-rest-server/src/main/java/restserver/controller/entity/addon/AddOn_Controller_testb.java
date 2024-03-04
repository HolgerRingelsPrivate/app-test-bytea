package restserver.controller.entity.addon;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import common.service.ServiceFactory;
import common.service.ServiceLocator;
import common.service.app.exception.AppXception;
import common.service.rel.db.ISqlConnectionService;
import server.app.model.entity.ET_testb_EntityService;
import server.app.model.entity.ET_testb_Factory;
import server.app.model.entity.ET_testb_Pojo;
import utils.datatype.helper.DataTypeHelperBLOBTEXT;

@RestController
public class AddOn_Controller_testb {
	

	private static final String LOG_INFO_ADDON_One_Mirror 	= "Processing AddOn One Mirror";
	private static final String LOG_INFO_ADDON_One_Create 	= "Processing AddOn One Create";
	private static final String LOG_INFO_ADDON_One_Search 	= "Processing AddOn One Search";

	private static final String sEMPTY = "";



	/*****************************************
	 * SOMETHING
	 *****************************************/
	@CrossOrigin
	@RequestMapping(method=RequestMethod.PUT, value="/testb/addon/something")
	public ResponseEntity<Object> doSomething(
		) {
		
		ResponseEntity<Object> responseEntity 
		= new ResponseEntity<Object>("", HttpStatus.OK);
	
		return responseEntity;

	}

	/*****************************************
	 * ADDON One Field Mirror
	 *****************************************/
	@CrossOrigin
	@RequestMapping(method=RequestMethod.PUT, value="/testb/addon/one/fieldmirror")
	public ResponseEntity<Object> fieldMirrorTest() {

		Logger logger = LoggerFactory.getLogger(AddOn_Controller_testb.class);
		logger.info(LOG_INFO_ADDON_One_Mirror);

		
		try {
			
			// ****************************************
			//  Emulation: file-content arrived as a string
			// ****************************************
			Path pathInput = Paths.get("/Users/holgerringels/Downloads/test/Short.csv");
			byte[] data = Files.readAllBytes(pathInput);
			String sInput = new String(data);

			// ****************************************
			//  Create an Entry within Database (and get an ID for it)
			// ****************************************
			ET_testb_Pojo pojoCreate = new ET_testb_Pojo();
			pojoCreate.setField("");
			pojoCreate.setField2(sInput);
			pojoCreate.setField3(DataTypeHelperBLOBTEXT.convertStringToHealthyUnreadibleCharacters(sInput));
			ET_testb_EntityService service = ET_testb_Factory.getService();
			service.create(sEMPTY, sEMPTY, pojoCreate, null);
			String id = pojoCreate.get_id();

			// ****************************************
			//  Retrieve Entry from Database by ID
			// ****************************************
			ET_testb_Pojo pojoRead  = null;
			pojoRead = service.read(sEMPTY, sEMPTY, id, null);
			byte[] dataMirror  = pojoRead.getField2().getBytes();

			// ****************************************
			//  Check for identity of byte arrays
			// ****************************************
			boolean equals = Arrays.equals(data, dataMirror);
			System.out.println("Identical ByteArray: " + equals);

			// ****************************************
			//  Write result back to filesystem
			// ****************************************
			Path pathOutput = Paths.get("/Users/holgerringels/Downloads/test/Short_c.csv");
			Files.write(pathOutput, dataMirror);
			
		} catch (Exception e) {
			e.printStackTrace();
		} catch (AppXception e) {
			e.getException().printStackTrace();
		}
		
		
		ResponseEntity<Object> responseEntity 
		= new ResponseEntity<Object>("", HttpStatus.OK);
	
		return responseEntity;

	}


	/*****************************************
	 * ADDON One Field Create
	 *****************************************/
	@CrossOrigin
	@RequestMapping(method=RequestMethod.POST, value="/testb/addon/one/field")
	public ResponseEntity<Object> create() {

		Logger logger = LoggerFactory.getLogger(AddOn_Controller_testb.class);
		logger.info(LOG_INFO_ADDON_One_Create);

		String id = null;
		
		try {
			
			// ****************************************
			//  Emulation: file-content arrived as a string
			// ****************************************
			Path pathInput = Paths.get("/Users/holgerringels/Downloads/test/configs.data.converted.csv");
			byte[] data = Files.readAllBytes(pathInput);
			String sInput = new String(data);

			// ****************************************
			//  Create an Entry within Database (and get an ID for it)
			// ****************************************
			ET_testb_Pojo pojoCreate = new ET_testb_Pojo();
			pojoCreate.setField("");
			pojoCreate.setField2(sInput);
			pojoCreate.setField3(DataTypeHelperBLOBTEXT.convertStringToHealthyUnreadibleCharacters(sInput));
			ET_testb_EntityService service = ET_testb_Factory.getService();
			service.create(sEMPTY, sEMPTY, pojoCreate, null);
			id = pojoCreate.get_id();

			
		} catch (Exception e) {
			e.printStackTrace();
		} catch (AppXception e) {
			e.getException().printStackTrace();
		}

		ResponseEntity<Object> responseEntity 
		= new ResponseEntity<Object>(id, HttpStatus.OK);
	
		return responseEntity;

	}


	/*****************************************
	 * ADDON One Field Search
	 *****************************************/
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET, value="/testb/addon/one/field")
	public ResponseEntity<Object> search() {

		ServiceLocator sl = ServiceFactory.getInstance().getServiceLocator();
		ISqlConnectionService sqlConnectionService = sl.getSqlConnectionService();
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.info(LOG_INFO_ADDON_One_Search);

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			// ****************************************
			//  Emulation: use file-content as search criteria
			// ****************************************
			Path pathInput = Paths.get("/Users/holgerringels/Downloads/test/configs.data.converted.csv");
			byte[] data = Files.readAllBytes(pathInput);

			conn = sqlConnectionService.getConnection();
			
			String sqlSelect = "select * from testb where field = ?";
			ps = conn.prepareStatement(sqlSelect);
			ps.setBinaryStream(1, new ByteArrayInputStream(data));
			
			rs = ps.executeQuery();
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println(" Identified records (list of ids");
			System.out.println("--------------------------------------------------------------------------------");
			while (rs.next()) {
				String id = rs.getString(1);
				System.out.println(id);
			}
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlConnectionService.cleanUpSQLObjects(conn, ps, rs);
		}

		
	
		ResponseEntity<Object> responseEntity 
		= new ResponseEntity<Object>("", HttpStatus.OK);
	
		return responseEntity;

	}
	
	
}