import React, { Component } from "react";

import ET_testb_List from "./ET_testb_List";
import ET_testb_Object from "./ET_testb_Object";

class ET_testb_Ui extends Component {

  constructor(props) {
    super(props);

       // Create the state
       this.state = {
		idOfObjectToShow : null,
		showMode:null 
      };
  }
  
  functionShowList() {
    this.setState({ 
		idOfObjectToShow : null,
		showMode:null 
    });
  }

  functionShowObject(id, mode) { //where mode can be 'C'reate, 'R'ead or 'U'pdate
    this.setState({ 
		idOfObjectToShow : id,
		showMode:mode
    });
  }


  render() {
	
    const { idOfObjectToShow, showMode} = this.state;
	let indShowList 	= (showMode === null);
	let indShowObject 	= (showMode !== null);
	
	if (indShowList) {

	    return (
	      <div>
	          <ET_testb_List
				  functionShowObject = {this.functionShowObject.bind(this)}
			  />
	      </div>
	    );
		
	}

	if (indShowObject) {

	    return (
	      <div>
	          <ET_testb_Object
				idOfObjectToShow = {idOfObjectToShow}
				showMode = {showMode}
			  	functionShowList = {this.functionShowList.bind(this)}
			  />
	      </div>
	    );
		
	}
	

  }
  
}

export default ET_testb_Ui;