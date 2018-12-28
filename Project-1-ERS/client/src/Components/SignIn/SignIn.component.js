import React from 'react';
import ErsClient from '../../Axios/ErsClient';

//This is the manager component but I'm scared to change the name lol
export class SignInComponent extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      password: ''
    }
  }
  //update fields as user types
  passwordChange = (e) => {
    this.setState({
      ...this.state,
      password: e.target.value
    })
  }

  usernameChange = (e) => {
    this.setState({
      ...this.state,
      username: e.target.value
    })
  }
  //if success push history to stack
  submit = (e) => {
    e.preventDefault();
    let cred = this.state;
    ErsClient.post('users/login', cred)
      .then(res => {
        if (res.status === 200){
        this.props.history.push('/manager');
        }
      })
      .catch(err => {
       window.location.assign('127.0.0.1/404')
      })
  }

  render() {
    return (

      <div className="container" id="login-main">
        <div className="row">
          <div className="col-12 col-md-offset-3">
            <div className="panel panel-login">
              <div className="panel-heading">
                <div className="row">
                  <div className="col-xs-6">
                    <h6><strong>Manager Login to Revature ERS</strong></h6>
                  </div>
                </div>
                <hr />
              </div>
              <div className="panel-body">
                <div className="row">
                  <div className="col-12">
                    <form id="login-form"  onSubmit={this.submit}>

                      <div className="form-group ">
                        <input type="text" name="username" id="username" tabIndex="1" className="form-control" placeholder="Username" required 
                        onChange={this.usernameChange}/>
                      </div>

                      <div className="invalid-feedback">
                        Please enter a username.
                      </div>

                      <div className="form-group">
                        <input type="password" name="password" id="password" tabIndex="2" className="form-control" placeholder="Password" onChange={this.passwordChange} required />
                      </div>

                      <div className="form-group">
                        <div className="row">
                          <div className=" col-xl col-lg col-sm-6 col-sm-offset-3">
                            <input type="submit" name="login-submit" id="login-submit" tabIndex="4" className="form-control btn btn-login" />
                          </div>
                        </div>
                      </div>
                      <div className="form-group">
                        <div className="row">
                          <div className="col-lg-12">
                           
                          </div>
                        </div>
                      </div>
                    </form>


                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}