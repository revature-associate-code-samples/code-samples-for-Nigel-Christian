import React from 'react';
import time from '../../Include/time';
import { FaCheckCircle } from 'react-icons/fa';
import { FaRegTimesCircle } from 'react-icons/fa';

export class ApprovalComponent extends React.PureComponent {
  // pure like filtered water! all state is handled in ManagerComponent and passes values as props
  render() {
    return (
        <div className="col col-12 col-md-12 col-lg-12 reimb-col">
        <div className="card reimb-card">
        <ul className="list-group list-group-flush">
        <li className="list-group-item"><strong>REVATURE ERS FORM v0.1B1810</strong></li>
        <li  className="list-group-item">First Name: {this.props.info.firstName.toUpperCase()}</li>
        <li  className="list-group-item flex-row-sb">Last Name: {this.props.info.lastName.toUpperCase()}</li>
        <li  className="list-group-item flex-row-sb">Email: {this.props.info.email.toUpperCase()}</li>
        <li  className="list-group-item flex-row-sb">Request #: {this.props.info.request.reimbId}</li>
        <li  className="list-group-item flex-row-sb">Amount $: {this.props.info.request.amount}</li>
        <li  className="list-group-item flex-row-sb">Submitted on: {time(this.props.info.request.submitted)}</li>
        <li  className="list-group-item flex-row-sb">Resolved on: {time(this.props.info.request.resolved)}</li>
        <li  className="list-group-item flex-row-sb">Description: {this.props.info.request.description.toUpperCase()}</li>
        <li  className="list-group-item flex-row-sb">Type: {this.props.info.request.typeString.toUpperCase()}</li>
        <li  className="list-group-item flex-row-sb">Status: {this.props.info.request.statusString.toUpperCase()}</li>
        {/* display button on pending forms only! */}
        {this.props.info.request.statusString === 'pending' &&
        <li className="list-group-item flex-row-sb">
        <div id ="approval" container> 
        <FaCheckCircle className='pointer' style={{color: "green"}} size={50}
        onClick={this.props.approve(this.props.info.request.reimbId)}/>
        </div>
        <div>
        <FaRegTimesCircle className='pointer' style={{color: "red"}} size={50}
        onClick={this.props.deny(this.props.info.request.reimbId)}/>
        </div>
        </li>
        }
        </ul>
        </div>
        
  </div>
    )
  }
}
