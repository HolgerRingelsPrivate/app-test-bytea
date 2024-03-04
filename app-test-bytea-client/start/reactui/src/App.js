import React, { Component } from 'react';
import './app.scss';
import { Content } from 'carbon-components-react';
import ApplicationHeader from './components/ApplicationHeader/ApplicationHeader';
import { Theme } from '@carbon/react';
import { Route, Switch } from 'react-router-dom';
import LandingPage from './content/LandingPage/LandingPage';

import * as AppConstants from "./modules/Common/AppConstants"
import * as UIParams from "./modules/Common/UIParams"

class App extends Component {

  constructor(props) {
    super(props);
    let today = new Date();
    var now = today.toLocaleString();

    this.state = {
     renderTimeStamp : now,
     uiParams : null,
     showAppInfo : false,
     showAppAbout : false
    };

  }

  async componentDidMount() {
    this.updateUiParams();
  }

  async updateUiParams() {

    let uiParamsAsString = UIParams.CONSTANTS.UI_PARAMS_AS_STRING;
    let uiParams = JSON.parse(uiParamsAsString);

    sessionStorage.setItem(AppConstants.CONSTANTS.SESSION_STORAGE_KEY_UI_PARAMETERS, uiParamsAsString);
    this.setState({uiParams : uiParams });
  }


  render() {

    const {showAppAbout, showAppInfo} = this.state;

    return (
      <>
        <Theme theme="g100">
          <ApplicationHeader 
              uiParams = {this.state.uiParams}
          />
        </Theme>     
        
        <Content>
          <Switch>
            <Route exact path="/" component={() => 
              <LandingPage 
                updateUi={this.updateUi.bind(this)}/> } 
            />
          </Switch>
        </Content>
      </>
    );
  }

  updateUi() {
    let today = new Date();
    var now = today.toLocaleString();
    this.setState({
      renderTimeStamp : now
    });

  }

}

export default App;