import axios from 'axios';
import { environment } from './Environment';
let jwtToken = localStorage.getItem('JWT');

export const BountiesClient = axios.create({
  baseURL: environment.qqBountyContext+"bounties",
  headers: {
    'Authorization': jwtToken,
    'Content-Type': 'application/json'
  }
});

export const AnswersClient = axios.create({
  baseURL: environment.qqBountyContext + "answers",
  headers: { 
    'Content-Type': 'application/json'
  }
});

export const UsersClient = axios.create({
  baseURL: environment.qqBountyContext + "users",
  headers: {
    'Authorization': jwtToken,
    'Content-Type': 'application/json'
  }
});

export const AWSClientBlank = axios.create({
  baseURL: "",
  headers: {
    'Authorization': jwtToken,
    'Content-Type': 'application/json'
  }
});

export const LndClient = axios.create({
  baseURL: environment.lndContext,
  headers: {
    'Content-Type': 'application/json'
  }
});