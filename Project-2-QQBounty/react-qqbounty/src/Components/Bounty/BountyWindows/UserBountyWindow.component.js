import React from 'react';
import { connect } from 'react-redux';
import BountyWindow from './BountyWindow.component';

export class UserBountyWindow extends React.Component {
  
	render() {
		return (
      <>
        <div id="new-bounty-window-wrapper" className="bounty-window-wrapper">
          <h1 id="new-bounty-window-title">{this.props.user.username}'s bounties</h1>
          <BountyWindow bounties={this.props.bounties} />
        </div>
      </>
		)
	}  
}

const mapStateToProps = (state) => {
  return {
    bounties: state.bounty.userBounties.bounty_list.content,
    user: state.user.user
  }
}

const mapDispatchToProps = {
  
}

export default connect(mapStateToProps, mapDispatchToProps)(UserBountyWindow)