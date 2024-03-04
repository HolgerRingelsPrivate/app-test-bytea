import React, { Component } from "react";
import { Tabs, Tab, Theme, Loading } from '@carbon/react';

import * as AppConstants from "./../../modules/Common/AppConstants"
import * as UIParams        from "./../../modules/Common/UIParams"

import LoginForm from './../../modules/Login/LoginForm';
import EntityRoot from "./../../modules/Common/app/EntityRoot";




class LandingPage extends Component {

  constructor(props) {
    super(props);

    let loggedInUser = sessionStorage.getItem(AppConstants.CONSTANTS.SESSION_STORAGE_KEY_LOGGED_IN_USER_NAME);
    let defaultShowContentUi = false;
    let defaultShowLogInUi = true;
    if (loggedInUser !== null) {
      defaultShowContentUi = true;
      defaultShowLogInUi = false;
    }

    console.log('CONSTRUCTOR');
    this.state = {
      tenant: "", //initially the tenant is not selected
      case: "", //initially the case is not selected
      uiParams: null,

      showContentUi: defaultShowContentUi,
      showLogInUi: defaultShowLogInUi,
      modalForm: {
        user: null,
        password: null,
        loginFailed: false,
      },


    };
  }

  /**
 * This method reacts on changes from Modal Dialog Fields
 * @param {*} event 
 */
  handleLogInFormChange(event) {
    let fieldName = event.target.id;
    let fieldVal = event.target.value;
    console.log(fieldName + " : " + fieldVal);
    this.setState({ modalForm: { ...this.state.modalForm, [fieldName]: fieldVal } });
  }


  async handleLogInEnter(event) {
    let success = true;
    if (success === true) {
      sessionStorage.setItem(AppConstants.CONSTANTS.SESSION_STORAGE_KEY_LOGGED_IN_USER_NAME, this.state.modalForm.user);
      this.setState({ modalForm: { ...this.state.modalForm, user: null, password: null } });
      this.setState({ showContentUi: true, modalShowUi: false });
      this.props.updateUi();
    } else {
      this.setState({ modalForm: { ...this.state.modalForm, loginFailed: true } });
    }
  }

  async componentDidMount() {
    this.updateUiParams();
  }

  async updateUiParams() {
    let uiParamsAsString = UIParams.CONSTANTS.UI_PARAMS_AS_STRING;
    let uiParams = JSON.parse(uiParamsAsString);
    sessionStorage.setItem(AppConstants.CONSTANTS.SESSION_STORAGE_KEY_UI_PARAMETERS, uiParamsAsString);
    this.setState({ uiParams: uiParams });
  }

  render() {


    let windowLocationPath = window.location.pathname;
    console.log(windowLocationPath);

    const { uiParams, showContentUi, showLogInUi: modalShowUi, modalForm: modalForm } = this.state;
    if (uiParams === null) {
      return (
        <div className="App">
          <Loading />
        </div>
      );
    }

    if (modalShowUi) {
      return (
        <>
          <Theme theme="g10">
            <LoginForm
              handleLogInFormChange={this.handleLogInFormChange.bind(this)}
              handleLogInEnter={this.handleLogInEnter.bind(this)}
              indSMEModus={false}
              modalForm={modalForm}
            />
          </Theme>
        </>

      );
    }

    return (
        <Theme theme="g10">
          <div style={{ padding: "25px 15px 2000px 15px"}}>
            <EntityRoot />
          </div>
        </Theme>
    );


  }
}


export default LandingPage;
