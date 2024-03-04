import React, { Component } from "react";
import * as ET_testb_RestClient from './ET_testb_RestClient';
import ErrorDisplay from './../Common/app/ErrorDisplay';
import { Button } from "@carbon/react";
import { Grid, Column } from '@carbon/react';
import { Form, FormGroup, FormLabel, TextInput, Dropdown, Checkbox } from "@carbon/react";
import * as DataTypeHelper from './../Common/DataTypeHelper';
import * as Common_i18nLabels from './../Common/Common_i18nLabels';                  
import * as ET_testb_i18nLabels from './ET_testb_i18nLabels';                  

class ET_testb_Object extends Component {
 
  constructor(props) {
    super(props);
    this.state = {
      data : {
        field : '',
        field2 : '',
        field3 : '',
      }, 
      error : null,
      mode : props.showMode,
    };
  }

  componentDidMount() {
    const { idOfObjectToShow } = this.props;
    const { mode } = this.state;
    if (mode === 'R') {
      ET_testb_RestClient.read( idOfObjectToShow, this.functionOnOK.bind(this), this.functionOnError.bind(this));
    }
  }

  functionOnOK(data) {
    this.setState({ 
      data : data, error : null,
    });
  }

  functionOnError(error) {
    this.setState({
      error : error,
    });
  }

  functionCloseObject() {
    this.props.functionShowList();
  }

  functionSwitchToEditMode() {
    this.setState({
      mode : 'U',
    });
  }

  functionSaveObject() {
    if (this.state.mode === 'U') {
      let id = this.props.idOfObjectToShow;
      let data = this.state.data;
      ET_testb_RestClient.update( id, data, this.functionCloseObject.bind(this), this.functionOnError.bind(this));
    }
    if (this.state.mode === 'C') {
      let data = this.state.data;
      ET_testb_RestClient.create( data, this.functionCloseObject.bind(this), this.functionOnError.bind(this));
    }
  }

// ************************************************************************************************
// OnChange Function for DatatType 1 (Text), Cardinality 1 Changes
// ************************************************************************************************


  functionHandleTextFieldChange(event) {
    let fieldName = event.target.id.split("_")[3];
    let newValue  = event.target.value;
    this.setState({ data: 
      {...this.state.data, 
      [fieldName] : newValue,
      } 
    });
  }

// ************************************************************************************************
// OnChange Function for DatatType 2 (Code) Changes
// ************************************************************************************************

// ** ---------------------------------------------------------------------------------------------
//  Cardinality 2
// ** ---------------------------------------------------------------------------------------------

  functionHandleCheckboxToggle (fieldName, code) {
    const { mode } = this.state;
    if (mode === 'R') { return ;}
    let stateStringOld = this.state.data[fieldName];
    let stateStringNew = DataTypeHelper.toggleCheckBox(stateStringOld, code);
    this.setState({ data: 
      {...this.state.data, 
        [fieldName] : stateStringNew,
      } 
    });
    return;
  }

  
// ************************************************************************************************
// OnChange Function for DatatType 3 (Number), Cardinality 1 Changes
// ************************************************************************************************

  functionHandleNumberFieldChange(event) {
    let fieldName = event.target.id.split("_")[3];
    let newValue  = event.target.value;

    if (newValue !== '') { //empty is allowed

      //... and it MUST be only numbers
      var regExp = new RegExp("^\\d+$");
      var indValid = regExp.test(newValue);
      if (indValid === false)  {
        return;
      }
    }

    this.setState({ data: 
      {...this.state.data, 
        [fieldName] : Number(newValue),
      } 
    });
  }


// ************************************************************************************************
// OnChange Function for DatatType 4 (Boolean) Changes
// ************************************************************************************************

  functionHandleBooleanChange (fieldName) {
    let indCurrentValue = this.state.data[fieldName];
    let indNewValue= true;
    if (indCurrentValue === true ) {
      indNewValue= false;
    }

    this.setState({ data: 
      {...this.state.data, 
        [fieldName] : indNewValue,
      } 
    });
  return;
  }


// ************************************************************************************************
// OnChange Function for DatatType 17 (BlobText), Cardinality 1 Changes
// ************************************************************************************************


  functionHandleBlobTextFieldChange(event) {
    let fieldName = event.target.id.split("_")[3];
    let newValue  = event.target.value;
    this.setState({ data: 
      {...this.state.data, 
      [fieldName] : newValue,
      } 
    });
  }


  

// ************************************************************************************************
// * RENDER
// ************************************************************************************************

  render() {
    const { idOfObjectToShow } = this.props;
    
    const { mode } = this.state;
    const { data } = this.state;
    const { error } = this.state;
    
    let indErrorIndicated = (error !== null);

    let fRef = this.functionCloseObject.bind(this);
    let fRef2 = this.functionSwitchToEditMode.bind(this);
    let fRef3 = this.functionSaveObject.bind(this);



      return (
        <div>
            <h3>{ET_testb_i18nLabels  .get('$F1')}</h3>
            <br/>

                <Form>
                  <FormGroup>
                    <FormLabel>{ET_testb_i18nLabels  .get('$F1_field')}</FormLabel>
                    {mode === 'R'
                    ? <TextInput size = 'lg' readOnly   value={this.state.data.field}/>
                    : <TextInput size = 'lg'            value={this.state.data.field} 
                                  id = "ET_testb_Object_field" 
                                  onChange={this.functionHandleBlobTextFieldChange.bind(this)}/> 
                    }

                    <FormLabel>{ET_testb_i18nLabels  .get('$F1_field2')}</FormLabel>
                    {mode === 'R'
                    ? <TextInput size = 'lg' readOnly   value={this.state.data.field2}/>
                    : <TextInput size = 'lg'            value={this.state.data.field2} 
                                  id = "ET_testb_Object_field2" 
                                  onChange={this.functionHandleTextFieldChange.bind(this)}/> 
                    }
                    <FormLabel>{ET_testb_i18nLabels  .get('$F1_field3')}</FormLabel>
                    {mode === 'R'
                    ? <TextInput size = 'lg' readOnly   value={this.state.data.field3}/>
                    : <TextInput size = 'lg'            value={this.state.data.field3} 
                                  id = "ET_testb_Object_field3" 
                                  onChange={this.functionHandleTextFieldChange.bind(this)}/> 
                    }

                  </FormGroup>
                </Form>


          &nbsp;<br/>
          <Button kind="secondary" onClick={() => { fRef() }} size="sm">{Common_i18nLabels.get('$BUTTON_CLOSE')}</Button>

          {mode === 'R'
          ? <>
              &nbsp;&nbsp;
              <Button kind="primary" onClick={() => { fRef2() }} size="sm">{Common_i18nLabels.get('$BUTTON_EDIT')}</Button>
            </>
          : <>
              &nbsp;&nbsp;
              <Button kind="primary" onClick={() => { fRef3() }} size="sm">{Common_i18nLabels.get('$BUTTON_SAVE_AND_CLOSE')}</Button>
          </>
          }
          { indErrorIndicated === true
          	? <>
              <hr/>
              <ErrorDisplay 
                errorInfo={error} 
            /></>
          	:<></>
		      }
        </div>
      );
  }

}

export default ET_testb_Object;