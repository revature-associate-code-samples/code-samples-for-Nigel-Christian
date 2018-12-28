import React from 'react';
import { connect } from 'react-redux';
import BountyWindow from './BountyWindow.component';

export class NewBountyWindow extends React.Component {
  
	render() {
		return (
      <>
        <div id="new-bounty-window-wrapper" className="bounty-window-wrapper">
          <h1 className="bounty-window-title">New</h1>
          <BountyWindow bounties={this.props.bounties} />
        </div>
      </>
		)
	}  
}

const mapStateToProps = (state) => {
  return {
    bounties: state.bounty.newBounties.bounty_list.content
  }
}

const mapDispatchToProps = {
}

export default connect(mapStateToProps, mapDispatchToProps)(NewBountyWindow)