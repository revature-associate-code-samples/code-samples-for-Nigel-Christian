import axios from 'axios';
import { snackbarTypes } from './Snackbar.actions';

const SERVER_ADDRESS = process.env.REACT_APP_SERVER_ADDRESS;

export const bountyTypes = {
  GET_SEARCH_BOUNTIES: 'GET_SEARCH_BOUNTIES',
  GET_NEW_BOUNTIES: 'GET_NEW_BOUNTIES',
  GET_OLD_BOUNTIES: 'GET_OLD_BOUNTIES',
  GET_POPULAR_BOUNTIES: 'GET_POPULAR_BOUNTIES',
  GET_HIGH_PAY_BOUNTIES: 'GET_HIGH_PAY_BOUNTIES',
  GET_USER_BOUNTIES: 'GET_USER_BOUNTIES',
  POST_BOUNTY: 'POST_BOUNTY',
  OPEN_BOUNTY_MODAL: 'OPEN_BOUNTY_MODAL',
  CLOSE_BOUNTY_MODAL: 'CLOSE_BOUNTY_MODAL',
  ADD_TO_NEW_BOUNTIES: 'ADD_TO_NEW_BOUNTIES'
}



export const getNewBounties = () => (dispatch) => {

  axios.get(SERVER_ADDRESS + '/bounties/newest')
  .then(response => {
    dispatch({
      type: bountyTypes.GET_NEW_BOUNTIES,
      payload: {
        newBounties: response.data.result
      }
    });
  })
  .catch(error => {
  });
}

export const getOldBounties = () => (dispatch) => {
  axios.get(SERVER_ADDRESS + '/bounties/oldest')
  .then(response => {
    dispatch({
      type: bountyTypes.GET_OLD_BOUNTIES,
      payload: {
        oldBounties: response.data.result
      }
    });
  })
  .catch(error => {
  });
}

export const getPopularBounties = () => (dispatch) => {
  axios.get(SERVER_ADDRESS + '/bounties/popular')
  .then(response => {
    dispatch({
      type: bountyTypes.GET_POPULAR_BOUNTIES,
      payload: {
        popularBounties: response.data.result
      }
    });
  })
  .catch(error => {

  });
}

export const getHighPayBounties = () => (dispatch) => {
  axios.get(SERVER_ADDRESS + '/bounties/cost')
  .then(response => {
    dispatch({
      type: bountyTypes.GET_HIGH_PAY_BOUNTIES,
      payload: {
        highPayBounties: response.data.result
      }
    });
  })
  .catch(error => {

  });
}

export const getUserBounties = () => (dispatch) => {
  if (localStorage.getItem('JWT')) {
    let jwtToken = localStorage.getItem('JWT');
    axios.get(`${SERVER_ADDRESS}/bounties/user`,
    {
      headers: {
        'Authorization': `Bearer ${jwtToken}`
      }
    })
    .then((response) => {
      dispatch({
        type: bountyTypes.GET_USER_BOUNTIES,
        payload: {
          userBounties: response.data.result
        }
      })
    })
  }
}

export const getSearchBounties = (pSubjects) => (dispatch) => {
  console.log(pSubjects)
  let paramString = "";
  if (pSubjects.length !== 0) {
    paramString = "?"
    pSubjects.forEach((subject) => {
      paramString += "subjects=" + subject + "&"
    })
    paramString = paramString.substring(0, paramString.length - 1);
  }
  if (localStorage.getItem('JWT')) {
    let jwtToken = localStorage.getItem('JWT');
    axios.get(`${SERVER_ADDRESS}/bounties/subject${paramString}`,
    {
      headers: {
        'Authorization': `Bearer ${jwtToken}`
      }
    })
    .then((response) => {
      dispatch({
        type: bountyTypes.GET_SEARCH_BOUNTIES,
        payload: {
          searchBounties: response.data.result
        }
      })
    })
  }
}

export const submitBounty = (state) => (dispatch) => {
  console.log(state);
  if (localStorage.getItem('JWT')) {
    let jwtToken = localStorage.getItem('JWT');
    axios.defaults.headers.common['Authorization'] = 'Bearer ' + jwtToken;
    axios.post(SERVER_ADDRESS + '/bounties', state)
      .then(response => {

        setTimeout(()=>{
        axios.get(SERVER_ADDRESS + '/bounties/newest')
          .then(response => {
            dispatch({
              type: bountyTypes.GET_NEW_BOUNTIES,
              payload: {
                newBounties: response.data.result
              }
            });
          })
          .catch(error => {
          });

        axios.get(SERVER_ADDRESS + '/bounties/cost')
          .then(response => {
            dispatch({
              type: bountyTypes.GET_NEW_BOUNTIES,
              payload: {
                newBounties: response.data.result
              }
            });
          })
          .catch(error => {
          });
        },2000)
        dispatch({
          type: snackbarTypes.SNACKBAR_ADD,
          payload: {
            message: "Bounty Submitted"
          }
        });

      })
      .catch(error => {

      });
  }
}

export const openBountyModal = (pBounty) => (dispatch) => {
  dispatch({
    type: bountyTypes.OPEN_BOUNTY_MODAL,
    payload: {
      modalOpen: true,
      modalBountyId: pBounty.bountyId,
      modalBounty: pBounty
    }
  });
}

export const closeBountyModal = () => (dispatch) => {
  dispatch({
    type: bountyTypes.CLOSE_BOUNTY_MODAL,
    payload: {
      modalOpen: false
    }
  });
}