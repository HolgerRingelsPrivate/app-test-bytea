import React, { Component } from "react";

import { Accordion, AccordionItem} from '@carbon/react';
import { Button } from "@carbon/react";
import * as ET_testb_i18nLabels from './../../Entities/ET_testb_i18nLabels';
import * as Common_i18nLabels from './../Common_i18nLabels';

class EntityNavigation extends Component {
  constructor(props) {
    super(props);
  }

  functionChangeEntityToDislay(entityToDisplay) {
    this.props.functionDisplayEntity(entityToDisplay);
  }

  render() {

    let fRef1 = this.functionChangeEntityToDislay.bind(this);

    return (
      <div>
        <br/>
        <Accordion align="start">
          <AccordionItem title={ET_testb_i18nLabels  .get('$NV')}>
            <Button kind="ghost" onClick={() => { fRef1('testb_list') }} size="sm">{Common_i18nLabels.get("$LIST")}</Button>
          </AccordionItem>
        </Accordion>
      </div>
    );
  }
}

export default EntityNavigation;
