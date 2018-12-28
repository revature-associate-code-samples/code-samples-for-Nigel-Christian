import React from 'react';
import { connect } from 'react-redux';
import BountyWindow from './BountyWindow.component';

export class HighPayBountyWindow extends React.Component {
  
	render() {
		return (
      <>
        <div id="high-pay-bounty-window-wrapper" className="bounty-window-wrapper">
          <h1 className="bounty-window-title">High Bounties</h1>
          <BountyWindow bounties={this.props.bounties} />
        </div>
      </>
		)
	}  
}

const mapStateToProps = (state) => {
  return {
    bounties: state.bounty.highPayBounties.bounty_list.content
  }
}

const mapDispatchToProps = {
}

export default connect(mapStateToProps, mapDispatchToProps)(HighPayBountyWindow)