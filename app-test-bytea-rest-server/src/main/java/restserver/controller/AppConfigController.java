package restserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import common.model.pojo.PojoAppInfo;
import common.model.rest.controller.ModelAppConfigController;

@RestController
public class AppConfigController extends ModelAppConfigController{

	@RequestMapping(method=RequestMethod.GET, value="/Common/config")
	public ResponseEntity<Object>  deliverAppConfig() {
		
		PojoAppInfo pojoAppInfo = this.getPojoAppInfo(); 
		
		ResponseEntity<Object> responseEntity 
		= new ResponseEntity<Object>(pojoAppInfo, HttpStatus.OK);
		
		return responseEntity;

	}
	

}
