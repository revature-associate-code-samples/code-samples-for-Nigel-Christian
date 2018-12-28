import React, { Component } from 'react';
import { Provider } from 'react-redux';
import { store } from './Redux/Store';

import  NavBar  from './Components/Navbar/NavBar.component';
import  MainContent  from './Components/MainContent.component';
import  AppFooter from './Components/Commons/AppFooter.component';

import './Css/App.css';
class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <>
          <NavBar />
          <MainContent />
				  <AppFooter />
        </>
      </Provider>
    );
  }
}

export default App;