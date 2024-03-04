import React from 'react';
import { useState } from 'react';

import {
  Button,
  Header,
  HeaderContainer,
  HeaderName,
  HeaderNavigation,
  HeaderMenuButton,
  HeaderMenuItem,
  HeaderGlobalBar,
  HeaderGlobalAction,
  SkipToContent,
  ComposedModal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  UnorderedList,
  ListItem, Accordion,
  Loading,
  AccordionItem,
  HeaderMenu
} from '@carbon/react';

import { User, Help, Information, Logout } from '@carbon/react/icons';

import * as AppConstants from "./../../modules/Common/AppConstants";
import { Theme } from '@carbon/react';

async function reset() {
  AppConstants.resetSessionVariablesToBaseSet(true);
  window.location.replace('/');
}

const ApplicationHeader = (props) => {

  const [helpModalOpen, setHelpModalOpen] = useState(false)
  const [infoModalOpen, setInfoModalOpen] = useState(false)
  const [userModelOpen, setUserModalOpen] = useState(false)


  let uiParams = props.uiParams;
  if (uiParams === null) {
    return (
      <div>
        <Loading />
      </div>
    );
  }

  let indShowElementsForLoggedInUser = false;
  let loggedInUser = sessionStorage.getItem(AppConstants.CONSTANTS.SESSION_STORAGE_KEY_LOGGED_IN_USER_NAME);
  if (loggedInUser !== null) {
    indShowElementsForLoggedInUser = true;
  }


  return <HeaderContainer
    render={({ isSideNavExpanded, onClickSideNavExpand }) => (
      <Header aria-label="Media">
        <SkipToContent />
        {/*
        <HeaderMenuButton
          aria-label="Open menu"
          onClick={onClickSideNavExpand}
          isActive={isSideNavExpanded}
        />
*/}
        <HeaderName prefix="">
          Media DB
        </HeaderName>
        <HeaderNavigation aria-label="Header Navigation 1">
          {/*
        <HeaderMenuItem element={Link} to="/login">
            Login
        </HeaderMenuItem>
        <HeaderMenuItem element={Link} to="/drop">
            Drag&Drop
        </HeaderMenuItem>
        <HeaderMenuItem element={Link} to="/modal">
            Modals
        </HeaderMenuItem>
        <HeaderMenuItem element={Link} to="/process">
            Process Bar
        </HeaderMenuItem>
        <HeaderMenuItem element={Link} to="/process-warning">
            Process Bar Warning
        </HeaderMenuItem>
*/}
        </HeaderNavigation>
        <HeaderGlobalBar>
          <HeaderNavigation aria-label="Carbon Tutorial">
            {/*
        <HeaderMenu aria-label="EN" menuLinkName="EN">
            <HeaderMenuItem href="#">DE</HeaderMenuItem>
            <HeaderMenuItem href="#">EN</HeaderMenuItem>
          </HeaderMenu>
*/}
          </HeaderNavigation>

          {indShowElementsForLoggedInUser
            ? <>
              <HeaderGlobalAction aria-label="Help" tooltipAlignment="start"
                  onClick={() =>
                    setHelpModalOpen(true)
                  }              
              >
                <Help
                  size={20}
                />
              </HeaderGlobalAction>
            </>
            : <></>
          }

          <ComposedModal open={helpModalOpen} onClose={() => setHelpModalOpen(false)}>

            <ModalHeader title="Media - Help" />
            <Theme theme="g10">
            <ModalBody >
              <div className="padding-l-16">
                <div>
                  <p className="margin-b-16">
                    Please find here some frequently asked questions. If you can't find an answer, feel free to reach out to <a href="mailto:someone@app.org">someone@app.org</a>
                    <Accordion align="start">
                      <AccordionItem title="I found an error / bug, how can I report it?">
                        t.b.d
                      </AccordionItem>
                      <AccordionItem title="Who can I contact for more information on this service?">
                        t.b.d.
                      </AccordionItem>
                    </Accordion>
                  </p>
                </div>
              </div>
            </ModalBody>
			</Theme>
            

          </ComposedModal>

          {indShowElementsForLoggedInUser
            ? <>
              <HeaderGlobalAction aria-label="Info" tooltipAlignment="start"
                  onClick={() =>
                    setInfoModalOpen(true)
                  }              
              >
                <Information
                  size={20}
                />
              </HeaderGlobalAction>
            </>
            : <></>}

          <ComposedModal open={infoModalOpen} onClose={() => setInfoModalOpen(false)}>
            <ModalHeader title="Media - Information" />
            <Theme theme="g10">

            <ModalBody >
              <div className="padding-l-16">
                <UnorderedList>
                  <ListItem>Media Mission: Make Personal Records to find as easy as possible</ListItem>
                  <ListItem>Version: 1.0</ListItem>
                </UnorderedList>

              </div>
            </ModalBody>
            </Theme>
          </ComposedModal>

          {
            indShowElementsForLoggedInUser
              ? <>
                <HeaderGlobalAction aria-label="User" tooltipAlignment="start"
                  onClick={() =>
                    setUserModalOpen(true)
                  }
                >
                  <User
                    size={20}
                  />
                </HeaderGlobalAction>
                <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
              </>
              : <></>
          }

          <ComposedModal open={userModelOpen} onClose={() => setUserModalOpen(false)}>
            <ModalHeader title="Media - User" />
			<Theme theme="g10">

            <ModalBody >
              <div className="padding-l-16">

                <div>
                      <p className="margin-b-16">
                        Logged in User
                        <>&nbsp;&nbsp;:&nbsp;&nbsp;</>
                        {sessionStorage.getItem(AppConstants.CONSTANTS.SESSION_STORAGE_KEY_LOGGED_IN_USER_NAME)}
                      </p>
                      <hr size="1"></hr>
                </div>


              </div>
            </ModalBody>
            <ModalFooter>
              <Button
                          renderIcon={Logout}
                          iconDescription="Logout"
                          onClick={() =>
                            reset()
                          }
                        >
                          Log Out
              </Button>
            </ModalFooter>    
			</Theme>
          </ComposedModal>

        </HeaderGlobalBar>
      </Header>
    )}
  />
};

export default ApplicationHeader;
