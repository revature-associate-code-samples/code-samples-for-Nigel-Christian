import React from 'react';
import ErsClient from '../../Axios/ErsClient';
import time from '../../Include/time';
import { FaSignOutAlt } from 'react-icons/fa';
import { ReimbursementComponent } from './Reimbursement.component';

//this is the view for the logged in user
export class UserComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userName: '',
      reimbs: [],
      noData: ''
      
    }
  }

  //watch out now! lexical this!
  componentDidMount() {
    //it doesn't matter what comes after that slash
    //user id is fetched server side
    ErsClient.get('users/active')
      .then((response) => {
        
        if (response.data[0].request.reimbId > 0) {
          this.setState({ 
            ...this.state,
            userName: response.data[0].username, //this is how i get user name for logged in user
            reimbs: response.data 
          })
         
        } else {
          
          this.setState({
            ...this.state,
            userName: response.data[0].username,
            noData: "Create Your First Reimbursement"  //first time users see this
          })
        }
  
      })
      //you did something bad like trying to navigate here without logging in
      .catch(err => {
        window.location.assign('127.0.0.1/404')
      })
  }

  logout = () => {
    //end session
    ErsClient.post('users/logout')
      .then(res => {
        if (res.status === 200) {
          window.location.assign('/home')
        }
      })
      .catch(err => {
        window.location.assign('127.0.0.1/404')
      })
  }

  render() {
    return (
      <div>
        
        <span id="logout"><FaSignOutAlt className='pointer' style={{ color: "grey" }} size={20} onClick={this.logout} />Logout</span><h4>Logged in as: {this.state.userName}</h4>
        <>
          <hr />
          {/* Inject the reimbursement form */}
          <ReimbursementComponent/>
          <h6>{this.state.noData}</h6>
          {/* checking that noData maintains initial state
          seems confusing but if the user has no reimbursements
          noData gets state set to a message saying to create a reimbursement */}
          {this.state.noData === '' &&
            this.state.reimbs.map((info, index) => (
              <div className="col col-12 col-md-12 col-lg-12 reimb-col">
                <div key={index} className="card reimb-card">
                  <ul className="list-group list-group-flush">
                    <li className="list-group-item"><strong>REVATURE ERS FORM v0.1B1810</strong></li>
                    <li className="list-group-item">First Name: {info.firstName.toUpperCase()}</li>
                    <li className="list-group-item flex-row-sb">Last Name: {info.lastName.toUpperCase()}</li>
                    <li className="list-group-item flex-row-sb">Email: {info.email.toUpperCase()}</li>
                    <li className="list-group-item flex-row-sb">Request #: {info.request.reimbId}</li>
                    <li className="list-group-item flex-row-sb">Amount $: {info.request.amount}</li>
                    <li className="list-group-item flex-row-sb">Submitted on: {time(info.request.submitted)}</li>
                    <li className="list-group-item flex-row-sb">Resolved on: {time(info.request.resolved)}</li>
                    <li className="list-group-item flex-row-sb">Description: {info.request.description.toUpperCase()}</li>
                    <li className="list-group-item flex-row-sb">Type: {info.request.typeString.toUpperCase()}</li>
                    <li className="list-group-item flex-row-sb">Status: {info.request.statusString.toUpperCase()}</li>
                    <li className="list-group-item flex-row-sb">

                    </li>
                  </ul>
                </div>

              </div>
            ))
          }
            
        </>
      </div>
    )
  }
}