import axios from 'axios';
import { environment } from './Environment';

const ErsClient = axios.create({ 
baseURL:`${environment.context}`,
headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: true
});

export default ErsClient;