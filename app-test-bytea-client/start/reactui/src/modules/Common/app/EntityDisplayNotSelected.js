import React, { Component } from "react";
import * as Common_i18nLabels from './../Common_i18nLabels';

class EntityDisplayNotSelected extends Component {
  constructor(props) {
    super(props);

       // Create the state
       this.state = {
        // pageToDisplay: 0,
      };
  }

  render() {
    return (
      <div>
        <br/>
        <br/>
        <br/>
        <br/>
        <p>
            &nbsp;&nbsp;{Common_i18nLabels.get("$PLEASE_SELECT_IN_LEFT_HANDED_NAVIGATION")}
        </p>
      </div>
    );
  }
}

export default EntityDisplayNotSelected;
