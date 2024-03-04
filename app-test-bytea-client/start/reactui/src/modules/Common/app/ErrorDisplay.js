import React, { Component } from "react";

class ErrorDisplay extends Component {
  constructor(props) {
    super(props);
  }

  render() {

    const { errorInfo } = this.props;

    return (
      <div className="thisApp_ERROR_Display">
        <h3>An Error Occured</h3>
        <p>ErrorCode {errorInfo.errorCode} : {errorInfo.errorText}</p>        
      </div>
    );
  }
}

export default ErrorDisplay;
