import { bountyTypes } from "../../Redux/Actions/Bounty.actions";

const initialState = {
  newBounties:  {bounty_list:{
    content: []
  }},
  oldBounties:  {bounty_list:{
    content: []
  }},  
  popularBounties:  {bounty_list:{
    content: []
  }},
	highPayBounties: {bounty_list:{
    content: []
  }},
	searchBounties: {bounty_list:{
    content: []
  }},
  userBounties: {bounty_list:{
    content: []
  }},
  modalOpen: false,
  modalBountyId: null,
  modalBounty: null
}

export const bountyReducer = (state = initialState, action) => {
  switch (action.type) {
    case bountyTypes.GET_NEW_BOUNTIES:
        return {
        ...state,
        newBounties: action.payload.newBounties
		}
    case bountyTypes.GET_OLD_BOUNTIES:
        return {
        ...state,
        oldBounties: action.payload.oldBounties
		}
		case bountyTypes.GET_POPULAR_BOUNTIES:
      return {
        ...state,
        popularBounties: action.payload.popularBounties
		}
    case bountyTypes.GET_HIGH_PAY_BOUNTIES:
      return {
        ...state,
        highPayBounties: action.payload.highPayBounties
		}
    case bountyTypes.GET_SEARCH_BOUNTIES:
        return {
        ...state,
        searchBounties: action.payload.searchBounties
        }
    case bountyTypes.GET_USER_BOUNTIES:
        return {
        ...state,
        userBounties: action.payload.userBounties
        }
    case bountyTypes.OPEN_BOUNTY_MODAL:
      return {
        ...state,
        modalOpen:      action.payload.modalOpen,
        modalBountyId:  action.payload.modalBountyId,
        modalBounty:    action.payload.modalBounty
    }
    case bountyTypes.CLOSE_BOUNTY_MODAL:
      return {
        ...state,
        modalOpen:      action.payload.modalOpen,
        modalBountyId:  null,
        modalBounty:    null
    }
    case bountyTypes.ADD_TO_NEW_BOUNTIES:
    return {
        ...state
    }
    default:
      break;
  }

  return state;
}