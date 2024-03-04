import React, { Component } from "react";
import EntityNavigation from "./EntityNavigation"

import EntityDisplayNotSelected from "./EntityDisplayNotSelected"
import EntityDisplaySelected from "./EntityDisplaySelected"

class EntityRoot extends Component {
  constructor(props) {
    super(props);

       // Create the state
       this.state = {
        entityToDisplay: null,
      };

  }

  functionDisplayEntity(entityToDisplay) { 
    this.setState({ 
      entityToDisplay : entityToDisplay
    });
  }
  

  render() {

    const {entityToDisplay} = this.state;
    let indNoEntitySelected = (entityToDisplay === null);

    return (
      <div>
      <table width="100%">
                <tr>
                  <td width="22%"  valign="top">
                    <EntityNavigation
                        functionDisplayEntity = {this.functionDisplayEntity.bind(this)}
                    />               
                  </td>
                  <td width="2%"  valign="top">
                    &nbsp;
                  </td>
                  <td width="76%" valign="top">
                    {indNoEntitySelected
                    ? 
                      <EntityDisplayNotSelected/>
                    : 
                      <EntityDisplaySelected
                        entityToDisplay = {entityToDisplay}
                      />
                    }
                    &nbsp;<br/>
                  </td>
                </tr>
          </table>      
        </div>
    );
  }
}

export default EntityRoot;
