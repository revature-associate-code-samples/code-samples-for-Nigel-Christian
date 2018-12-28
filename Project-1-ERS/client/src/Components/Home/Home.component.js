import React from 'react';

import HomeImg from '../../Assets/home-img.png';

export class HomeComponent extends React.Component {
  render() {
    return (
      <div className="Container">
          <h1 ><strong>Revature ERS</strong></h1>
          <h6 ><strong>Employee Reimbursement System</strong></h6>
        
        
          <img id="home-image" src={HomeImg} alt="Network, Connections, Communication, Digital, Internet"/>
         
  
        </div>
        
    )
  }
}

