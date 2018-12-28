import React from 'react';
import Slider from "react-slick";
import "slick-carousel/slick/slick.css"; 
import "slick-carousel/slick/slick-theme.css";

import BountyCard from './BountyCard.component';

import { connect } from 'react-redux';

const settings = {
  dots: false,
  infinite: false,
  speed: 500,
  slidesToShow: 5,
  slidesToScroll: 5,
  initialSlide: 0,
  swipeToSlide: true,
  responsive: [
    {
      breakpoint: 1024,
      settings: {
        slidesToShow: 3,
        slidesToScroll: 3,
        infinite: true
      }
    },
    {
      breakpoint: 600,
      settings: {
        slidesToShow: 2,
        slidesToScroll: 2,
        initialSlide: 2
      }
    },
    {
      breakpoint: 480,
      settings: {
        slidesToShow: 1,
        slidesToScroll: 1
      }
    }
  ]
};
export class BountyWindow extends React.Component {

  renderBountyCarousel = (pBounties) => {
    if(pBounties.length !== 0) {
      return  pBounties.map(bounty => {
                return <BountyCard key={bounty.bountyId} bounty={bounty} />
              })
    } else {
      return <div>No Bounties =(</div>;
    }
  }

  render() {
    let carouselSlides = this.renderBountyCarousel(this.props.bounties);
    return (
      <div className="bounty-carousel-wrapper">
        <Slider
          {...settings} 
        >
          {carouselSlides}
        </Slider>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    bounty: state.bounty
  }
}

const mapDispatchToProps = {
}

export default connect(mapStateToProps, mapDispatchToProps)(BountyWindow)