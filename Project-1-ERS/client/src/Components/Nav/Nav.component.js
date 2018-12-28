import React from 'react';
import { Link } from 'react-router-dom';
import RevLogo from '../../Assets/rev-logo.png';

// borrowing this nifty nav bar from Mr. Blake
export class AppNav extends React.PureComponent {
  render() {
    return (
      <div>
        <nav className="navbar navbar-toggleable-md navbar-expand-lg navbar-light bg-light display-front nav-pad">
          <div className="navbar-header c-pointer shift-left">
            <Link to="/home" className="unset-anchor">
              <img className="img-adjust-position rev-logo" src={RevLogo} alt="revature" />
            </Link>
            </div>
          
          
          <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>

          <div className="collapse navbar-collapse" id="navbarsExample04">
            <ul className="navbar-nav ml-auto margin-nav">
              <li className="nav-item active">
                <Link to="/home" className="unset-anchor nav-link">Home</Link>
              </li>
              
              <li className="nav-item active">
                <Link to="/manager-sign-in" className="unset-anchor nav-link">Manager Access</Link>
              </li>

              <li className="nav-item active">
                <Link to="/employee-sign-in" className="unset-anchor nav-link">Employee Access</Link>
              </li>

              <li className="nav-item active">
                <Link to="/register" className="unset-anchor nav-link">Register</Link>
              </li>

              <li className="nav-item active">
                <Link to="/about" className="unset-anchor nav-link">About</Link>
              </li>
            </ul>
          </div>
        </nav>
      </div >
    );
  }
}

export default AppNav ;