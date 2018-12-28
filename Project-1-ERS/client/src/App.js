
import './Include/bootstrap';
import './App.css';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import AppNav from './Components/Nav/Nav.component';
import { HomeComponent } from './Components/Home/Home.component';
import { SignInComponent } from './Components/SignIn/SignIn.component';
import { UserSignInComponent } from './Components/SignIn/UserSignIn.component';
import { RegisterComponent } from './Components/Register/Register.component';
import { AboutComponent } from './Components/About/About.component';
import { FourOFourComponent }  from './Components/404/FourOFour.component'
import { ManagerComponent } from './Components/Manager/Manager.component';
import { UserComponent } from './Components/User/UserComponent.component';

class App extends Component {
  render() {
    return (
        <BrowserRouter >
          <>
            <AppNav />
            <div id="main-content-container">
              <Switch>
                <Route path="/home" component={HomeComponent} />
               
                <Route path="/manager-sign-in" component={SignInComponent} />
                <Route path="/employee-sign-in" component={UserSignInComponent} />
                <Route path="/register" component={RegisterComponent} />
                <Route path="/manager" component={ManagerComponent} />
                <Route path="/employee" component={UserComponent} />
                <Route path="/about" component={AboutComponent} />
                {/* default */}
                <Route  component={FourOFourComponent} />
              </Switch>
            </div>
          </>
        </BrowserRouter>
    );
  }
}

export default App;
