package restserver;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import common.model.rest.server.ModelServerApp;
import common.service.config.AppLogoPrinter;


//Application must be able to find all interfaces, implementations and controllers
@ComponentScan(
		  basePackages = "common.service",
		  excludeFilters = {
				  @ComponentScan.Filter(type=FilterType.REGEX,pattern="common.service.rel.db.db2.*"),
				  @ComponentScan.Filter(type=FilterType.REGEX,pattern="common.service.rel.db.mysql.*")
		  }
		)
@ComponentScan(basePackages = "restserver.controller")
@ComponentScan(basePackages = "test.controller_ONLY_FOR_DEMO")

@SpringBootApplication
public class TestByteARestServer extends ModelServerApp {


	public static void main(String[] args) {

		AppLogoPrinter.main(new String[0]);

		//Waiting for Database Connection 
		boolean bContinue = waitForDatabaseConnection();
		if (!bContinue) {
			return;
		}

		//after this try catch block, the system is able to use Log4j
		try {
			checkOperatingSystem();
			grantAppDirectoryStructure();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Logger logger = LoggerFactory.getLogger(TestByteARestServer.class);

		
		try {
			handleDatabaseSchemaOnStartup();
		} catch (Exception e) {
			logger.error(null, e);
			return;
		}
		
	    SpringApplication app = new SpringApplication(TestByteARestServer.class);
	    
		boolean developmentMode = isDevelopmentMode();
	    if (developmentMode) {
	    	logger.info(MSG_DEVELOPMENT_MODE);
	    	app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
	    } else {
	    	logger.info(MSG_RUNTIME_MODE);
	    }
	    
	    app.run(args); 

	}

}
