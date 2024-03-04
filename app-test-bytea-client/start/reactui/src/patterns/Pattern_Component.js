import React, { Component } from "react";

import { ContentSwitcher, Switch} from "carbon-components-react";

class Pattern_Component extends Component {
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
        <p>
          Hello World
        </p>
      </div>
    );
  }
}

export default Pattern_Component;
