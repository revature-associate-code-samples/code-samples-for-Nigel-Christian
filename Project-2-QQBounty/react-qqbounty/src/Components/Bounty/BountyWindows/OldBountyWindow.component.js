import React from 'react';
import { connect } from 'react-redux';
import BountyWindow from './BountyWindow.component';

export class OldBountyWindow extends React.Component {
  
	render() {
		return (
      <>
        <div id="old-bounty-window-wrapper" className="bounty-window-wrapper">
          <h1 className="bounty-window-title">Expire Soon</h1>
          <BountyWindow bounties={this.props.bounties} />
        </div>
      </>
		)
	}  
}

const mapStateToProps = (state) => {
  return {
    bounties: state.bounty.oldBounties.bounty_list.content
  }
}

const mapDispatchToProps = {
}

export default connect(mapStateToProps, mapDispatchToProps)(OldBountyWindow)