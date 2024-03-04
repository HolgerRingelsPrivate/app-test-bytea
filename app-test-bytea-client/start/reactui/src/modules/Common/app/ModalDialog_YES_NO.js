import React, { Component } from "react";
import { Modal } from "@carbon/react";
import * as Common_i18nLabels  from './../Common_i18nLabels';

class ModalDialog_YES_NO extends Component {
  constructor(props) {
    super(props);
  }


  async closeDialog(indYES) { // YES = true or false
    const { functionClose } = this.props;
    functionClose(indYES)
  }

  render() {

    const { modalHeader, question} = this.props;
    let indMultiLineQuestion = Array.isArray(question);
    if (indMultiLineQuestion === false) {
      return (
        <div>
            <Modal
              modalHeading={modalHeader} 
              open
              primaryButtonText="{Common_i18nLabels.get('$MODAL_DIALOG_YES')}"
              secondaryButtonText="{Common_i18nLabels.get('$MODAL_DIALOG_NO')}"
              onRequestSubmit={e => this.closeDialog(true)}
              onSecondarySubmit={e => this.closeDialog(false)}
            >
            <p>{question}</p>  
            </Modal> 
        </div>
      );
    } else {
      return (
        <div>
            <Modal
              modalHeading={modalHeader} 
              open
              primaryButtonText={Common_i18nLabels.get('$MODAL_DIALOG_YES')}
              secondaryButtonText={Common_i18nLabels.get('$MODAL_DIALOG_NO')}
              onRequestSubmit={e => this.closeDialog(true)}
              onSecondarySubmit={e => this.closeDialog(false)}
            >
            {question.map(function(lineOfQuestion) {
                if (lineOfQuestion === "")
                {
                  return (
                    <p>&nbsp;</p>  
                  )
                } else {
                  return (
                    <p>{lineOfQuestion}</p>  
                  )
                }
              })
            }
            </Modal> 
        </div>
      );
    }
  }
}

export default ModalDialog_YES_NO;
