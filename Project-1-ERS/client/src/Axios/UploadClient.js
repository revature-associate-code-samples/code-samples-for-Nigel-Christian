import axios from 'axios';

const UploadClient = axios.create({ 
    baseURL:"https://gotcode.hopto.org/",
    headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    
    export default UploadClient;