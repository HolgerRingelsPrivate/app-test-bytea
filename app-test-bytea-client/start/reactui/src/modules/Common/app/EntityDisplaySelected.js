import React, { Component } from "react";
import ET_testb_Ui from "./../../Entities/ET_testb_Ui";

class EntityDisplaySelected extends Component {
  constructor(props) {
    super(props);

       // Create the state
       this.state = {
        // pageToDisplay: 0,
      };
  }

  render() {

    let {entityToDisplay} = this.props;

    if (entityToDisplay === 'testb_list') { 
      return (<div><ET_testb_Ui/></div>);
    }


    return (
      <div>
        <br/>
        <br/>
        <p>
        <br/>
        EntitySelectedDisplay : {entityToDisplay}
        </p>
      </div>
    );
  }
}

export default EntityDisplaySelected;
