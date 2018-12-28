import React from 'react';


export class AboutComponent extends React.Component {


  render() {
    return (
      // not using LINK here because it wouldn't let me nav to external webpage
        <div> 
          <a href="http://github.com/callmekurisu" target="_blank"
          rel="noopener noreferrer" >
          <img id="home-image" src="https://assets-cdn.github.com/images/modules/logos_page/Octocat.png" alt="Network, Connections, Communication, Digital, Internet"/></a>
        <h4>Developed with love by Nigel Christian</h4>
        </div>
        
    )
  }
}