import React, { Component } from "react";


class MyConmponent extends Component {
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
        <p>
          Hello World MyComponent
        </p>
      </div>
    );
  }
}

export default MyConmponent;
