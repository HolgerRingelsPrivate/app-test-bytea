import React, { Component } from "react";
import * as ET_testb_RestClient from './ET_testb_RestClient';
import ErrorDisplay from './../Common/app/ErrorDisplay';
import ModalDialog_YES_NO from './../Common/app/ModalDialog_YES_NO';

import { Button } from "@carbon/react";
import { Table, TableHead, TableRow, TableHeader, TableBody, TableCell } from "@carbon/react";
import { Loading } from "@carbon/react";
import { Tooltip} from "@carbon/react";
import { ViewFilled, TrashCan } from '@carbon/react/icons'; 
import * as Common_i18nLabels from './../Common/Common_i18nLabels';                  
import * as ET_testb_i18nLabels from './ET_testb_i18nLabels';                  
import * as ET_testb_Hook from './../EntitiesHooks/ET_testb_Hook';                  



class ET_testb_List extends Component {
 
  constructor(props) {
    super(props);
       this.state = {
        data : null, 
        error : null,

        decisionDialogModal: {
          active: false,
          object: null,
        }
        
      };
  }

  componentDidMount() {
    ET_testb_RestClient.list( this.functionOnOK.bind(this), this.functionOnError.bind(this));
  }

  functionOnOK(data) {
    this.setState({ 
      data : data, error : null,
    });
  }

  functionOnError(error) {
    this.setState({
      data : null, error : error,
    });
  }

  functionOpenObject(id, showMode) {
    this.props.functionShowObject(id, showMode);
  }

  functionDeleteObject(id) {
    ET_testb_RestClient.deleteEntity( id, this.componentDidMount.bind(this), this.functionOnError.bind(this))
  }


  async open_ModalDialog_Delete_YES_NO(object) {
      this.setState({ decisionDialogModal: {...this.state.decisionDialogModal, 
        active: true,
        object: object,
        } 
      }); 
    }

  async close_ModalDialog_Delete_YES_NO(indYES) {

    const {decisionDialogModal } = this.state;

    //Specifics to do before the dialog is closed
    let idToDelete = decisionDialogModal.object._id;

    //Standard Code supporting asyncProcessDialogsModal =====>
    this.setState({ decisionDialogModal: {...this.state.decisionDialogModal, 
      active: false,
      object: null,
    } 
    });
    //Specifics to do after the dialog is closed
    if (indYES) {
      ET_testb_RestClient.deleteEntity( idToDelete, this.componentDidMount.bind(this), this.functionOnError.bind(this))
    }
  }  

  render() {

    const { data, error, decisionDialogModal } = this.state;

    if (decisionDialogModal.active === true) {
    
      let question = ET_testb_Hook .questionInDeleteConfirm(decisionDialogModal.object);
       
      return (
        <div>
          <ModalDialog_YES_NO
            modalHeader = {Common_i18nLabels.get('$MODAL_DIALOG_DELETE_ENTRY_HEADER')}
            question = {question}
            functionClose = {this.close_ModalDialog_Delete_YES_NO.bind(this)}
          />
        </div>
      );
    }

    let indInitial = (data === null) && (error === null);
    let indDataAvailable = (data !== null) && (error === null);
    let indErrorIndicated = (data === null) && (error !== null);

    if (indInitial) {

      return (
        <div>
          <h3>{ET_testb_i18nLabels  .get('$L1')}</h3>
            <Loading/>
        </div>
      );
  
    }

    if (indDataAvailable) {

      let fRef = this.functionOpenObject.bind(this);
      let fRefD = this.functionDeleteObject.bind(this);
      let fRefMD = this.open_ModalDialog_Delete_YES_NO.bind(this);

      return (
        <div>
          <h3>{ET_testb_i18nLabels  .get('$L1')}</h3>
          &nbsp;<br/>
          <Button kind="primary" onClick={() => { fRef('', 'C') }} size="sm">{Common_i18nLabels.get('$BUTTON_CREATE')}</Button>
          &nbsp;<br/>
          &nbsp;<br/>
          <Table aria-label="sample table">
            <TableHead>
              <TableRow>
                <TableHeader>{ET_testb_i18nLabels  .get('$L1_field')}</TableHeader>
                <TableHeader>{ET_testb_i18nLabels  .get('$L1_field2')}</TableHeader>
                <TableHeader>{ET_testb_i18nLabels  .get('$L1_field3')}</TableHeader>
                <TableHeader>&nbsp;</TableHeader>
              </TableRow>
            </TableHead>
            <TableBody>

            {data.map(function(dataEntry) {
                return (
                  <TableRow key={dataEntry._id}>
                    <TableCell>{dataEntry.field}</TableCell>
                    <TableCell>{dataEntry.field2}</TableCell>
                    <TableCell>{dataEntry.field3}</TableCell>
                    <TableCell>
                    <TrashCan size={20}
                      onClick={() => { fRefMD(dataEntry) }}
                    />
                    &nbsp;&nbsp;&nbsp;
                    <ViewFilled size={20}
                        onClick={() => { fRef(dataEntry._id, 'R') }}
                    />
                    </TableCell>
                  </TableRow>
                )
              })
              }


            </TableBody>
          </Table> 

        </div>
      );

    }

    if (indErrorIndicated) {

      return (
        <div>
          <h3>List of testb</h3>

            <ErrorDisplay 
              errorInfo={error} 
            />

        </div>
      );
  
    }


  }

}

export default ET_testb_List;