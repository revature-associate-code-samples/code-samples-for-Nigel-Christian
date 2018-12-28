import React from 'react';
import ErsClient from '../../Axios/ErsClient';
import { FaSignOutAlt } from 'react-icons/fa';
import { ApprovalComponent } from './Approval.component';

export class ManagerComponent extends React.Component {
 constructor(props){
   super(props);
   this.state = {
     reimbs: [],
     reimbId: 0,
     statusId: 0
   }
 }
 //get data to display
 //watch out now! lexical this!
  componentDidMount(){
    ErsClient.get('users/')
      .then((response) => {
        console.log(`got ${response.data.length} reimbursements`)
        this.setState({
          ...this.state,
          reimbs: response.data 
        })
    })
      
    .catch(err => {
      window.location.assign('127.0.0.1/404')
     })
    
    }
  
    logout = () => {
      
      ErsClient.post('users/logout')
        .then(res => {
          if (res.status === 200){
            window.location.assign('/home')
          }
        })
        .catch(err => {
         window.location.assign('127.0.0.1/404')
          console.log(err);
        })
      }
      // teach me how to curry! we need this to make the approval button work
      //without the curry sauce all form id would cascade through
      approve = (rId) => (event) => {
        let statusId = 2;
        ErsClient.patch(`reimbs/${rId}/${statusId}`)
        .then(res => {
          if (res.status === 200){
              //fallback to reload
          }
        })
        .catch(err => {
         window.location.assign('127.0.0.1/404')
        })
        window.setTimeout(()=>{//just waiting for the server 
          window.location.reload()
     },6000)
    }

    deny = (rId) => (event) => {
      let statusId = 3;
      ErsClient.patch(`reimbs/${rId}/${statusId}`)
        .then(res => {
          if (res.status === 200){
              window.location.reload(true)
          }
        })
        .catch(err => {
         window.location.assign('127.0.0.1/404')
        })

        window.setTimeout(()=>{//just waiting for the server 
          window.location.reload()
     },6000)
  }
  render() {
    return (
        <div>
          <span id="logout"><FaSignOutAlt className='pointer' style={{color: "grey"}} size={20} onClick={this.logout}/>Logout</span>
          {this.state.reimbs.length > 0 &&
          <h4>Logged in as: Finance Manager</h4>
          }
    <>
    {/* map through state and pass as props, pass functions too! mind blown! */}
        <hr />
          {
            this.state.reimbs.map(info => 
              <ApprovalComponent 
                key={info.reimbId}
                rID={info.reimbId}
                info={info}
                approve={this.approve}
                deny={this.deny} />
            )
          }
    </>
        </div>
    )
  }
}

