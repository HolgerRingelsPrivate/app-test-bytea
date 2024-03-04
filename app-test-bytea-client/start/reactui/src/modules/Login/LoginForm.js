import React, { Component } from "react";
import { Button, FormGroup, Stack, TextInput, PasswordInput, Form } from "@carbon/react";
import { ArrowRight } from '@carbon/react/icons';


// Gneral Focus Hook
const utilizeFocus = () => {
	const ref = React.createRef()
	const setFocus = () => {ref.current &&  ref.current.focus()}

	return {setFocus, ref} 
}

class LoginForm extends Component {
  constructor(props) {
    super(props);
    this.inputFocus = utilizeFocus()
       // Create the state
       this.state = {
        // pageToDisplay: 0,
      };
  }

  componentDidMount(){
    this.inputFocus.setFocus()
  }

  handleKeyPress(e) {
    if (e.charCode === 13) { // Enter ??
      this.handleLogInEnter();
    }
  }

  render() {

    const {modalForm} = this.props;

    let indWarn = modalForm.loginFailed;

    return (
      <div className="login-position background-img fullscreen">
      <h1 className="padding-08">Log In</h1>
      <Form>
        <FormGroup
            legendId="form-group-1"
            style={{
                maxWidth: '400px'
            }}
        >
            {indWarn
            ?
            <Stack gap={7}>
                <TextInput
                id="user"
                labelText="Username"
                placeholder="username"
                onChange={this.handleLogInFormChange.bind(this)}
                onKeyPress={(e) => this.handleKeyPress(e)}
                warn={true}
                />
                <PasswordInput
                id="password"
                labelText="Password"
                placeholder="Password"
                onChange={this.handleLogInFormChange.bind(this)}
                onKeyPress={(e) => this.handleKeyPress(e)}
                warn={true}
                warnText = {'Combination of username/password is unknown'}
                />
                <Button ref={this.inputFocus.ref} onClick={this.handleLogInEnter.bind(this)} className="full-width"renderIcon={ArrowRight} iconDescription="Add" >Login</Button>
            </Stack>
            :
            <Stack gap={7}>
                <TextInput
                id="user"
                labelText="Username"
                placeholder="username"
                onChange={this.handleLogInFormChange.bind(this)}
                onKeyPress={(e) => this.handleKeyPress(e)}

                />
                
                <PasswordInput
                id="password"
                labelText="Password"
                placeholder="Password"
                onChange={this.handleLogInFormChange.bind(this)}
                onKeyPress={(e) => this.handleKeyPress(e)}

                />
                <Button ref={this.inputFocus.ref} onClick={this.handleLogInEnter.bind(this)} className="full-width"renderIcon={ArrowRight} iconDescription="Add" >Login</Button>
            </Stack>
            }

        </FormGroup>
      </Form>
  </div>
  );
  }
  
  
  /**
   * This method reacts on changes from Modal Dialog Fields
   * @param {*} event 
   */
    async handleLogInFormChange(event) {
      await this.props.handleLogInFormChange(event);
    }
  
    async handleLogInEnter(event) {
	  await this.props.handleLogInEnter(event);
	}

}



export default LoginForm;
