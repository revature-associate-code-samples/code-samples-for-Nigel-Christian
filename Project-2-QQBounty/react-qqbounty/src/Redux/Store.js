import { createStore, compose, applyMiddleware } from 'redux';
import reduxThunk from 'redux-thunk';
import { state } from './Reducers';

const composeEnhancers =  window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || window.__REDUX_DEVTOOLS_EXTENSION__ || compose;

const enhancer = composeEnhancers(
  applyMiddleware(reduxThunk)
)

export const store = createStore(
  state,
  enhancer
);