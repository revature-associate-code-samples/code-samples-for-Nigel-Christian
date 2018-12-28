import { combineReducers }  from 'redux';
import { bountyReducer }   from './Bounty.reducer';
import { userReducer }      from './User.reducer';
import { snackbarReducer }      from './Snackbar.reducer';
import { storeReducer }      from './Store.reducer';

import { mainContentReducer }   from './MainContent.reducer';

export const state = combineReducers({
  user:         userReducer,
  snackbar:     snackbarReducer,
  store:        storeReducer,
  bounty:       bountyReducer,
  mainContent:  mainContentReducer
})