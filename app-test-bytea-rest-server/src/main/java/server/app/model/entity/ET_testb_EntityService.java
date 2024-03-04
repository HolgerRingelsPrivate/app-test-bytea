package server.app.model.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;
import java.util.Vector;

import common.service.app.exception.AppXception;
import common.service.ServiceFactory;
import common.service.ServiceLocator;
import common.service.rel.db.ISqlConnectionService;

import server.app.base.StringConstants;
import server.app.model.codetable.CT_ErrorCodes;
import server.app.model.codetable.CT_LogLevel;
import server.app.model.codetable.CT_SoftDeletion;
import utils.datatype.helper.DataTypeHelperBLOBTEXT;
import server.app.model.codetable.CT_AppXceptionRelevance;

public class ET_testb_EntityService {

	private static final String STAR = "*";
	private static final String EMPTY = "";
	private static final String SPACE = " ";

    // ********************************************************************************
    // * SQL Statements:
    // ********************************************************************************
    
	private static final String SQL_create 	= "INSERT INTO testb (_id, field, field2, field3, _softdeleted, _lastmodified) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_read 	= "SELECT _id, field, field2, field3, _softdeleted, _lastmodified FROM testb WHERE _id = ?";
	private static final String SQL_list 	= "SELECT _id, field, field2, field3, _softdeleted, _lastmodified FROM testb WHERE _softdeleted='%1' order by _lastmodified desc";
	private static final String SQL_listall = "SELECT _id, field, field2, field3, _softdeleted, _lastmodified FROM testb order by _lastmodified desc";
	private static final String SQL_select 	= "SELECT _id, field, field2, field3, _softdeleted, _lastmodified FROM testb"; //Extended by Code
	private static final String SQL_update  = "UPDATE testb SET field=?, field2=?, field3=?, _softdeleted=?, _lastmodified=? WHERE _id = ?";
	private static final String SQL_delete 	= "UPDATE testb SET _softdeleted='y', _lastmodified=?";

	private static final String SQL_count 			= "SELECT count(*) from testb";
	private static final String SQL_groupcount_1	= "SELECT %1, count(%1) from testb";
	private static final String SQL_groupcount_2	= "group by %1 order by %1 asc";
	
	private static final String METHOD_NAME_create 	= "create";
	private static final String METHOD_NAME_read 	= "read";
	private static final String METHOD_NAME_update 	= "update";
	private static final String METHOD_NAME_list 	= "list";
	private static final String METHOD_NAME_select 	= "select";
	private static final String METHOD_NAME_delete 	= "delete";

	private static final String METHOD_NAME_count 			= "count";
	private static final String METHOD_NAME_group_count 	= "groupCount";
	
	private static final String METHOD_NAME_SUFFIX_PrimaryKeys 	= "PrimaryKeys";
	
	private static final String PARAM1 = "%1";
//	private static final String PARAM2 = "%2";
//	private static final String PARAM3 = "%3";
//	private static final String PARAM4 = "%4";

	private static final String MSG_NOT_FOUND = "Entity not found with ID :";

	/********************************************************************************
	 * C R E A T E
	 ********************************************************************************
	 *
	 * This method creates an Entity in Database based on Pojo. As a result handed over pojo is updated with new PrimaryKey (_id)
	 * @param metaData containing information about business context 
	 * @param processid id of process
	 * @param conInput (optional)
	 * @throws AppXception created, but not yet saved
	 */
	public void create (String metaData, String processid, ET_testb_Pojo pojo, Connection conInput) throws AppXception {

		if (pojo.get_softdeleted() == null) {
			pojo.set_softdeleted(CT_SoftDeletion.NOT_DELETED);
		}

		ServiceLocator sl = ServiceFactory.getInstance().getServiceLocator();
		ISqlConnectionService sqlConnectionService = sl.getSqlConnectionService();
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {

			//Use parameter SQL Connection (if there is one) or create a temporary one 
			if (conInput != null) {
				conn = conInput;
			} else {
				conn = sqlConnectionService.getConnection();
			}

			//this allow creation from UI and creation from data import
			long timeStamp = System.currentTimeMillis();
			String _id = pojo.get_id();
			if (_id == null) {
				UUID uuid = UUID.randomUUID();
				_id = uuid.toString();
			} else {
				timeStamp = pojo.get_lastmodified();
			}

			ps=conn.prepareStatement(SQL_create);

			int nIndex = 1;
			ps.setString (nIndex++,  _id);
			
			ps.setBinaryStream(nIndex++, new ByteArrayInputStream(pojo.getField().getBytes()), pojo.getField().getBytes().length);
			ps.setString (nIndex++, DataTypeHelperBLOBTEXT.convertStringToByteSequenceString(pojo.getField2()));
			ps.setString (nIndex++,pojo.getField3());
			
			ps.setString (nIndex++,pojo.get_softdeleted());
			ps.setLong 	 (nIndex++,timeStamp);


			ps.executeUpdate();
			
			pojo.set_id(_id);
			pojo.set_lastmodified(timeStamp);

		} catch (Exception e) {
			logger.error(StringConstants.EMPTY, e);
			String errorText = CT_ErrorCodes.textForCode(CT_ErrorCodes.SQL_DATABASE_ERROR);
			throw new AppXception(metaData, processid, getClass(), METHOD_NAME_create, e, StringConstants.EMPTY, CT_ErrorCodes.SQL_DATABASE_ERROR, errorText, null, CT_LogLevel.FATAL, CT_AppXceptionRelevance.BACKGROUND);
		} finally {
			//if there was no parameter SQL Connection, then release created SQL Connection
			if (conInput == null) {
				sqlConnectionService.cleanUpSQLObjects(conn, null, null);
			} 
			//always release Prepared Statement
			sqlConnectionService.cleanUpSQLObjects(null, ps, null);
		}

	}
	
	/********************************************************************************
	 * R E A D
	 ********************************************************************************
	 *
	 * This method reads an Entity based on a PrimaryKey (_id)
	 * @param metaData containing information about business context 
	 * @param processid id of process
	 * @param _id Primary Key
	 * @param conInput (optional)
	 * @return Pojo
	 * @throws AppXception created, but not yet saved
	 */
	public ET_testb_Pojo read (String metaData, String processid, String _id, Connection conInput) throws AppXception {
		
		ServiceLocator sl = ServiceFactory.getInstance().getServiceLocator();
		ISqlConnectionService sqlConnectionService = sl.getSqlConnectionService();
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;


		try {

			//Use parameter SQL Connection (if there is one) or create a temporary one 
			if (conInput != null) {
				conn = conInput;
			} else {
				conn = sqlConnectionService.getConnection();
			}
			
			ps = conn.prepareStatement(SQL_read);
			ps.setString(1, _id);
			rs =  ps.executeQuery();
			
			ET_testb_Pojo pojo = new ET_testb_Pojo();

			while (rs.next()) {

				int nIndex = 1;
				pojo.set_id(_id);
				nIndex++;
				
				pojo.setField (new String(rs.getBinaryStream(nIndex++).readAllBytes()));
				pojo.setField2 (DataTypeHelperBLOBTEXT.convertByteSequenceStringToString(rs.getString (nIndex++)));
				pojo.setField3 (rs.getString (nIndex++));
				pojo.set_softdeleted(rs.getString(nIndex++));
				pojo.set_lastmodified(rs.getLong(nIndex++));
				
			}

			if (pojo.get_id() == null) {
				logger.debug(MSG_NOT_FOUND + _id);
				String errorText = CT_ErrorCodes.textForCode(CT_ErrorCodes.SQL_ENTITY_UNKNOWN);
				throw new AppXception(metaData, processid, getClass(), METHOD_NAME_read, null, _id, CT_ErrorCodes.SQL_ENTITY_UNKNOWN, errorText, null, CT_LogLevel.WARN, CT_AppXceptionRelevance.BACKGROUND);			
			}
			
			return pojo;
			
		} catch (Exception e) {
			logger.error(StringConstants.EMPTY, e);
			String errorText = CT_ErrorCodes.textForCode(CT_ErrorCodes.SQL_DATABASE_ERROR);
			throw new AppXception(metaData, processid, getClass(), METHOD_NAME_read, e, StringConstants.EMPTY, CT_ErrorCodes.SQL_DATABASE_ERROR, errorText ,null, CT_LogLevel.FATAL, CT_AppXceptionRelevance.BACKGROUND);
		} finally {
			//if there was no parameter SQL Connection, then release created SQL Connection
			if (conInput == null) {
				sqlConnectionService.cleanUpSQLObjects(conn, null, null);
			} 
			//always release Prepared Statement and ResultSet
			sqlConnectionService.cleanUpSQLObjects(null, ps, rs);
		}
	}

	/********************************************************************************
	 * U P D A T E
	 ********************************************************************************
	 *
	 * This method updates an Entity in Database
	 * @param metaData containing information about business context 
	 * @param processid id of process
	 * @param pojo
	 * @param conInput (optional)
	 * @throws AppXception created, but not yet saved
	 */
	public void update (String metaData, String processid, ET_testb_Pojo pojo, Connection conInput) throws AppXception {

		ServiceLocator sl = ServiceFactory.getInstance().getServiceLocator();
		ISqlConnectionService sqlConnectionService = sl.getSqlConnectionService();
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {

			//Use parameter SQL Connection (if there is one) or create a temporary one 
			if (conInput != null) {
				conn = conInput;
			} else {
				conn = sqlConnectionService.getConnection();
			}

			long timeStamp = System.currentTimeMillis();

			ps=conn.prepareStatement(SQL_update);

			int nIndex = 1;
			ps.setBinaryStream(nIndex++, new ByteArrayInputStream(pojo.getField().getBytes()), pojo.getField().getBytes().length);
			ps.setString(nIndex++,pojo .getField2());
			ps.setString(nIndex++,pojo .getField3());
            ps.setString(nIndex++,pojo .get_softdeleted());
            ps.setLong  (nIndex++,timeStamp);
            

			ps.setString(nIndex++,  pojo	.get_id()); //WHERE

			ps.executeUpdate();
			
			pojo.set_lastmodified(timeStamp);
			
			
			return;

		} catch (Exception e) {
			logger.error(StringConstants.EMPTY, e);
			String errorText = CT_ErrorCodes.textForCode(CT_ErrorCodes.SQL_DATABASE_ERROR);
			throw new AppXception(metaData, processid, getClass(), METHOD_NAME_update, e, StringConstants.EMPTY, CT_ErrorCodes.SQL_DATABASE_ERROR, errorText, null, CT_LogLevel.FATAL, CT_AppXceptionRelevance.BACKGROUND);
		} finally {
			//if there was no parameter SQL Connection, then release created SQL Connection
			if (conInput == null) {
				sqlConnectionService.cleanUpSQLObjects(conn, null, null);
			} 
			//always release Prepared Statement
			sqlConnectionService.cleanUpSQLObjects(null, ps, null);
		}

	}


	private static final String SQL_WHERE_CLAUSE_DELETE_ALL = "WHERE _id IS NOT NULL";
	private static final String SQL_WHERE_CLAUSE_DELETE_BY_ID = "WHERE _id = '%1'";
	private static final String SQL_WHERE_CLAUSE_DELETE_BY_ID_PARAM_1 = "%1";
	/********************************************************************************
	 * D E L E T E
	 ********************************************************************************
	 *
	 * This method deletes Entities based on a select clause
	 * @param metaData containing information about business context 
	 * @param processid id of process
	 * @param _id Id of Entity to delete (or '*' to delete all)
	 * @param conInput  (optional)
	 * @throws AppXception created, but not yet saved
	 */
	public void delete (String metaData, String processid, String _id, Connection conInput) throws AppXception {

		ServiceLocator sl = ServiceFactory.getInstance().getServiceLocator();
		ISqlConnectionService sqlConnectionService = sl.getSqlConnectionService();
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {

			//Use parameter SQL Connection (if there is one) or create a temporary one 
			if (conInput != null) {
				conn = conInput;
			} else {
				conn = sqlConnectionService.getConnection();
			}

			String sqlDeleteString = SQL_delete; // "UPDATE testb SET _softdeleted='y', _lastmodified=?";
			String sql = EMPTY;
			if (STAR.equals(_id)) {
				sql = sqlDeleteString + SPACE + SQL_WHERE_CLAUSE_DELETE_ALL;
			} else {
				sql = sqlDeleteString + SPACE + SQL_WHERE_CLAUSE_DELETE_BY_ID;
				sql = sql.replace(SQL_WHERE_CLAUSE_DELETE_BY_ID_PARAM_1, _id);
			}
			ps=conn.prepareStatement(sql);
			ps.setLong(1, System.currentTimeMillis());

			ps.executeUpdate();
			
			return;

		} catch (Exception e) {
			logger.error(StringConstants.EMPTY, e);
			String errorText = CT_ErrorCodes.textForCode(CT_ErrorCodes.SQL_DATABASE_ERROR);			
			throw new AppXception(metaData, processid, getClass(), METHOD_NAME_delete, e, StringConstants.EMPTY, CT_ErrorCodes.SQL_DATABASE_ERROR, errorText, null, CT_LogLevel.FATAL, CT_AppXceptionRelevance.BACKGROUND);
		} finally {
			//if there was no parameter SQL Connection, then release created SQL Connection
			if (conInput == null) {
				sqlConnectionService.cleanUpSQLObjects(conn, null, null);
			} 
			//always release Prepared Statement
			sqlConnectionService.cleanUpSQLObjects(null, ps, null);
		}
	}
	
	/********************************************************************************
	 * L I S T
	 ********************************************************************************
	 *
	 * This method reads a fully blown list of all Entities
	 * @param metaData containing information about business context 
	 * @param processid id of process
	 * @param softDelSelection 'y', 'n' or 'yn' (both)
	 * @param conInput (optional)
	 * @return all entities in database as fully blown Pojos
	 * @throws AppXception created, but not yet saved
	 */
	public ET_testb_Pojo[] list (String metaData, String processid, String softDelSelection, Connection conInput) throws AppXception {
		
		ServiceLocator sl = ServiceFactory.getInstance().getServiceLocator();
		ISqlConnectionService sqlConnectionService = sl.getSqlConnectionService();
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;


		Vector<ET_testb_Pojo> vResult = new Vector<ET_testb_Pojo>();
		try {

			//Use parameter SQL Connection (if there is one) or create a temporary one 
			if (conInput != null) {
				conn = conInput;
			} else {
				conn = sqlConnectionService.getConnection();
			}
			
			if (CT_SoftDeletion.DELETED_AND_NOT_DELETED.equals(softDelSelection)) {
				ps = conn.prepareStatement(SQL_listall);
			} else {
				String sql = SQL_list;
				sql = sql.replace(PARAM1, softDelSelection);
				ps = conn.prepareStatement(sql);
			}
			
			rs =  ps.executeQuery();
			while (rs.next()) {

				ET_testb_Pojo pojo = new ET_testb_Pojo();

				int nIndex = 1;
				pojo.set_id			(rs.getString	(nIndex++));
				
				pojo.setField (new String(rs.getBinaryStream(nIndex++).readAllBytes()));
				pojo.setField2 (DataTypeHelperBLOBTEXT.convertByteSequenceStringToString(rs.getString (nIndex++)));
				pojo.setField3 (rs.getString (nIndex++));
				
				pojo.set_softdeleted(rs.getString (nIndex++));
				pojo.set_lastmodified(rs.getLong(nIndex++));

				vResult.add(pojo);
			}
			
			ET_testb_Pojo[] aryResult = vResult.toArray(new ET_testb_Pojo[0]);
			return aryResult;
			
		} catch (Exception e) {
			logger.error(StringConstants.EMPTY, e);
			String errorText = CT_ErrorCodes.textForCode(CT_ErrorCodes.SQL_DATABASE_ERROR);
			throw new AppXception(metaData, processid, getClass(), METHOD_NAME_list, e, StringConstants.EMPTY, CT_ErrorCodes.SQL_DATABASE_ERROR, errorText,  null, CT_LogLevel.FATAL, CT_AppXceptionRelevance.BACKGROUND);
		} finally {
			//if there was no parameter SQL Connection, then release created SQL Connection
			if (conInput == null) {
				sqlConnectionService.cleanUpSQLObjects(conn, null, null);
			} 
			//always release Prepared Statement and ResultSet
			sqlConnectionService.cleanUpSQLObjects(null, ps, rs);
		}
	}

	/********************************************************************************
	 * L I S T     P R I M A R Y     K E Y S
	 ********************************************************************************
	 *
	 * This method reads a list of PrimaryKeys (same method as list (but only PrimaryKeys are returned)
	 * @param metaData containing information about business context 
	 * @param processid id of process
	 * @param softDelSelection 'y', 'n' or 'yn' (both)
	 * @param conInput (optional)
	 * @return all entities in database as id-list
	 * @throws AppXception created, but not yet saved
	 */
	public String[] listPrimaryKeys (String metaData, String processid, String softDelSelection, Connection conInput) throws AppXception {
		
		ServiceLocator sl = ServiceFactory.getInstance().getServiceLocator();
		ISqlConnectionService sqlConnectionService = sl.getSqlConnectionService();
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;


		Vector<String> vResult = new Vector<String>();
		try {

			//Use parameter SQL Connection (if there is one) or create a temporary one 
			if (conInput != null) {
				conn = conInput;
			} else {
				conn = sqlConnectionService.getConnection();
			}
			
			if (CT_SoftDeletion.DELETED_AND_NOT_DELETED.equals(softDelSelection)) {
				ps = conn.prepareStatement(SQL_listall);
			} else {
				String sql = SQL_list;
				sql = sql.replace(PARAM1, softDelSelection);
				ps = conn.prepareStatement(sql);
			}
			
			rs =  ps.executeQuery();
			while (rs.next()) {

				String primaryKey	= rs.getString	(1);
				
				vResult.add(primaryKey);
			}
			
			String[] aryResult = vResult.toArray(new String[0]);
			return aryResult;
			
		} catch (Exception e) {
			logger.error(StringConstants.EMPTY, e);
			String errorText = CT_ErrorCodes.textForCode(CT_ErrorCodes.SQL_DATABASE_ERROR);
			throw new AppXception(metaData, processid, getClass(), METHOD_NAME_list + METHOD_NAME_SUFFIX_PrimaryKeys, e, StringConstants.EMPTY, CT_ErrorCodes.SQL_DATABASE_ERROR, errorText, null, CT_LogLevel.FATAL, CT_AppXceptionRelevance.BACKGROUND);
		} finally {
			//if there was no parameter SQL Connection, then release created SQL Connection
			if (conInput == null) {
				sqlConnectionService.cleanUpSQLObjects(conn, null, null);
			} 
			//always release Prepared Statement and ResultSet
			sqlConnectionService.cleanUpSQLObjects(null, ps, rs);
		}
	}
	
	
	/********************************************************************************
	 * S E L E C T     ( W H E R E . . . )
	 ********************************************************************************
	 *
	 * This method reads a fully blown list of selected Entities
	 * @param metaData containing information about business context 
	 * @param processid id of process
	 * @param conInput (optional)
	 * @param selectClause can contain WHERE- and ORDER BY clause
	 * @return selected entities as fully blown Pojos (ordered as identified in selectClause, if there is an ORDER-statement inside)
	 * @throws AppXception created, but not yet saved
	 */
	public ET_testb_Pojo[] select (String metaData, String processid, Connection conInput, String selectClause) throws AppXception {
		
		ServiceLocator sl = ServiceFactory.getInstance().getServiceLocator();
		ISqlConnectionService sqlConnectionService = sl.getSqlConnectionService();
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		Vector<ET_testb_Pojo> vResult = new Vector<ET_testb_Pojo>();
		try {

			//Use parameter SQL Connection (if there is one) or create a temporary one 
			if (conInput != null) {
				conn = conInput;
			} else {
				conn = sqlConnectionService.getConnection();
			}
			
			ps = conn.prepareStatement(SQL_select);
			String sSqlBase = ps.toString();
			String sqlComplete = sSqlBase + " " + selectClause;
			sqlConnectionService.cleanUpSQLObjects(null, ps, null);

			ps2 = conn.prepareStatement(sqlComplete);
			rs =  ps2.executeQuery();
			while (rs.next()) {

				ET_testb_Pojo pojo = new ET_testb_Pojo();
				
				int nIndex = 1;
				pojo.set_id			(rs.getString	(nIndex++));
				pojo.setField (new String(rs.getBinaryStream(nIndex++).readAllBytes()));
				pojo.setField2 (rs.getString (nIndex++));
				pojo.setField3 (rs.getString (nIndex++));

				vResult.add(pojo);
			}
			
			ET_testb_Pojo[] aryResult = vResult.toArray(new ET_testb_Pojo[0]);
			return aryResult;
			
		} catch (Exception e) {
			logger.error(StringConstants.EMPTY, e);
			String errorText = CT_ErrorCodes.textForCode(CT_ErrorCodes.SQL_DATABASE_ERROR);
			throw new AppXception(metaData, processid, getClass(), METHOD_NAME_select, e, StringConstants.EMPTY, CT_ErrorCodes.SQL_DATABASE_ERROR, errorText, null, CT_LogLevel.FATAL, CT_AppXceptionRelevance.BACKGROUND);
		} finally {
			//if there was no parameter SQL Connection, then release created SQL Connection
			if (conInput == null) {
				sqlConnectionService.cleanUpSQLObjects(conn, null, null);
			} 
			//always release Prepared Statement and ResultSet
			sqlConnectionService.cleanUpSQLObjects(null, ps2, rs);
		}
	}

	/********************************************************************************
	 * S E L E C T     P R I M A R Y     K E Y S      ( W H E R E . . . )
	 ********************************************************************************
	 *
	 * This method reads a list of PrimaryKeys (same method as select (but only PrimaryKeys are returned)
	 * @param metaData containing information about business context 
	 * @param processid id of process
	 * @param conInput (optional)
	 * @param selectClause can contain WHERE- and ORDER BY clause
	 * @return selected entities in database as id-list
	 * @throws AppXception created, but not yet saved
	 */
	public String[] selectPrimaryKeys (String metaData, String processid, Connection conInput, String selectClause) throws AppXception {
		
		ServiceLocator sl = ServiceFactory.getInstance().getServiceLocator();
		ISqlConnectionService sqlConnectionService = sl.getSqlConnectionService();
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		Vector<String> vResult = new Vector<String>();
		try {

			//Use parameter SQL Connection (if there is one) or create a temporary one 
			if (conInput != null) {
				conn = conInput;
			} else {
				conn = sqlConnectionService.getConnection();
			}
			
			ps = conn.prepareStatement(SQL_select);
			String sSqlBase = ps.toString();
			String sqlComplete = sSqlBase + " " + selectClause;
			sqlConnectionService.cleanUpSQLObjects(null, ps, null);

			ps2 = conn.prepareStatement(sqlComplete);
			rs =  ps2.executeQuery();
			while (rs.next()) {

				String _id = rs.getString(1);
				
				vResult.add(_id);
			}
			
			String[] aryResult = vResult.toArray(new String[0]);
			return aryResult;
			
		} catch (Exception e) {
			logger.error(StringConstants.EMPTY, e);
			String errorText = CT_ErrorCodes.textForCode(CT_ErrorCodes.SQL_DATABASE_ERROR);
			throw new AppXception(metaData, processid, getClass(), METHOD_NAME_select + METHOD_NAME_SUFFIX_PrimaryKeys, e, StringConstants.EMPTY, CT_ErrorCodes.SQL_DATABASE_ERROR, errorText, null, CT_LogLevel.FATAL, CT_AppXceptionRelevance.BACKGROUND);
		} finally {
			//if there was no parameter SQL Connection, then release created SQL Connection
			if (conInput == null) {
				sqlConnectionService.cleanUpSQLObjects(conn, null, null);
			} 
			//always release Prepared Statement and ResultSet
			sqlConnectionService.cleanUpSQLObjects(null, ps2, rs);
		}
	}
	
	/********************************************************************************
	 * C O U N T       ( W H E R E )
	 ********************************************************************************
	 *
	 * This method counts the entries selected by a where clause<br/>
	 * e.g.: select count(*) from appsys_errors where processid = '2581438d-2d98-4304-bc0e-d26fd3c40aa2' and critical = true
	 * @param metaData containing information about business context 
	 * @param processid id of process
	 * @param conInput Connection to database (optional)
	 * @param selectClause Example: where processid = '2581438d-2d98-4304-bc0e-d26fd3c40aa2' and critical = true
	 * @return the identified amount:  count (*)
	 * @throws AppXception created, but not yet saved
	 */
	public Long count (String metaData, String processid, Connection conInput, String selectClause) throws AppXception {

		Long result = null;
		
		ServiceLocator sl = ServiceFactory.getInstance().getServiceLocator();
		ISqlConnectionService sqlConnectionService = sl.getSqlConnectionService();
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
		
			//Use parameter SQL Connection (if there is one) or create a temporary one 
			if (conInput != null) {
				conn = conInput;
			} else {
				conn = sqlConnectionService.getConnection();
			}
			
			String sql = SQL_count + " " + selectClause;
			ps = conn.prepareStatement(sql);
			
			rs =  ps.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}
			
			return result;
			
		} catch (Exception e) {
			logger.error(StringConstants.EMPTY, e);
			String errorText = CT_ErrorCodes.textForCode(CT_ErrorCodes.SQL_DATABASE_ERROR);			
			throw new AppXception(metaData, processid, getClass(), METHOD_NAME_count, e, StringConstants.EMPTY, CT_ErrorCodes.SQL_DATABASE_ERROR, errorText, null, CT_LogLevel.FATAL, CT_AppXceptionRelevance.BACKGROUND);
		} finally {
			//if there was no parameter SQL Connection, then release created SQL Connection
			if (conInput == null) {
				sqlConnectionService.cleanUpSQLObjects(conn, null, null);
			} 
			//always release Prepared Statement and ResultSet
			sqlConnectionService.cleanUpSQLObjects(null, ps, rs);
		}

	}
	

	/********************************************************************************
	 * G R O U P       C O U N T       ( W H E R E )
	 ********************************************************************************
	 *
	 * This method identifies the amount of groups within a column and identifies the amout of occurences for each group
	 * e.g.: 'select critical, count(critical) from appsys_errors where processid = '2581438d-2d98-4304-bc0e-d26fd3c40aa2' and critical = true group by critical order by critical asc'
	 * @param metaData containing information about business context 
	 * @param processid id of process
	 * @param conInput Connection to database (optional)
	 * @param selectClause where clause to select - e.g.: where processid = '2581438d-2d98-4304-bc0e-d26fd3c40aa2' and critical = true
	 * @param column e.g. 'critical'
	 * @return a Hashtable of grouped column-values and the corresponding occurences
	 * @throws AppXception created, but not yet saved
	 */
	public Hashtable<Object, Long> groupCount(String metaData, String processid, Connection conInput, String selectClause, String column) throws AppXception {
		
		Hashtable<Object, Long> result = new Hashtable<Object, Long>(); 
		
		ServiceLocator sl = ServiceFactory.getInstance().getServiceLocator();
		ISqlConnectionService sqlConnectionService = sl.getSqlConnectionService();
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			//Use parameter SQL Connection (if there is one) or create a temporary one 
			if (conInput != null) {
				conn = conInput;
			} else {
				conn = sqlConnectionService.getConnection();
			}

			String sqlPrefix = SQL_groupcount_1.replace(PARAM1, column) + StringConstants.SPACE;
			String sqlPostfix = StringConstants.SPACE + SQL_groupcount_2.replace(PARAM1, column);
			String sql = sqlPrefix + selectClause + sqlPostfix;
			ps = conn.prepareStatement(sql);

			rs =  ps.executeQuery();
			while (rs.next()) {
				Object o = rs.getObject(1);
				Long l = rs.getLong(2);
				result.put(o, l);
			}

			return result;
			
		} catch (Exception e) {
			logger.error(StringConstants.EMPTY, e);
			String errorText = CT_ErrorCodes.textForCode(CT_ErrorCodes.SQL_DATABASE_ERROR);		
			throw new AppXception(metaData, processid, getClass(), METHOD_NAME_group_count, e, StringConstants.EMPTY, CT_ErrorCodes.SQL_DATABASE_ERROR, errorText, null, CT_LogLevel.FATAL, CT_AppXceptionRelevance.BACKGROUND);
		} finally {
			//if there was no parameter SQL Connection, then release created SQL Connection
			if (conInput == null) {
				sqlConnectionService.cleanUpSQLObjects(conn, null, null);
			} 
			//always release Prepared Statement and ResultSet
			sqlConnectionService.cleanUpSQLObjects(null, ps, rs);
		}


	}

	/********************************************************************************
	 * Internal Functions
	 ********************************************************************************/

	private String getTimeStampWithMetaData(long timeStamp, String metaData) {
		String metaDataString = metaData;
		if (metaDataString == null) {metaDataString = StringConstants.EMPTY;}
		Date date = new Date(timeStamp);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String formattedDate = formatter.format(date);
		String resultString = formattedDate + SPACE + metaDataString;
		return resultString;		
	}

	
}
