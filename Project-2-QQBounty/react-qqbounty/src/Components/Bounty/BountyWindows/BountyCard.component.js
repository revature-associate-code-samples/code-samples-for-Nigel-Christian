import React from 'react';
import gem from '../../../Assets/images/gem.png';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Chip from '@material-ui/core/Chip';
import { connect } from 'react-redux';
import * as bountyActions from '../../../Redux/Actions/Bounty.actions';

class BountyCard extends React.PureComponent {
 
  renderSubjectChips = () => {
    if(this.props.bounty.subject !== null) {
      return this.props.bounty.subject.map(subject => {
                return <Chip  key={subject.subject}
                              label={subject.subject} 
                              className="bounty-card-chip"
                              color="primary"
                              />
              })
    } else return null;
  }

  render() {
    let tChips = this.renderSubjectChips();
  
    return (
      <Card className="bounty-card" onClick={() => this.props.openModal(this.props.bounty)}>
        <CardContent className="bounty-card-content">
          <div className="bounty-card-main">
            <h6 className="bounty-card-title">
              {this.props.bounty.title}
              <hr className="my-2" />
            </h6>
            {tChips}
            <hr className="my-2" />
            <p className="bounty-card-description">
              {this.props.bounty.description}
            </p>
          </div>
        
          <div className="bounty-card-footer">
          <span className="margin-auto">
            {this.props.bounty.amount} 
            <img src={gem} className="display-initial margin-auto" width="20px" height="20px" alt="Reward: "/>
          </span>
          </div>
        </CardContent>
      </Card>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    
  }
}

const mapDispatchToProps = {
  openModal: bountyActions.openBountyModal
}

export default connect(mapStateToProps, mapDispatchToProps)(BountyCard)