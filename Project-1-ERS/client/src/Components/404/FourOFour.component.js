import React from 'react';
import  FourOFourImg from '../../Assets/atl.png';
import { Link } from 'react-router-dom';

//you done messed up now!
export class FourOFourComponent extends React.Component {
  render() {
    return (
      <div>
        <p>ERROR! PAGE NOT FOUND</p>
        <Link to="/home" className="unset-anchor">
          <img id="four-image" src={FourOFourImg} alt="404"/>
        </Link> 
      </div>
    )
  }
}